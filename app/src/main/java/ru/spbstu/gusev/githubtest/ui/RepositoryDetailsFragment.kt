package ru.spbstu.gusev.githubtest.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_repository_details.*
import kotlinx.android.synthetic.main.network_error_try_again_button.*
import ru.spbstu.gusev.githubtest.R
import ru.spbstu.gusev.githubtest.data.ResultWrapper
import ru.spbstu.gusev.githubtest.extensions.snackbarError
import ru.spbstu.gusev.githubtest.model.CommitModel
import ru.spbstu.gusev.githubtest.model.GithubRepositoryModel
import ru.spbstu.gusev.githubtest.viewmodel.RepositoryDetailsViewModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "githubRepositoryModel"

class RepositoryDetailsFragment : BaseFragment() {
    private lateinit var githubRepositoryModel: GithubRepositoryModel

    override val layoutRes: Int
        get() = R.layout.fragment_repository_details
    override val progressBar: ProgressBar?
        get() = progress_bar
    private lateinit var viewModel: RepositoryDetailsViewModel

    companion object {
        @JvmStatic
        fun newInstance(githubRepositoryModel: GithubRepositoryModel): RepositoryDetailsFragment =
            RepositoryDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, githubRepositoryModel)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            githubRepositoryModel = it.getParcelable(ARG_PARAM1) ?: throw NullPointerException()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(
            RepositoryDetailsViewModel::class.java
        )
        subscribeForData()
        viewModel.fetchCommits(githubRepositoryModel.commits_url.removeSuffix("{/sha}"))
    }

    private fun updateUi(
        response: ResultWrapper<List<CommitModel>>
    ) {
        hideContent()
        when (response) {
            is ResultWrapper.Success -> {
                val commitsList = response.value
                if (commitsList.isNotEmpty()) {
                    val commit = commitsList.first()
                    mapDataToUi(commit)
                    showContent()
                } else {
                    snackbarError(getString(R.string.empty_list_error))
                }
            }
            is ResultWrapper.GenericError -> {
                snackbarError(response.error?.error_description ?: getString(R.string.error))
            }
            is ResultWrapper.NetworkError -> {
                snackbarError(getString(R.string.network_error))
            }
        }
    }

    private fun mapDataToUi(commit: CommitModel) {
        with(githubRepositoryModel) {
            Glide.with(this@RepositoryDetailsFragment).load(owner.avatar_url).into(avatar_img)
            repository_name_text.text = full_name
            user_login_text.text = owner.login

            val commitAuthor = commit.commit.author.name
            val commitAuthorSpanString = SpannableString(
                String.format(getString(R.string.authored), commitAuthor)
            )
            commitAuthorSpanString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                commitAuthor.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            commit_author_text.text = commitAuthorSpanString

            commit_date_text.text = commit.commit.author.date.formatDate()

            val messageTitle = getString(R.string.message)
            val messageSpanString =
                SpannableString(String.format(messageTitle, commit.commit.message))
            messageSpanString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                messageTitle.split(" ").first().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            commit_message_text.text = messageSpanString

            parents_sha_text.text = commit.parents.joinToString(";") { it.sha }
        }
    }

    private fun String.formatDate(): String {
        val date = this.split("T").first().split("-")
        return "${date[2]}.${date[1]}.${date[0]}"
    }

    private fun subscribeForData() {
        viewModel.commitsResponse.observe(viewLifecycleOwner, Observer {
            updateUi(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showProgress(it)
        })
    }

    private fun hideContent() {
        repository_details_content_layout.visibility = View.GONE
        network_error_try_again_include.visibility = View.VISIBLE
        try_again_button.setOnClickListener {
            viewModel.fetchCommits(githubRepositoryModel.commits_url.removeSuffix("{/sha}"))
        }
    }

    private fun showContent() {
        repository_details_content_layout.visibility = View.VISIBLE
        network_error_try_again_include.visibility = View.GONE
    }

}
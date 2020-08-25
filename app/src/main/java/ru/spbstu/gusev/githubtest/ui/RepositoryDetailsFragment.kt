package ru.spbstu.gusev.githubtest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ru.spbstu.gusev.githubtest.R
import ru.spbstu.gusev.githubtest.model.GithubRepositoryDetailsModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "repositoryDetailsModel"

class RepositoryDetailsFragment : BaseFragment() {
    private var repositoryDetailsModel: GithubRepositoryDetailsModel? = null
    override val layoutRes: Int
        get() = R.layout.fragment_repository_details
    override val progressBar: ProgressBar?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repositoryDetailsModel = it.getParcelable(ARG_PARAM1)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(repositoryDetailsModel: GithubRepositoryDetailsModel) =
            RepositoryDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, repositoryDetailsModel)
                }
            }
    }
}
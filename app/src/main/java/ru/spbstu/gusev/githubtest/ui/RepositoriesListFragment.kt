package ru.spbstu.gusev.githubtest.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_repositories_list.*
import ru.spbstu.gusev.githubtest.R
import ru.spbstu.gusev.githubtest.data.ResultWrapper
import ru.spbstu.gusev.githubtest.extensions.snackbarError
import ru.spbstu.gusev.githubtest.navigation.Navigator
import ru.spbstu.gusev.githubtest.ui.recycler.GithubRepositoriesAdapter
import ru.spbstu.gusev.githubtest.viewmodel.RepositoriesListViewModel
import javax.inject.Inject


class RepositoriesListFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_repositories_list
    override val progressBar: ProgressBar?
        get() = progress_bar
    private lateinit var repositoriesAdapter: GithubRepositoriesAdapter
    private lateinit var viewModel: RepositoriesListViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @Inject
    lateinit var navigator: Navigator

    companion object {
        @JvmStatic
        fun newInstance() =
            RepositoriesListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(
            RepositoriesListViewModel::class.java
        )
        setupRecycler()
        subscribeForData()
        viewModel.refreshRepositoriesList()
        setSwipeRefreshLayout()
    }

    private fun subscribeForData() {
        viewModel.repositoriesListRefreshResponse.observe(viewLifecycleOwner, Observer {
            updateUi(it) { repositoriesList ->
                repositoriesAdapter.updateList(repositoriesList)
            }
        })
        viewModel.repositoriesListToAddResponse.observe(viewLifecycleOwner, Observer {
            updateUi(it) { repositoriesList ->
                repositoriesAdapter.addToList(repositoriesList)
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showProgress(it)
        })
    }

    private fun <T> updateUi(
        response: ResultWrapper<List<T>>,
        onSuccess: ((List<T>) -> Unit)
    ) {
        hideContent()
        when (response) {
            is ResultWrapper.Success -> {
                val valuesList = response.value
                if (valuesList.isNotEmpty()) {
                    onSuccess.invoke(valuesList)
                    showContent()
                } else {
                    snackbarError(getString(R.string.empty_list_error))
                }
            }
            is ResultWrapper.GenericError -> {
                snackbarError(response.error?.error_description ?: "Error")
            }
            is ResultWrapper.NetworkError -> {
                snackbarError(getString(R.string.network_error))
            }
        }
    }

    private fun setupRecycler() {
        repositoriesAdapter = GithubRepositoriesAdapter {
            viewModel.addToRepositoriesList(it)
        }
        val recyclerLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            github_repositories_recycler.context,
            recyclerLayoutManager.orientation
        )
        github_repositories_recycler.apply {
            adapter = repositoriesAdapter
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
        repositoriesAdapter.setOnItemClickListener {
            navigator.showRepositoryDetails(it)
        }
    }

    private fun setSwipeRefreshLayout() {
        swipeRefreshLayout = swipe_refresh_layout

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshRepositoriesList()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun hideContent() {
        github_repositories_recycler.visibility = View.GONE
        network_error_try_again_include.visibility = View.VISIBLE
    }

    private fun showContent() {
        github_repositories_recycler.visibility = View.VISIBLE
        network_error_try_again_include.visibility = View.GONE
    }

}
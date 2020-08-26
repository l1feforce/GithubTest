package ru.spbstu.gusev.githubtest.navigation

import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.gusev.githubtest.R
import ru.spbstu.gusev.githubtest.model.GithubRepositoryModel
import ru.spbstu.gusev.githubtest.ui.RepositoriesListFragment
import ru.spbstu.gusev.githubtest.ui.RepositoryDetailsFragment

class Navigator {
    var activity: AppCompatActivity? = null
    private val BACK_STACK_ROOT_TAG = "root_fragment"
    private var lastFragmentTag = "repositoriesListFragment"
    private val backstack = mutableListOf(lastFragmentTag)

    val backstackSize: Int
    get() = backstack.size

    fun showRepositoriesList() {
        val tag = "repositoriesListFragment"
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container, RepositoriesListFragment.newInstance(),
                tag
            )
            .commit()
        backstack.add(tag)
    }

    fun showRepositoryDetails(githubRepositoryModel: GithubRepositoryModel) {
        val tag = "repositoryDetailsFragment"
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                RepositoryDetailsFragment.newInstance(githubRepositoryModel),
                tag
            )
            .addToBackStack(BACK_STACK_ROOT_TAG)
            .commit()
        backstack.add(tag)
    }

    fun restoreBackstack() {
        backstack.forEach {
            val fragment = activity!!.supportFragmentManager.findFragmentByTag(it) ?: RepositoriesListFragment.newInstance()
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    fragment,
                    it
                )
                .commit()
        }
    }

    fun backStackRemoveLast() {
        backstack.removeAt(backstack.size - 1)
    }
}
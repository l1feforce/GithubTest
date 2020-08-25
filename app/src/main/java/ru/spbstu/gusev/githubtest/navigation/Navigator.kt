package ru.spbstu.gusev.githubtest.navigation

import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.gusev.githubtest.R
import ru.spbstu.gusev.githubtest.model.GithubRepositoryDetailsModel
import ru.spbstu.gusev.githubtest.ui.RepositoriesListFragment
import ru.spbstu.gusev.githubtest.ui.RepositoryDetailsFragment

class Navigator {
    var activity: AppCompatActivity? = null

    fun showRepositoriesList() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RepositoriesListFragment.newInstance())
            .commit()
    }

    fun showRepositoryDetails(repositoryDetailsModel: GithubRepositoryDetailsModel) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                RepositoryDetailsFragment.newInstance(repositoryDetailsModel)
            )
            .commit()
    }
}
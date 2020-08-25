package ru.spbstu.gusev.githubtest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import ru.spbstu.gusev.githubtest.R


class RepositoriesListFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_repositories_list
    override val progressBar: ProgressBar?
        get() = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RepositoriesListFragment()
    }
}
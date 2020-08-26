package ru.spbstu.gusev.githubtest.ui

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import ru.spbstu.gusev.githubtest.di.DI
import toothpick.Toothpick

abstract class BaseFragment: Fragment() {
    abstract val layoutRes: Int
    abstract val progressBar: ProgressBar?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        Toothpick.inject(this, appScope)
    }

    fun showProgress(isLoading: Boolean) {
        progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}
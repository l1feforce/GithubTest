package ru.spbstu.gusev.githubtest.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.spbstu.gusev.githubtest.R

fun Fragment.snackbarError(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).setBackgroundTint(
        requireContext().getColorFromTheme(
            R.attr.colorError
        )
    ).setTextColor(requireContext().getColorFromTheme(R.attr.colorOnError)).show()
}
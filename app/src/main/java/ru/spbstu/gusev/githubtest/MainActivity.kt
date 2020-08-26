package ru.spbstu.gusev.githubtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.spbstu.gusev.githubtest.di.DI
import ru.spbstu.gusev.githubtest.navigation.Navigator
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        Toothpick.inject(this, appScope)
        navigator.activity = this
        if (navigator.backstackSize == 0) {
            navigator.showRepositoriesList()
        } else navigator.restoreBackstack()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            navigator.backStackRemoveLast()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.activity = null
    }
}
package ru.spbstu.gusev.githubtest

import android.app.Application
import ru.spbstu.gusev.githubtest.di.DI
import ru.spbstu.gusev.githubtest.di.module.AppModule
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initToothpick()
        initAppScope()
    }

    private fun initToothpick() {
        Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
    }

    private fun initAppScope() {
        Toothpick.openScope(DI.APP_SCOPE)
            .installModules(AppModule())
    }

}
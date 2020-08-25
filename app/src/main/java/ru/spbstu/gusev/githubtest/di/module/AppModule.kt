package ru.spbstu.gusev.githubtest.di.module

import ru.spbstu.gusev.githubtest.data.retrofit.ApiProvider
import ru.spbstu.gusev.githubtest.data.retrofit.GithubApi
import toothpick.config.Module

class AppModule : Module() {
    init {

        bind(GithubApi::class.java).toProvider(
            ApiProvider::class.java)
    }
}
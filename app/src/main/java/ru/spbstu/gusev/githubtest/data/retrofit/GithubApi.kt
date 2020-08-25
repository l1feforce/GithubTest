package ru.spbstu.gusev.githubtest.data.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.spbstu.gusev.githubtest.model.CommitModel
import ru.spbstu.gusev.githubtest.model.GithubRepositoryModel

interface GithubApi {

    @GET
    fun getCommits(@Url url: String): Deferred<List<CommitModel>>

    @GET("/repositories")
    fun getPublicRepositories(@Query("since") since: String?): Deferred<List<GithubRepositoryModel>>

}
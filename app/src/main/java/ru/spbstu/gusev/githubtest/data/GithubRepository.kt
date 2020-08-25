package ru.spbstu.gusev.githubtest.data

import ru.spbstu.gusev.githubtest.data.retrofit.GithubApi
import ru.spbstu.gusev.githubtest.model.CommitModel
import ru.spbstu.gusev.githubtest.model.GithubRepositoryModel
import javax.inject.Inject

class GithubRepository
@Inject constructor(
    val githubApi: GithubApi
) {
    suspend fun getPublicRepositoriesList(since: String? = null): ResultWrapper<List<GithubRepositoryModel>> =
        safeApiCall { githubApi.getPublicRepositories(since).await() }

    suspend fun getCommit(url: String): ResultWrapper<List<CommitModel>> =
        safeApiCall { githubApi.getCommits(url).await() }

}

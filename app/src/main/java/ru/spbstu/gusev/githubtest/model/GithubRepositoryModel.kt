package ru.spbstu.gusev.githubtest.model

data class GithubRepositoryModel(
    var id: String,
    var full_name: String,
    var commits_url: String,
    var owner: OwnerModel
)
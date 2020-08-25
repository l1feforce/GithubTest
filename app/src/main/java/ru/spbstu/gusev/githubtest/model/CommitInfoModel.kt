package ru.spbstu.gusev.githubtest.model

data class CommitInfoModel(
    var message: String,
    var author: CommitAuthorModel
)
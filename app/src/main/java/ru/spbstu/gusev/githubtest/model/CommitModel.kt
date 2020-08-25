package ru.spbstu.gusev.githubtest.model

data class CommitModel(
    var commit: CommitInfoModel,
    var parents: List<CommitParentModel>
)
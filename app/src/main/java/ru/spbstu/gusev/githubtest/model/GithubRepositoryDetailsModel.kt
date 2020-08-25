package ru.spbstu.gusev.githubtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepositoryDetailsModel(
    val ownerAvatarUrl: String,
    val ownerLogin: String,
    val repoFullName: String,
    val commitMessage: String,
    val commitAuthorName: String,
    val commitDate: String,
    val parentsSha: List<String>
): Parcelable
package ru.spbstu.gusev.githubtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepositoryModel(
    var id: String,
    var full_name: String,
    var commits_url: String,
    var owner: OwnerModel
) : Parcelable
package ru.spbstu.gusev.githubtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OwnerModel(
    var avatar_url: String = "",
    var login: String = ""
): Parcelable

package app.vazovsky.lesson_5_klyueva.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(val value: String? = null) : Parcelable
package app.vazovsky.lesson_4_klyueva.models

import androidx.annotation.DrawableRes

data class DetailInfoItem(
    override val title: String,
    val desc: String,
    @DrawableRes override val img: Int,
    val isImportant: Boolean = false
) : InfoItem(title, img)
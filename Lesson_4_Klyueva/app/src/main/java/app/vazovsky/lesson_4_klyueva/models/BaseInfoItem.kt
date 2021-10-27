package app.vazovsky.lesson_4_klyueva.models

import androidx.annotation.DrawableRes

data class BaseInfoItem(
    override val title: String,
    @DrawableRes override val img: Int
) : InfoItem(title, img)
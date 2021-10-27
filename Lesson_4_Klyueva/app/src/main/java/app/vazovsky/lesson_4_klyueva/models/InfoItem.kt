package app.vazovsky.lesson_4_klyueva.models

import androidx.annotation.DrawableRes

sealed class InfoItem(
    open val title: String,
    @DrawableRes open val img: Int
)

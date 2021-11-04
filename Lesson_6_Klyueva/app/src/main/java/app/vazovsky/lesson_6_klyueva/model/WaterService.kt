package app.vazovsky.lesson_6_klyueva.model

data class WaterService(
    override val title: String,
    override val code: String,
    override val icon: Int,
    override val info: String,
    override val desc: String,
    var newData: Int = 0,
    override var isWarning: Boolean = false
) : Service(title,code,icon,info,desc,isWarning)
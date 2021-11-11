package app.vazovsky.lesson_7_klyueva.data.model


import android.os.Parcelable
import app.vazovsky.lesson_7_klyueva.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import kotlin.math.abs


@Parcelize
data class Bridge(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("divorces") val divorces: List<Divorce>,
    @SerializedName("photo_close_url") val photoCloseUrl: String?,
    @SerializedName("photo_open_url") val photoOpenUrl: String?,
    var isPushing: Boolean = false
) : Parcelable {
    companion object {
        const val STATE_NORMAL = R.drawable.ic_brige_normal
        const val STATE_SOON = R.drawable.ic_brige_soon
        const val STATE_LATE = R.drawable.ic_brige_late
    }

    fun getState(): Int {
        val currentTime = Calendar.getInstance()
        val currentMinutes = currentTime.get(Calendar.HOUR_OF_DAY) * 60 + currentTime.get(Calendar.MINUTE)
        for (divorce in divorces) {
            val divorceStartMinutes = divorce.start!!.split(":").let {
                it[0].toInt() * 60 + it[1].toInt()
            }
            val divorceEndMinutes = divorce.end!!.split(":").let {
                it[0].toInt() * 60 + it[1].toInt()
            }
            if (currentMinutes in divorceStartMinutes..divorceEndMinutes) {
                return STATE_LATE
            } else if (abs(divorceStartMinutes - currentMinutes) <= 60) {
                return STATE_SOON
            }
        }
        return STATE_NORMAL
    }
}

@Parcelize
data class Divorce(
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?
) : Parcelable
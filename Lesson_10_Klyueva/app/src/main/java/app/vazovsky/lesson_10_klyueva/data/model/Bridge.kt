package app.vazovsky.lesson_10_klyueva.data.model

import android.os.Parcelable
import app.vazovsky.lesson_10_klyueva.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import kotlin.math.abs
import android.graphics.Bitmap
import android.graphics.Canvas

import android.graphics.drawable.VectorDrawable

import android.os.Build
import androidx.appcompat.content.res.AppCompatResources.getDrawable

import com.google.android.gms.maps.model.BitmapDescriptor


@Parcelize
data class Bridge(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("divorces") val divorces: List<Divorce>,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?,
    @SerializedName("photo_close_url") val photoCloseUrl: String?,
    @SerializedName("photo_open_url") val photoOpenUrl: String?
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

    fun getLatLng(): LatLng {
        return if (lat != null && lng != null) {
            LatLng(lat, lng)
        } else {
            LatLng(0.0, 0.0)
        }
    }

    fun getDivorceString(): String {
        return buildString {
            for (divorce in divorces) {
                append("${divorce.start} - ${divorce.end}\t\t")
            }
        }
    }

    @Parcelize
    data class Divorce(
        @SerializedName("start") val start: String?,
        @SerializedName("end") val end: String?
    ) : Parcelable
}
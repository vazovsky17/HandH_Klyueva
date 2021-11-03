package app.vazovsky.lesson_5_klyueva.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.vazovsky.lesson_5_klyueva.R
import app.vazovsky.lesson_5_klyueva.model.Service
import com.bumptech.glide.Glide


class ServiceViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Service) -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(app.vazovsky.lesson_5_klyueva.R.layout.item_service, parent, false)
    ) {
    private val imageViewServiceImage by lazy { itemView.findViewById<ImageView>(app.vazovsky.lesson_5_klyueva.R.id.imageViewServiceImage) }
    private val textViewServiceTitle by lazy { itemView.findViewById<TextView>(app.vazovsky.lesson_5_klyueva.R.id.textViewServiceTitle) }
    private val textViewServiceCoupon by lazy { itemView.findViewById<TextView>(app.vazovsky.lesson_5_klyueva.R.id.textViewServiceCoupon) }
    private val textViewServiceAddress by lazy { itemView.findViewById<TextView>(app.vazovsky.lesson_5_klyueva.R.id.textViewServiceAddress) }

    private val imageViewServiceFavorite by lazy { itemView.findViewById<ImageView>(app.vazovsky.lesson_5_klyueva.R.id.imageViewServiceFavorite) }
    private val buttonContextMenu by lazy { itemView.findViewById<ImageView>(app.vazovsky.lesson_5_klyueva.R.id.buttonContextMenu) }

    fun bind(service: Service) {
        itemView.setOnClickListener {
            onItemClick(service)
        }

        textViewServiceTitle.text = service.title
        textViewServiceCoupon.text = service.coupon
        textViewServiceAddress.text = service.address
        imageViewServiceFavorite.visibility = if (service.isFavorite) View.VISIBLE else View.GONE
        buttonContextMenu.setOnClickListener {
            Toast.makeText(
                itemView.context,
                "Тут могло быть контекстное меню. Но добавление в избранное теперь происходит по нажатию на image",
                Toast.LENGTH_SHORT
            ).show()
        }
        Glide.with(itemView.context.applicationContext).load(service.image).centerCrop()
            .into(imageViewServiceImage)

        imageViewServiceImage.setOnClickListener {
            val animIn: Animation = AnimationUtils.loadAnimation(itemView.context, R.anim.anim_in)
            val animOut: Animation = AnimationUtils.loadAnimation(itemView.context, R.anim.anim_out)
            if (service.isFavorite) {
                service.isFavorite = false
                imageViewServiceFavorite.startAnimation(animOut)
                imageViewServiceFavorite.visibility = View.GONE
            } else {
                service.isFavorite = true
                imageViewServiceFavorite.startAnimation(animIn)
                imageViewServiceFavorite.visibility = View.VISIBLE
            }
        }
    }
}

package app.vazovsky.lesson_6_klyueva.ui.ex3

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import app.vazovsky.lesson_6_klyueva.R
import com.bumptech.glide.Glide

class ChildFragment : Fragment(R.layout.fragment_child) {
    companion object {
        const val EXTRA_URL_PICTURE = "extra_url_picture"
        const val EXTRA_PAGER_POSITION = "extra_pager_position"

        fun newInstance(url: String, position: Int): ChildFragment {
            return ChildFragment().apply {
                arguments = bundleOf(
                    EXTRA_URL_PICTURE to url,
                    EXTRA_PAGER_POSITION to position + 1
                )
            }
        }
    }

    private val urlPicture by lazy { arguments?.getString(EXTRA_URL_PICTURE) }
    private val pagerPosition by lazy { arguments?.getInt(EXTRA_PAGER_POSITION) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageViewPagerPicture = view.findViewById<ImageView>(R.id.imageViewPagerPicture)
        Glide.with(this)
            .load(urlPicture)
            .centerCrop()
            .into(imageViewPagerPicture)
        val textViewPagerPosition = view.findViewById<TextView>(R.id.textViewPagerPosition)
        textViewPagerPosition.text = "Картинка $pagerPosition"
        imageViewPagerPicture.setOnClickListener {
            Toast.makeText(context, textViewPagerPosition.text, Toast.LENGTH_SHORT).show()
        }

    }
}
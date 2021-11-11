package app.vazovsky.lesson_7_klyueva.presentation.bridge

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import app.vazovsky.lesson_7_klyueva.R
import app.vazovsky.lesson_7_klyueva.data.model.Bridge
import app.vazovsky.lesson_7_klyueva.data.model.Bridge.Companion.STATE_LATE
import app.vazovsky.lesson_7_klyueva.presentation.FragmentListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout

class BridgeFragment : Fragment(R.layout.fragment_bridge) {
    companion object {
        const val EXTRA_BRIDGE = "extra_bridge"

        fun newInstance(bridge: Bridge): BridgeFragment {
            return BridgeFragment().apply {
                arguments = bundleOf(
                    EXTRA_BRIDGE to bridge
                )
            }
        }
    }

    private var fragmentListener: FragmentListener? = null

    private val bridge by lazy {
        arguments?.getParcelable<Bridge>(EXTRA_BRIDGE)!!
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            fragmentListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appBar = view.findViewById<AppBarLayout>(R.id.appBar)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            fragmentListener!!.goBack()
        }


        view.findViewById<ImageView>(R.id.imageViewBridgeState).setImageResource(bridge.getState())
        view.findViewById<TextView>(R.id.textViewBridgeName).text = bridge.name
        view.findViewById<TextView>(R.id.textViewBridgeDescription).text = bridge.description
        view.findViewById<TextView>(R.id.textViewBridgeDivorce).text = buildString {
            for (divorce in bridge.divorces) {
                append("${divorce.start} - ${divorce.end}\t\t")
            }
        }

        Glide.with(this)
            .asBitmap()
            .load(if (bridge.getState() == STATE_LATE) bridge.photoCloseUrl else bridge.photoOpenUrl)
            .centerCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    appBar.background = BitmapDrawable(resources, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
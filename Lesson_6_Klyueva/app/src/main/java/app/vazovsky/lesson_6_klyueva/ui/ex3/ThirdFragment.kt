package app.vazovsky.lesson_6_klyueva.ui.ex3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.vazovsky.lesson_6_klyueva.R
import com.google.android.material.button.MaterialButton

class ThirdFragment : Fragment(R.layout.fragment_third) {
    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonShowBanner = view.findViewById<MaterialButton>(R.id.buttonShowBanner)
        var isShowed = false

        buttonShowBanner.setOnClickListener {
            val fragment = ContainerFragment.newInstance()

            if (!isShowed) {
                isShowed = true
                childFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .addToBackStack(null)
                    .commit()
                buttonShowBanner.text = getString(R.string.button_show_banner_hidden)
            } else {
                isShowed = false
                for (frag in childFragmentManager.fragments){
                    childFragmentManager
                        .beginTransaction()
                        .remove(frag)
                        .commit()
                }
                buttonShowBanner.text = getString(R.string.button_show_banner_showed)
            }
        }
    }
}
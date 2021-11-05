package app.vazovsky.lesson_6_klyueva.ui.ex3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import app.vazovsky.lesson_6_klyueva.R
import app.vazovsky.lesson_6_klyueva.tools.PagerAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class ContainerFragment : Fragment(R.layout.fragment_container) {
    companion object {
        fun newInstance(): ContainerFragment {
            return ContainerFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val dotsIndicator = view.findViewById<DotsIndicator>(R.id.dotsIndicator)
        viewPager.adapter = PagerAdapter(this)
        dotsIndicator.setViewPager2(viewPager)
    }
}
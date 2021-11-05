package app.vazovsky.lesson_6_klyueva.tools

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.vazovsky.lesson_6_klyueva.ui.ex3.ChildFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val PAGER_URLS = listOf(
            "https://spark.ru/upload/startups/l_5f130e04e9af2.jpg",
            "https://sun9-12.userapi.com/impf/c851420/v851420593/d1652/nwrSLfwSxBU.jpg?size=604x604&quality=96&sign=c82c8b11a5985ec68052197f60d3b6a9&type=album",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTr_MX0cys-A7Jj8Piram-ZBI917hby_5N9og&usqp=CAU"
        )
    }

    override fun getItemCount() = PAGER_URLS.size

    override fun createFragment(position: Int): Fragment {
        return ChildFragment.newInstance(PAGER_URLS[position], position)
    }

}
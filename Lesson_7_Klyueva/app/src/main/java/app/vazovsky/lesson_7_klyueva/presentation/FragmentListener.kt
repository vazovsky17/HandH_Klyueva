package app.vazovsky.lesson_7_klyueva.presentation

import androidx.fragment.app.Fragment

interface FragmentListener {
    fun openFragment(fragment: Fragment)
    fun goBack()
}
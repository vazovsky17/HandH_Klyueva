package app.vazovsky.lesson_8_klyueva.presentation

import androidx.fragment.app.Fragment

interface FragmentListener {
    fun goToFragment(fragment: Fragment)
    fun goBack()
}
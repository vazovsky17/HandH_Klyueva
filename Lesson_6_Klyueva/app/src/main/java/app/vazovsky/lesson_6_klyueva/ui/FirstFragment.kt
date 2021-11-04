package app.vazovsky.lesson_6_klyueva.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.vazovsky.lesson_6_klyueva.R
import com.google.android.material.appbar.MaterialToolbar

class FirstFragment : Fragment(R.layout.fragment_first) {
    companion object {
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar.setOnMenuItemClickListener {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
            true
        }
    }
}
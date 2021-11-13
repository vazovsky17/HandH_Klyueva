package app.vazovsky.lesson_8_klyueva.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.vazovsky.lesson_8_klyueva.R
import app.vazovsky.lesson_8_klyueva.databinding.ActivityMainBinding
import app.vazovsky.lesson_8_klyueva.presentation.notelist.NoteListFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(
                binding.fragmentContainer.id,
                NoteListFragment.newInstance()
            )
            .commit()
    }
}
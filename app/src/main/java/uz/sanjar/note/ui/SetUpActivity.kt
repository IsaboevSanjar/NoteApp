package uz.sanjar.note.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import uz.sanjar.note.R
import uz.sanjar.note.core.adapter.OnBoardAdapter
import uz.sanjar.note.core.cache.SetUpHelper
import uz.sanjar.note.core.model.BoardData
import uz.sanjar.note.databinding.ActivityBoardBinding

class SetUpActivity : AppCompatActivity() {

    private lateinit var data: ArrayList<BoardData>
    private val adapter = OnBoardAdapter()

    private var _binding: ActivityBoardBinding? = null
    private val binding get() = _binding!!
    private val boardOpen: Boolean = SetUpHelper.getHelper().board
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onBoardPager.adapter = adapter
        if (!boardOpen) {
            loadBoardData()
        } else {
            val intent = Intent(this, Password::class.java)
            startActivity(intent)
            finish()
        }
        adapter.nextButtonPressed = {

        }
        val window = this.window
        window.navigationBarColor =
            ContextCompat.getColor(this, R.color.board_back)
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.board_back)

        binding.nextBoardButton.setOnClickListener {
            if (binding.onBoardPager.currentItem == data.size - 1) {
                val intent = Intent(this, Password::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.onBoardPager.setCurrentItem(binding.onBoardPager.currentItem + 1, true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.onBoardPager.registerOnPageChangeCallback(pagerCallback)
    }

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            if (position == data.size - 1) {
                binding.nextBoardButton.setText(R.string.START)
            } else {
                binding.nextBoardButton.setText(R.string.NEXT)
            }

        }
    }

    override fun onStop() {
        super.onStop()
        binding.onBoardPager.unregisterOnPageChangeCallback(pagerCallback)
    }

    private fun loadBoardData() {
        this.data = ArrayList<BoardData>()
        data.add(
            BoardData(
                title = "Security note app.",
                description = "If you note your essential things, others can't read it because you will have passCode",
                image = R.drawable.ic_security,
                word = "S",
                word1 = "E",
                word2 = "C",
                word3 = "U",
                word4 = "R",
                word5 = "I",
                word6 = "T",
                word7 = "Y",
                word8 = "",
                word9 = "",
                word10 = "",
                word11 = ""
            )
        )
        data.add(
            BoardData(
                title = "Easy to use.",
                description = "Everyone can use this app without any difficulties for remembering their works which he/she is going to do or have done.",
                image = R.drawable.ic_note_user_friendl,
                word = "U",
                word1 = "S",
                word2 = "E",
                word3 = "R",
                word4 = " ",
                word5 = "F",
                word6 = "I",
                word7 = "E",
                word8 = "N",
                word9 = "D",
                word10 = "L",
                word11 = "Y"
            )
        )
        data.add(
            BoardData(
                title = "",
                description = "",
                image = R.drawable.ic_note_start,
                word = "S",
                word1 = "T",
                word2 = "A",
                word3 = "R",
                word4 = "T",
                word5 = "",
                word6 = "",
                word7 = "",
                word8 = "",
                word9 = "",
                word10 = "",
                word11 = ""
            )
        )
        adapter.data = data
    }
}
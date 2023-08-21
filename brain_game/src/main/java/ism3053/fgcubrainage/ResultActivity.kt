package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var displayScore: TextView
    private lateinit var displayDigit: TextView
    private lateinit var difficultyText: TextView
    private lateinit var userName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Pull passed variables from Intent
        val extras = intent.extras
        val score = extras?.getInt("score")
        val digitLength = extras?.getInt("digitLength")

        // Identify Specific Views
        displayScore = findViewById(R.id.scoreCount)
        displayDigit = findViewById(R.id.digitCount)
        difficultyText = findViewById(R.id.difText)
        userName = findViewById(R.id.nameText)

        // If digitLength is 0 the score is coming from Arithmetic
        if (digitLength == 0) {
            displayScore.text = score?.toString()
            difficultyText.visibility = View.GONE
        } else {
            // Set score & dif
            displayScore.text = score?.toString()
            displayDigit.text = digitLength?.toString()
        }
    }

    fun onReturnClick(view: View?) {
        val text = userName.text.toString()

        // If empty prompt them Toast to enter something. Else delay submit score to DB and return to page
        if (text.isEmpty()) {
            showToast("Please Enter a Name!")
        } else {
            showToast("$text was added to the Leaderboard")

            // Set handler for postDelay
            val handler = Handler()

            // Delay to validate some stateChanges then continue game
            handler.postDelayed({

                // Return to Category Selection
                val intent = Intent(this@ResultActivity, CategoryActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }

    fun showToast(greetingText: String?) {
        val prompt = Toast.makeText(this, greetingText, Toast.LENGTH_LONG)
        prompt.setGravity(Gravity.CENTER, 0, 550)
        prompt.show()
    }
}

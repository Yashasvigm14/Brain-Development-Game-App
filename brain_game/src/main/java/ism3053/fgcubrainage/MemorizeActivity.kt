package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MemorizeActivity : AppCompatActivity() {
    private var memoryGrid: GridLayout? = null
    private var memResponse: TextView? = null
    private val buttonStates = false
    private var gridButtons: Button? = null
    private var displayScore: TextView? = null
    private var Lives: ImageView? = null

    // ============= Game variables to be utilized =============
    private var userEntry: String? = null

    // Provides initial length value to be incremented for different random ranges
    private var digitLengthCount = 2

    // Bounds for random numbers range equivalence [10,99] || [100,999] || [1000,9999]
    private var max = 89
    private var min = 10
    private var generatedNumber: String? = null

    // Count to keep track of # of errors. If less than 0 (meaning >= 3) end game
    private var numberOfLives = 2

    // Difficult Increase Counter
    private var difCount = 0
    private var currentScore = 0

    // ============= Game variables to be utilized =============
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorize)

        // Initializes variables
        memoryGrid = findViewById(R.id.mem_grid)
        memResponse = findViewById(R.id.textResponse)
        displayScore = findViewById(R.id.scoreCount)

        // Call game start
        MemoryGame()
    }

    fun onButtonClick(view: View) {
        // Obtains predefined button value
        val incomingValue = view as Button
        // If first instance set initial value, else concatenate
        userEntry = if (userEntry == null || userEntry === "") {
            incomingValue.text.toString()
        } else {
            userEntry + incomingValue.text.toString()
        }
        if (userEntry!!.length == digitLengthCount) {
            memResponse!!.text = userEntry
            validateAnswer(userEntry!!)
        } else {
            memResponse!!.text = userEntry
        }
    }

    fun MemoryGame() {
        val digit = Random()

        // Set handler for postDelay
        val handler = Handler()

        // Check if user has any Lives left (if not end game)
        if (numberOfLives < 0) {
            gameOver()
        } else {
            // Checks difficulty state;
            checkDifficulty()

            // Displays currentScore
            displayScore!!.text = currentScore.toString()

            // Random number generator as questions (with bound respect to digitLengthCount
            generatedNumber = (digit.nextInt(max) + min).toString()

            // Disables buttons on Load
            setButtonState(buttonStates)

            // Display length & populate digit
            memResponse!!.text = "$digitLengthCount digits"

            // Change provided text to randomly generated value
            handler.postDelayed({
                memResponse!!.text = generatedNumber.toString()
                userEntry = ""
            }, 2000)

            // Remove provided digits & enable buttons for user to guess
            handler.postDelayed({
                memResponse!!.text = ""
                setButtonState(!buttonStates)
            }, 3500)
        }
    }

    fun setButtonState(buttonState: Boolean) {
        // Int array with every Id of grid buttons
        val ids = intArrayOf(
            R.id.gridButton0,
            R.id.gridButton1,
            R.id.gridButton2,
            R.id.gridButton3,
            R.id.gridButton4,
            R.id.gridButton5,
            R.id.gridButton6,
            R.id.gridButton7,
            R.id.gridButton8,
            R.id.gridButton9
        )
        for (i in 0..9) {
            gridButtons = findViewById(ids[i])
            if (buttonState == true) {
                gridButtons?.alpha = 1f
            } else {
                gridButtons?.alpha = 0.5f
            }
            gridButtons?.isClickable = buttonState
        }
    }

    fun validateAnswer(userEntry: String) {
        // If user is Correct up difficulty count & increment score
        if (userEntry == generatedNumber.toString()) {
            val greetingText = getString(R.string.correct)
            showToast(greetingText)
            difCount++
            currentScore += 5
        } else {
            val greetingText = getString(R.string.incorrect)
            showToast(greetingText)
            removeLive(numberOfLives)
            numberOfLives--
        }

        // Set handler for postDelay
        val handler = Handler()

        // Delay to validate some stateChanges then continue game
        handler.postDelayed({ MemoryGame() }, 3000)
    }

    fun showToast(greetingText: String?) {
        val prompt = Toast.makeText(this, greetingText, Toast.LENGTH_LONG)
        prompt.setGravity(Gravity.CENTER, 0, 550)
        prompt.show()
    }

    fun removeLive(index: Int) {
        // Int array with every Id of Lives
        val ids = intArrayOf(R.id.Live0, R.id.Live1, R.id.Live2)

        // sets ImageView to specific index & makes it disappear
        Lives = findViewById(ids[index])
        Lives?.visibility = View.GONE
    }

    // Check if the user has any Lives left or if the difficulty needs to be increased
    fun gameOver() {
        // Disable buttons
        setButtonState(buttonStates)

        // Display Game Over
        memResponse!!.text = getString(R.string.game_over)

        // Set handler for postDelay
        val handler = Handler()

        // Delay to ensure user saw Game Over then go to results
        handler.postDelayed({
            val intent = Intent(this@MemorizeActivity, ResultActivity::class.java)
            val extras = Bundle()
            // Pass Score and digit length (difficulty) reached
            extras.putInt("score", currentScore)
            extras.putInt("digitLength", digitLengthCount)
            intent.putExtras(extras)
            startActivity(intent)
            finish()
        }, 4000)
    }

    // Check if the user has any Lives left or if the difficulty needs to be increased
    fun checkDifficulty() {
        if (difCount == 2) {
            // Temp variables being attached to max or min
            val nine = 9
            val zero = 0

            // Increase the random number range to a digit above
            max = Integer.valueOf(max.toString() + nine.toString())
            min = Integer.valueOf(min.toString() + zero.toString())

            // Increment length for digit above
            digitLengthCount++

            // Reset difficulty counter for another potential increase
            difCount = 0
        }
    }
}

package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ArithmeticActivity : AppCompatActivity() {
    private var questionPrompt: TextView? = null
    private var gridButtons: Button? = null
    private var buttonStates = false
    private var Lives: ImageView? = null
    private var displayScore: TextView? = null

    // Bug found with testing: Sometimes questions may have duplicate answers due to order
    // of value creation in logic. Could update to have correct answer be placed first
    // and set all other values according to that position.
    // ============= Game variables to be utilized =============
    // Bounds for random numbers range equivalence [1,9] || [10,99] || [100,999] || [1000,9999]
    private var max = 50
    private val min = 0

    // Set char array with arithmetic symbols
    private val arithmeticSymbols = charArrayOf('+', '-', '*', '/')

    // Int array with every Id of grid buttons
    private val buttonId = intArrayOf(R.id.gridButton0, R.id.gridButton1, R.id.gridButton2, R.id.gridButton3)

    // Keep track of correct answer to validate form onClick
    private var correctAnswer = 0
    private var correctAnswerDiv = 0.0

    // Count to keep track of # of errors. If less than 0 (meaning >= 3) end game
    private var numberOfLives = 2

    // Difficult Increase Counter
    private var difCount = 0
    private var currentScore = 0

    // ============= Game variables to be utilized =============
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arithmetic)
        questionPrompt = findViewById(R.id.questionText)
        displayScore = findViewById(R.id.scoreCount)

        // Call main logic of Arithmetic game
        ArithmeticGame()
    }

    fun onMemButtonClick(view: View) {
        // Obtains predefined button value
        val incomingValue = view as Button

        // Set selected value into string
        val userSelection = incomingValue.text.toString()

        // Disables buttons click to avoid spam clicks
        setButtonState(buttonStates)

        // Check answer that was selected
        validateAnswer(userSelection)
    }

    private fun ArithmeticGame() {
        // End game is # of Lives is < 0
        if (numberOfLives < 0) {
            gameOver()
        } else {
            // Set handler for postDelay
            val handler = Handler()

            // Displays currentScore
            displayScore!!.text = currentScore.toString()

            // Checks difficulty state;
            checkDifficulty()

            // Disables buttons on Load
            setButtonState(buttonStates)

            // Display question
            generateQuestion()

            // Remove provided digits & enable buttons for user to guess
            handler.postDelayed({ setButtonState(!buttonStates) }, 2000)
        }
    }

    // Generates questions
    private fun generateQuestion() {
        val digit = Random()

        // Generate correct answer position
        val generatePosition = digit.nextInt(4)

        // Generate value for array symbol
        val selectSymbol = digit.nextInt(4)
        val firstValue = (digit.nextInt(max) + min).toString()
        val secondValue = (digit.nextInt(max) + min).toString()
        questionPrompt!!.text = "What is $firstValue ${arithmeticSymbols[selectSymbol]} $secondValue"

        // Call valuePlacements to place values within buttons
        valuePlacements(firstValue, secondValue, generatePosition, selectSymbol)
    }

    // Takes in specific questionParameters and sets buttons according to position
    private fun valuePlacements(firstValue: String, secondValue: String, generatePosition: Int, selectSymbol: Int) {
        val digit = Random()

        // Loop for placing values on buttons (not a fan on big if-else blocks but gets the job done)
        for (i in 0..3) {
            gridButtons = findViewById(buttonId[i])
            // Check if gridButton position matches (if so place correct answer within this button)
            if (i == generatePosition) {
                // Check if it's addition
                if (arithmeticSymbols[selectSymbol] == '+') {
                    correctAnswer = firstValue.toInt() + secondValue.toInt()
                    gridButtons!!.text = correctAnswer.toString()
                } else if (arithmeticSymbols[selectSymbol] == '-') {
                    correctAnswer = firstValue.toInt() - secondValue.toInt()
                    gridButtons!!.text = correctAnswer.toString()
                } else if (arithmeticSymbols[selectSymbol] == '*') {
                    correctAnswer = firstValue.toInt() * secondValue.toInt()
                    gridButtons!!.text = correctAnswer.toString()
                } else {
                    correctAnswerDiv = firstValue.toDouble() / secondValue.toDouble()
                    gridButtons!!.text = String.format("%.2f", correctAnswerDiv)
                }
            } else {
                // Generate random values
                var randomFirst = (digit.nextInt(max) + min).toString()
                var randomSecond = (digit.nextInt(max) + min).toString()
                var randomAnswer: Int
                var randomAnswerDiv: Double

                // Check if it's addition
                if (arithmeticSymbols[selectSymbol] == '+') {
                    randomAnswer = randomFirst.toInt() + randomSecond.toInt()

                    // While loop to avoid duplicates answer
                    while (randomAnswer == correctAnswer) {
                        // Generate random values
                        randomFirst = (digit.nextInt(max) + min).toString()
                        randomSecond = (digit.nextInt(max) + min).toString()
                        randomAnswer = randomFirst.toInt() + randomSecond.toInt()
                    }
                    gridButtons!!.text = randomAnswer.toString()
                } else if (arithmeticSymbols[selectSymbol] == '-') {
                    randomAnswer = randomFirst.toInt() - randomSecond.toInt()

                    // While loop to avoid duplicates answer
                    while (randomAnswer == correctAnswer) {
                        // Generate random values
                        randomFirst = (digit.nextInt(max) + min).toString()
                        randomSecond = (digit.nextInt(max) + min).toString()
                        randomAnswer = randomFirst.toInt() + randomSecond.toInt()
                    }
                    gridButtons!!.text = randomAnswer.toString()
                } else if (arithmeticSymbols[selectSymbol] == '*') {
                    randomAnswer = randomFirst.toInt() * randomSecond.toInt()

                    // While loop to avoid duplicates answer
                    while (randomAnswer == correctAnswer) {
                        // Generate random values
                        randomFirst = (digit.nextInt(max) + min).toString()
                        randomSecond = (digit.nextInt(max) + min).toString()
                        randomAnswer = randomFirst.toInt() + randomSecond.toInt()
                    }
                    gridButtons!!.text = randomAnswer.toString()
                } else {
                    randomAnswerDiv = randomFirst.toDouble() / randomSecond.toDouble()

                    // While loop to avoid duplicates answer
                    while (randomAnswerDiv == correctAnswerDiv) {
                        // Generate random values
                        randomFirst = (digit.nextInt(max) + min).toString()
                        randomSecond = (digit.nextInt(max) + min).toString()
                        randomAnswer = randomFirst.toInt() + randomSecond.toInt()
                    }
                    gridButtons!!.text = String.format("%.2f", randomAnswerDiv)
                }
            }
        }
    }

    private fun validateAnswer(userSelection: String) {
        // If user is Correct up difficulty count & increment score
        if (userSelection == correctAnswer.toString() || userSelection == String.format("%.2f", correctAnswerDiv)) {
            val greetingText = getString(R.string.correct)
            showToast(greetingText)
            difCount++
            currentScore += 10
        } else {
            val greetingText = getString(R.string.incorrect)
            showToast(greetingText)
            removeLive(numberOfLives)
            numberOfLives--
        }

        // Set handler for postDelay
        val handler = Handler()

        // Delay to validate some stateChanges then continue game
        handler.postDelayed({ ArithmeticGame() }, 3000)
    }

    private fun showToast(greetingText: String?) {
        val prompt = Toast.makeText(this, greetingText, Toast.LENGTH_LONG)
        prompt.setGravity(Gravity.CENTER, 0, 550)
        prompt.show()
    }

    private fun removeLive(index: Int) {
        // Int array with every Id of Lives
        val ids = intArrayOf(R.id.Live0, R.id.Live1, R.id.Live2)

        // sets ImageView to specific index & makes it disappear
        Lives = findViewById(ids[index])
        Lives?.visibility = View.GONE
    }

    private fun gameOver() {
        // Disable buttons
        setButtonState(buttonStates)

        // Display Game Over
        questionPrompt!!.text = "Game Over!"

        // Set handler for postDelay
        val handler = Handler()

        // Delay to ensure user saw Game Over then go to results
        handler.postDelayed({
            val intent = Intent(this@ArithmeticActivity, ResultActivity::class.java)
            val extras = Bundle()
            // Pass Score and digit length (difficulty) reached
            extras.putInt("score", currentScore)
            intent.putExtras(extras)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun setButtonState(buttonState: Boolean) {
        // Int array with every Id of grid buttons
        val ids = intArrayOf(R.id.gridButton0, R.id.gridButton1, R.id.gridButton2, R.id.gridButton3)
        for (i in 0..3) {
            gridButtons = findViewById(ids[i])
            gridButtons?.apply {
                alpha = if (buttonState) 1f else .5f
                isClickable = buttonState
            }
        }
    }

    private fun checkDifficulty() {
        if (difCount == 2) {
            // Increase the random number range to a digit above
            max += 50

            // Reset difficulty counter for another potential increase
            difCount = 0
        }
    }
}

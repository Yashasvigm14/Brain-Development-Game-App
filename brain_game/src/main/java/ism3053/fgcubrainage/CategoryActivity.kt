package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {
    private var additionSymbol: ImageView? = null
    private var subtractionSymbol: ImageView? = null
    private var multiplicationSymbol: ImageView? = null
    private var divisionSymbol: ImageView? = null
    private var memorize_Activity_btn: Button? = null
    private var mainMenu_Activity_btn: Button? = null
    private var arith_Activity_btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // Assign component references to variables
        memorize_Activity_btn = findViewById(R.id.memoryBtn)
        mainMenu_Activity_btn = findViewById(R.id.menuReturn)
        arith_Activity_btn = findViewById(R.id.arithBtn)

        // Find additionSymbol and call rotateAdd
        additionSymbol = findViewById(R.id.additionSym)
        rotateAdd(additionSymbol)

        // Find subtractionSymbol and call rotateSub
        subtractionSymbol = findViewById(R.id.subSym)
        rotateSub(subtractionSymbol)

        // Find multiplicationSymbol and call rotateMult
        multiplicationSymbol = findViewById(R.id.multSym)
        rotateMult(multiplicationSymbol)

        // Find divisionSymbol and call rotateDiv
        divisionSymbol = findViewById(R.id.divSym)
        rotateDiv(divisionSymbol)

        // Event handler for the button to jump to the Arithmetic Game when button is clicked
        arith_Activity_btn?.setOnClickListener { view: View? ->
            val intent = Intent(this@CategoryActivity, ArithmeticActivity::class.java)
            startActivity(intent)
        }

        // Event handler for the button to jump to the Memory Game when button is clicked
        memorize_Activity_btn?.setOnClickListener { view: View? ->
            val intent = Intent(this@CategoryActivity, MemorizeActivity::class.java)
            startActivity(intent)
        }

        // Event handler for the button to jump to the Main Menu when button is clicked
        mainMenu_Activity_btn?.setOnClickListener { view: View? ->
            val intent = Intent(this@CategoryActivity, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Given specific ImageView, it will rotate infinitely
    private fun rotateAdd(additionSymbol: ImageView?) {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            5.5f
        )
        rotate.duration = 3000
        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        additionSymbol?.startAnimation(rotate)
    }

    // Given specific ImageView, it will rotate infinitely
    private fun rotateSub(subtractionSymbol: ImageView?) {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.4f,
            Animation.RELATIVE_TO_SELF,
            25f
        )
        rotate.duration = 5000
        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        subtractionSymbol?.startAnimation(rotate)
    }

    // Given specific ImageView, it will rotate infinitely
    private fun rotateMult(multiplicationSymbol: ImageView?) {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            -6.5f
        )
        rotate.duration = 7000
        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        multiplicationSymbol?.startAnimation(rotate)
    }

    // Given specific ImageView, it will rotate infinitely
    private fun rotateDiv(divisionSymbol: ImageView?) {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            -8f
        )
        rotate.duration = 2000
        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        divisionSymbol?.startAnimation(rotate)
    }
}

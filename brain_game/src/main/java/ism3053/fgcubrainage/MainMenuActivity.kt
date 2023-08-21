package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainMenuActivity : AppCompatActivity() {
    private lateinit var logoView: ImageView
    private lateinit var start_Activity_btn: Button
    private lateinit var board_Activity_btn: Button
    private lateinit var about_Activity_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        logoView = findViewById(R.id.logo)
        rotateLogo(logoView)

        val backButton = findViewById<View>(R.id.backbutton)
        backButton.setOnClickListener {
            startActivity(Intent(this@MainMenuActivity, SplashActivity::class.java))
            finish()
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(Intent(this@MainMenuActivity, SplashActivity::class.java))
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        start_Activity_btn = findViewById(R.id.startButton)
        board_Activity_btn = findViewById<Button>(R.id.boardButton)
        about_Activity_btn = findViewById(R.id.settingButton)

        start_Activity_btn.setOnClickListener {
            val intent = Intent(this@MainMenuActivity, CategoryActivity::class.java)
            startActivity(intent)
        }

        board_Activity_btn.setOnClickListener {
            val intent = Intent(this@MainMenuActivity, LeaderboardActivity::class.java)
            startActivity(intent)
        }

        about_Activity_btn.setOnClickListener {
            val intent = Intent(this@MainMenuActivity, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun rotateLogo(logoView: ImageView) {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 6000
        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        logoView.startAnimation(rotate)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}

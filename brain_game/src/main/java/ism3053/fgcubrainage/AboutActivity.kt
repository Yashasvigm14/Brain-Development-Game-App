package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    lateinit var mainMenuActivityBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Remove Action bar in the top
        supportActionBar?.hide()

        mainMenuActivityBtn = findViewById(R.id.settingsReturn)

        // Event handler for the button to jump to the Main Menu page when button is clicked
        mainMenuActivityBtn.setOnClickListener {
            val intent = Intent(this@AboutActivity, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

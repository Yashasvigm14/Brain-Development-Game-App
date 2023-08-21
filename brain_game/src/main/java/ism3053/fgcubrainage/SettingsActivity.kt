package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var mainMenu_Activity_btn: Button
    private lateinit var aboutPage_Activity_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mainMenu_Activity_btn = findViewById<Button>(R.id.menuReturn)!!
        aboutPage_Activity_btn = findViewById<Button>(R.id.aboutButton)!!

        mainMenu_Activity_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SettingsActivity, MainMenuActivity::class.java)
            startActivity(intent)
        })

        aboutPage_Activity_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SettingsActivity, AboutActivity::class.java)
            startActivity(intent)
        })
    }
}

package ism3053.fgcubrainage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LeaderboardActivity : AppCompatActivity() {
    private var textPlacement: TextView? = null
    private var mainMenu_Activity_btn: Button? = null

    var names = intArrayOf(R.id.name0, R.id.name1, R.id.name2)
    var scores = intArrayOf(R.id.score0, R.id.score1, R.id.score2)
    var gameModes = intArrayOf(R.id.mode0, R.id.mode1, R.id.mode2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        supportActionBar?.hide()
        mainMenu_Activity_btn = findViewById(R.id.menuReturn)

        mainMenu_Activity_btn?.setOnClickListener { view: View? ->
            val intent = Intent(this@LeaderboardActivity, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        try {
            val obj = JSONObject(loadJSONfromAssets())
            val userArray = obj.getJSONArray("users")

            for (i in 0 until userArray.length()) {
                val userDetail = userArray.getJSONObject(i)

                textPlacement = findViewById(names[i])
                textPlacement?.text = userDetail.getString("name")

                textPlacement = findViewById(scores[i])
                textPlacement?.text = userDetail.getString("score")

                textPlacement = findViewById(gameModes[i])
                textPlacement?.text = userDetail.getString("gameMode")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONfromAssets(): String? {
        var json: String? = null
        try {
            val `is` = assets.open("data.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charsets.UTF_8) // Update this line
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return json
    }
}

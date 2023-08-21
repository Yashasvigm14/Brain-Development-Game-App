import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ism3053.fgcubrainage.R

class BoardAdapter(
    private val names: ArrayList<String>,
    private val scores: ArrayList<String>,
    private val gameModes: ArrayList<String>
) : RecyclerView.Adapter<BoardVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rowlayout, parent, false)
        return BoardVH(view).linkAdapter(this)
    }

    override fun onBindViewHolder(holder: BoardVH, position: Int) {
        holder.name.text = names[position]
        holder.score.text = scores[position]
        holder.gameMode.text = gameModes[position]
    }

    override fun getItemCount(): Int {
        return names.size
    }
}

class BoardVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.namePlace)
    val score: TextView = itemView.findViewById(R.id.score)
    val gameMode: TextView = itemView.findViewById(R.id.gameMode)
    private var adapter: BoardAdapter? = null

    fun linkAdapter(adapter: BoardAdapter): BoardVH {
        this.adapter = adapter
        return this
    }
}

package njp.project.kanbanapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.kanban_item.view.*

class KanbanAdapter(
    private var items: MutableList<Kanban>
) : RecyclerView.Adapter<KanbanAdapter.KanbanViewHolder>() {

    var index : Int = -1

    class KanbanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KanbanViewHolder {
        return KanbanViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.kanban_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KanbanViewHolder, position: Int) {
        val current = items[position]
        holder.itemView.apply {
            kanbanTitle.text = current.title
            kanbanContent.text = current.content
            kanbanCheck.isChecked = current.isChecked
            kanbanCheck.isEnabled = current.isEnabled
            changeColor(kanbanTitle)
            kanbanCheck.setOnCheckedChangeListener { _, isChecked ->
                current.isChecked = !current.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addKanban(kanban: Kanban){
        items.add(kanban)
        notifyItemInserted(items.size-1)
    }

    fun deleteChecked(){
        items.removeAll { kanban ->
            kanban.isChecked
        }
        notifyDataSetChanged()
    }

    fun updateKanban(kanban: Kanban){
        addKanban(kanban)
        items.removeAt(this.index)
        items.forEach { item ->
            item.isEnabled = true
        }
        notifyDataSetChanged()
    }

    private fun changeColor(kanbanTitle: TextView){
        if(kanbanTitle.text.equals("Do zrobienia")){
            kanbanTitle.setBackgroundColor(Color.parseColor("#b22222")) //Firebrick
        }else if (kanbanTitle.text.equals("W trakcie")){
            kanbanTitle.setBackgroundColor(Color.parseColor("#b8860b")) //Dark goldenrod
        }else{
            kanbanTitle.setBackgroundColor(Color.parseColor("#2e8b57")) //Seagreen
        }
    }

    fun getItems() : MutableList<Kanban>{
        return items
    }

    fun setEditedIndex(kanban: Kanban){
        this.index = items.indexOf(kanban)
        items.forEach { item -> item.isEnabled = false }
        notifyDataSetChanged()
    }

}
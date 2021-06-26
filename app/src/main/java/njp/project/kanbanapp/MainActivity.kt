package njp.project.kanbanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var kanbanAdapter : KanbanAdapter
    private lateinit var title : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kanbanAdapter = KanbanAdapter(mutableListOf())
        kanbanList.adapter = kanbanAdapter
        kanbanList.layoutManager = LinearLayoutManager(this)

        val options = arrayOf("Do zrobienia","W trakcie","Zakończone")

        kanbanSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            options
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        kanbanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                title = options[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

        }

        addKanban.setOnClickListener {
            val kanbanContent = kanbanTask.text.toString()
            if(kanbanContent.isNotEmpty()){
                val kanban = Kanban(title,kanbanContent)
                kanbanAdapter.addKanban(kanban)
                kanbanTask.text.clear()
            }
        }
        deleteKanban.setOnClickListener {
            kanbanAdapter.deleteChecked()
        }
        editKanban.setOnClickListener {
            val items = kanbanAdapter.getItems()
            val filtered = items.filter { it.isChecked }
            if(filtered.size == 1){
                val kanban = filtered.first()
                kanbanTask.setText(kanban.content)
                kanbanSpinner.setSelection(options.indexOf(kanban.title))
                saveKanban.isEnabled = true
                deleteKanban.isEnabled = false
                addKanban.isEnabled = false
                editKanban.isEnabled = false
                kanbanAdapter.setEditedIndex(kanban)
            }
        }
        saveKanban.setOnClickListener {
            val kanbanContent = kanbanTask.text.toString()
            if(kanbanContent.isNotEmpty()){
                val kanban = Kanban(title,kanbanContent)
                kanbanTask.text.clear()
                saveKanban.isEnabled = false
                deleteKanban.isEnabled = true
                addKanban.isEnabled = true
                editKanban.isEnabled = true
                kanbanAdapter.updateKanban(kanban)
            }
        }

    }
}
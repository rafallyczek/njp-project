package njp.project.kanbanapp

data class Kanban(
    val title: String,
    val content: String,
    var isChecked: Boolean = false,
    var isEnabled: Boolean = true
)
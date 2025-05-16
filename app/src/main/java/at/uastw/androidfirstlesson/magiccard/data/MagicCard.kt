package at.uastw.androidfirstlesson.magiccard.data

data class MagicCard(
    val name: String,
    val types: List<String>,
    val colors: List<String> = emptyList(),
    val imageUrl: String?,
    val text: String? = null
)

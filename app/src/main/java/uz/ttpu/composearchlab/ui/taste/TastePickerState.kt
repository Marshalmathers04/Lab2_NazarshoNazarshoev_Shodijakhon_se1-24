package uz.ttpu.composearchlab.ui.taste

const val REQUIRED_LIKES = 3

data class Artist(
    val id: Int,
    val name: String,
    val genre: String
)

val seedArtists = listOf(
    Artist(1, "Nilufar Skye", "Pop"),
    Artist(2, "Amir Neon", "Pop"),
    Artist(3, "Zarina Bloom", "Pop"),
    Artist(4, "Iron Caravan", "Rock"),
    Artist(5, "Qora Tun", "Rock"),
    Artist(6, "Steel Orchard", "Rock"),
    Artist(7, "Midnight Samarkand", "Jazz"),
    Artist(8, "Blue Registan", "Jazz"),
    Artist(9, "Saida Quartet", "Jazz"),
    Artist(10, "Silk Road Duo", "Folk"),
    Artist(11, "Navruz Ensemble", "Folk"),
    Artist(12, "Chor Minor", "Folk")
)

// Comment (Task 10): likedIds is a read-only Set<Int> and updates create a new object via copy().
// Immutable UI state prevents direct mutation bugs, ensures thread safety, and allows Jetpack Compose
// to perform structural equality checks to skip unnecessary recompositions.
data class TastePickerState(
    val artists: List<Artist>,
    val selectedGenre: String? = null,
    val likedIds: Set<Int> = emptySet()
) {
    val genres: List<String>
        get() = artists.map { it.genre }.distinct()

    val visibleArtists: List<Artist>
        get() = if (selectedGenre == null) artists else artists.filter { it.genre == selectedGenre }

    val likedCount: Int
        get() = likedIds.size

    val canContinue: Boolean
        get() = likedCount >= REQUIRED_LIKES
}

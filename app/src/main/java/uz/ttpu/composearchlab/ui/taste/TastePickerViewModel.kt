package uz.ttpu.composearchlab.ui.taste

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TastePickerViewModel : ViewModel() {

    private val _state = MutableStateFlow(TastePickerState(artists = seedArtists))
    val state: StateFlow<TastePickerState> = _state.asStateFlow()

    fun onGenreClick(genre: String) {
        // selecting the already-selected genre clears the filter (selectedGenre = null)
        _state.update { current ->
            val newGenre = if (current.selectedGenre == genre) null else genre
            current.copy(selectedGenre = newGenre)
        }
    }

    fun onArtistLikeToggled(id: Int) {
        // add the id if it is absent, remove it if it is present
        _state.update { current ->
            val newLikedIds = if (id in current.likedIds) {
                current.likedIds - id
            } else {
                current.likedIds + id
            }
            current.copy(likedIds = newLikedIds)
        }
    }
}

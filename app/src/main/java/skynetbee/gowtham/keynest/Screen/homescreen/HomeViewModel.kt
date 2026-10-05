package skynetbee.gowtham.keynest.Screen.homescreen

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import skynetbee.gowtham.keynest.data.local.KeyNestDatabase
import skynetbee.gowtham.keynest.data.local.PasswordEntity

data class HomeUiState(
    val searchQuery: String = "",
    val selectedCategoryIndex: Int = 0, // 0: All, 1: Social, 2: Work, 3: Finance, 4: Personal
    val isLoading: Boolean = false,
    val copiedPasswordId: Long? = null
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val passwordDao = KeyNestDatabase.getDatabase(application).passwordDao()
    private val clipboardManager = application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val categories = listOf("All", "Social", "Work", "Finance", "Personal")

    // Reactive database stream mapped against current search & category selection
    val filteredPasswords: StateFlow<List<PasswordEntity>> = combine(
        passwordDao.getAllPasswords(),
        _uiState
    ) { dbPasswords, state ->
        val selectedCategory = categories.getOrElse(state.selectedCategoryIndex) { "All" }
        dbPasswords.filter { item ->
            val matchesCategory = selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)
            val matchesSearch = item.title.contains(state.searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onCategorySelected(index: Int) {
        _uiState.update { it.copy(selectedCategoryIndex = index) }
    }

    fun toggleFavorite(item: PasswordEntity) {
        viewModelScope.launch {
            passwordDao.updatePassword(item.copy(isFavorite = !item.isFavorite))
        }
    }

    fun copyPasswordToClipboard(item: PasswordEntity) {
        viewModelScope.launch {
            // Copy plain text to Android System Clipboard
            val clip = ClipData.newPlainText("KeyNest Password", item.encryptedPassword)
            clipboardManager.setPrimaryClip(clip)

            _uiState.update { it.copy(copiedPasswordId = item.id) }
            delay(2000)
            _uiState.update { it.copy(copiedPasswordId = null) }
        }
    }

    fun deletePassword(id: Long) {
        viewModelScope.launch {
            passwordDao.deletePasswordById(id)
        }
    }
}
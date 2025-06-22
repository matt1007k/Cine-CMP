package dev.maxmeza.cineapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val fruitList = listOf(
        "Apple", "Banana", "Orange", "Grape", "Strawberry", "Watermelon", "Mango", "Pineapple", "Peach", "Cherry"
    )
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<String>>(emptyList())
    val searchResults: StateFlow<List<String>> = _searchResults.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(600)
                .distinctUntilChanged()
                .mapLatest { query ->
                    filterFruits(query)
                }
                .collect { filtered ->
                    _searchResults.value = filtered
                }
        }
    }

    private fun CoroutineScope.filterFruits(query: String): List<String> {
        return fruitList.filter { it.contains(query, ignoreCase = true) }
    }

    fun onChangeQueryChange(value: String) {
        _searchQuery.value = value
    }


}
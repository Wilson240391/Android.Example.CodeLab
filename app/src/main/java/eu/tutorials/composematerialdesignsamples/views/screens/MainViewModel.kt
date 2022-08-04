package eu.tutorials.composematerialdesignsamples.views.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.composematerialdesignsamples.MainApp
import eu.tutorials.composematerialdesignsamples.Util.ArticleCategory
import eu.tutorials.composematerialdesignsamples.Util.getArticleCategory
import eu.tutorials.composematerialdesignsamples.domain.models.news.NewsResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repositoryApi

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> get() = _isError
    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _isError.value = true
        }
    }

    private val _newResponse = MutableStateFlow(NewsResponse())
    val newsResponse: StateFlow<NewsResponse> get() = _newResponse
    fun getTopArticles() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler){
            _newResponse.value = repository.getArticles()
            _isLoading.value = false
        }
    }

    private val _getArticleByCategory = MutableStateFlow(NewsResponse())
    val getArticleByCategory: StateFlow<NewsResponse> get() = _getArticleByCategory
    fun getArticlesByCategory(category: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _getArticleByCategory.value = repository.getArticleByCategory(category = category)
            _isLoading.value = false
        }
    }

    private val _selectedCategory: MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?> get() = _selectedCategory
    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        _selectedCategory.value = newCategory
    }

    val sourceName = MutableStateFlow("engadget")
    private val _getArticleBySource = MutableStateFlow(NewsResponse())
    val getArticleBySource: StateFlow<NewsResponse> get() = _getArticleBySource
    fun getArticleBySource() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler){
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
            _isLoading.value = false
        }
    }

    val query = MutableStateFlow("")
    private val _searchedNewsResponse = MutableStateFlow(NewsResponse())
    val searchedNewsResponse: StateFlow<NewsResponse> get() = _searchedNewsResponse
    fun getSearchedArticles(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedNewsResponse.value = repository.getSearchedArticles(query)
        }
    }
}

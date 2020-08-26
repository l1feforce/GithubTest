package ru.spbstu.gusev.githubtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spbstu.gusev.githubtest.data.ResultWrapper
import ru.spbstu.gusev.githubtest.data.repositories.GithubRepository
import ru.spbstu.gusev.githubtest.di.DI
import ru.spbstu.gusev.githubtest.model.GithubRepositoryModel
import toothpick.Toothpick
import javax.inject.Inject

class RepositoriesListViewModel : ViewModel() {
    private val scope = Toothpick.openScope(DI.APP_SCOPE)

    @Inject
    lateinit var githubRepository: GithubRepository
    val repositoriesListRefreshResponse =
        MutableLiveData<ResultWrapper<List<GithubRepositoryModel>>>()
    val repositoriesListToAddResponse =
        MutableLiveData<ResultWrapper<List<GithubRepositoryModel>>>()
    val isLoading = MutableLiveData(true)

    init {
        Toothpick.inject(this, scope)
    }

    fun refreshRepositoriesList() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            repositoriesListRefreshResponse.value =
                githubRepository.getPublicRepositoriesList()
            isLoading.value = false
        }
    }

    fun addToRepositoriesList(since: String? = null) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            repositoriesListToAddResponse.value = githubRepository.getPublicRepositoriesList(since)
            isLoading.value = false
        }
    }

}
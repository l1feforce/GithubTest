package ru.spbstu.gusev.githubtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spbstu.gusev.githubtest.di.DI
import ru.spbstu.gusev.githubtest.data.ResultWrapper
import ru.spbstu.gusev.githubtest.data.repositories.GithubRepository
import ru.spbstu.gusev.githubtest.model.CommitModel
import toothpick.Toothpick
import javax.inject.Inject

class RepositoryDetailsViewModel : ViewModel() {
    private val scope = Toothpick.openScope(DI.APP_SCOPE)

    @Inject
    lateinit var githubRepository: GithubRepository
    val commitsResponse =
        MutableLiveData<ResultWrapper<List<CommitModel>>>()
    val isLoading = MutableLiveData(true)

    init {
        Toothpick.inject(this, scope)
    }

    fun refreshCommits(url: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            commitsResponse.value =
                githubRepository.getCommits(url)
            isLoading.value = false
        }
    }
}
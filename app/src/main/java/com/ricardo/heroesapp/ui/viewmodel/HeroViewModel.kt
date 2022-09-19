package com.ricardo.heroesapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardo.heroesapp.data.model.Hero
import com.ricardo.heroesapp.domain.HeroUseCases
import kotlinx.coroutines.launch

class HeroViewModel : ViewModel() {

    private val _heroesLiveData = MutableLiveData<List<Hero>>()
    val heroesList: LiveData<List<Hero>> = _heroesLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val heroUseCases = HeroUseCases()

    init {
        _isLoading.postValue(true)
    }

    fun getAllHeroes(total: Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            heroUseCases.getAllHeroes(total).collect { response ->
                if (response.isSuccessful) {
                    _heroesLiveData.postValue(response.body()?.data?.results ?: emptyList())
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun getHeroDetail(heroId: Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            heroUseCases.getHeroDetail(heroId).collect { response ->
                if (response.isSuccessful) {
                    _heroesLiveData.postValue(response.body()?.data?.results ?: emptyList())
                }
                _isLoading.postValue(false)
            }
        }
    }
}
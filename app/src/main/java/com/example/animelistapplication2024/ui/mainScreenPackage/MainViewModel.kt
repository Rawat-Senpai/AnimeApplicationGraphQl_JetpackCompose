package com.example.animelistapplication2024.ui.mainScreenPackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.animelistapplication2024.repository.MainRepository
import com.example.animelistapplication2024.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel() {

    fun getAnimeList() = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading())
        try {
            val apiResponse = mainRepository.getAnimeList()
            if (!apiResponse.hasErrors()) {
                emit(NetworkResult.Success(apiResponse.data?.Page))
            } else {
                emit(NetworkResult.Error(null, apiResponse.errors.toString()))
            }
        } catch (exception: Exception) {
            emit(exception.localizedMessage?.let { NetworkResult.Error(null, it) })
        }
    }


}
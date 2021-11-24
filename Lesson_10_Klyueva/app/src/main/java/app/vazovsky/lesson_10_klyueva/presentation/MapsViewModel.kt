package app.vazovsky.lesson_10_klyueva.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.vazovsky.lesson_10_klyueva.data.State
import app.vazovsky.lesson_10_klyueva.data.remote.BridgeApi
import kotlinx.coroutines.launch

class MapsViewModel : ViewModel() {
    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    fun loadBridges() {
        viewModelScope.launch {
            try {
                _stateLiveData.postValue(State.Loading())
                val bridges = BridgeApi.apiService.getBridges()
                _stateLiveData.postValue(State.Data(bridges))
            } catch (e: Exception) {
                _stateLiveData.postValue(State.Error(e))
            }
        }
    }
}
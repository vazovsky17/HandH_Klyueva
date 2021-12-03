package app.vazovsky.lesson_12_klyueva.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.data.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    fun loadBridgeDetail(id: Int) {
        viewModelScope.launch {
            _stateLiveData.postValue(State.Loading)
            try {
                val bridge = repository.getBridgeDetail(id)
                _stateLiveData.postValue(State.Data(bridge))
            } catch (e: Exception) {
                _stateLiveData.postValue(State.Error(e))
            }
        }
    }
}
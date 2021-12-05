package app.vazovsky.lesson_12_klyueva.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.vazovsky.lesson_12_klyueva.data.model.Bridge
import app.vazovsky.lesson_12_klyueva.data.model.State
import app.vazovsky.lesson_12_klyueva.data.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _stateLiveData = MutableLiveData<State<List<Bridge>>>()
    val stateLiveData: LiveData<State<List<Bridge>>> = _stateLiveData

    fun loadBridges() {
        viewModelScope.launch {
            _stateLiveData.postValue(State.Loading())
            try {
                val bridges = repository.getBridges()
                _stateLiveData.postValue(State.Data(bridges))
            } catch (e: Exception) {
                _stateLiveData.postValue(State.Error(e))
            }
        }
    }
}
package app.vazovsky.lesson_7_klyueva.presentation.bridgelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.vazovsky.lesson_7_klyueva.data.BridgeState
import app.vazovsky.lesson_7_klyueva.data.remote.BridgeApi
import kotlinx.coroutines.launch

class BridgeListViewModel : ViewModel() {

    private val _bridgeListStateLiveData = MutableLiveData<BridgeState>()
    val bridgeListStateLiveData: LiveData<BridgeState> = _bridgeListStateLiveData

    fun loadBridges() {
        viewModelScope.launch {
            try {
                _bridgeListStateLiveData.postValue(BridgeState.Loading())
                val bridges = BridgeApi.apiService.getBridges()
                _bridgeListStateLiveData.postValue(BridgeState.Data(bridges))
            } catch (e: Exception) {
                _bridgeListStateLiveData.postValue(BridgeState.Error(e))
            }
        }
    }
}
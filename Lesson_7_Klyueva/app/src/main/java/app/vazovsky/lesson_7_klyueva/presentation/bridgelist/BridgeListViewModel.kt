package app.vazovsky.lesson_7_klyueva.presentation.bridgelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.vazovsky.lesson_7_klyueva.data.remote.BridgeApi
import kotlinx.coroutines.launch

class BridgeListViewModel : ViewModel() {

    val bridgeState = MutableLiveData<BrigdeState>()

    fun loadBridges() {
        viewModelScope.launch {
            try {
                bridgeState.postValue(BrigdeState.Loading())
                val bridges = BridgeApi.apiService.getBridges()
                bridgeState.postValue(BrigdeState.Data(bridges))
            } catch (e: Exception) {
                bridgeState.postValue(BrigdeState.Error(e))
            }
        }
    }
}
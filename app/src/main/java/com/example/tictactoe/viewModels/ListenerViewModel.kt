package com.example.tictactoe.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListenerViewModel: ViewModel()
{
    private val _isTwoPlayers = MutableLiveData<Boolean>()
    val isTwoPlayers: LiveData<Boolean> get() = _isTwoPlayers

    private val _isXSelected = MutableLiveData<Boolean>()
    val isXSelected: LiveData<Boolean> get() = _isXSelected

    fun setIsTwoPlayers(isTwoPlayers: Boolean)
    {
        _isTwoPlayers.value = isTwoPlayers
    }

    fun setIsXSelected(isX: Boolean)
    {
        _isXSelected.value = isX
    }
}
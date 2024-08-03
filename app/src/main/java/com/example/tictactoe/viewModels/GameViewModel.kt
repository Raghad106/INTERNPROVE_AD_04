package com.example.tictactoe.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    private val _board = MutableLiveData<Array<IntArray>>()
    val board: LiveData<Array<IntArray>> get() = _board

    private val _winner = MutableLiveData<String?>()
    val winner: LiveData<String?> get() = _winner

    private var userIsX = true // true = User is X, false = User is O
    private var currentPlayerIsX = true // true = Current player is X, false = Current player is O
    private var isTwoPlayers = false

    init
    {
        resetBoard()
    }

    fun setPlayerSymbol(isX: Boolean)
    {
        userIsX = isX
        currentPlayerIsX = isX // Ensure player starts as X for a new game
        resetBoard()
    }

    fun setTwoPlayers(isTwoPlayers: Boolean)
    {
        this.isTwoPlayers = isTwoPlayers
    }

    fun makeMove(row: Int, col: Int)
    {
        val currentBoard = _board.value ?: return
        if (currentBoard[row][col] == -1 && _winner.value == null)
        {
            currentBoard[row][col] = if (currentPlayerIsX) 1 else 0
            _board.value = currentBoard

            if (checkWinner())
            {
                _winner.value = if (currentPlayerIsX) "X" else "O"
            }
            else if (isBoardFull())
            {
                _winner.value = "Draw"
            }
            else
            {
                currentPlayerIsX = !currentPlayerIsX
                if (!isTwoPlayers && currentPlayerIsX != userIsX)
                {
                    aiMove()
                }
            }
        }
    }

    fun resetBoard()
    {
        _board.value = Array(3) { IntArray(3) { -1 } }
        _winner.value = null
       
    }

    private fun checkWinner(): Boolean
    {
        val currentBoard = _board.value ?: return false

        for (i in 0..2) {
            if (currentBoard[i][0] == currentBoard[i][1] && currentBoard[i][0] == currentBoard[i][2] && currentBoard[i][0] != -1)
            {
                return true
            }
        }

        for (i in 0..2) {
            if (currentBoard[0][i] == currentBoard[1][i] && currentBoard[0][i] == currentBoard[2][i] && currentBoard[0][i] != -1)
            {
                return true
            }
        }

        if (currentBoard[0][0] == currentBoard[1][1] && currentBoard[0][0] == currentBoard[2][2] && currentBoard[0][0] != -1)
        {
            return true
        }

        if (currentBoard[0][2] == currentBoard[1][1] && currentBoard[0][2] == currentBoard[2][0] && currentBoard[0][2] != -1)
        {
            return true
        }

        return false
    }

    private fun isBoardFull(): Boolean
    {
        val currentBoard = _board.value ?: return false
        for (i in 0..2)
        {
            for (j in 0..2)
            {
                if (currentBoard[i][j] == -1)
                {
                    return false
                }
            }
        }
        return true
    }

    private fun aiMove() {
        viewModelScope.launch {
            delay(500) // 0.5 seconds delay
            val currentBoard = _board.value ?: return@launch
            for (i in 0..2) {
                for (j in 0..2) {
                    if (currentBoard[i][j] == -1) {
                        makeMove(i, j)
                        return@launch
                    }
                }
            }
        }
    }
}
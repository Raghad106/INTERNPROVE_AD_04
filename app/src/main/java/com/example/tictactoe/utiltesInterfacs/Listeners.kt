package com.example.tictactoe.utiltesInterfacs

import androidx.fragment.app.Fragment

interface Listeners
{
    fun onFragmentTransform(isTwoPlayers:Boolean)
    fun onXorOSelect(isX:Boolean)
    fun showDialog()
}
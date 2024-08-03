package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.databinding.ActivityMainBinding
import com.example.tictactoe.fragments.GameFragment
import com.example.tictactoe.fragments.SelectNumberOfPlayersFragment
import com.example.tictactoe.fragments.SelectXorOFragment
import com.example.tictactoe.utiltesInterfacs.Listeners
import com.example.tictactoe.viewModels.GameViewModel
import com.example.tictactoe.viewModels.ListenerViewModel

class MainActivity : AppCompatActivity(),Listeners
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var gameViewModel: GameViewModel

    private val listenerViewModel: ListenerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        replaceFragment(SelectNumberOfPlayersFragment())

        listenerViewModel.isTwoPlayers.observe(this, Observer { isTwoPlayers->
                replaceFragment(GameFragment.newInstance(isTwoPlayers))
                showDialog()
        })

        listenerViewModel.isXSelected.observe(this, Observer { isX ->
            gameViewModel.setPlayerSymbol(isX)
        })


    }

    override fun onFragmentTransform(isTwoPlayers: Boolean)
    {
        listenerViewModel.setIsTwoPlayers(isTwoPlayers)
    }

    override fun onXorOSelect(isX: Boolean)
    {
        listenerViewModel.setIsXSelected(isX)
    }

    override fun showDialog()
    {
        val dialog:SelectXorOFragment=SelectXorOFragment()
        dialog.show(supportFragmentManager, "XOrO")
    }

    private fun replaceFragment(fragment:Fragment)
    {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FLFragmentContainer, fragment)
            .addToBackStack("back")
            .commit()
    }

}
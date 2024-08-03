package com.example.tictactoe.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.databinding.FragmentSelectNumberOfPlayersBinding
import com.example.tictactoe.utiltesInterfacs.Listeners
import com.example.tictactoe.viewModels.GameViewModel
import com.example.tictactoe.viewModels.ListenerViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
class GameFragment : Fragment()
{
    private var isTwoPlayers: Boolean = false

    private lateinit var gameViewModel: GameViewModel
    private lateinit var listenerViewModel: ListenerViewModel

    private var _binding:FragmentGameBinding?=null
    private val binding get() = requireNotNull(_binding)
    private lateinit var listener: Listeners

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if (context is Listeners)
            listener=context
        else
            throw RuntimeException("$context must implement OnOperationSelectedListener")
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isTwoPlayers = it.getBoolean(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding= FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        listenerViewModel = ViewModelProvider(requireActivity()).get(ListenerViewModel::class.java)

        val isTwoPlayers = arguments?.getBoolean(ARG_PARAM1) ?: false
        gameViewModel.setTwoPlayers(isTwoPlayers)

        listenerViewModel.isXSelected.observe(viewLifecycleOwner, Observer { isX ->
            gameViewModel.setPlayerSymbol(isX)
        })

        gameViewModel.board.observe(viewLifecycleOwner, Observer { board ->
            updateBoardUI(board)
        })

        gameViewModel.winner.observe(viewLifecycleOwner, Observer { winner ->
            winner?.let {
                showWinnerDialog(it)
            }
        })

        setBoardClickListeners(binding.root)
    }

    //Updates the text of the buttons based on the board state
    private fun updateBoardUI(board: Array<IntArray>)
    {
        binding.button00.text = getButtonText(board[0][0])
        binding.button01.text = getButtonText(board[0][1])
        binding.button02.text = getButtonText(board[0][2])
        binding.button10.text = getButtonText(board[1][0])
        binding.button11.text = getButtonText(board[1][1])
        binding.button12.text = getButtonText(board[1][2])
        binding.button20.text = getButtonText(board[2][0])
        binding.button21.text = getButtonText(board[2][1])
        binding.button22.text = getButtonText(board[2][2])
    }

    //Returns the appropriate text for a button based on the cell value
    private fun getButtonText(value: Int): String
    {
        return when (value)
        {
            1 -> "X"
            0 -> "O"
            else -> ""
        }
    }

    private fun setBoardClickListeners(view: View)
    {
        val buttons = listOf(
            binding.button00, binding.button01, binding.button02,
            binding.button10, binding.button11, binding.button12,
            binding.button20, binding.button21, binding.button22
        )

        //forEachIndexed is a Kotlin extension function that iterates over each element in the list, providing both the element (button) and its index (index)
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val row = index / 3
                val col = index % 3
                gameViewModel.makeMove(row, col)
               // button.isEnabled = false // Disable the button after it’s clicked
            }
        }

        binding.resetButton.setOnClickListener {
            gameViewModel.resetBoard()
            listener.showDialog()
        }
    }

    private fun showWinnerDialog(winner: String)
    {
        AlertDialog.Builder(requireContext())
            .setTitle("The winner is:")
            .setMessage("$winner")
            .setPositiveButton("OK")
            { dialog, _ ->
                dialog.dismiss()
                gameViewModel.resetBoard()
                listener.showDialog()
            }
            .show()
    }
    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    companion object
    {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(isTwoPlayers: Boolean) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1,isTwoPlayers)
                }
            }
    }
}
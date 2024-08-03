package com.example.tictactoe.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentSelectNumberOfPlayersBinding
import com.example.tictactoe.utiltesInterfacs.Listeners
import com.example.tictactoe.viewModels.ListenerViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class SelectNumberOfPlayersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSelectNumberOfPlayersBinding?=null
    private val binding get() = requireNotNull(_binding)
    private val listenerViewModel: ListenerViewModel by activityViewModels()
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
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding= FragmentSelectNumberOfPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.BTOnePlayer.setOnClickListener {
            listenerViewModel.setIsTwoPlayers(false)
        }
        binding.BTTowPlayer.setOnClickListener {
            listenerViewModel.setIsTwoPlayers(true)
        }
    }

    companion object
    {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectNumberOfPlayersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
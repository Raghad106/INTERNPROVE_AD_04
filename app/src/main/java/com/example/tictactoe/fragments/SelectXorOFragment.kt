package com.example.tictactoe.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentSelectXorOBinding
import com.example.tictactoe.utiltesInterfacs.Listeners
import com.example.tictactoe.viewModels.ListenerViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SelectXorOFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
class SelectXorOFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var isTwoPlayers: Boolean = false
    private lateinit var listener:Listeners

    private var _binding:FragmentSelectXorOBinding?=null
    private val binding get() = requireNotNull(_binding)
    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if (context is Listeners)
            listener=context
        else
            throw RuntimeException("\"$context must implement OnOperationSelectedListener\"")
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
        _binding= FragmentSelectXorOBinding.inflate(inflater,container,false)
        binding.BTX.setOnClickListener {
            listener.onXorOSelect( true)
            dismiss()
        }

        binding.BTO.setOnClickListener {
            listener.onXorOSelect(false)
            dismiss()
        }
        return binding.root
    }

    override fun onStart()
    {
        super.onStart()
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
    }

    companion object
    {
        @JvmStatic
        fun newInstance(isTwoPlayers:Boolean) =
            SelectXorOFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1,isTwoPlayers)
                }
            }
    }
}
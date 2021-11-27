package com.example.messageapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messageapp.databinding.FragmentMessageDetailBinding
import com.example.messageapp.ui.fragment.AllMessageFragment.Companion.MESSAGE_BODY
import com.example.messageapp.ui.fragment.AllMessageFragment.Companion.MESSAGE_HEADER

class MessageDetailFragment : Fragment() {
    private var binding: FragmentMessageDetailBinding? = null

    private val messageName by lazy {
        requireArguments().getString(MESSAGE_HEADER)
    }

    private val messageBody by lazy {
        requireArguments().getString(MESSAGE_BODY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvName?.text=messageName
        binding?.tvBody?.text=messageBody
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            MessageDetailFragment()
    }
}
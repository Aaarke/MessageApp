package com.example.messageapp.ui.fragment

import android.Manifest.permission.READ_SMS
import android.Manifest.permission.RECEIVE_SMS
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.example.messageapp.databinding.AllMessageFragmentBinding
import com.example.messageapp.model.LocalMessage
import com.example.messageapp.ui.adapter.AllMessageAdapter
import com.example.messageapp.ui.viewmodel.AllMessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.messageapp.R
import com.example.messageapp.ui.adapter.HeaderAdapter
import java.util.*


@AndroidEntryPoint
class AllMessageFragment : Fragment() {
    private var binding: AllMessageFragmentBinding? = null

    companion object {
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 100
        const val MESSAGE_HEADER = "message_header"
        const val MESSAGE_BODY = "message_body"
        fun newInstance() = AllMessageFragment()
    }

    private var viewModel:AllMessageViewModel?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllMessageFragmentBinding.inflate(inflater, container, false)
        viewModel= ViewModelProvider(requireActivity())[AllMessageViewModel::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkPermissions()) {
            observeViewModel()
        }
    }



    private fun observeViewModel() {
        viewModel?.apply {
            getAllSms()
            allLocalMessageList.observe(viewLifecycleOwner, {
                it?.let { linkedHashmap->
                    binding?.processMap(linkedHashmap)
                }
            })
            navigateToDetail.observe(viewLifecycleOwner,{
                it?.let {
                    navigateToMessageDetail(it.first,it.second)
                }
            })
        }
    }

    private fun AllMessageFragmentBinding.processMap(linkedHashmap: TreeMap<Long, MutableList<LocalMessage>?>) {
        val concatAdapter = ConcatAdapter()
        for ((key, list) in linkedHashmap.entries) {
            list?.let {
                val allMessageAdapter = AllMessageAdapter { currentMessage ->
                    navigateToMessageDetail(currentMessage)
                }
                val headerAdapter=HeaderAdapter()
                allMessageAdapter.allMessage=it
                headerAdapter.allMessage= mutableListOf(LocalMessage("","","",Date(),"",key))
                concatAdapter.addAdapter(headerAdapter)
                concatAdapter.addAdapter(allMessageAdapter)
            }

        }
        rvMessages.adapter = concatAdapter

    }

    private fun navigateToMessageDetail(currentMessage: LocalMessage) {
        var bundle=Bundle()
        bundle.putString(MESSAGE_HEADER,currentMessage.number)
        bundle.putString(MESSAGE_BODY,currentMessage.body)
        findNavController().navigate(R.id.messageDetailFragment,bundle)
    }

    private fun navigateToMessageDetail(header: String,body:String) {
        var bundle=Bundle()
        bundle.putString(MESSAGE_HEADER,header)
        bundle.putString(MESSAGE_BODY,body)
        findNavController().navigate(R.id.messageDetailFragment,bundle)
    }




    private fun checkPermissions(): Boolean {
        return if (requireContext().let {
                ContextCompat.checkSelfPermission(
                    it,
                    READ_SMS
                )
            } != PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Request Permissions")
            requestMultiplePermissions.launch(
                arrayOf(READ_SMS,RECEIVE_SMS)
            )
            false
        } else {
            true
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("TAG", "${it.key} = ${it.value}")
            }
            if (permissions[READ_SMS] == true) {
                observeViewModel()
            } else {
                checkPermissions()
            }
            if (permissions[RECEIVE_SMS] == false) {
                checkPermissions()

            }
        }




    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
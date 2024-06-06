package com.github.lucascalheiros.waterreminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.lucascalheiros.waterreminder.R


class RootFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findNavController().navigate(R.id.action_rootFragment_to_mainAppFlowFragment)
        return inflater.inflate(R.layout.fragment_root, container, false)
    }

}
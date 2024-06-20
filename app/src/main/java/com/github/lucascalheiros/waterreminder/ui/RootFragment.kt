package com.github.lucascalheiros.waterreminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.lucascalheiros.waterreminder.R
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.IsFirstAccessCompletedUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class RootFragment : Fragment() {

    private val isFirstAccessCompletedUseCase: IsFirstAccessCompletedUseCase by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            if (isFirstAccessCompletedUseCase()) {
                findNavController().navigate(R.id.action_rootFragment_to_mainAppFlowFragment)
            } else {
                findNavController().navigate(R.id.action_rootFragment_to_firstAccessFlowFragment)
            }
        }
        return inflater.inflate(R.layout.fragment_root, container, false)
    }

}
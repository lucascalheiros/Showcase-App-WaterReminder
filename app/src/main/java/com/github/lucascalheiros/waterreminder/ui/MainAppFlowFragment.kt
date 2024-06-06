package com.github.lucascalheiros.waterreminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.github.lucascalheiros.waterreminder.R
import com.github.lucascalheiros.waterreminder.databinding.FragmentMainAppFlowBinding


class MainAppFlowFragment : Fragment() {

    private var binding: FragmentMainAppFlowBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainAppFlowBinding.inflate(inflater, container, false).apply {
        binding = this
        setupBottomNavBar()
        setupEdgeToEdgeInsets()
    }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun FragmentMainAppFlowBinding.setupBottomNavBar() {
        val navController =
            childFragmentManager.findFragmentById(R.id.fcvMainAppNav) as NavHostFragment
        navView.setupWithNavController(navController.navController)
    }

    private fun FragmentMainAppFlowBinding.setupEdgeToEdgeInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(navView) { view: View, insets: WindowInsetsCompat ->
            val bottomInset =
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            view.updateLayoutParams<LinearLayout.LayoutParams> {
                bottomMargin = bottomInset
            }
            insets
        }
    }

}
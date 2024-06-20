package com.github.lucascalheiros.waterreminder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.lucascalheiros.waterreminder.databinding.ActivityMainBinding
import com.github.lucascalheiros.waterreminder.ui.setups.setupEdgeToEdge
import com.github.lucascalheiros.waterreminder.ui.setups.setupOrientation
import com.github.lucascalheiros.waterreminder.ui.setups.setupSplashScreen


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupOrientation()
        setupSplashScreen()
        setupEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }

}

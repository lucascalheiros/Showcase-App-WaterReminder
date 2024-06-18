package com.github.lucascalheiros.waterreminder.ui.setups

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.setupOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}
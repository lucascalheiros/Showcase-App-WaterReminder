package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput.ActivityLevelInputFragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.confirmationinput.ConfirmationInputFragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.introducing.IntroducingFragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.notificationinput.NotificationInputFragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput.TemperatureLevelInputFragment
import com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.weightinput.WeightInputFragment

class FirstAccessFlowPageAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val fragmentsList = listOf(
        IntroducingFragment(),
        WeightInputFragment(),
        ActivityLevelInputFragment(),
        TemperatureLevelInputFragment(),
        NotificationInputFragment(),
        ConfirmationInputFragment()
    )

    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }
}
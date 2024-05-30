package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.notificationtime.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.ContextualPosition
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.setSurfaceListBackground
import com.github.lucascalheiros.waterremindermvp.common.ui.helpers.showDivider
import com.github.lucascalheiros.waterremindermvp.feature.settings.databinding.ListItemAddNotificationTimeBinding

class AddNotificationTimeViewHolder(
    private val binding: ListItemAddNotificationTimeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(onAddScheduleClick: () -> Unit) {
        binding.llContainer.setOnClickListener {
            onAddScheduleClick.invoke()
        }
    }

    fun updateContextualUI(contextualPosition: ContextualPosition) {
        binding.root.setSurfaceListBackground(contextualPosition)
        binding.divider.isVisible = contextualPosition.showDivider
    }

    companion object {
        fun inflate(parent: ViewGroup): AddNotificationTimeViewHolder =
            AddNotificationTimeViewHolder(
                ListItemAddNotificationTimeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )
    }
}

package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterreminder.feature.settings.databinding.ViewScreenSectionTitleBinding

class SectionTitleViewHolder(private val binding: ViewScreenSectionTitleBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(@StringRes title: Int) {
        binding.tvSectionTitle.setText(title)
    }

    companion object {
        fun inflate(parent: ViewGroup): SectionTitleViewHolder = SectionTitleViewHolder(
            ViewScreenSectionTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
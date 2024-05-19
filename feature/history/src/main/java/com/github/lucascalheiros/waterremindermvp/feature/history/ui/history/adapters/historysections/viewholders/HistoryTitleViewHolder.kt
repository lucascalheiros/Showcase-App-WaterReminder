package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lucascalheiros.waterremindermvp.feature.history.R
import com.github.lucascalheiros.waterremindermvp.feature.history.databinding.ViewScreenTitleBinding

class HistoryTitleViewHolder(binding: ViewScreenTitleBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.tvTitle.setText(R.string.history)
    }

    companion object {
        fun inflate(parent: ViewGroup): HistoryTitleViewHolder = HistoryTitleViewHolder(
            ViewScreenTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
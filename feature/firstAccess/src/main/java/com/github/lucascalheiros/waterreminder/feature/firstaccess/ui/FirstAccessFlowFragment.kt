package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.github.lucascalheiros.waterreminder.feature.firstaccess.R
import com.github.lucascalheiros.waterreminder.feature.firstaccess.databinding.FragmentFirstAccessFlowBinding


class FirstAccessFlowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFirstAccessFlowBinding.inflate(inflater, container, false).apply {

        // Set up the LinearLayoutManager with center snapping
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        // Create and set adapter
        val items = (0..100).map { it.toString() }
        val adapter = NumberPickerAdapter(items)
        recyclerView.adapter = adapter

        // Attach SnapHelper to center items
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


    }.root

}

class NumberPickerAdapter(private val items: List<String>) :
    RecyclerView.Adapter<NumberPickerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tvDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.picker_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}



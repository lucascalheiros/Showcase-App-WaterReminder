package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.github.lucascalheiros.waterremindermvp.common.ui.databinding.DialogContentListBinding
import com.github.lucascalheiros.waterremindermvp.common.ui.getThemeAwareColor
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.feature.home.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createSelectWaterSourceDialog(
    waterSourceTypeList: List<WaterSourceType>,
    onConfirm: (WaterSourceType) -> Unit
): AlertDialog {

    val binding =
        DialogContentListBinding.inflate(LayoutInflater.from(this))

    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.select_water_source_type)
        .setView(binding.root)
        .create().also {
            binding.lvData.adapter = SingleSelectionWaterSourceTypeAdapter(this, waterSourceTypeList)

            binding.lvData.setOnItemClickListener { _, _, position, _ ->
                val selectedItem = waterSourceTypeList[position]
                onConfirm(selectedItem)
                it.dismiss()
            }
        }
}

private class SingleSelectionWaterSourceTypeAdapter(context: Context, items: List<WaterSourceType>) :
    ArrayAdapter<WaterSourceType>(context, R.layout.simple_selectable_centered_list_item, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val waterSourceType = getItem(position) ?: return view
        val color = waterSourceType.run {
            view.context.getThemeAwareColor(
                lightColor,
                darkColor
            )
        }.toInt()
        val textView: TextView = view.findViewById(android.R.id.text1)
        textView.text = waterSourceType.name
        textView.setTextColor(color)
        return view
    }
}
package com.github.lucascalheiros.waterreminder.common.ui.pickers.color

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.github.lucascalheiros.waterreminder.common.ui.databinding.ViewHsvColorPickerBinding

class HsvColorPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var hueValue = 0f
    private var brightnessValue = 1f
    private var saturationValue = 1f

    private val binding =
        ViewHsvColorPickerBinding.inflate(LayoutInflater.from(context), this).apply {
            orientation = VERTICAL
            setVerticalGravity(Gravity.CENTER)
            setHorizontalGravity(Gravity.CENTER)
            hueSlider.colorFromValue = {
                val hue = it.toFloat() * 360f
                val hsv = FloatArray(3)
                hsv[0] = hue
                hsv[1] = brightnessValue
                hsv[2] = saturationValue
                Color.HSVToColor(hsv)
            }
            brightnessSlider.colorFromValue = {
                val brightness = it.toFloat()
                val hsv = FloatArray(3)
                hsv[0] = hueValue
                hsv[1] = brightness
                hsv[2] = saturationValue
                Color.HSVToColor(hsv)
            }
            saturationSlider.colorFromValue = {
                val saturation = it.toFloat()
                val hsv = FloatArray(3)
                hsv[0] = hueValue
                hsv[1] = brightnessValue
                hsv[2] = saturation
                Color.HSVToColor(hsv)
            }
            hueSlider.onColorChange = {
                hueValue = it.toFloat() * 360f
                saturationSlider.invalidate()
                brightnessSlider.invalidate()
                updateColor()
            }
            brightnessSlider.onColorChange = {
                brightnessValue = it.toFloat()
                hueSlider.invalidate()
                saturationSlider.invalidate()
                updateColor()
            }
            saturationSlider.onColorChange = {
                saturationValue = it.toFloat()
                brightnessSlider.invalidate()
                hueSlider.invalidate()
                updateColor()
            }
        }

    fun setColor(@ColorInt color: Int) {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hueValue = hsv[0]
        brightnessValue = hsv[1]
        saturationValue = hsv[2]
        with(binding) {
            hueSlider.currentValue = hueValue / 360.0
            brightnessSlider.currentValue = brightnessValue.toDouble()
            saturationSlider.currentValue = saturationValue.toDouble()
        }
    }

    fun getColor(): Int {
        val hsv = FloatArray(3)
        hsv[0] = hueValue
        hsv[1] = brightnessValue
        hsv[2] = saturationValue
        return Color.HSVToColor(hsv)
    }

    private fun updateColor() {
        val hsv = FloatArray(3)
        hsv[0] = hueValue
        hsv[1] = brightnessValue
        hsv[2] = saturationValue
        binding.cvColorIndicator.setCardBackgroundColor(getColor())
    }
}
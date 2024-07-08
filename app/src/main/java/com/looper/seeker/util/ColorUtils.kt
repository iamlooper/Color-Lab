package com.looper.seeker.util

import android.graphics.Color
import androidx.core.graphics.ColorUtils

object ColorUtils {

    /**
     * Applies a content style to the color, reducing its brightness slightly.
     * @param color the original color in integer format.
     * @return the adjusted color with reduced brightness.
     */
    fun applyContentStyle(color: Int): Int {
        return adjustBrightness(color, 0.9f)
    }

    /**
     * Applies an expressive style to the color, increasing its saturation to enhance vibrancy.
     * @param color the original color in integer format.
     * @return the adjusted color with increased saturation.
     */
    fun applyExpressiveStyle(color: Int): Int {
        return adjustSaturation(color, 1.3f)
    }

    /**
     * Applies a fidelity style to the color, slightly increasing both saturation and brightness
     * to enhance the original color while keeping it true to its form.
     * @param color the original color in integer format.
     * @return the adjusted color with increased saturation and brightness.
     */
    fun applyFidelityStyle(color: Int): Int {
        return adjustSaturation(adjustBrightness(color, 1.1f), 1.1f)
    }

    /**
     * Applies a fruit salad style to the color, creating a two-toned effect by shifting the hue
     * and blending it with the original color.
     * @param color the original color in integer format.
     * @return the blended color with a shifted hue.
     */
    fun applyFruitSaladStyle(color: Int): Int {
        val shiftedHueColor = shiftHue(color, 45f)
        return blendColors(color, shiftedHueColor, 0.5f)
    }

    /**
     * Applies a monochrome style to the color by reducing its saturation, creating a monochromatic effect.
     * @param color the original color in integer format.
     * @return the adjusted color with reduced saturation.
     */
    fun applyMonoChromeStyle(color: Int): Int {
        return adjustSaturation(color, 0.2f)
    }

    /**
     * Applies a neutral style to the color by reducing its saturation to create a low vibrancy effect.
     * @param color the original color in integer format.
     * @return the adjusted color with reduced saturation.
     */
    fun applyNeutralStyle(color: Int): Int {
        return adjustSaturation(color, 0.7f)
    }

    /**
     * Applies a rainbow style to the color by moderately adjusting its saturation and blending it with
     * a neutral toned version of the same color.
     * @param color the original color in integer format.
     * @return the blended color with chromatic accents.
     */
    fun applyRainbowStyle(color: Int): Int {
        val accentedColor = adjustSaturation(color, 1.2f)
        val neutralColor = adjustSaturation(color, 0.8f)
        return blendColors(accentedColor, neutralColor, 0.6f)
    }

    /**
     * Applies a tonal spot style to the color, adjusting its saturation moderately to achieve mid-vibrancy.
     * @param color the original color in integer format.
     * @return the adjusted color with moderate saturation.
     */
    fun applyTonalSpotStyle(color: Int): Int {
        return adjustSaturation(color, 1.15f)
    }

    /**
     * Applies a vibrant style to the color, significantly increasing its saturation and slightly rotating the hue.
     * @param color the original color in integer format.
     * @return the adjusted color with increased vibrancy and a subtle hue shift.
     */
    fun applyVibrantStyle(color: Int): Int {
        return adjustSaturation(shiftHue(color, 10f), 1.3f)
    }

    // Helper methods with detailed functionality for internal use within the object.

    /**
     * Adjusts the brightness of a color.
     * @param color the original color in integer format.
     * @param factor the factor by which to adjust the brightness.
     * @return the color with adjusted brightness.
     */
    private fun adjustBrightness(color: Int, factor: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= factor
        return Color.HSVToColor(hsv)
    }

    /**
     * Adjusts the saturation of a color.
     * @param color the original color in integer format.
     * @param factor the factor by which to adjust the saturation.
     * @return the color with adjusted saturation.
     */
    private fun adjustSaturation(color: Int, factor: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[1] *= factor
        return Color.HSVToColor(hsv)
    }

    /**
     * Shifts the hue of a color.
     * @param color the original color in integer format.
     * @param degrees the number of degrees to shift the hue.
     * @return the color with the hue shifted.
     */
    private fun shiftHue(color: Int, degrees: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[0] = (hsv[0] + degrees) % 360f
        return Color.HSVToColor(hsv)
    }

    /**
     * Blends two colors based on a given ratio.
     * @param color1 the first color in integer format.
     * @param color2 the second color in integer format.
     * @param ratio the ratio to blend the first color to the second color.
     * @return the blended color.
     */
    private fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
        val inverseRatio = 1f - ratio
        val r = Color.red(color1) * ratio + Color.red(color2) * inverseRatio
        val g = Color.green(color1) * ratio + Color.green(color2) * inverseRatio
        val b = Color.blue(color1) * ratio + Color.blue(color2) * inverseRatio
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }

    /**
     * Adjusts the luminance of a color in LAB color space and returns the adjusted color in RGB format.
     * @param color the original color in integer format.
     * @param luminance the luminance value to set.
     * @return the color with the adjusted luminance.
     */
    fun adjustLuminance(color: Int, luminance: Double): Int {
        val labColor = DoubleArray(3)
        ColorUtils.colorToLAB(color, labColor)
        labColor[0] = luminance
        return ColorUtils.LABToColor(labColor[0], labColor[1], labColor[2])
    }

    /**
     * Applies a specified style to the color based on the given style name.
     * @param color the original color in integer format.
     * @param style the name of the style to apply.
     * @return the color adjusted by the specified style.
     */
    fun applyStyle(color: Int, style: String): Int {
        var newColor = color
        when (style) {
            "SPRITZ" -> newColor = applyNeutralStyle(color)
            "VIBRANT" -> newColor = applyVibrantStyle(color)
            "EXPRESSIVE" -> newColor = applyExpressiveStyle(color)
            "RAINBOW" -> newColor = applyRainbowStyle(color)
            "FRUIT_SALAD" -> newColor = applyFruitSaladStyle(color)
            "CONTENT" -> newColor = applyContentStyle(color)
            "MONOCHROMATIC" -> newColor = applyMonoChromeStyle(color)
            "FIDELITY" -> newColor = applyFidelityStyle(color)
            "TONAL_SPOT" -> newColor = applyTonalSpotStyle(color)
        }
        return newColor
    }
}
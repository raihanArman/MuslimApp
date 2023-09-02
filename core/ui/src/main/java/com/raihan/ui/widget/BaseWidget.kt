package com.raihan.ui.widget

import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode

/**
 * @author Raihan Arman
 * @date 02/09/23
 */
abstract class BaseWidget : GlanceAppWidget() {
    @Composable
    abstract fun WidgetContent()
    override val sizeMode: SizeMode
        get() = SizeMode.Exact

    @Composable
    override fun Content() {
        WidgetContent()
    }
}

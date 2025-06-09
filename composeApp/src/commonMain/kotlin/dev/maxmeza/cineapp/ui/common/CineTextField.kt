package dev.maxmeza.cineapp.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.maxmeza.cineapp.ui.Blue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    supportingText: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colorError: Color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        enabled = enabled,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Blue),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier,
//            .background(MaterialTheme.colorScheme.surfaceContainer, shape = CircleShape)
//            .indicatorLine(enabled, isError, interactionSource, colors),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                leadingIcon = leadingIcon,
                isError = isError,
                label = {
                    Text(label)
                },
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Red
                ),
                supportingText = {
                    if (supportingText != null) {
                        Text(supportingText)
                    }
                },
                container = {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceContainer, shape = CircleShape)
                            .border(BorderStroke(width = 1.dp, color = colorError), shape = CircleShape)
                    )
                }
            )
        })

}

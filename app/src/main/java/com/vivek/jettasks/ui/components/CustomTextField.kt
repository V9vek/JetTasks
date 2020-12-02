package com.vivek.jettasks.ui.components

import androidx.compose.foundation.BaseTextField
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@ExperimentalFoundationApi
@Composable
fun TextField(
    input: TextFieldValue,
    onInputChange: (TextFieldValue) -> Unit,
    placeholder: String,
    modifier: Modifier,
    fontSize: Int,
    textColor: Color
) {
    Box(
        modifier = modifier
    ) {
        BaseTextField(
            value = input,
            onValueChange = { onInputChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardType = KeyboardType.Text,
            textColor = textColor,
            textStyle = TextStyle(fontSize = fontSize.sp)
        )

        val disableContentColor =
            AmbientEmphasisLevels.current.disabled.applyEmphasis(textColor.copy(0.5f))
        if (input.text.isEmpty()) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.body1.copy(color = disableContentColor),
                modifier = Modifier.align(Alignment.CenterStart),
                fontSize = TextUnit.Sp(fontSize)
            )
        }
    }
}
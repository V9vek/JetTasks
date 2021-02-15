package com.vivek.jettasks.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
    input: String,
    onInputChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier,
    fontSize: Int,
    textColor: Color,
    visible: Boolean
) {
    if (visible) {
        Box(
            modifier = modifier
        ) {
            BasicTextField(
                value = input,
                onValueChange = { onInputChange(it) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = TextStyle(color = textColor, fontSize = fontSize.sp)
            )

            if (input.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.body1.copy(color = textColor.copy(0.5f)),
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = fontSize.sp
                )
            }
        }
    }
}
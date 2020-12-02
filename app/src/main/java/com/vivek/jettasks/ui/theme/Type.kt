package com.vivek.jettasks.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.vivek.jettasks.R

private val ProductSans = fontFamily(
    font(R.font.product_sans_regular),
    font(R.font.prodcut_sans_bold, FontWeight.Bold)
)

val typography = Typography(
    defaultFontFamily = ProductSans,
    h4 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 18.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 18.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 12.sp
    )
)
package com.example.calculatorapp

import android.os.Bundle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatorapp.ui.theme.CalculatorAppTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorAppTheme {
                App()
            }
        }
    }
}

val buttonSize = 85.dp
val buttonPadding = 2.dp

@Composable
fun App() {
    var firstTerm: String? by remember { mutableStateOf(null) }
    var secondTerm: String? by remember { mutableStateOf(null) }
    var operand: String? by remember { mutableStateOf(null) }

    val numberPressed : (String) -> Unit = { digit ->
        if(firstTerm == null) {
            firstTerm = digit
        } else if(operand == null) {
            firstTerm += digit
        } else if(secondTerm == null) {
            secondTerm = digit
        } else {
            secondTerm += digit
        }
    }

    val operandChosen : (String) -> Unit = { oper ->
        if(firstTerm != null && secondTerm == null) {
            operand = oper
        }
    }

    val backspaceIsClicked : () -> Unit = { // TODO: Fix the logic
        if(secondTerm != null) {
            secondTerm = if(secondTerm!!.length == 1) {
                null
            } else {
                secondTerm!!.substring(0, secondTerm!!.length - 1)
            }
        } else if(operand != null) {
            operand = null
        } else if(firstTerm != null) {
            firstTerm = if (firstTerm!!.length == 1) {
                null
            } else {
                firstTerm!!.substring(0, firstTerm!!.length - 1)
            }
        }
    }

    val equalsPressed : () -> Unit = {
        if(firstTerm != null && operand != null && secondTerm != null) {
            firstTerm = when(operand) {
                "+" -> "${firstTerm!!.toInt() + secondTerm!!.toInt()}"
                "-" -> "${firstTerm!!.toInt() - secondTerm!!.toInt()}"
                "*" -> "${firstTerm!!.toInt() * secondTerm!!.toInt()}"
                ":" -> "${firstTerm!!.toInt() / secondTerm!!.toInt()}"
                else -> "Error"
            }
            operand = null
            secondTerm = null
        }
    }

    Column() {
        Row() {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Text(
                modifier = Modifier.padding(10.dp),
                text = "123",//firstTerm?.plus(operand)?.plus(secondTerm)?:""
                fontFamily = FontFamily(Font(R.font.sevensegment)),
                fontSize = 18.em
            )
        }
        var n = 1
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0..2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    for (j in 0..2) {
                        CustomButton(
                            modifier = Modifier
                                .size(width = buttonSize, height = buttonSize)
                                .padding(buttonPadding),
                            onClick = numberPressed,
                            text = "${n++}",
                            shape = RoundedCornerShape(15.dp)
                        )
                    }
                    CustomButton(
                        modifier = Modifier
                            .size(width = buttonSize, height = buttonSize)
                            .padding(buttonPadding),
                        onClick = numberPressed,
                        text = "${n++}",
                        shape = RoundedCornerShape(15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors()
    ) {
    Button(
        modifier = modifier,
        onClick = {
            onClick(text)
        },
        shape = shape,
        colors = colors
    ) {
        Text(
            text = text,
            fontSize = fontSize
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun AppPreview() {
    App()
}

@Preview
@Composable
fun CustomButtonPreview() {
    CustomButton(
        modifier = Modifier.size(100.dp),
        onClick = {  },
        text = "1fgfdg",
        shape = RoundedCornerShape(15.dp)
    )
}
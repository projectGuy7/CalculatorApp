package com.example.calculatorapp

import android.os.Bundle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorAppTheme {
                Scaffold { innerPadding ->
                    App(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

val buttonPadding = 2.dp

@Composable
fun App(modifier: Modifier) {
    var firstTerm: String? by remember { mutableStateOf(null) }
    var secondTerm: String? by remember { mutableStateOf(null) }
    var operand: String? by remember { mutableStateOf(null) }
    val result: String = (firstTerm?:"0")
                            .plus(operand?:"")
                            .plus(secondTerm?:"")
    var resultPreview : String = if(firstTerm != null && operand != null && secondTerm != null) {
        when(operand) {
            "+" -> "${firstTerm!!.toInt() + secondTerm!!.toInt()}"
            "-" -> "${firstTerm!!.toInt() - secondTerm!!.toInt()}"
            "*" -> "${firstTerm!!.toInt() * secondTerm!!.toInt()}"
            "/" -> "${firstTerm!!.toInt() / secondTerm!!.toInt()}"
            else -> "Error"
        }
    } else {
        ""
    }

    val numberPressed : (String) -> Unit = { digit ->
        if((digit != "0" || result != "0") &&
            !(digit == "0" && secondTerm == null && operand == "/") &&
            !(digit == "0" && secondTerm == "0")
            )
        {
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
    }

    val backspaceIsClicked : (String) -> Unit = { // TODO: Fix the logic
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

    val equalsPressed : (String) -> Unit = {
        if(firstTerm != null && operand != null && secondTerm != null) {
            firstTerm = when(operand) {
                "+" -> "${firstTerm!!.toInt() + secondTerm!!.toInt()}"
                "-" -> "${firstTerm!!.toInt() - secondTerm!!.toInt()}"
                "*" -> "${firstTerm!!.toInt() * secondTerm!!.toInt()}"
                "/" -> "${firstTerm!!.toInt() / secondTerm!!.toInt()}"
                else -> "Error"
            }
            firstTerm = if(firstTerm == "0") null else firstTerm
            operand = null
            secondTerm = null
        }
    }

    val operandChosen : (String) -> Unit = { oper ->
        if(secondTerm != null) {
            equalsPressed("")
            operand = oper
        } else if(firstTerm != null && secondTerm == null) {
            operand = oper
        }
    }

    Column(
        modifier
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier,
                        text = result,
                        fontFamily = FontFamily(Font(R.font.sevensegment)),
                        fontSize = 18.em
                    )
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = resultPreview,
                        fontFamily = FontFamily(Font(R.font.sevensegment)),
                        fontSize = 18.em
                    )
                }
            }
        }

        val smallButtonModifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .padding(buttonPadding)
        val equalsButtonModifier = Modifier
            .fillMaxHeight()
            .weight(2f)
            .padding(buttonPadding)
        val deleteButtonModifier = Modifier
            .fillMaxSize()
            .weight(0.5f)
            .padding(buttonPadding)

        Column(
            modifier = Modifier.weight(3f)
        ) {
            CustomButton(
                modifier = deleteButtonModifier,
                onClick = backspaceIsClicked,
                text = "<",
                shape = RoundedCornerShape(15.dp)
            )
            Row(
                modifier = Modifier.weight(1f)
            ) {
                for(i in 7..9) {
                    CustomButton(
                        modifier = smallButtonModifier,
                        onClick = numberPressed,
                        text = "$i",
                        shape = RoundedCornerShape(15.dp)
                    )
                }
                CustomButton(
                    modifier = smallButtonModifier,
                    onClick = operandChosen,
                    text = "+",
                    shape = RoundedCornerShape(15.dp)
                )
            }
            Row(
                modifier = Modifier.weight(1f)
            ) {
                for(i in 4..6) {
                    CustomButton(
                        modifier = smallButtonModifier,
                        onClick = numberPressed,
                        text = "$i",
                        shape = RoundedCornerShape(15.dp)
                    )
                }
                CustomButton(
                    modifier = smallButtonModifier,
                    onClick = operandChosen,
                    text = "-",
                    shape = RoundedCornerShape(15.dp)
                )
            }
            Row(
                modifier = Modifier.weight(1f)
            ) {
                for(i in 1..3) {
                    CustomButton(
                        modifier = smallButtonModifier,
                        onClick = numberPressed,
                        text = "$i",
                        shape = RoundedCornerShape(15.dp)
                    )
                }
                CustomButton(
                    modifier = smallButtonModifier,
                    onClick = operandChosen,
                    text = "*",
                    shape = RoundedCornerShape(15.dp)
                )
            }
            Row(
                modifier = Modifier.weight(1f)
            ) {
                CustomButton(
                    modifier = smallButtonModifier,
                    onClick = numberPressed,
                    text = "0",
                    shape = RoundedCornerShape(15.dp)
                )
                CustomButton(
                    modifier = equalsButtonModifier,
                    onClick = equalsPressed,
                    text = "=",
                    shape = RoundedCornerShape(15.dp)
                )
                CustomButton(
                    modifier = smallButtonModifier,
                    onClick = operandChosen,
                    text = "/",
                    shape = RoundedCornerShape(15.dp)
                )
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
    App(modifier = Modifier)
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
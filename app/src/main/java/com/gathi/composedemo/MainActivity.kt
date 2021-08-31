package com.gathi.composedemo

import android.R
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gathi.composedemo.ui.theme.ComposeDemoTheme
import com.gathi.composedemo.vm.MainViewModel


class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
                val scanText = remember {
                    mutableStateOf("")
                }

                val errorText = remember {
                    mutableStateOf("Oops something is wrong")
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.background
                ) {

                    ScannerInput(
                        scannerText = scanText,
                        errorText = errorText,
                        scannerStatusImage = null,
                        onTextEntered = {
                            Log.d("Main", "Key entered...")
                            errorText.value = ""
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@ExperimentalComposeUiApi
@Composable
fun ScannerInput(
    scannerText: MutableState<String>,
    errorText: MutableState<String>,
    scannerStatusImage: MutableState<String>?,
    onTextEntered: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val isError = errorText.value.isNotEmpty()
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = scannerText.value,
                onValueChange = {
                    scannerText.value = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onTextEntered()
                    }
                ),
                isError = isError,
                label = {
                    Text(
                        "Scan/Enter UPC"
                    )
                },
                trailingIcon = {
                    val tintColor =
                        if (isError) {
                            Color.Red
                        } else {
                            Color.DarkGray
                        }

                    Icon(
                        Icons.Default.Info,
                        "info",
                        tint = tintColor)
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (isError) {
                Text(
                    text = errorText.value,
                    color = Color.Red
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val scanText = remember {
        mutableStateOf("")
    }

    val errorText = remember {
        mutableStateOf("Oops something is wrong...")
    }


    ComposeDemoTheme {
        ScannerInput(
            scannerText = scanText,
            errorText = errorText,
            scannerStatusImage = null
        ) {

        }
    }
}
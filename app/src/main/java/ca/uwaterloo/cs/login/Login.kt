package ca.uwaterloo.cs.login

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uwaterloo.cs.destinations.MainContentDestination
import ca.uwaterloo.cs.destinations.SignupAsManagerDestination
import ca.uwaterloo.cs.destinations.SignupAsWorkerDestination
import ca.uwaterloo.cs.ui.theme.OnlineFoodRetailTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start = true)
@Composable
fun Login(
    navigator: DestinationsNavigator
) {
    OnlineFoodRetailTheme {
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                //.wrapContentSize(align = Alignment.Center)
                .padding(20.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            //Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Online Food Retailer",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(21.dp))

            var username by remember {mutableStateOf("")}
            var password by remember {mutableStateOf("")}
            var usernameErrorFound by remember {mutableStateOf(false)}
            var passwordErrorFound by remember {mutableStateOf(false)}

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                isError = usernameErrorFound,
                singleLine = true,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Spacer(modifier = Modifier.height(2.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                isError = passwordErrorFound,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            Button(
                onClick = {
                    if (verifyLogin(username, password)) {
                        navigator.navigate(MainContentDestination)
                    }
                    else {
                        usernameErrorFound = true
                        passwordErrorFound = true
                    }
                          },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(21.dp))

            Text(
                text = "Don't have an account yet?",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Button(
                onClick = {
                    navigator.navigate(SignupAsManagerDestination)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Sign up as Manager")
            }
            Button(
                onClick = {
                    navigator.navigate(SignupAsWorkerDestination)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Sign up as Worker")
            }
        }
    }
}

fun verifyLogin(username: String, password: String) : Boolean {
    //check username and password against db
    if (username != "test")
        return false
    return true
}
package ca.uwaterloo.cs.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uwaterloo.cs.Singleton
import ca.uwaterloo.cs.bemodels.SignUpFarmer
import ca.uwaterloo.cs.db.DBManager
import ca.uwaterloo.cs.destinations.LoginDestination
import ca.uwaterloo.cs.destinations.MainContentDestination
import ca.uwaterloo.cs.form.FormState
import ca.uwaterloo.cs.ui.theme.OnlineFoodRetailTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination()
@Composable
fun SignupAsManager(
    navigator: DestinationsNavigator
) {
    val dbManager = DBManager()
    val formState by remember { mutableStateOf(FormState()) }
    OnlineFoodRetailTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .wrapContentSize(align = Alignment.Center)
                .padding(20.dp)
        )  {
            Text(
                text = "Sign up as a manager",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(21.dp))
            var username by remember {mutableStateOf("")}
            var firstName by remember { mutableStateOf("")}
            var familyName by remember { mutableStateOf("")}
            var farmName by remember {mutableStateOf("")}

            var usernameErrorFound by remember {mutableStateOf(false)}
            var farmNameErrorFound by remember {mutableStateOf(false)}

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                isError = usernameErrorFound,
                singleLine = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(2.dp))
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                singleLine = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(2.dp))
            TextField(
                value = familyName,
                onValueChange = { familyName = it },
                label = { Text("Family Name") },
                singleLine = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(2.dp))
            TextField(
                value = farmName,
                onValueChange = { farmName = it },
                label = { Text("Farm Name") },
                isError = farmNameErrorFound,
                singleLine = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(2.dp))

            Button(
                onClick = {
                    val signUpFarmer = SignUpFarmer(
                        Singleton.userId,
                        firstName,
                        familyName,
                        farmName
                    )
                    dbManager.storeSignUpFarmer(signUpFarmer)
                    navigator.navigate(MainContentDestination)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Sign up")
            }
        }
    }
}

fun verifySignupAsManager(username: String, farmName: String): Boolean {
    return true
}
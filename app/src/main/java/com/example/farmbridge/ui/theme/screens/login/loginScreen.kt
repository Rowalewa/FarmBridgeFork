package com.example.farmbridge.ui.theme.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.farmbridge.R
import com.example.farmbridge.data.AuthViewModel
import com.example.farmbridge.navigation.ROUTE_ABOUT
import com.example.farmbridge.navigation.ROUTE_HOME
import com.example.farmbridge.navigation.ROUTE_REGISTER

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController){
    Box {
        Image(painter = painterResource(id = R.drawable.plant9),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize())
        var email by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var pass by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var context = LocalContext.current
        Column (modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Login Page",
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 40.sp)
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = email, onValueChange = {email=it},
                label = { Text(text = "Enter Email") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = pass, onValueChange = {pass=it},
                label = { Text(text = "Enter Password") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
            val mylogin= AuthViewModel(navController, context )
            mylogin.login(
                email.text.trim(),
                pass.text.trim(),
            )
                navController.navigate(ROUTE_HOME)
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate(ROUTE_REGISTER) },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Don't have an account? Click to Register")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate(ROUTE_ABOUT) },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "The About Page")
            }
        }
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(navController = rememberNavController())
}
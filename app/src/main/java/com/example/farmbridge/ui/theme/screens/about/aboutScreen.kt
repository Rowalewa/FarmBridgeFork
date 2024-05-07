package com.example.farmbridge.ui.theme.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.farmbridge.R
import com.example.farmbridge.navigation.ROUTE_HOME
import com.example.farmbridge.navigation.ROUTE_LOGIN

@Composable
fun AboutScreen(navController: NavController){
    Box (modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(id = R.drawable.plant9),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize())
        Column (modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "About Page",
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp)
            Text(text = "This is an application that helps the farmers to connect with companies that helps them in ordering of fertilizers and farming tools.")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {navController.navigate(ROUTE_HOME)}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "HOME")
            }
//            Spacer(modifier = Modifier.height(20.dp))
//            Button(onClick = {navController.navigate(ROUTE_HOME)}, modifier = Modifier.fillMaxWidth()) {
//                Text(text = "GALLERY")
//            }
        }

    }
}
@Preview
@Composable
fun AboutScreenPreview(){
    AboutScreen(navController = rememberNavController())
}
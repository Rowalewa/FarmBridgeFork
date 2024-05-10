package com.example.farmbridge.ui.theme.screens.delivery

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(navController: NavHostController, userId: String, productId: String) {
    var context = LocalContext.current
    var locationOptions = listOf(
        "Ngong",
        "Kiambu",
        "Thika",
        "Ruai",
        "Kirinyaga",
        "Machakos"
    )
    var isLocationExpanded by remember {
        mutableStateOf(false)
    }
    var location by remember {
        mutableStateOf(locationOptions[0])
    }


    Column {
        TextField(
            value = userId,
            onValueChange = {},
            label = { Text(text = "User Id")},
            modifier = Modifier.fillMaxWidth()
        )
        TextField(value = productId,
            onValueChange = {},
            label = { Text(text = "Product Id")},
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 0.dp,
                    bottom = 0.dp
                )
                .border(width = Dp.Hairline, color = Color.White)
        ) {
            Text(
                text = "Location:",
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                color = Color.White
            )
            ExposedDropdownMenuBox(
                expanded = isLocationExpanded,
                onExpandedChange = { isLocationExpanded = !isLocationExpanded }
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        ),
                    value = location,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLocationExpanded) },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Magenta,
                        unfocusedTextColor = Color.Red,
                        focusedContainerColor = Color.Cyan,
                        unfocusedContainerColor = Color.Green,
                        disabledContainerColor = Color.White,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.Magenta
                    ),
                )
                ExposedDropdownMenu(
                    expanded = isLocationExpanded,
                    onDismissRequest = { isLocationExpanded = false }) {
                    locationOptions.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text) },
                            onClick = { location = locationOptions[index] },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }

            }
        }
        Text(text = "Currently Selected: $location")

        Button(onClick = {
            var myDelivery = ProductViewModel(navController, context)
            myDelivery.makeDelivery(
                userId,
                productId,
                location
            )
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}




@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun DeliveryScreenPreview(){
    DeliveryScreen(navController = rememberNavController(), userId = "", productId = "")
}
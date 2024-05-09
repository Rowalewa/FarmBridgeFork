package com.example.farmbridge.ui.theme.screens.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.farmbridge.models.Product
import com.example.farmbridge.navigation.ROUTE_DELIVERY
import com.example.farmbridge.navigation.ROUTE_UPDATE_PRODUCT
import com.example.myapplication.data.ProductViewModel

@Composable
fun ViewProductsScreen(navController: NavHostController, userId: String){
    val context = LocalContext.current
    val productRepository = ProductViewModel(navController, context)


    val emptyProductsState = remember { mutableStateOf(Product("","","","","")) }
    val emptyProductsListState = remember { mutableStateListOf<Product>() }

    val products = productRepository.viewProduct(emptyProductsState, emptyProductsListState)


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "All Products",
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive,
            color = Color.Red)

        Spacer(modifier = Modifier.height(20.dp))
        @Composable
        fun ProductItem(
            productName:String,
            productQuantity:String,
            productPrice:String,
            productImageUrl:String,
            productId:String,
            navController:NavHostController,
            productRepository:ProductViewModel
        ) {

            Card(
                shape = MaterialTheme.shapes.medium ,
                modifier = Modifier
                    .size(300.dp, 300.dp)
                    .padding(15.dp),

                ) {
                Column(modifier = Modifier
                    .clickable {}
                    .background(Color.Black)) {
                    Image(
                        painter = rememberAsyncImagePainter(productImageUrl),
                        contentDescription = "product",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(300.dp, 200.dp)

                    )
                    Text(
                        text = "Name: $productName",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif,
                        color = Color.White
                    )
                    Text(
                        text = "Quantity: $productQuantity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif,
                        color = Color.White
                    )
                    Text(
                        text = "Price: $productPrice",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif,
                        color = Color.White
                    )
                    Button(
                        onClick = { navController.navigate("$ROUTE_DELIVERY/$userId/$productId") },
                        colors = ButtonDefaults.buttonColors(Color.Green),
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = "Buy",
                            fontSize = 15.sp
                        )
                    }
                    Row {
                        Button(onClick = {
                            productRepository.deleteProduct(productId)
                        }
                        ) {
                            Text(text = "Delete")
                        }
                        Button(onClick = {
                            navController.navigate("$ROUTE_UPDATE_PRODUCT/$productId")
                        }) {
                            Text(text = "Update")
                        }
                    }
                }
            }
        }


        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(products){
                ProductItem(
                    productName = it.productName,
                    productQuantity = it.productQuantity,
                    productPrice = it.productPrice,
                    productImageUrl = it.productImageUrl,
                    productId = it.productId,
                    navController = navController,
                    productRepository = productRepository
                )
            }
        }
    }
}

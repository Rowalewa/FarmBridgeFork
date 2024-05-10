package com.example.farmbridge.ui.theme.screens.products

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        AppTopBar()
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
                shape = MaterialTheme.shapes.large ,
                modifier = Modifier
                    .size(400.dp, 400.dp)
                    .padding(20.dp),

                ) {
                Column(modifier = Modifier
                    .clickable {}
                    .background(Color.Black)) {
                    Image(
                        painter = rememberAsyncImagePainter(productImageUrl),
                        contentDescription = "product",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .size(400.dp, 200.dp)
                            .padding(10.dp)

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
                    Row (
                        modifier = Modifier.padding(
                            start = 10.dp
                        )
                    ){
                        Button(onClick = {
                            productRepository.deleteProduct(productId)
                        },
                            modifier = Modifier.width(150.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text = "Delete")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {
                            navController.navigate("$ROUTE_UPDATE_PRODUCT/$productId")
                        },
                            modifier = Modifier.width(150.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                        ) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(){

    val context = LocalContext.current.applicationContext
    TopAppBar(title = {Text(text = "FarmBridge", fontFamily = FontFamily.Serif, fontSize = 30.sp,
        fontWeight = FontWeight.Bold, color = Color.Green)},
        navigationIcon ={
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Home, contentDescription ="Home", tint = Color.White )

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black ,
            titleContentColor = Color.Black ,
            navigationIconContentColor = Color.White
        ),
        actions = {
//            IconButton(onClick = { Toast.makeText(context,"You have clicked the cart icon",Toast.LENGTH_SHORT).show()}) {
//                Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "notification", tint = Color.White)
//            }
//            Spacer(modifier = Modifier.height(5.dp))

            IconButton(onClick = { Toast.makeText(context,"Account Option", Toast.LENGTH_SHORT).show()}) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "menu", tint = Color.White)
            }
        }

    )

}

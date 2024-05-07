package com.example.farmbridge.ui.theme.screens.products

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.farmbridge.R
import com.example.farmbridge.navigation.ROUTE_DELIVERY

@Composable
fun ViewProduct(navController: NavHostController){
   Column {
       TopBar()
       LazyColumn(
           verticalArrangement = Arrangement.spacedBy(4.dp),
           contentPadding = PaddingValues(horizontal = 22.dp, vertical = 18.dp),
//        modifier = Modifier.background(Color.White)
       ) {
           items(toolsList) { tools ->
               ListAppear(
                   model = tools,
                   navController
               )
           }
       }
   }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){

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
data class Tools(val name: String,val image: Int,val price: String,val button:String)
private val toolsList= listOf(
    Tools("Slasher", R.drawable.slasher,"300","Buy"),
    Tools("Ladder", R.drawable.ladder,"500","Buy"),
    Tools("ForkJembe", R.drawable.forkjembe,"700","Buy"),
    Tools("Shears", R.drawable.hedgeshears,"1500","Buy"),
    Tools("Jembe", R.drawable.jembe,"500","Buy"),
    Tools("Rake", R.drawable.rake,"400","Buy"),
    Tools("LeafRake", R.drawable.leafrake,"500","Buy"),
    Tools("Spade", R.drawable.spade,"700","Buy"),
    Tools("Shovel", R.drawable.shovel,"500","Buy"),
    Tools("Machete", R.drawable.panga,"700","Buy")
)
@Composable
fun ListAppear(model:Tools, navController: NavController){
    Card(shape = MaterialTheme.shapes.medium ,
        modifier = Modifier
            .size(300.dp, 300.dp)
            .padding(15.dp),

        ) {
        Column(modifier = Modifier
            .clickable {}
            .background(Color.Black)) {
            Image(painter = painterResource(id = model.image ),
                contentDescription = model.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp, 200.dp)

            )
            Row {
                Text(text = model.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White)
                Spacer(modifier = Modifier.width(100.dp))
                Button(onClick = { navController.navigate(ROUTE_DELIVERY) },
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ){
                    Text(
                        text = "Buy",
                        fontSize = 15.sp
                    )
                }

                
            }
            Text(text = model.price,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewProductPreview(){
    ViewProduct(navController = rememberNavController())
}
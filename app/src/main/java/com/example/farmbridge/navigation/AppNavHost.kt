package com.example.farmbridge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmbridge.ui.theme.screens.about.AboutScreen
import com.example.farmbridge.ui.theme.screens.delivery.DeliveryScreen
import com.example.farmbridge.ui.theme.screens.home.HomeScreen
//import com.example.farmbridge.ui.theme.screens.home.HomeScreen
import com.example.farmbridge.ui.theme.screens.login.LoginScreen
import com.example.farmbridge.ui.theme.screens.products.AddProductScreen
import com.example.farmbridge.ui.theme.screens.products.UpdateProductScreen
import com.example.farmbridge.ui.theme.screens.products.ViewProduct
import com.example.farmbridge.ui.theme.screens.products.ViewProductsScreen
import com.example.farmbridge.ui.theme.screens.register.RegisterScreen

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController:NavHostController=rememberNavController(),
    startDestination:String = ROUTE_LOGIN
){
    NavHost(navController = navController,modifier= Modifier,
        startDestination=startDestination){
        composable("$ROUTE_HOME/{userId}"){passedData->
            HomeScreen(navController, passedData.arguments?.getString("userId")!!)
        }
        composable(ROUTE_LOGIN){
            LoginScreen(navController)
        }
        composable(ROUTE_REGISTER){
            RegisterScreen(navController)
        }
        composable(ROUTE_ABOUT){
            AboutScreen(navController)
        }
        composable(ROUTE_ADD_PRODUCT){
            AddProductScreen(navController)
        }
        composable("$ROUTE_VIEW_PRODUCT/{userId}"){passedData->
            ViewProductsScreen(navController, passedData.arguments?.getString("userId")!!)
        }
        composable("$ROUTE_UPDATE_PRODUCT/{productId}"){passedData ->
            UpdateProductScreen(navController,passedData.arguments?.getString("productId")!!)
        }
        composable("$ROUTE_DELIVERY/{userId}/{productId}") {passedData->
            DeliveryScreen(
                navController,
                passedData.arguments?.getString("userId")!!,
                passedData.arguments?.getString("productId")!!
            )

        }


    }


}
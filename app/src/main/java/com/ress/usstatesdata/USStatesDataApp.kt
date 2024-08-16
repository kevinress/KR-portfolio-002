package com.ress.usstatesdata

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun USStatesDataApp(navController: NavHostController){
    val usStatesDataViewModel: MainViewModel = viewModel()
    val viewstate by usStatesDataViewModel.datasState

    NavHost(navController = navController, startDestination = Screen.USStatesScreen.route){
        composable(route = Screen.USStatesScreen.route){
            USStatesScreen(viewstate = viewstate, navigateToStateMap = {
                navController.currentBackStackEntry?.savedStateHandle?.set("dat", it)
                navController.navigate(Screen.StateMapScreen.route)
            })
        }
        composable(route = Screen.StateMapScreen.route){
            val data = navController.previousBackStackEntry?.savedStateHandle?.
            get<Data>("dat") ?: Data("","","")
            StateMapScreen(data = data)
        }
    }
}
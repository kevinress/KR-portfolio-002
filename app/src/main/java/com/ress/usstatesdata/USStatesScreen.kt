package com.ress.usstatesdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun USStatesScreen(modifier: Modifier = Modifier,
                   viewstate:MainViewModel.USStatesState,
                   navigateToStateMap: (Data) -> Unit){
    val usStatesViewModel: MainViewModel = viewModel()
    val previewUrls by usStatesViewModel.previewUrlsState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "US States Data",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                            color = Color.White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2196F3))
            )
        }
    ){ innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when{
                viewstate.loading ->{
                    CircularProgressIndicator(modifier.align(Alignment.Center))
                }

                viewstate.error != null ->{
                    Text("ERROR OCCURRED")
                }
                else ->{
                    DataScreen(datas = viewstate.list,
                        previewUrls = previewUrls,
                        navigateToStateMap)
                }
            }
        }
    }
}

@Composable
fun DataScreen(datas: List<Data>,
               previewUrls: List<String?>,
               navigateToStateMap: (Data) -> Unit){
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()){
        items(datas.zip(previewUrls)){ (data, previewUrl) ->
            DataItem(data = data, previewUrl = previewUrl, navigateToStateMap)
        }
    }
}

@Composable
fun DataItem(data: Data, previewUrl: String?, navigateToStateMap: (Data) -> Unit){

    val formattedPopulation = formatPopulation(data.population.toInt())

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { navigateToStateMap(data) },
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 4.dp)
        ){
            previewUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.BottomCenter).size(150.dp)
                )
            }
        }

        Text(
            text = "State: ${data.state}",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Population: $formattedPopulation",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )
    }
}

fun formatPopulation(population: Int): String {
    val decimalFormat = DecimalFormat("#,###").apply {
        groupingSize = 3
        decimalFormatSymbols = decimalFormatSymbols.apply {
            groupingSeparator = '.'
        }
    }
    return decimalFormat.format(population)
}
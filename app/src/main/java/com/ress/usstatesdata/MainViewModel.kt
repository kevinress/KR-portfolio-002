package com.ress.usstatesdata

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val _datasState = mutableStateOf(USStatesState())
    val datasState: State<USStatesState> = _datasState
    val previewUrlsState = mutableStateOf<List<String?>>(emptyList())

    init {
        fetchDatas()
    }

    private fun fetchDatas() {
        viewModelScope.launch {
            try {
                val response = usStatesService.getDatas()
//                val dataList = response.data
//                take 8 data for dev process for faster load
                val dataList = response.data.take(12)
                _datasState.value = _datasState.value.copy(
                    list = dataList,
                    loading = false,
                    error = null,
                )
                fetchPreviewUrls(dataList)
            } catch (e: Exception) {
                _datasState.value = _datasState.value.copy(
                    loading = false,
                    error = "Error fetching Data ${e.message}"
                )
            }
        }
    }

    private fun fetchPreviewUrls(dataList: List<Data>) {
        viewModelScope.launch {
            try {
                val previewUrls = dataList.map { data ->
                    val query = "${data.slugState}-flag"
                    val response = pixabayService.searchImagesURL(
                        BuildConfig.API_KEY,
                        query
                    )
                    response.hits.firstOrNull()?.previewURL ?: ""
                }
                previewUrlsState.value = previewUrls
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    data class USStatesState(
        val loading: Boolean = true,
        val list: List<Data> = emptyList(),
        val error: String? = null,
    )
}
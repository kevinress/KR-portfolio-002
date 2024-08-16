package com.ress.usstatesdata

sealed class Screen(val route:String) {
    object USStatesScreen:Screen("usstatesscreen")
    object StateMapScreen:Screen("statemapscreen")
}
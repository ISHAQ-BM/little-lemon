package com.example.littlelemon

sealed class FilterType {
    data object All : FilterType()
    data object Starters : FilterType()
    data object Mains : FilterType()
    data object Desserts : FilterType()
    data object Drinks : FilterType()
}
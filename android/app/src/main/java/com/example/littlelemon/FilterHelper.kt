package com.example.littlelemon

class FilterHelper {

    fun filterMenu(type: FilterType, menuList: List<MenuItemRoom>): List<MenuItemRoom> {
        return when (type) {
            FilterType.All -> menuList
            FilterType.Starters -> menuList.filter { it.category == "starters" }
            FilterType.Mains -> menuList.filter { it.category == "mains" }
            FilterType.Desserts -> menuList.filter { it.category == "desserts" }
            FilterType.Drinks -> menuList.filter { it.category == "drinks" }
        }
    }
}
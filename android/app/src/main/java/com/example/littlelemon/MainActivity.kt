package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }


    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    companion object{


        val databaseMenuItems  = MutableLiveData<List<MenuItemRoom>>()
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>()
            .menu

    }



    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                val menuItems = fetchMenu()
                saveMenuToDatabase(menuItems)
            }
        }

        setContent {
            LittleLemonTheme {
                val databaseMenuItems = database
                    .menuItemDao()
                    .getAll()
                    .observeAsState(emptyList())
                    .value

                var searchPhrase by remember {
                    mutableStateOf("")
                }
                var selectedCategory by remember {
                    mutableStateOf("All")
                }

                val filterHelper = FilterHelper()

                val categoryFilteredItems = filterHelper.filterMenu(
                    type = when (selectedCategory) {
                        "starters" -> FilterType.Starters
                        "mains" -> FilterType.Mains
                        "desserts" -> FilterType.Desserts
                        "drinks" -> FilterType.Drinks
                        else -> FilterType.All
                    },
                    menuList = databaseMenuItems
                )
                val filteredMenuItems = categoryFilteredItems.filter {
                    it.title.contains(searchPhrase, ignoreCase = true)
                }



                    Navigation(menuItems = filteredMenuItems,
                        onSearch = { searchPhrase = it },
                        onCategorySelected = { category ->
                            selectedCategory = if (selectedCategory == category) "All" else category
                        },
                        selectedCategory = selectedCategory,
                        searchPhrase = searchPhrase,)

            }
        }
    }


}

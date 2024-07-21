package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonTheme


@Composable
fun Home(
    navController: NavHostController,
    menuItems: List<MenuItemRoom>,
    onSearch: (String) -> Unit,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    searchPhrase: String
) {
    val categories = listOf("All") + menuItems.map { it.category }.distinct()
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()

        ) {
            Box(
                Modifier.padding(top = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Little Lemon Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 28.dp)
                        .height(50.dp)


                )
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .size(48.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { navController.navigate(Profile.route) }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF495E57))
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF4CE14)
                )
                Text(
                    text = stringResource(id = R.string.location),
                    fontSize = 24.sp,
                    color = Color(0xFFEDEFEE)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 18.dp, bottom = 48.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.description),
                        color = Color(0xFFEDEFEE),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(bottom = 28.dp)
                            .fillMaxWidth(0.6f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.upperpanelimage),
                        contentDescription = "Upper Panel Image",
                        modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    )
                }
                OutlinedTextField(
                    value = searchPhrase,
                    onValueChange = {onSearch(it)},
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(Color.LightGray),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search,
                            contentDescription = null )
                    },
                    shape =RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(
                            text = "Enter search phrase")
                    }

                )
            }

            Column {
                Text(
                    text = "ORDER FOR DELIVERY!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(8.dp),
                    color = Color.Black
                )

                CategoryButtons(categories = categories.filter { it != "All" },
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected)
                HorizontalDivider(thickness = 1.dp)
                MenuItemsList(items = menuItems)

            }

        }



}








@Composable
fun CategoryButtons(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
){
        LazyRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            items(
                items= categories,
                itemContent = { category ->
                    val isSelected = category == selectedCategory
                    Button( onClick = { onCategorySelected(category) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected)
                                Color(0xFF495E57) else Color(0xFFEDEFEE),
                            contentColor = if (isSelected)
                                Color(0xFFFFFFFF)else Color(0xFF495E57))
                    ) {
                        Text(text = category,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }
            )
        }



}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Card (
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    )
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = menuItem.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = menuItem.description,
                                color = Color.Gray,
                                modifier = Modifier
                                    .padding(top = 5.dp, bottom = 5.dp)
                                    .fillMaxWidth(.75f)
                            )
                            Text(
                                text = menuItem.price.toString(),
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        GlideImage(
                            model = menuItem.image,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}
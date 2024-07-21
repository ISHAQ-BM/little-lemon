package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(bottom = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Box(
                modifier.padding(top = 16.dp)
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
                    modifier = modifier.size(48.dp).align(Alignment.CenterEnd).clickable { navController.navigate(Profile.route) }
                )
            }

        }

    }
}
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Home(navController = NavHostController(context = LocalContext.current))
    }
}
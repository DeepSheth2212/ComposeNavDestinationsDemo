package com.plcoding.composenavdestinationsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.composenavdestinationsdemo.destinations.PostScreenDestination
import com.plcoding.composenavdestinationsdemo.destinations.ProfileScreenDestination
import com.plcoding.composenavdestinationsdemo.ui.theme.ComposeNavDestinationsDemoTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class MainActivity : ComponentActivity() {

    //Parcelable - means classes/objects which can be passed between the screens using intents etc
    //In Xml we can use these @Parcelable to send objects across screen using intents
    //but in jetpack compose navigation composable it is not defined to pass objects ..because using NavContoller we use url(Strings) to navigate across screen and in that we cant pass object
    //so to solve this problem we use this library (raamcosta's compose-destinations) which reduces boiler plate code for navigation purpose
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavDestinationsDemoTheme {
                DestinationsNavHost(navGraph = NavGraphs.root) // we dont have to use navHost and NavContoller and assign each composable to NavHost and their routes etc... instead simply use this
            }
        }
    }
}

@Destination(start = true) //to autogenerate the classes for each screen / Destination
@Composable
fun LoginScreen(
    navigator : DestinationsNavigator // we dont use navController to navigate in this library instead we use this
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen")
        Button(onClick = {
            navigator.navigate(
                //this is auto generated class for Profile Screen by the library(we have to rebuild the project once we assign annotations and arguments in each composable screens)
                ProfileScreenDestination(
                    User(
                        "Deep","2212", LocalDateTime.now()//bcz this is also parcelable
                    )
                )
            )
        }) {
            Text("Go to Profile Screen")
        }
    }
}

@Destination
@Composable
fun ProfileScreen(
    navigator : DestinationsNavigator,
    user: User
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Screen: ${user.name}", textAlign = TextAlign.Center)
        Button(onClick = {
            navigator.navigate(
                PostScreenDestination()
            )
        }) {
            Text("Go to Post Screen")
        }
    }
}

@Destination
@Composable
fun PostScreen(
    showOnlyPostsByUser: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Post Screen, $showOnlyPostsByUser")
    }
}
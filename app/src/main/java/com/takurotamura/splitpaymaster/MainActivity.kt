package com.takurotamura.splitpaymaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.takurotamura.splitpaymaster.presentation.ScreenRoute
import com.takurotamura.splitpaymaster.presentation.event_add.EventAddScreen
import com.takurotamura.splitpaymaster.presentation.event_detail.EventDetailScreen
import com.takurotamura.splitpaymaster.presentation.event_list.EventListScreen
import com.takurotamura.splitpaymaster.presentation.payment_add.PaymentAddScreen
import com.takurotamura.splitpaymaster.ui.theme.SplitPayMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplitPayMasterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                     val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.EventListScreen.route
                    ) {
                        // イベント一覧画面
                        composable(route = ScreenRoute.EventListScreen.route) {
                            EventListScreen(navController = navController)
                        }
                        // イベント追加画面
                        composable(route = ScreenRoute.EventAddScreen.route) {
                            EventAddScreen(navController = navController)
                        }
                        // イベント詳細画面
                        composable(route = ScreenRoute.EventDetailScreen.route + "/{eventId}") {
                            EventDetailScreen(navController = navController)
                        }
                        // 支払い追加画面
                        composable(route = ScreenRoute.PaymentAddScreen.route + "/{eventId}") {
                            PaymentAddScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
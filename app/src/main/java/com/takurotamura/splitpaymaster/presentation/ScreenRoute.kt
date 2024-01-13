package com.takurotamura.splitpaymaster.presentation

sealed class ScreenRoute(val route: String) {
    object EventListScreen : ScreenRoute("event_list_screen")
    object EventDetailScreen : ScreenRoute("event_detail_screen")
    object EventAddScreen : ScreenRoute("event_add_screen")
    object PaymentAddScreen : ScreenRoute("payment_add_screen")
}

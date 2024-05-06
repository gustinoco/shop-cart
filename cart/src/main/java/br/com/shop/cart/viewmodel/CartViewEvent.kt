package br.com.shop.cart.viewmodel

sealed class CartViewEvent {

    data class Loading(val show: Boolean) : CartViewEvent()

}
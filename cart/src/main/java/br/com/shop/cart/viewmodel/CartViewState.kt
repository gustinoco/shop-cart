package br.com.shop.cart.viewmodel

import br.com.shop.cart.model.CartModel

sealed class CartViewState {

    data class SuccessCart(val cartModel: MutableList<CartModel>) : CartViewState()
    data class SuccessPayment(val receiptId: String) : CartViewState()
    data class Error(val message: String) : CartViewState()

}
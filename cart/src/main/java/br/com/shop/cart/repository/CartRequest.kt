package br.com.shop.cart.repository

import br.com.shop.cart.model.CartModel
import com.google.gson.annotations.SerializedName

data class CartRequest(
    @SerializedName("listItens")
    val itens: List<CartModel>
)
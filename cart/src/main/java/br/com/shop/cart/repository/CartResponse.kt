package br.com.shop.cart.repository

import com.google.gson.annotations.SerializedName
import br.com.shop.cart.model.CartDTO

data class CartResponse(
    @SerializedName("data")
    val data: List<CartDTO>
)
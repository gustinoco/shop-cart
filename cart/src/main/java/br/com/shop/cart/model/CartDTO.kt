package br.com.shop.cart.model

import com.google.gson.annotations.SerializedName

data class CartDTO(
    @SerializedName("idProduct")
    val idProduct: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("valuePast")
    val valuePast: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("stockPast")
    val stockPast: Int
)

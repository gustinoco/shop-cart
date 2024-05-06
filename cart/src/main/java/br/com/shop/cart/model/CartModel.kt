package br.com.shop.cart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartModel(
    val idProduct: String,
    val value: String,
    val valuePast: String,
    val hasPastValue: Boolean,
    val description: String,
    val stock: Int,
    val stockPast: Int,
    val hasPastStock: Boolean,
    val quantity: Int
) : Parcelable
package br.com.shop.cart.repository

import br.com.shop.cart.model.CartModel

object CartMapper {

    fun convertResponseToModel(
        response: CartResponse,
    ): MutableList<CartModel> {
        val list = mutableListOf<CartModel>()
        response.data.forEach() {
            list.add(
                CartModel(
                    idProduct = it.idProduct,
                    value = it.value,
                    description = it.description,
                    stock = it.stock,
                    valuePast = it.valuePast,
                    hasPastValue = false,
                    stockPast = it.stockPast,
                    hasPastStock = true,
                    quantity = 1
                )
            )
        }
        return list
    }
}

package br.com.shop.cart.repository

import br.com.shop.commons.ShopNetwork
import br.com.shop.cart.commons.CartAnalyticsConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val api: ShopNetwork?) {

    suspend fun payment(
        cartRequest: CartRequest
    ): String {
        val response = withContext(Dispatchers.Default) {
            api?.postNetwork(
                CartAnalyticsConstants.urlEndpointShopping,
                cartRequest,
                String::class
            ) as String
        }
        return response
    }
}
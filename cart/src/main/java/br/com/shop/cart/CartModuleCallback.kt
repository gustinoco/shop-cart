package br.com.shop.cart

import br.com.shop.cart.repository.CartResponse

interface CartModuleCallback {
    suspend fun getCartsFromCacheOrUrl(key: String): CartResponse
}

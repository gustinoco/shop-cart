package br.com.shop.cart.usecase

import br.com.shop.cart.CartModuleCallback
import br.com.shop.cart.model.CartModel
import br.com.shop.cart.repository.CartMapper
import br.com.shop.cart.repository.CartRepository
import br.com.shop.cart.repository.CartRequest
import br.com.shop.cart.repository.CartResponse

class CartUseCase(private val callback: CartModuleCallback, private val repository: CartRepository) {

    suspend fun loadCartApi(cpf: String): MutableList<CartModel> {
        val response = callback.getCartsFromCacheOrUrl(cpf) as CartResponse
        return CartMapper.convertResponseToModel(response)
    }

    suspend fun paymentCart(listItens: List<CartModel>): String {
        return repository.payment(CartRequest(listItens))
    }
}
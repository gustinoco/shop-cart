package br.com.shop.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.shop.cart.CartModuleSession
import br.com.shop.cart.repository.CartRepository

class CartViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        CartViewModel(
            CartModuleSession.callback,
            CartRepository(CartModuleSession.dependencies.shopNetwork)
        ) as T
}
package br.com.shop.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.shop.cart.CartModuleCallback
import br.com.shop.cart.model.CartModel
import br.com.shop.cart.repository.CartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import br.com.shop.cart.usecase.CartUseCase

class CartViewModel(callback: CartModuleCallback, repository: CartRepository) : ViewModel() {

    private val state: MutableLiveData<CartViewState> = MutableLiveData()
    private val event: MutableLiveData<CartViewEvent> = MutableLiveData()
    val viewState: LiveData<CartViewState> = state
    val viewEvent: LiveData<CartViewEvent> = event

    private var cartUseCase: CartUseCase

    private val viewModelJob = SupervisorJob()
    private val coroutineContext = Dispatchers.Main + viewModelJob

    init {
        cartUseCase = CartUseCase(callback, repository)
    }

    fun init(cpf: String) {

        CoroutineScope(coroutineContext).launch {

            try {
                event.value = CartViewEvent.Loading(true)
                val response = cartUseCase.loadCartApi(cpf)
                event.value = CartViewEvent.Loading(false)
                state.value = CartViewState.SuccessCart(response)
            } catch (e: Exception) {
                event.value = CartViewEvent.Loading(false)
                state.value = CartViewState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun effetuatePayment(list: List<CartModel>) {
        CoroutineScope(coroutineContext).launch {

            try {
                event.value = CartViewEvent.Loading(true)
                val response = cartUseCase.paymentCart(list)
                event.value = CartViewEvent.Loading(false)
                state.value = CartViewState.SuccessPayment(response)
            } catch (e: Exception) {
                event.value = CartViewEvent.Loading(false)
                state.value = CartViewState.Error(e.message ?: "Erro desconhecido")
            }

        }
    }
}
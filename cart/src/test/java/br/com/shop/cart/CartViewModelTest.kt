package br.com.shop.cart
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.shop.commons.ShopNetwork
import br.com.shop.cart.model.CartDTO
import br.com.shop.cart.model.CartModel
import br.com.shop.cart.repository.CartRepository
import br.com.shop.cart.repository.CartResponse
import br.com.shop.cart.usecase.CartUseCase
import br.com.shop.cart.utils.CoroutineTestExtension
import br.com.shop.cart.utils.MainDispatcherRule
import br.com.shop.cart.viewmodel.CartViewEvent
import br.com.shop.cart.viewmodel.CartViewModel
import br.com.shop.cart.viewmodel.CartViewState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.coEvery
import kotlinx.coroutines.delay

@ExtendWith(CoroutineTestExtension::class)
class CartViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel

    @MockK
    private lateinit var useCase: CartUseCase

    @MockK
    private lateinit var shopNetwork: ShopNetwork

    private lateinit var repository: CartRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this)
        repository = CartRepository(shopNetwork)
        viewModel = CartViewModel(repository)
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validate state sucess loading itens shopping`() = runBlockingTest {
        coEvery {
            shopNetwork.postNetwork(any(), any(), any())
        } returns CartResponse(listOf(CartDTO("1", "100", "teste", 10)))

        coEvery {
            useCase.loadCartApi(any())
        } returns mutableListOf(CartModel("1", "100", "teste", 10))


        viewModel.init("")
        delay(100)
        assert(viewModel.viewEvent.value is CartViewEvent.Loading)
        assert(viewModel.viewState.value is CartViewState.Success)
    }

    @Test
    fun `validate state false cart pending state`() = runBlockingTest {
        coEvery {
            useCase.loadCartApi(any())
        } returns mutableListOf(CartModel("1", "100", "teste", 10))
        viewModel.init("")
        assert(viewModel.viewState.value is CartViewState.Error)
    }
}

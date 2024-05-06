package br.com.shop.cart.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.shop.cart.databinding.CartActivityMainBinding
import br.com.shop.commons.ShopRoutes
import br.com.shop.cart.CartModuleSession
import br.com.shop.cart.commons.CartAnalyticsConstants
import br.com.shop.cart.viewmodel.CartViewEvent
import br.com.shop.cart.viewmodel.CartViewModel
import br.com.shop.cart.viewmodel.CartViewModelFactory
import br.com.shop.cart.viewmodel.CartViewState

internal class CartActivity: AppCompatActivity() {

    private lateinit var binding: CartActivityMainBinding

    private lateinit var cartAdapter: CartAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory())[CartViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(ShoploginSession.theme)
        binding = CartActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initObservers()
    }

    fun initObservers() {
        viewModel.viewState.observe(this) {
            when (it) {
                is CartViewState.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                is CartViewState.SuccessCart -> {
                    cartAdapter = CartAdapter(
                        this,
                        it.cartModel,
                        CartModuleSession.dependencies.shopCache,
                        CartModuleSession.dependencies.shopAnalytics
                    )
                    binding.listItensCart.adapter = cartAdapter
                }

                is CartViewState.SuccessPayment -> {
                    Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show()
                    CartModuleSession.dependencies.shopCache?.deleteCache(CartModuleSession.keyCart)
                    CartModuleSession.dependencies.shopNavigate?.navigate(this, ShopRoutes.Home.route)

                }
            }
        }

        viewModel.viewEvent.observe(this) {
            when (it) {
                is CartViewEvent.Loading -> if (it.show) {
                    binding.nestedScroll.visibility = android.view.View.GONE
                    binding.progressbar.visibility = android.view.View.VISIBLE
                } else {
                    binding.progressbar.visibility = android.view.View.GONE
                    binding.nestedScroll.visibility = android.view.View.VISIBLE
                }
            }
        }
    }

    fun init() {
        CartModuleSession.dependencies.shopAnalytics?.trackScreen(CartAnalyticsConstants.TrackScreenShooping)
        viewModel.init(CartModuleSession.keyCart)
        binding.listItensCart.layoutManager = LinearLayoutManager(this)
        binding.btnPayment.setOnClickListener {
            viewModel.effetuatePayment(cartAdapter.getItens())
        }
    }
}
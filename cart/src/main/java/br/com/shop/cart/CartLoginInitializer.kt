package br.com.shop.cart

import android.content.Context
import android.content.Intent
import br.com.shop.cart.view.CartActivity
import kotlin.properties.Delegates

class CartLoginInitializer private constructor(builder: Builder) {

    init {
        CartModuleSession.dependencies = builder.cartModuleDependencies
        CartModuleSession.callback = builder.callback
        CartModuleSession.theme = builder.theme
        CartModuleSession.keyCart = builder.keyCart
        startModuleActivity(builder.context)
    }

    private fun startModuleActivity(context: Context) {
        context.startActivity(
            Intent(context, CartActivity::class.java)
        )
    }

    class Builder {
        internal var context: Context by Delegates.notNull()
        internal var theme: Int by Delegates.notNull()
        internal var callback: CartModuleCallback by Delegates.notNull()
        internal var keyCart: String by Delegates.notNull()
        internal var cartModuleDependencies: CartModuleDependencies by Delegates.notNull()

        fun setContext(context: Context) = apply {
            this.context = context
        }

        fun setCallback(callback: CartModuleCallback) = apply {
            this.callback = callback
        }

        fun setTheme(theme: Int) = apply {
            this.theme = theme
        }

        fun setKeyCart(keyCart: String) = apply {
            this.keyCart = keyCart
        }

        fun setLoginModuleDependencies(cartModuleDependencies: CartModuleDependencies) = apply {
            this.cartModuleDependencies = cartModuleDependencies
        }

        fun build() {
            CartLoginInitializer(this)
        }
    }
}

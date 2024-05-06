package br.com.shop.cart

import kotlin.properties.Delegates

object CartModuleSession {

    var dependencies: CartModuleDependencies by Delegates.notNull()
    var callback: CartModuleCallback by Delegates.notNull()
    var theme: Int by Delegates.notNull()
    var keyCart: String by Delegates.notNull()

}
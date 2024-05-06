package br.com.shop.cart.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.shop.cart.databinding.CartItemListItensBinding
import br.com.shop.commons.ShopAnalytics
import br.com.shop.commons.ShopCache
import com.google.android.material.snackbar.Snackbar
import br.com.shop.cart.commons.CartAnalyticsConstants
import br.com.shop.cart.model.CartModel

class CartAdapter(
    private var mContext: Context,
    private var productList: MutableList<CartModel>,
    private var shopCache: ShopCache?,
    private var shopAnalytics: ShopAnalytics?
) : RecyclerView.Adapter<CartAdapter.CardViewHolder>() {

    inner class CardViewHolder(var view: CartItemListItensBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CartItemListItensBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun getItens() : MutableList<CartModel>{
        return productList
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val product = productList[position]
        val view = holder.view

        view.btnRemoveCart.setOnClickListener {
            shopAnalytics?.trackAction(
                CartAnalyticsConstants.TrackScreenShooping,
                "Removeu produto: " + product.description
            )
            Snackbar.make(it, "${product.description} produto removido do carrinho", Snackbar.LENGTH_SHORT).show()
        }
        view.txtProductDescription.text = product.description
        view.txtValueDescription.text = product.value
        view.txtStockDescription.text = product.stock.toString()
        if (product.hasPastValue)
            view.cartBoxValuePast.visibility = View.VISIBLE
        else
            view.cartBoxValuePast.visibility = View.GONE
        view.btnRemoveCart.isEnabled = product.hasPastStock
    }
}
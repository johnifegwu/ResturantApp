package com.mickleentityltdnigeria.resturantapp

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.extensions.CartItem
import com.mickleentityltdnigeria.resturantapp.extensions.module
import java.io.InputStream

class ShoppingCartAdapter(
    private var cartItems: List<CartItem>,
    itemClickListener: CartRecyclerViewItemClickListener
) : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    private val myContext: Context = ApplicationContextProvider.getContext()
    private val assetManager: AssetManager = myContext.getAssets()
    private val mItemClickListener: CartRecyclerViewItemClickListener = itemClickListener

    public fun setCartItems(cartItems: List<CartItem>) {
        this.cartItems = cartItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_cart_row, parent, false)

        //Create View Holder
        val myViewHolder = ViewHolder(view, myContext, assetManager)

        //Item Clicks
        myViewHolder.itemView.setOnClickListener(View.OnClickListener {
            mItemClickListener.onItemClicked(
                cartItems.get(myViewHolder.getLayoutPosition())
            )
        })

        myViewHolder.itemView.findViewById<Button>(R.id.btnUpdateCart).setOnClickListener(View.OnClickListener {
            try {
                var qty: String = myViewHolder.itemView.findViewById<EditText>(R.id.txtQty).text.toString()
                try {
                    if(qty.toInt()<0){
                        qty = "0"
                    }
                }catch (e:Exception){
                    qty = "0"
                }
                updateCartItem(qty.toInt(), cartItems.get(myViewHolder.getLayoutPosition()))
               //update data adapter.

            } catch (e: Exception) {
                Toast.makeText(it.context, e.message, Toast.LENGTH_LONG).show()
            }

        })

        return myViewHolder
    }

    override fun getItemCount() = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    private fun updateCartItem(Qty: Int, cartItem: CartItem) {
        module.shoppingCart.updateCartItem(cartItem.itemDesc, Qty)
    }


    class ViewHolder(
        view: View,
        private val myContext: Context,
        private val assetManager: AssetManager
    ) : RecyclerView.ViewHolder(view) {
        fun bind(cartItem: CartItem) {
            try
            {
                val ims: InputStream = assetManager.open(cartItem.itemImgUrl)
                val d: Drawable = Drawable.createFromStream(ims, null)

                itemView.findViewById<ImageView>(R.id.cartImg).setImageDrawable(d)
                itemView.findViewById<TextView>(R.id.txtCartDesc).text = cartItem.itemDesc
                itemView.findViewById<TextView>(R.id.txtPrice).text = "$"+ cartItem.itemPrice
                itemView.findViewById<EditText>(R.id.txtQty).setText(cartItem.itemQty.toString())
            }catch (e: Exception)
            {
                Toast.makeText(myContext, e.message, Toast.LENGTH_LONG).show()
            }

        }

    }

}

//RecyclerView Click Listener
interface CartRecyclerViewItemClickListener {
    fun onItemClicked(cartItem: CartItem)
}
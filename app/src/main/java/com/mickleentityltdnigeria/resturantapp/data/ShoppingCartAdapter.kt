package com.mickleentityltdnigeria.resturantapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper
import com.mickleentityltdnigeria.resturantapp.utils.module
import java.io.ByteArrayInputStream
import java.io.InputStream

class ShoppingCartAdapter(private var cartItems: List<CartItem>, itemClickListener: CartRecyclerViewItemClickListener) : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    private lateinit var progress: ProgressBar
    private val mItemClickListener: CartRecyclerViewItemClickListener = itemClickListener

    fun setCartItems(cartItems: List<CartItem>) {
        this.cartItems = cartItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_cart_row, parent, false)
        AppGlobals.setAppContext(parent.context)
        //Create View Holder
        val myViewHolder = ViewHolder(view, AppGlobals.getAppContext())
        progress = myViewHolder.itemView.findViewById<ProgressBar>(R.id.progressBarCartRow)

        //Item Clicks
        myViewHolder.itemView.setOnClickListener {
            mItemClickListener.onItemClicked(
                cartItems.get(myViewHolder.getLayoutPosition())
            )
        }

        myViewHolder.itemView.findViewById<Button>(R.id.btnUpdateCart).setOnClickListener {
            progress.visibility = View.VISIBLE
            try {
                var qty: String =
                    myViewHolder.itemView.findViewById<EditText>(R.id.txtQty).text.toString()
                try {
                    if (qty.toInt() < 0) {
                        qty = "0"
                    }
                } catch (e: Exception) {
                    qty = "0"
                }
                updateCartItem(qty.toInt(), cartItems.get(myViewHolder.getLayoutPosition()))
                //update data adapter.
            } catch (e: Exception) {
                Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
            }
            progress.visibility = View.GONE
        }


        return myViewHolder
    }

    private fun updateCartItem(qty:Int, cartItem: CartItem) {
        cartItem.foodQty = qty
        if(qty > 0){
            module.MyShoppingCart.UpdateCart(cartItem)
        }else{
            module.MyShoppingCart.DeleteCart(cartItem.getCartID().toString())
        }
        module.MyShoppingCart.getCartItems(module.userName)
    }

    override fun getItemCount() = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }



    class ViewHolder(view: View, private val myContext: Context) : RecyclerView.ViewHolder(view) {
        fun bind(cartItem: CartItem) {
            try
            {
                val ims: InputStream = ByteArrayInputStream(ImageHelper.getInstant().base64StringToByteArray(cartItem.foodImg)) //assetManager.open(cartItem.foodImgUrl)
                val d: Drawable = Drawable.createFromStream(ims, null)

                itemView.findViewById<ImageView>(R.id.cartImg).setImageDrawable(d)
                itemView.findViewById<TextView>(R.id.txtCartDesc).text = cartItem.foodDesc
                itemView.findViewById<TextView>(R.id.txtPrice).text = cartItem.currency+ cartItem.foodPrice
                itemView.findViewById<EditText>(R.id.txtQty).setText(cartItem.foodQty)
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
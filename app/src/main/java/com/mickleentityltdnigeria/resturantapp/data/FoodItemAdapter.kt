package com.mickleentityltdnigeria.resturantapp.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.ApplicationContextProvider
import com.mickleentityltdnigeria.resturantapp.R
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper
import com.mickleentityltdnigeria.resturantapp.utils.module


class FoodItemAdapter(private val foodItems: List<FoodItem>,
    itemClickListener: MyRecyclerViewItemClickListener) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    private lateinit var progress: ProgressBar
    private val myContext:Context = ApplicationContextProvider.getContext()
    private val mItemClickListener: MyRecyclerViewItemClickListener = itemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_resturant_row, parent, false)
        // Register interest in the CartItems Added.
        val cartItemAdded = CartItemChangedHandler { cartItems -> callGetCartItems(cartItems, view) }
        module.MyShoppingCart.cartItemsAdded.addListener("foodItemAdapterCartItemsAdded",cartItemAdded)
        //
        //Create View Holder
        val myViewHolder = ViewHolder(view, myContext)

        //Item Clicks
       myViewHolder.itemView.setOnClickListener(View.OnClickListener {
            mItemClickListener.onItemClicked(
                foodItems[myViewHolder.layoutPosition]
            )
        })
        progress = myViewHolder.itemView.findViewById(R.id.progressBarFoodRow)

        myViewHolder.itemView.findViewById<Button>(R.id.btnAdd).setOnClickListener(View.OnClickListener {
            progress.visibility = View.VISIBLE
            try {
                addToCart(1, foodItems[myViewHolder.layoutPosition])
            }catch (e: Exception){
                Toast.makeText(it.context, e.message, Toast.LENGTH_LONG).show()
            }
            progress.visibility = View.GONE
        })

        myViewHolder.itemView.findViewById<ImageView>(R.id.imgFood).setOnClickListener(View.OnClickListener {
            //
            progress.visibility = View.VISIBLE
            try {
               val fooditem = foodItems[myViewHolder.layoutPosition]
               Toast.makeText(it.context, fooditem.foodDesc, Toast.LENGTH_SHORT).show()
                module.foodItem = fooditem
                Navigation.findNavController(view)
                    .navigate(R.id.action_FirstFragment_to_showPictureFragment)
           }finally {
           }
            progress.visibility = View.GONE
        })

        return myViewHolder
    }

    override fun getItemCount() = foodItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = foodItems[position]
        holder.bind(fooditem)
    }

    private fun addToCart(Qty: Int, foodItem: FoodItem) {
        progress.visibility = View.VISIBLE
        val cartItem:CartItem = CartItem("",foodItem.foodID, foodItem.resturantID, module.userName,foodItem.foodImg,foodItem.foodPrice
        ,foodItem.currency,foodItem.foodDesc,foodItem.foodImgUrl,Qty)
        module.MyShoppingCart.AddCartItem(cartItem)
    }

    private fun callGetCartItems(cartItems:List<CartItem>, view:View){
        progress.visibility = View.GONE
        module.MyShoppingCart.getCartItems(module.userName)
    }


    class ViewHolder(view: View, private val myContext: Context) : RecyclerView.ViewHolder(view) {
        fun bind(fooditem: FoodItem) {
            try
            {
                itemView.findViewById<ImageView>(R.id.imgFood).setImageDrawable(ImageHelper.getInstant().imageFromString(fooditem.foodImg))
                itemView.findViewById<TextView>(R.id.txtFoodDesc).text = fooditem.foodDesc
                itemView.findViewById<TextView>(R.id.txtPrice).text = (fooditem.currency + fooditem.foodPrice)
            }catch (e: Exception)
            {
                Toast.makeText(myContext, e.message, Toast.LENGTH_LONG).show()
            }

        }

    }

}

//RecyclerView Click Listener
interface MyRecyclerViewItemClickListener {
    fun onItemClicked(f: FoodItem)
}
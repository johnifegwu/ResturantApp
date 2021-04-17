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
import java.text.NumberFormat


class FoodItemAdapter(
    private var foodItems: MutableList<FoodItem>,
    itemClickListener: MyRecyclerViewItemClickListener
) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    private val myContext:Context = ApplicationContextProvider.getContext()
    private val mItemClickListener: MyRecyclerViewItemClickListener = itemClickListener

    fun appendData(foodItems: MutableList<FoodItem>){
        this.foodItems.addAll(foodItems)
        notifyDataSetChanged()
    }

    fun clearData(){
        this.foodItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_fooditem_row, parent, false)
        //Create View Holder
        val myViewHolder = ViewHolder(view, myContext)
        //Item Clicks
       myViewHolder.itemView.setOnClickListener(View.OnClickListener {
           mItemClickListener.onItemClicked(
               foodItems[myViewHolder.layoutPosition]
           )
       })
        myViewHolder.itemView.findViewById<Button>(R.id.btnAdd).setOnClickListener(View.OnClickListener {
            try {
                val foodItem = foodItems[myViewHolder.layoutPosition]
                val cartItem:CartItem = CartItem(
                    "",
                    foodItem.foodID,
                    foodItem.resturantID,
                    module.userName,
                    foodItem.foodImg,
                    foodItem.foodPrice,
                    foodItem.currency,
                    foodItem.foodDesc,
                    foodItem.foodImgUrl,
                    1
                )
                mItemClickListener.onAddToCartClicked(cartItem)
            } catch (e: Exception) {
                Toast.makeText(it.context, e.message, Toast.LENGTH_LONG).show()
            }
        })

        myViewHolder.itemView.findViewById<ImageView>(R.id.imgFood).setOnClickListener(View.OnClickListener {
            //
            try {
                val fooditem = foodItems[myViewHolder.layoutPosition]
                Toast.makeText(it.context, fooditem.foodDesc, Toast.LENGTH_SHORT).show()
                module.foodItem = fooditem
                Navigation.findNavController(view)
                    .navigate(R.id.action_FirstFragment_to_showPictureFragment)
            } catch (e: Exception) {
                Toast.makeText(it.context, e.message, Toast.LENGTH_LONG).show()
            }
        })

        return myViewHolder
    }

    override fun getItemCount() = foodItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = foodItems[position]
        holder.bind(fooditem)
    }

    class ViewHolder(view: View, private val myContext: Context) : RecyclerView.ViewHolder(view) {
        fun bind(fooditem: FoodItem) {
            try
            {
                itemView.findViewById<ImageView>(R.id.imgFood).setImageDrawable(
                    ImageHelper.getInstance().imageFromString(
                        fooditem.foodImg
                    )
                )
                val dc = NumberFormat.getNumberInstance()//
                itemView.findViewById<TextView>(R.id.txtFoodDesc).text = fooditem.foodDesc
                itemView.findViewById<TextView>(R.id.txtPrice).text = (fooditem.currency + dc.format(fooditem.foodPrice))
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
    fun onAddToCartClicked(cartItem: CartItem)
}
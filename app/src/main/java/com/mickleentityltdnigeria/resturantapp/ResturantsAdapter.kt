package com.mickleentityltdnigeria.resturantapp

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.data.FoodItem
import com.mickleentityltdnigeria.resturantapp.extensions.module
import java.io.InputStream


class ResturantsAdapter(
    private val foodItems: List<FoodItem>,
    itemClickListener: MyRecyclerViewItemClickListener
) : RecyclerView.Adapter<ResturantsAdapter.ViewHolder>() {

    private val myContext:Context = ApplicationContextProvider.getContext()
    private val assetManager: AssetManager = myContext.getAssets()
    private val mItemClickListener: MyRecyclerViewItemClickListener = itemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_resturant_row, parent, false)

        //Create View Holder
        val myViewHolder = ViewHolder(view, myContext, assetManager)

        //Item Clicks
       myViewHolder.itemView.setOnClickListener(View.OnClickListener {
            mItemClickListener.onItemClicked(
                foodItems.get(myViewHolder.getLayoutPosition())
            )
        })

        myViewHolder.itemView.findViewById<Button>(R.id.btnAdd).setOnClickListener(View.OnClickListener {
            try {
                addToCart(1, foodItems.get(myViewHolder.getLayoutPosition()))
            }catch (e: Exception){
                //Toast.makeText(it.context, e.message, Toast.LENGTH_LONG).show()
            }

        })

        myViewHolder.itemView.findViewById<ImageView>(R.id.imgFood).setOnClickListener(View.OnClickListener {
            val intent = module.genIntentForShowPic(it.context)
            try {
               val fooditem = foodItems.get(myViewHolder.getLayoutPosition())
               Toast.makeText(it.context, fooditem.foodDesc, Toast.LENGTH_SHORT).show()
               intent.putExtra("payLoad",fooditem)
               startActivity(it.context,intent,null)
           }finally {
               getActivity(it.context,intent.hashCode(),intent,intent.flags).cancel()
           }

        })

        return myViewHolder
    }

    override fun getItemCount() = foodItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = foodItems[position]
        holder.bind(fooditem)
    }

    private fun addToCart(Qty: Int, foodItem: FoodItem) {
        module.shoppingCart.addCartItem(
            foodItem.id,
            foodItem.foodPrice,
            foodItem.foodDesc,
            Qty,
            foodItem.foodUrl
        )
    }


    class ViewHolder(
        view: View,
        private val myContext: Context,
        private val assetManager: AssetManager
    ) : RecyclerView.ViewHolder(view) {
        fun bind(fooditem: FoodItem) {
            try
            {
                val ims: InputStream = assetManager.open(fooditem.foodUrl)
                val d: Drawable = Drawable.createFromStream(ims, null)

                itemView.findViewById<ImageView>(R.id.imgFood).setImageDrawable(d)
                itemView.findViewById<TextView>(R.id.txtFoodDesc).text = fooditem.foodDesc
                itemView.findViewById<TextView>(R.id.txtPrice).text = "$"+ fooditem.foodPrice
            }catch (e: Exception)
            {
                Toast.makeText(myContext, e.message, Toast.LENGTH_LONG).show()
            }

        }

    }

}

//RecyclerView Click Listener
interface MyRecyclerViewItemClickListener {
    fun onItemClicked(fooditem: FoodItem)
}
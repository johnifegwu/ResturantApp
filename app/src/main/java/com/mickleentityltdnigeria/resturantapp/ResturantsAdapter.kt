package com.mickleentityltdnigeria.resturantapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.data.FoodItem
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import java.io.InputStream


class ResturantsAdapter(private val foodItems: List<FoodItem>) :
    RecyclerView.Adapter<ResturantsAdapter.ViewHolder>() {

    private val myContext:Context = ApplicationContextProvider.getContext()
    private val assetManager: AssetManager = myContext.getAssets()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_resturant_row, parent, false)
        return ViewHolder(view,myContext,assetManager)
    }

    override fun getItemCount() = foodItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = foodItems[position]
        holder.bind(fooditem);
    }

    class ViewHolder(view: View, private val myContext:Context,private val assetManager: AssetManager) : RecyclerView.ViewHolder(view) {
        fun bind(fooditem: FoodItem) {
            try
            {
                val ims: InputStream = assetManager.open(fooditem.foodUrl)
                val d: Drawable = Drawable.createFromStream(ims,null)
                itemView.findViewById<ImageView>(R.id.imgFood).setImageDrawable(d)
                itemView.findViewById<TextView>(R.id.txtFoodDesc).text = fooditem.foodDesc
                itemView.findViewById<TextView>(R.id.txtPrice).text = "$"+ fooditem.foodPrice
            }catch (e: Exception)
            {
               val s = e.message
            }

        }

    }

}
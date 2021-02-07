package com.mickleentityltdnigeria.resturantapp

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.data.FoodItem


class ResturantsAdapter(private val foodItems: List<FoodItem>) :
    RecyclerView.Adapter<ResturantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_resturant_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = foodItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = foodItems[position];
        holder.bind(fooditem);
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(fooditem: FoodItem) {
            itemView.findViewById<ImageView>(R.id.imgFood)
                .setImageBitmap(BitmapFactory.decodeFile("drawable/" + fooditem.foodUrl ));
            itemView.findViewById<TextView>(R.id.txtFoodDesc).text = fooditem.foodDesc;
            itemView.findViewById<TextView>(R.id.txtPrice).text = "$"+ fooditem.foodPrice;
        }
    }

}
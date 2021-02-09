package com.mickleentityltdnigeria.resturantapp

//import android.widget.Button
//import androidx.navigation.fragment.findNavController

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.data.FoodItem
import com.mickleentityltdnigeria.resturantapp.extensions.module


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/

        val foodItems = listOf(
            FoodItem(11, "meal1.jpg", "Bulgarian fish toast & veggie", 14.5),
            FoodItem(12, "meal2.jpg", "Macaroni with tomato source", 10.89),
            FoodItem(13, "meal3.jpg", "Italian pancake with honey", 12.99),
            FoodItem(14, "meal4.jpg", "Mediterranean grill", 20.43),
            FoodItem(15, "meal5.jpg", "Honey pancake", 7.99),
            FoodItem(16, "meal6.jpg", "Chicken lap grill", 30.0),
            FoodItem(17, "meal7.jpg", "Mediterranean chicken", 20.55),
            FoodItem(18, "bugger1.jpg", "Continental pancake", 4.99),
            FoodItem(19, "bugger2.jpg", "African pancake with strawberry cream", 8.99),
            FoodItem(20, "bugger3.jpg", "Texas pancake with vanilla", 3.99)
        )

        //Reference of RecyclerView
       val mRecyclerView:RecyclerView = view.findViewById<RecyclerView>(R.id.resturantRecyclerView)

        //Linear Layout Manager
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        //Set Layout Manager to RecyclerView
        mRecyclerView.layoutManager = linearLayoutManager

        //Create adapter
        val adapter = ResturantsAdapter(foodItems, object : MyRecyclerViewItemClickListener {
            //Handling clicks
            override fun onItemClicked(foodItem: FoodItem) {
                Toast.makeText(context, foodItem.foodDesc, Toast.LENGTH_SHORT).show()
                val intent = module.genIntentForShowPic(context)
                intent.putExtra("payLoad",foodItem)
                startActivity(intent)
            }
        })

        //Set adapter to RecyclerView
        mRecyclerView.adapter = adapter

    }
}
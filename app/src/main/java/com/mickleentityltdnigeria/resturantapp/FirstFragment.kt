package com.mickleentityltdnigeria.resturantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
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

        // Register interest in the CartItem Change.
        val cartChanged = CartItemChangedHandler { qty -> displayCartQty(qty, view) }
        module.shoppingCart.cartItemChanged.addListener(cartChanged)

        val foodItems = listOf(
            FoodItem(
                11,
                "meal1.jpg",
                "Bulgarian fish toast & veggie",
                14.5
            ),
            FoodItem(
                12,
                "meal2.jpg",
                "Macaroni with tomato source",
                10.89
            ),
            FoodItem(
                13,
                "meal3.jpg",
                "Italian pancake with honey",
                12.99
            ),
            FoodItem(
                14,
                "meal4.jpg",
                "Mediterranean grill",
                20.43
            ),
            FoodItem(
                15,
                "meal5.jpg",
                "Honey pancake",
                7.99
            ),
            FoodItem(
                16,
                "meal6.jpg",
                "Chicken lap grill",
                30.0
            ),
            FoodItem(
                17,
                "meal7.jpg",
                "Mediterranean chicken",
                20.55
            ),
            FoodItem(
                18,
                "bugger1.jpg",
                "Continental pancake",
                4.99
            ),
            FoodItem(
                19,
                "bugger2.jpg",
                "African pancake with strawberry cream",
                8.99
            ),
            FoodItem(
                20,
                "bugger3.jpg",
                "Texas pancake with vanilla",
                3.99
            )
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
                //do something here.
            }

        })

        //Set adapter to RecyclerView
        mRecyclerView.adapter = adapter

    }

    private fun displayCartQty(Qty: Int, v: View?) {
        Snackbar.make(v!!, "$Qty item(s) added to Cart.", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
        //Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
    }

}
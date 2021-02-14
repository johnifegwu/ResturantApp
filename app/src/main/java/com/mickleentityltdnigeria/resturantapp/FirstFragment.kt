package com.mickleentityltdnigeria.resturantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
import com.mickleentityltdnigeria.resturantapp.service.Service
import com.mickleentityltdnigeria.resturantapp.utils.module


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    lateinit var foodItems:List<FoodItem>
    lateinit var txtsearchString:EditText
    lateinit var txtsearchZipCode:EditText
    lateinit var btnSearch:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.btnSearch = view.findViewById<Button>(R.id.btnSearch)
        this.txtsearchString = view.findViewById<EditText>(R.id.txtSearchString)
        this.txtsearchZipCode = view.findViewById<EditText>(R.id.txtSearchZipCode)
        this.txtsearchZipCode.setText(module.zipCode)
        btnSearch.setOnClickListener(View.OnClickListener {
            try {
                if(txtsearchString.text.toString() != "" && txtsearchZipCode.text.toString() != "" ){
                    foodItems = Service.food().SearchFoodItems(txtsearchString.text.toString(), txtsearchZipCode.text.toString())
                    //Reference of RecyclerView
                    val mRecyclerView:RecyclerView =  view.findViewById<RecyclerView>(R.id.resturantRecyclerView)

                    //Create adapter
                    val adapter = FoodItemAdapter(foodItems,object : MyRecyclerViewItemClickListener {
                        override fun onItemClicked(foodItem: FoodItem) {

                        }
                    })

                    //Set adapter to RecyclerView
                    mRecyclerView.swapAdapter(adapter, false)
                    //
                }else{
                    Snackbar.make(view, "Please enter a valid Zip-Code and the kind of food you would want to eat.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

            } catch (e: java.lang.Exception) {
                Toast.makeText(this.context, e.message, Toast.LENGTH_LONG).show()
            }
        })
        // Register interest in the CartItem Change.
        val cartChanged = CartItemChangedHandler { qty -> displayCartQty(qty, view) }
        Service.cart().Cart.cartItemChanged.addListener(cartChanged)

        //Reference of RecyclerView
       val mRecyclerView:RecyclerView = view.findViewById<RecyclerView>(R.id.resturantRecyclerView)

        //Linear Layout Manager
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        //Set Layout Manager to RecyclerView
        mRecyclerView.layoutManager = linearLayoutManager

        //Create adapter
        val adapter = FoodItemAdapter(foodItems, object : MyRecyclerViewItemClickListener {
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
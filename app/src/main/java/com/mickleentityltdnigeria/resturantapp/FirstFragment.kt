package com.mickleentityltdnigeria.resturantapp

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mickleentityltdnigeria.resturantapp.dalc.CountryDalc
import com.mickleentityltdnigeria.resturantapp.dalc.FoodDalc
import com.mickleentityltdnigeria.resturantapp.data.FoodItemAdapter
import com.mickleentityltdnigeria.resturantapp.data.MyRecyclerViewItemClickListener
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem
import com.mickleentityltdnigeria.resturantapp.data.model.CurrentLocation
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
import com.mickleentityltdnigeria.resturantapp.extensions.CountryChangedHandler
import com.mickleentityltdnigeria.resturantapp.extensions.CurrentLocationChangedHandler
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler
import com.mickleentityltdnigeria.resturantapp.utils.module


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    var foodItems:List<FoodItem> = ArrayList<FoodItem>()
    lateinit var txtsearchString:EditText
    lateinit var txtsearchZipCode:TextView
    lateinit var btnSearch:Button
    lateinit var btnChangeLocation:TextView
    lateinit var progress:ProgressBar
    lateinit var foodData: FoodDalc
    lateinit var countryData: CountryDalc;
    //lateinit var mAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //mAuth.currentUser?.reload()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       //Initialise ShoppingCart
        this.countryData = CountryDalc()
        foodData = FoodDalc()
        this.progress = view.findViewById<ProgressBar>(R.id.progressBarSearch)
        this.progress.visibility = View.VISIBLE
        //
        this.btnSearch = view.findViewById<Button>(R.id.btnSearch)
        this.btnChangeLocation = view.findViewById<TextView>(R.id.btnChangeLocation)
        this.txtsearchString = view.findViewById<EditText>(R.id.txtSearchString)
        this.txtsearchZipCode = view.findViewById<TextView>(R.id.txtSearchZipCode)
        //
        this.txtsearchZipCode.text = module.zipCode
        //
        val countriesFetched = CountryChangedHandler { countries ->
           module.myCountries = countries
        }
        this.countryData.countriesFetched.addListener(countriesFetched)
        try{
            this.countryData.getCountries()
        }catch (e:Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
        }
        // Register interest in the CurrentLocation.
        val locationsFetched = CurrentLocationChangedHandler { locations ->
            val l:CurrentLocation = locations.get(0)
            module.locationID = l.getLocationID().toString()
            module.country = l.getCountry().toString()
            module.state = l.getState().toString()
            module.city = l.getCity().toString()
            module.zipCode = l.getZipCode().toString()
            this.txtsearchZipCode.text = module.zipCode
        }
        val locationsNotFound = CurrentLocationChangedHandler { locations ->
            val lc = CurrentLocation(
                "",
                module.userID,
                module.country,
                module.state,
                module.city,
                module.zipCode
            )
            module.MyCurrentLocation.AddCurrentLocation(lc)
        }
        val locationsAdded = CurrentLocationChangedHandler { locations ->
            val l = locations.get(0)
            module.locationID = l.getLocationID().toString()
            module.country = l.getCountry().toString()
            module.state = l.getState().toString()
            module.city = l.getCity().toString()
            module.zipCode = l.getZipCode().toString()
            this.txtsearchZipCode.text = module.zipCode
        }
        val locationUpdated = CurrentLocationChangedHandler { locations ->
            val l = locations.get(0)
            module.locationID = l.getLocationID().toString()
            module.country = l.getCountry().toString()
            module.state = l.getState().toString()
            module.city = l.getCity().toString()
            module.zipCode = l.getZipCode().toString()
            this.txtsearchZipCode.text = module.zipCode
        }
        module.MyCurrentLocation.locationsUpdated.addListener(locationUpdated)
        module.MyCurrentLocation.locationsFetched.addListener(locationsFetched)
        module.MyCurrentLocation.locationsNotFound.addListener(locationsNotFound)
        module.MyCurrentLocation.locationsAdded.addListener(locationsAdded)
        try{
            module.MyCurrentLocation.GetCurrentLocation(module.userID)
        }catch (e:Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG)
        }
        //
        // Register interest in the FoodItemsFetched.
        val foodItemsFetched = FoodItemUpdatedHandler { foodItems -> getSearchResults(
            foodItems,
            view
        ) }
        foodData.foodItemsFetched.addListener(foodItemsFetched)
        //
        btnSearch.setOnClickListener(View.OnClickListener {
            this.progress.visibility = View.VISIBLE
            try {
                if (!TextUtils.isEmpty(
                        txtsearchString.text.toString().trim()
                    ) && !TextUtils.isEmpty(
                        txtsearchZipCode.text.toString().trim()
                    )
                ) {
                    foodData.SearchFoodItems(
                        txtsearchString.text.toString().trim(),
                        module.country.trim() + "-" + txtsearchZipCode.text.toString().trim()
                    )

                } else {
                    Snackbar.make(
                        view,
                        "Please enter a valid Zip-Code and the kind of food you would want to eat.",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Action", null).show()
                }

            } catch (e: java.lang.Exception) {
                Toast.makeText(this.context, e.message, Toast.LENGTH_LONG).show()
            }
            this.progress.visibility = View.GONE
        })
        //
        btnChangeLocation.setOnClickListener(View.OnClickListener {
            this.progress.visibility = View.GONE
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_FirstFragment_to_changeLocationFragment)
        })
        // Register interest in the CartItem Change.
        val cartItemsFetched = CartItemChangedHandler { cartItems -> displayCartQty(cartItems, view) }
        module.MyShoppingCart.cartItemsFetched.addListener(cartItemsFetched)
        try{
            module.MyShoppingCart.getCartItems(module.userName)
        }catch (e:Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG)
        }
        //Reference of RecyclerView
       val mRecyclerView:RecyclerView = view.findViewById<RecyclerView>(R.id.resturantRecyclerView)

        //Linear Layout Manager
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        //Set Layout Manager to RecyclerView
        mRecyclerView.layoutManager = linearLayoutManager

        //Create adapter
        val adapter = FoodItemAdapter(foodItems, object : MyRecyclerViewItemClickListener {
            //Handling clicks
            override fun onItemClicked(f: FoodItem) {
                //do something here.
            }

        })

        //Set adapter to RecyclerView
        mRecyclerView.adapter = adapter
        this.progress.visibility = View.GONE
    }

    private fun getSearchResults(foodItems: List<FoodItem>, view: View){
        //Reference of RecyclerView
        val mRecyclerView: RecyclerView =
            view.findViewById<RecyclerView>(R.id.resturantRecyclerView)

        //Create adapter
        val adapter = FoodItemAdapter(foodItems,
            object : MyRecyclerViewItemClickListener {
                override fun onItemClicked(f: FoodItem) {

                }
            })
        //Set adapter to RecyclerView
        mRecyclerView.swapAdapter(adapter, false)
        //
    }

    private fun displayCartQty(cartItems: List<CartItem>, v: View?) {
        val cartTotalQty = module.getCartTotalQty(cartItems)
        if(cartTotalQty > 0){
            this.btnChangeLocation.isClickable = false
            this.btnChangeLocation.isEnabled = false
        }else{
            this.btnChangeLocation.isClickable = true
            this.btnChangeLocation.isEnabled = true
        }
        Snackbar.make(
            v!!,
            "$cartTotalQty item(s) added to Cart.",
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
        //Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
    }


}
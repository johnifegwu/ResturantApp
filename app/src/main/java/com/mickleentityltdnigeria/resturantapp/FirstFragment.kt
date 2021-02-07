package com.mickleentityltdnigeria.resturantapp

//import android.widget.Button
//import androidx.navigation.fragment.findNavController
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.data.FoodItem

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

//    private lateinit var restRecyclerView: RecyclerView

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
            FoodItem("meal1.jpg","Bulgarian fish toast & veggie", 14.5 ) ,
            FoodItem("meal2.jpg","Macaroni with tomato source", 10.89 ),
            FoodItem("meal3.jpg","Italian pancake with honey", 12.99 ),
            FoodItem("meal4.jpg","Mediterranean grill", 20.43),
            FoodItem("meal5.jpg","Honey pancake", 7.99 ),
            FoodItem("meal6.jpg","Chicken lap grill", 30.0 ),
            FoodItem("meal7.jpg","Mediterranean chicken", 20.55 ),
            FoodItem("bugger1.jpg","Continental pancake", 4.99 ),
            FoodItem("bugger2.jpg","African pancake with strawberry cream", 8.99 ),
            FoodItem("bugger3.jpg","Texas pancake with vanilla", 3.99 )
        )

        view.findViewById<RecyclerView>(R.id.resturantRecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ResturantsAdapter(foodItems)
        }
    }
}
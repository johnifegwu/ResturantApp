package com.mickleentityltdnigeria.resturantapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
import com.mickleentityltdnigeria.resturantapp.service.Service
import com.mickleentityltdnigeria.resturantapp.utils.module
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var txtCart: TextView
    //private val myContext: Context = ApplicationContextProvider.getContext()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        AppGlobals.setAppContext(this)
        //
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            AppGlobals.setAppContext(this)
            Snackbar.make(view, "Shopping Cart", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            try {
                /*val intent = Intent(this,SampleFolderActivity::class.java)
                startActivity(intent)*/
                if(module.isLoggedIn){
                    Navigation.findNavController(view)
                        .navigate(R.id.action_FirstFragment_to_shoppingCartFragment)
                }else{
                    //Get ID from the system.
                    val data = HashMap<String, Any>()
                    data["UserName"] = "johnifegwu"
                    data["Email"] = "johnifegwu@outlool.com"

                    // Write a message to the database
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("message")

                    myRef.setValue("Hello, World!")

                    Toast.makeText(AppGlobals.getAppContext(), "New user added.", Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception) {
                Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
        this.txtCart = findViewById<TextView>(R.id.txtCartQty)

        // Register interest in the completed report
        var cartChanged = CartItemChangedHandler {
            displayCartQty(it)
        }
        Service.cart().Cart.cartItemChanged.addListener(cartChanged)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayCartQty(qty: Int) {
        txtCart.text = qty.toString()
    }
}
package com.mickleentityltdnigeria.resturantapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
import com.mickleentityltdnigeria.resturantapp.service.CartService
import com.mickleentityltdnigeria.resturantapp.service.Service
import com.mickleentityltdnigeria.resturantapp.utils.module


class MainActivity : AppCompatActivity() {

    lateinit var txtCart: TextView
    //private val myContext: Context = ApplicationContextProvider.getContext()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            AppGlobals.setAppContext(this)
            Snackbar.make(view, "Shopping Cart", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            try {
                if(module.isLoggedIn){
                    val intent = module.genIntentForShoppingCart(AppGlobals.getAppContext())
                    AppGlobals.StartActivity(intent)
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
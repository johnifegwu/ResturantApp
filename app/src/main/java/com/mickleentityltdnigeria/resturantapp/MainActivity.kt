package com.mickleentityltdnigeria.resturantapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mickleentityltdnigeria.resturantapp.extensions.module
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler

class MainActivity : AppCompatActivity() {

    public lateinit var txtCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        this.txtCart = findViewById<TextView>(R.id.txtCartQty)

        // Register interest in the completed report
        var cartChanged = CartItemChangedHandler {
            displayCartQty(it)
        }
        module.shoppingCart.cartItemChanged.addListener(cartChanged)

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

    public fun displayCartQty(qty:Int) {
        txtCart.setText(qty.toString())
    }
}
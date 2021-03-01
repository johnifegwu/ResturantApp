package com.mickleentityltdnigeria.resturantapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
import com.mickleentityltdnigeria.resturantapp.utils.module


class MainActivity : AppCompatActivity() {


    lateinit var txtCart: TextView
    //private val myContext: Context = ApplicationContextProvider.getContext()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        AppGlobals.setAppContext(this)
        module.activity = this
        //
        try{
            setContentView(R.layout.activity_main)
            setSupportActionBar(findViewById(R.id.toolbar))
        }catch (e: Exception){
            Snackbar.make(findViewById(R.id.txtCartQty), e.message.toString(), Snackbar.LENGTH_LONG).setAction(
                "Action",
                null
            ).show()
        }
        //
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        //
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            AppGlobals.setAppContext(this)
            Snackbar.make(view, "Shopping Cart", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            try {
                /*val intent = Intent(this,SampleFolderActivity::class.java)
                startActivity(intent)*/
                if(module.isLoggedIn){
                    //
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_FirstFragment_to_shoppingCartFragment)
                    //
                }
            }catch (e: Exception) {
                Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
        this.txtCart = findViewById<TextView>(R.id.txtCartQty)

        // Register interest in the completed report
        val cartChanged = CartItemChangedHandler {
            displayCartQty(it)
        }
        module.MyShoppingCart.cartItemsFetched.addListener(cartChanged)

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

    private fun displayCartQty(cartItems: List<CartItem>) {
        txtCart.text = module.getCartTotalQty(cartItems).toString()
    }
    fun getCartTotal(){
        Toast.makeText(this,txtCart.text.toString(),Toast.LENGTH_LONG).show()
    }
}
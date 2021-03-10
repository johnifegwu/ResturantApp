package com.mickleentityltdnigeria.resturantapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mickleentityltdnigeria.resturantapp.dalc.CartDalc
import com.mickleentityltdnigeria.resturantapp.dalc.CurrentLocationDalc
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem
import com.mickleentityltdnigeria.resturantapp.data.model.CurrentLocation
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler
import com.mickleentityltdnigeria.resturantapp.extensions.CurrentLocationChangedHandler
import com.mickleentityltdnigeria.resturantapp.extensions.LoginSuccessHandler
import com.mickleentityltdnigeria.resturantapp.utils.module


public class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar:Toolbar
    lateinit var navigationView:NavigationView
    lateinit var txtCart: TextView
    lateinit var btnMenuLoginLogout:TextView
    lateinit var txtMenuUserName:TextView
    lateinit var txtMenuCurrentLocation:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            AppGlobals.setAppContext(this)
            setContentView(R.layout.activity_main)
            //
            AppGlobals.setAppContext(this)
            module.activity = this
            //
            toolbar = findViewById(R.id.toolbar)
            //
            setSupportActionBar(findViewById(R.id.toolbar))
            drawerLayout = findViewById(R.id.drawer)
            navigationView = findViewById(R.id.navigationView)
            navigationView.setNavigationItemSelectedListener(this)
            actionBarDrawerToggle =  ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
            )
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.isDrawerIndicatorEnabled = true
            actionBarDrawerToggle.syncState()
            /*try{
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.HomeFragment)
            }finally {

            }*/
        }catch (e: Exception){
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
        //
        FirebaseDatabase.getInstance().setPersistenceEnabled(false)
        module.MyCurrentLocation = CurrentLocationDalc()
        module.MyShoppingCart = CartDalc()
        //
        val header: View = navigationView.getHeaderView(0)
        btnMenuLoginLogout = header.findViewById<TextView>(R.id.btnMenuLoginLogout)
        txtMenuUserName = header.findViewById<TextView>(R.id.txtMenuUserName)
        txtMenuCurrentLocation = header.findViewById<TextView>(R.id.txtMenuCurrentLocation)
        //
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view -> goToShoppingCart() }
        btnMenuLoginLogout.setOnClickListener { view ->
            if(btnMenuLoginLogout.text == ("Logout")){
                drawerLayout.closeDrawer(navigationView)
                logOut()
            }
            if(btnMenuLoginLogout.text == ("Login")){
                drawerLayout.closeDrawer(navigationView)
                logIn()
            }
        }
        val loginSuccessHandler = LoginSuccessHandler {
           updateUI()
        }
        module.loginSuccessHandlerEvent.addListener(loginSuccessHandler)
        //
        module.MyShoppingCart = CartDalc()

        this.txtCart = findViewById<TextView>(R.id.txtCartQty)

        // Register interest in the completed report
        val cartChanged = CartItemChangedHandler {
            displayCartQty(it)
        }
        module.MyShoppingCart.cartItemsFetched.addListener(cartChanged)
        // Register interest in the CurrentLocation.
        val locationsFetched = CurrentLocationChangedHandler { locations ->
            val l: CurrentLocation = locations[0]
            this.txtMenuCurrentLocation.text = ("Location: "+ l.getCountry() + ", " + l.getZipCode())
        }
        val locationsAdded = CurrentLocationChangedHandler { locations ->
            val l = locations[0]
            this.txtMenuCurrentLocation.text = ("Location: "+ l.getCountry() + ", " + l.getZipCode())
        }
        val locationUpdated = CurrentLocationChangedHandler { locations ->
            val l = locations[0]
            this.txtMenuCurrentLocation.text = ("Location: "+ l.getCountry() + ", " + l.getZipCode())
        }
        module.MyCurrentLocation.locationsUpdated.addListener(locationUpdated)
        module.MyCurrentLocation.locationsFetched.addListener(locationsFetched)
        module.MyCurrentLocation.locationsAdded.addListener(locationsAdded)
        this.updateUI()
    }

    private fun updateUI(){
       if(module.isLoggedIn){
           btnMenuLoginLogout.text = ("Logout")
           txtMenuUserName.text = (module.lastName + ", " + module.firstName)
           txtMenuCurrentLocation.text = ("Location: "+ module.country + ", " + module.zipCode)
       }else{
           btnMenuLoginLogout.text = ("Login")
           txtMenuUserName.text = ("Welcome Guest!")
           txtMenuCurrentLocation.text = ("Location: not set")
       }
    }

    private fun logOut(){
        //
        try{
            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
            module.userID = ""
            module.userName = ""
            module.userType = ""
            module.zipCode = ""
            module.country = ""
            module.state = ""
            module.city = ""
            module.isLoggedIn = false
            module.firstName = ""
            module.lastName = ""
            updateUI()
            Toast.makeText(AppGlobals.getAppContext(), "Sign out successfully.", Toast.LENGTH_LONG).show()
            //
        }catch (e: Exception){
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun logIn(){
        try {
            if(!module.isLoggedIn){
                //
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.LoginFragment)
                //
            }
        }catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }


    private fun goToShoppingCart(){
        try {
            if(module.isLoggedIn){
                //
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.shoppingCartFragment)
                //
            }
        }catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToFirstFragment(){
        try {
            if(module.isLoggedIn){
                //
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.FirstFragment)
                //
            }
        }catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToCustomerOrderListFragment(){
        try {
            if(module.isLoggedIn){
                //
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.customerOrderListFragment)
                //
            }
        }catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoHome(){
        try {
            //
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.HomeFragment)
                //
        }catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun displayCartQty(cartItems: List<CartItem>) {
        txtCart.text = module.getCartTotalQty(cartItems).toString()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHome -> gotoHome()
            R.id.menuBuyFood -> goToFirstFragment()
            R.id.menuShoppingCart -> goToShoppingCart()
            R.id.menuMyOrders -> goToCustomerOrderListFragment()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.itemId == R.id.menu_cart){
            goToShoppingCart()
        }
        if(item.itemId == R.id.menu_home){
            gotoHome()
        }
        return super.onOptionsItemSelected(item)
    }

}
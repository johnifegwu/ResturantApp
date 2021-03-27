package com.mickleentityltdnigeria.resturantapp



import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
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
import com.mickleentityltdnigeria.resturantapp.extensions.*
import com.mickleentityltdnigeria.resturantapp.utils.module


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView
    lateinit var txtCart: TextView
    lateinit var btnMenuLoginLogout: TextView
    lateinit var txtMenuUserName: TextView
    lateinit var txtMenuCurrentLocation: TextView
    lateinit var cartMenu:MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        module.PACKAGE_NAME = applicationContext.packageName
        try {
            AppGlobals.setAppContext(this)
            setContentView(R.layout.activity_main)
            module.activity = this
            //
            toolbar = findViewById(R.id.toolbar)
            //
            setSupportActionBar(findViewById(R.id.toolbar))
            drawerLayout = findViewById(R.id.drawer)
            navigationView = findViewById(R.id.navigationView)
            navigationView.setNavigationItemSelectedListener(this)
            actionBarDrawerToggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
            )
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.isDrawerIndicatorEnabled = true
            actionBarDrawerToggle.syncState()
            //
        } catch (e: Exception) {
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
            val logout:String = view.context.getString(R.string.menu_logout)
            val login:String = view.context.getString(R.string.menu_login)
            if (btnMenuLoginLogout.text == logout) {
                drawerLayout.closeDrawer(navigationView)
                logOut()
                updateUI()
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.LoginFragment)
            }
            if (btnMenuLoginLogout.text == login) {
                drawerLayout.closeDrawer(navigationView)
                logIn()
            }
        }
        val loginSuccessHandler = LoginSuccessHandler {
            updateUI()
        }
        module.loginSuccessHandlerEvent.addListener(loginSuccessHandler)
        //Set inactivity screen locker
        val userTimedOut = TimeOutEventHandler{
            logOut()
            updateUI()
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.LoginFragment)
        }
        ApplockManager.getInstance().enableDefaultAppLockIfAvailable(this.application)
        ApplockManager.userTimedOut.addListener(userTimedOut)
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
            this.txtMenuCurrentLocation.text =
                ("Location: " + module.toLowerCase(l.getCountry()) + ", " + l.getZipCode())
        }
        val locationsAdded = CurrentLocationChangedHandler { locations ->
            val l = locations[0]
            this.txtMenuCurrentLocation.text =
                ("Location: " + module.toLowerCase(l.getCountry()) + ", " + l.getZipCode())
        }
        val locationUpdated = CurrentLocationChangedHandler { locations ->
            val l = locations[0]
            this.txtMenuCurrentLocation.text =
                ("Location: " + module.toLowerCase(l.getCountry()) + ", " + l.getZipCode())
        }
        module.MyCurrentLocation.locationsUpdated.addListener(locationUpdated)
        module.MyCurrentLocation.locationsFetched.addListener(locationsFetched)
        module.MyCurrentLocation.locationsAdded.addListener(locationsAdded)
        this.updateUI()
    }

    private fun updateUI() {
        val logout: String = applicationContext.getString(R.string.menu_logout)
        val login = applicationContext.getString(R.string.menu_login)
        if (module.isLoggedIn) {
            btnMenuLoginLogout.text = logout
            txtMenuUserName.text = (module.lastName + ", " + module.firstName)
            txtMenuCurrentLocation.text =
                ("Location: " + module.toLowerCase(module.country) + ", " + module.zipCode)
           if(module.userType.equals(module.UserTypeSUPPER)){
               navigationView.menu.clear()
               navigationView.inflateMenu(R.menu.admin_drawer_menu)
           }else if(module.userType.equals(module.UserTypeSELLER)){
               navigationView.menu.clear()
               navigationView.inflateMenu(R.menu.merchant_drawer_menu)
           }else if(module.userType.equals(module.UserTypeCUSTOMER)){
               navigationView.menu.clear()
               navigationView.inflateMenu(R.menu.drawer_menu)
           }
            toolbar.isVisible = true
        } else {
            btnMenuLoginLogout.text = login
            txtMenuUserName.text = ("Welcome Guest!")
            txtMenuCurrentLocation.text = ("Location: not set")
            //hide menus
            toolbar.isVisible = false
            findViewById<FrameLayout>(R.id.frame_main).isVisible = false
        }
    }

    private fun displayCartQty(cartItems: List<CartItem>) {
        val cartTotal:Int = module.getCartTotalQty(cartItems)
        if(cartTotal > 0){
            txtCart.text = cartTotal.toString()
            findViewById<FrameLayout>(R.id.frame_main).isVisible = true
            cartMenu.isVisible = true
        }else{
            txtCart.text = cartTotal.toString()
            findViewById<FrameLayout>(R.id.frame_main).isVisible = false
            cartMenu.isVisible = false
        }

    }

    private fun logOut() {
        //
        try {
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
            Toast.makeText(
                AppGlobals.getAppContext(),
                "Signed out successfully.",
                Toast.LENGTH_LONG
            ).show()
            //
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun logIn() {
        try {
            if (!module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.LoginFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoAbout() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.aboutFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }


    private fun goToShoppingCart() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.shoppingCartFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToFirstFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.FirstFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToProfileFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.customerProfileFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToSellOnBonAppetittFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.sellOnBonAppetittFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToChangePasswordFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.ChangePasswordFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToCustomerOrderListFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.customerOrderListFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoHome() {
        try {
            //
            drawerLayout.closeDrawer(navigationView)
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.HomeFragment)
            //
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToNewRegistrationFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.newRegistrationFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToFeedbackListFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.feedBackListFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToDashboardFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.merchantDashboardFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToMyRestaurantFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.myRestaurantFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToMyFoodItemsFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.myFoodItemsFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToNewOrdersFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.newOrdersFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToUnShippedOrdersFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.unShippedOrdersFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToDeliverOrderFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.deliverOrderFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToMerchantAppendUserFragment() {
        try {
            if (module.isLoggedIn) {
                //
                drawerLayout.closeDrawer(navigationView)
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.merchantAppendUsersFragment)
                //
            }
        } catch (e: Exception) {
            Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        cartMenu = menu.findItem(R.id.menu_cart)
        cartMenu.isVisible = false
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Customer menu
            R.id.menuHome -> gotoHome()
            R.id.menuBuyFood -> goToFirstFragment()
            R.id.menuShoppingCart -> goToShoppingCart()
            R.id.menuMyOrders -> goToCustomerOrderListFragment()
            R.id.menuChangePassword -> goToChangePasswordFragment()
            R.id.menuProfile -> goToProfileFragment()
            R.id.menuSell -> goToSellOnBonAppetittFragment()
            R.id.menuAbout -> gotoAbout()
            //Merchant menu
            R.id.menu_merchant_Profile -> goToProfileFragment()
            R.id.menu_merchant_ChangePassword -> goToChangePasswordFragment()
            R.id.menu_merchant_about -> gotoAbout()
            R.id.menu_merchant_dashboard -> goToDashboardFragment()
            R.id.menu_my_restaurant -> goToMyRestaurantFragment()
            R.id.menu_my_food_items -> goToMyFoodItemsFragment()
            R.id.menu_new_orders -> goToNewOrdersFragment()
            R.id.menu_unshipped_orders -> goToUnShippedOrdersFragment()
            R.id.menu_deliver_order -> goToDeliverOrderFragment()
            R.id.menu_merchant_append_users -> goToMerchantAppendUserFragment()
            //Admin menu
            R.id.menu_new_registration -> goToNewRegistrationFragment()
            R.id.menu_Feedback -> goToFeedbackListFragment()

        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.itemId == R.id.menu_cart) {
            goToShoppingCart()
        }
        if (item.itemId == R.id.menu_home) {
            gotoHome()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        touch()
        Log.d("TAG", "User interaction to $this")
    }

    fun touch() {
        ApplockManager.getInstance().updateTouch()
    }

}
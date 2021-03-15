package com.mickleentityltdnigeria.resturantapp.data

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.*
import com.mickleentityltdnigeria.resturantapp.AppGlobals.*
import com.mickleentityltdnigeria.resturantapp.dalc.FoodDalc
import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc
import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.text.DecimalFormat

 class CustomerOrderListAdapter(
    private var orderDetails: List<FoodOrderDetail>,
    itemClickListener: CustomerOrderListRecyclerViewItemClickListener
) :
    RecyclerView.Adapter<CustomerOrderListAdapter.ViewHolder>() {

    private lateinit var foodOrderDalc: FoodOrderDalc
    private lateinit var resturantDalc: ResturantDalc
    private lateinit var foodDalc:FoodDalc
    private lateinit var progress: ProgressBar
    private val mItemClickListener: CustomerOrderListRecyclerViewItemClickListener = itemClickListener

     fun updateOrderDetails(orderDetails: List<FoodOrderDetail>){
         this.orderDetails = orderDetails
         notifyDataSetChanged()
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_customer_order_row, parent, false)
        setAppContext(parent.context)
        //
        foodOrderDalc = FoodOrderDalc()
        resturantDalc = ResturantDalc()
        foodDalc = FoodDalc()
        //Create View Holder
        val myViewHolder = ViewHolder(view, getAppContext(), foodDalc)
        //
        progress = myViewHolder.itemView.findViewById(R.id.progressBarCustomerOrderStatus)
        //Item Clicks
        myViewHolder.itemView.setOnClickListener {
            mItemClickListener.onItemClicked(
                orderDetails[myViewHolder.layoutPosition]
            )
        }
        //
        myViewHolder.itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).setOnClickListener {
            progress.visibility = View.VISIBLE
            try {
                cancelOrder(orderDetails[myViewHolder.layoutPosition])
            } catch (e:Exception) {
                Toast.makeText(getAppContext(), e.message, Toast.LENGTH_LONG).show()
            }
            progress.visibility = View.GONE
        }
        //
        myViewHolder.itemView.findViewById<ImageView>(R.id.btnPhoneContactSeller).setOnClickListener {
            progress.visibility = View.VISIBLE
            try {
                callMerchant(orderDetails[myViewHolder.layoutPosition])
            } catch (e:Exception) {
                Toast.makeText(getAppContext(), e.message, Toast.LENGTH_LONG).show()
            }
            progress.visibility = View.GONE
        }
        //
        myViewHolder.itemView.findViewById<ImageView>(R.id.btnEmailContactSeller).setOnClickListener {
            progress.visibility = View.VISIBLE
            try {
                emailMerchant(orderDetails[myViewHolder.layoutPosition])
            } catch (e:Exception) {
                Toast.makeText(getAppContext(), e.message, Toast.LENGTH_LONG).show()
            }
            progress.visibility = View.GONE
        }
        //
        return myViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderDetail = orderDetails[position]
        holder.bind(orderDetail)
    }

    //Cancels Order
    private fun cancelOrder(orderDetail: FoodOrderDetail) {
        if(!orderDetail.isShipped || !orderDetail.isDelivered){
            orderDetail.isCanceled = true
            foodOrderDalc.updateFoodOrderDetails(orderDetail)
        }
    }

    //puts a call through to the Merchant
    private fun callMerchant(orderDetail: FoodOrderDetail) {
        //
        val resturantFetched = ResturantUpdatedHandler { merchant ->
            val phone:String = merchant[0].phone
            val number:Uri = Uri.parse(phone)
            val callIntent = Intent(Intent.ACTION_DIAL,number)
            StartActivity(callIntent)
            this.resturantDalc.resturantDataFetched.removeListener("cusOrderresturantFetched")
        }
        this.resturantDalc.resturantDataFetched.addListener("cusOrderresturantFetched",resturantFetched)
        try{
            resturantDalc.getResturantByResturantID(orderDetail.resturantID)
        }catch (e:Exception){
            Toast.makeText(getAppContext(),e.message,Toast.LENGTH_LONG).show()
        }
        //
    }

    //puts a email through to the Merchant
    private fun emailMerchant(orderDetail: FoodOrderDetail) {
       //
        val resturantFetched = ResturantUpdatedHandler { merchant ->
            val em:String = merchant[0].email
            val str:Uri = Uri.parse(("mailto:$em"))
            val emailIntent = Intent(Intent.ACTION_VIEW,str)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Number: " + orderDetail.oderID)
            emailIntent.putExtra(Intent.EXTRA_TEXT,"")
            StartActivity(emailIntent)
            this.resturantDalc.resturantDataFetched.removeListener("cusOrderresturantFetched")
        }
        this.resturantDalc.resturantDataFetched.addListener("cusOrderresturantFetched",resturantFetched)
        try{
            resturantDalc.getResturantByResturantID(orderDetail.resturantID)
        }catch (e:Exception){
            Toast.makeText(getAppContext(),e.message,Toast.LENGTH_LONG).show()
        }
        //
    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }


    class ViewHolder(view: View, private val myContext: Context, private var foodDalc:FoodDalc) : RecyclerView.ViewHolder(view) {
        //
        fun bind(orderDetail: FoodOrderDetail) {
            lateinit var foodItem:FoodItem
            //
            try
            {
                val dc = DecimalFormat("#,###,##0.00")
                val cu:String = orderDetail.currency
                //
                val foodItemsFetched = FoodItemUpdatedHandler { fooditems ->
                    foodItem = fooditems[0]
                    //
                    itemView.findViewById<ImageView>(R.id.imgCustomerOrder).setImageDrawable(ImageHelper.getInstant().imageFromString(foodItem.foodImg))
                    itemView.findViewById<TextView>(R.id.txtCustomerOrderFoodItemName).text = foodItem.foodDesc
                    itemView.findViewById<TextView>(R.id.txtCustomerOrderPrice).text = (cu+ orderDetail.foodPrice)
                    itemView.findViewById<TextView>(R.id.txtCustomerOrderTotal).text = (cu+ dc.format((orderDetail.subTotal)))
                    itemView.findViewById<EditText>(R.id.txtCustomerOrderQty).setText(orderDetail.foodQty.toInt())
                    if(!orderDetail.isCanceled() && orderDetail.isPrinted() && !orderDetail.isShipped() && !orderDetail.isDelivered()){
                        itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress = 30
                        itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled = false
                    }else if(!orderDetail.isCanceled() && orderDetail.isPrinted() && orderDetail.isShipped() && !orderDetail.isDelivered()){
                        itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress = 50
                        itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled = false
                    }else if(!orderDetail.isCanceled() && orderDetail.isPrinted() && orderDetail.isShipped() && orderDetail.isDelivered()){
                        itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress = 100
                        itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled = false
                    }else{
                        itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress = 0
                        itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled = true
                    }
                }
                this.foodDalc.foodItemsFetched.addListener(foodItemsFetched)
                try{
                    //
                    this.foodDalc.getFoodItemByFoodID(orderDetail.foodID)
                    //
                }catch (e:Exception){
                    Toast.makeText(getAppContext(),e.message,Toast.LENGTH_LONG).show()
                }
               //
            }catch (e: Exception)
            {
                Toast.makeText(myContext, e.message, Toast.LENGTH_LONG).show()
            }
        }

    }

}

//RecyclerView Click Listener
interface CustomerOrderListRecyclerViewItemClickListener {
    fun onItemClicked(orderDetail: FoodOrderDetail)
}
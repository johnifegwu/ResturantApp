package com.mickleentityltdnigeria.resturantapp.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mickleentityltdnigeria.resturantapp.*
import com.mickleentityltdnigeria.resturantapp.AppGlobals.*
import com.mickleentityltdnigeria.resturantapp.dalc.FoodItemDalc
import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc
import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderDetailsEventHandler
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper
import java.text.DecimalFormat

class CustomerOrderListAdapter(
    private var orderDetails: MutableList<FoodOrderDetail>,
    itemClickListener: CustomerOrderListRecyclerViewItemClickListener
) :
    RecyclerView.Adapter<CustomerOrderListAdapter.ViewHolder>() {

    private lateinit var foodOrderDalc: FoodOrderDalc
    private lateinit var resturantDalc: ResturantDalc
    private val myContext: Context = ApplicationContextProvider.getContext()
    private lateinit var progress: ProgressBar
    private val mItemClickListener: CustomerOrderListRecyclerViewItemClickListener =
        itemClickListener

    fun updateData(orderDetails: MutableList<FoodOrderDetail>) {
        this.orderDetails.removeAll(orderDetails)
        this.orderDetails.addAll(orderDetails)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_customer_order_row, parent, false)
        //
        foodOrderDalc = FoodOrderDalc()
        resturantDalc = ResturantDalc()
        //Create View Holder
        val myViewHolder = ViewHolder(view, myContext)
        //
        progress = myViewHolder.itemView.findViewById(R.id.progressBarSingleCustomerOrder)
        //Item Clicks
        myViewHolder.itemView.setOnClickListener {
            mItemClickListener.onItemClicked(
                orderDetails[myViewHolder.layoutPosition]
            )
        }
        //
        myViewHolder.itemView.findViewById<ImageView>(R.id.btnPhoneContactSeller)
            .setOnClickListener {
                try {
                    callMerchant(orderDetails[myViewHolder.layoutPosition])
                } catch (e: Exception) {
                    Toast.makeText(getAppContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
        //
        myViewHolder.itemView.findViewById<ImageView>(R.id.btnEmailContactSeller)
            .setOnClickListener {
                try {
                    emailMerchant(orderDetails[myViewHolder.layoutPosition])
                } catch (e: Exception) {
                    Toast.makeText(getAppContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
        //
        return myViewHolder
    }

    //puts a call through to the Merchant
    private fun callMerchant(orderDetail: FoodOrderDetail) {
        //
        val resturantFetched = ResturantUpdatedHandler { merchant ->
            progress.visibility = View.GONE
            val phone: String = merchant[0].phone
            val number: Uri = Uri.parse(("tel:$phone"))
            val callIntent = Intent(Intent.ACTION_DIAL, number)
            StartActivity(callIntent)
            this.resturantDalc.resturantDataFetched.removeListener("cusOrderresturantFetched")
        }
        this.resturantDalc.resturantDataFetched.addListener(
            "cusOrderresturantFetched",
            resturantFetched
        )
        try {
            progress.visibility = View.VISIBLE
            resturantDalc.getResturantByResturantID(orderDetail.resturantID)
        } catch (e: Exception) {
            Toast.makeText(getAppContext(), e.message, Toast.LENGTH_SHORT).show()
        }
        //
    }

    //puts a email through to the Merchant
    private fun emailMerchant(orderDetail: FoodOrderDetail) {
        //
        val resturantFetched = ResturantUpdatedHandler { merchant ->
            progress.visibility = View.GONE
            val em: String = merchant[0].email
            val str: Uri = Uri.parse(("mailto:$em"))
            val emailIntent = Intent(Intent.ACTION_VIEW, str)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Number: " + orderDetail.oderID)
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            StartActivity(emailIntent)
            this.resturantDalc.resturantDataFetched.removeListener("cusOrderresturantFetched")
        }
        this.resturantDalc.resturantDataFetched.addListener(
            "cusOrderresturantFetched",
            resturantFetched
        )
        try {
            progress.visibility = View.VISIBLE
            resturantDalc.getResturantByResturantID(orderDetail.resturantID)
        } catch (e: Exception) {
            Toast.makeText(getAppContext(), e.message, Toast.LENGTH_SHORT).show()
        }
        //
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderDetails[position])
    }

    override fun getItemCount() = orderDetails.size


    class ViewHolder(view: View, private val myContext: Context) : RecyclerView.ViewHolder(view) {
        //
        fun bind(orderDetail: FoodOrderDetail) {
            //
            val foodItemDalc: FoodItemDalc = FoodItemDalc()
            val foodOrderDalc: FoodOrderDalc = FoodOrderDalc()
            //
            try {
                val dc = DecimalFormat("#,###,##0.00")
                val cu: String = orderDetail.currency
                //
                val foodItemsFetched = FoodItemUpdatedHandler { foodItems ->
                    try {
                        orderDetail.foodImg = foodItems[0].foodImg
                        itemView.findViewById<ImageView>(R.id.imgCustomerOrder).setImageDrawable(
                            ImageHelper.getInstance().imageFromString(orderDetail.foodImg)
                        )
                    } catch (e: Exception) {
                        Toast.makeText(getAppContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                if(orderDetail.foodImg == null){
                    foodItemDalc.foodItemsFetched.addListener(foodItemsFetched)
                    foodItemDalc.getFoodItemByFoodID(orderDetail.foodID)
                }else{
                    //
                    itemView.findViewById<ImageView>(R.id.imgCustomerOrder).setImageDrawable(
                        ImageHelper.getInstance().imageFromString(orderDetail.foodImg)
                    )
                }
                itemView.findViewById<TextView>(R.id.txtCustomerOrderFoodItemName).text =
                    orderDetail.foodDesc
                itemView.findViewById<TextView>(R.id.txtCustomerOrderPrice).text =
                    (cu + orderDetail.foodPrice)
                itemView.findViewById<TextView>(R.id.txtCustomerOrderTotal).text =
                    (cu + dc.format((orderDetail.subTotal)))
                itemView.findViewById<TextView>(R.id.txtCustomerOrderQty).text =
                    (orderDetail.foodQty.toString())
                //
                if (!orderDetail.isCanceled() && orderDetail.isPrinted() && !orderDetail.isShipped() && !orderDetail.isDelivered()) {
                    itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress =
                        30
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled =
                        false
                } else if (!orderDetail.isCanceled() && orderDetail.isPrinted() && orderDetail.isShipped() && !orderDetail.isDelivered()) {
                    itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress =
                        50
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled =
                        false
                } else if (!orderDetail.isCanceled() && orderDetail.isPrinted() && orderDetail.isShipped() && orderDetail.isDelivered()) {
                    itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress =
                        100
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled =
                        false
                } else if (!orderDetail.isCanceled() && !orderDetail.isPrinted() && !orderDetail.isShipped() && !orderDetail.isDelivered()) {
                    itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress =
                        0
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled =
                        true
                } else if (orderDetail.isCanceled()) {
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled =
                        false
                    itemView.findViewById<TextView>(R.id.textViewCanceled).visibility =
                        View.VISIBLE
                }
                //
                itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).setOnClickListener {
                    try {
                        //
                        if (!orderDetail.isPrinted()) {
                            //
                            orderDetail.setCanceled(true)
                            itemView.findViewById<ProgressBar>(R.id.progressBarSingleCustomerOrder).visibility =
                                View.VISIBLE
                            //
                            val orderUpdated = FoodOrderDetailsEventHandler { _ ->
                                itemView.findViewById<ProgressBar>(R.id.progressBarSingleCustomerOrder).visibility =
                                    View.GONE
                                itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled =
                                    false
                                itemView.findViewById<TextView>(R.id.textViewCanceled).visibility =
                                    View.VISIBLE
                            }
                            foodOrderDalc.foodOrderDetailsUpdated.addListener(orderUpdated)
                            foodOrderDalc.updateFoodOrderDetails(orderDetail)
                            //
                        }
                    } catch (e: Exception) {
                        Toast.makeText(getAppContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                //
            } catch (e: Exception) {
                Toast.makeText(getAppContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    }

    //RecyclerView Click Listener
    interface CustomerOrderListRecyclerViewItemClickListener
    {
        fun onItemClicked(orderDetail: FoodOrderDetail)
    }
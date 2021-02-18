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
import com.mickleentityltdnigeria.resturantapp.AppGlobals.StartActivity
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant
import com.mickleentityltdnigeria.resturantapp.service.Service
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.text.DecimalFormat

public class CustomerOrderListAdapter(
    private var orderDetails: List<FoodOrderDetail>,
    itemClickListener: CustomerOrderListRecyclerViewItemClickListener
) :
    RecyclerView.Adapter<CustomerOrderListAdapter.ViewHolder>() {

    private lateinit var progress: ProgressBar
    private val mItemClickListener: CustomerOrderListRecyclerViewItemClickListener = itemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.a_single_customer_order_row, parent, false)
        AppGlobals.setAppContext(parent.context)
        //Create View Holder
        val myViewHolder = CustomerOrderListAdapter.ViewHolder(view, AppGlobals.getAppContext())
        //
        progress = myViewHolder.itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus)
        //Item Clicks
        myViewHolder.itemView.setOnClickListener {
            mItemClickListener.onItemClicked(
                orderDetails.get(myViewHolder.getLayoutPosition())
            )
        }
        //
        myViewHolder.itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).setOnClickListener {
            progress.visibility = View.VISIBLE
            try {
                cancelOrder(orderDetails.get(myViewHolder.getLayoutPosition()))
            } catch (e:Exception) {
                Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show();
            }
            progress.visibility = View.GONE
        }
        //
        myViewHolder.itemView.findViewById<ImageView>(R.id.btnPhoneContactSeller).setOnClickListener {
            progress.visibility = View.VISIBLE
            try {
                callMerchant(orderDetails.get(myViewHolder.getLayoutPosition()))
            } catch (e:Exception) {
                Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show();
            }
            progress.visibility = View.GONE
        }
        //
        myViewHolder.itemView.findViewById<ImageView>(R.id.btnEmailContactSeller).setOnClickListener {
            progress.visibility = View.VISIBLE
            try {
                emailMerchant(orderDetails.get(myViewHolder.getLayoutPosition()))
            } catch (e:Exception) {
                Toast.makeText(AppGlobals.getAppContext(), e.message, Toast.LENGTH_LONG).show();
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
        Service.foodOder().CancelOrder(orderDetail)
    }

    //puts a call through to the Merchant
    private fun callMerchant(orderDetail: FoodOrderDetail) {
        val merchant:Resturant = Service.resturant().getResturantByResturantID(orderDetail.resturantID)
        val phone:String = merchant.phone
        val number:Uri = Uri.parse(phone)
        val callIntent:Intent = Intent(Intent.ACTION_DIAL,number)
        StartActivity(callIntent)
    }

    //puts a email through to the Merchant
    private fun emailMerchant(orderDetail: FoodOrderDetail) {
        val merchant:Resturant = Service.resturant().getResturantByResturantID(orderDetail.resturantID)
        val em:String = merchant.email
        val str:Uri = Uri.parse("mailto:" + em)
        var emailIntent:Intent = Intent(Intent.ACTION_VIEW,str)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Number: " + orderDetail.oderID)
        emailIntent.putExtra(Intent.EXTRA_TEXT,"")
        StartActivity(emailIntent)
    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }


    class ViewHolder(view: View, private val myContext: Context) : RecyclerView.ViewHolder(view) {
        fun bind(orderDetail: FoodOrderDetail) {
            try
            {
                val dc = DecimalFormat("#,###,##0.00")
                val cu:String = orderDetail.currency
                //
                val foodItem:FoodItem = Service.food().getFoodItemByFoodID(orderDetail.foodID)
                val ims: InputStream = ByteArrayInputStream(ImageHelper.getInstant().base64StringToByteArray(foodItem.foodImg)) //assetManager.open(cartItem.foodImgUrl)
                val d: Drawable = Drawable.createFromStream(ims, null)
               //
                itemView.findViewById<ImageView>(R.id.imgCustomerOrder).setImageDrawable(d)
                itemView.findViewById<TextView>(R.id.txtCustomerOrderFoodItemName).text = foodItem.foodDesc
                itemView.findViewById<TextView>(R.id.txtCustomerOrderPrice).text = cu+ orderDetail.foodPrice
                itemView.findViewById<TextView>(R.id.txtCustomerOrderTotal).text = cu+ dc.format((orderDetail.foodPrice * orderDetail.foodQty))
                itemView.findViewById<EditText>(R.id.txtCustomerOrderQty).setText(orderDetail.foodQty.toInt())
                if(!orderDetail.isCanceled && orderDetail.isShipped){
                    itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress = 50
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled = false
                }else if(!orderDetail.isCanceled && orderDetail.isShipped && orderDetail.isDelivered){
                    itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress = 100
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled = false
                }else{
                    itemView.findViewById<ProgressBar>(R.id.progressBarCustomerOrderStatus).progress = 0
                    itemView.findViewById<Button>(R.id.btnCancelCustomerOrder).isEnabled = true
                }
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
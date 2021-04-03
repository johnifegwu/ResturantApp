package com.mickleentityltdnigeria.resturantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PrintOrderActivity extends AppCompatActivity {

    public static FoodOrder foodOrder = new FoodOrder();
    public static FoodOrderDetail foodOrderDetails = new FoodOrderDetail();
    ImageView imgQRCode;
    TextView txtPrintAddress, txtPrintCity, txtPrintZipCode, txtPrintContactPerson, txtPrintContactPhone, txtPrintFoodDesc, txtPrintFoodQty, txtPrintFoodPrice, txtPrintSubTotal, txtPrintMarkup, txtPrintTotalDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_order);
        try {
            //initialize controls
            imgQRCode = this.findViewById(R.id.imgQRCode);
            txtPrintAddress = this.findViewById(R.id.txtPrintAddress);
            txtPrintCity = this.findViewById(R.id.txtPrintCity);
            txtPrintZipCode = this.findViewById(R.id.txtPrintZipCode);
            txtPrintContactPerson = this.findViewById(R.id.txtPrintContactPerson);
            txtPrintContactPhone = this.findViewById(R.id.txtPrintContactPhone);
            txtPrintFoodDesc = this.findViewById(R.id.txtPrintFoodDesc);
            txtPrintFoodQty = this.findViewById(R.id.txtPrintFoodQty);
            txtPrintFoodPrice = this.findViewById(R.id.txtPrintFoodPrice);
            txtPrintSubTotal = this.findViewById(R.id.txtPrintSubTotal);
            txtPrintMarkup = this.findViewById(R.id.txtPrintMarkup);
            txtPrintTotalDue = this.findViewById(R.id.txtPrintTotalDue);
            //set values
            try {
                QRGEncoder qrgEncoder = new QRGEncoder(foodOrderDetails.getID(), null, QRGContents.Type.TEXT, 500);
                Bitmap qrImg = qrgEncoder.encodeAsBitmap();
                imgQRCode.setImageBitmap(qrImg);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            txtPrintAddress.setText(foodOrder.getShippingAddress());
            txtPrintCity.setText(foodOrder.getShippingCity());
            txtPrintZipCode.setText(foodOrder.getShippingZipCode());
            txtPrintContactPerson.setText(foodOrder.getShippingContactPerson());
            txtPrintContactPhone.setText(foodOrder.getShippingContactPhone());
            txtPrintFoodDesc.setText(foodOrderDetails.getFoodDesc());
            txtPrintFoodQty.setText(String.valueOf(foodOrderDetails.getFoodQty()));
            txtPrintFoodPrice.setText((foodOrderDetails.getCurrency() + foodOrderDetails.getFoodPrice()));
            DecimalFormat dc = new DecimalFormat("#,###,##0.00");
            txtPrintSubTotal.setText((foodOrderDetails.getCurrency() + dc.format(foodOrderDetails.getFoodPrice() * foodOrderDetails.getFoodQty())));
            txtPrintMarkup.setText((foodOrderDetails.getCurrency() + dc.format(foodOrderDetails.getMarkUpValue())));
            txtPrintTotalDue.setText((foodOrderDetails.getCurrency() + dc.format(foodOrderDetails.getSubTotal())));
            //get ready to print
            View view = getWindow().getDecorView().findViewById(android.R.id.content);
            view.setDrawingCacheEnabled(true);
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            //And to print:

            PrintHelper photoPrinter = new PrintHelper(PrintOrderActivity.this); // Assume that 'this' is your activity
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            photoPrinter.setOrientation(PrintHelper.ORIENTATION_PORTRAIT);
            photoPrinter.printBitmap("Print Order: " + foodOrderDetails.getID(), bitmap);
            //finish();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
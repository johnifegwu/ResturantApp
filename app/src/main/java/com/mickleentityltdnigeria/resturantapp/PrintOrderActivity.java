package com.mickleentityltdnigeria.resturantapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.print.PrintHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.utils.idGen;

import java.text.DecimalFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PrintOrderActivity extends AppCompatActivity {

    public static FoodOrder foodOrder = new FoodOrder();
    public static FoodOrderDetail foodOrderDetails = new FoodOrderDetail();
    ImageView imgQRCode;
    TextView txtPrintAddress, txtPrintCity, txtPrintZipCode, txtPrintContactPerson, txtPrintContactPhone, txtPrintFoodDesc, txtPrintFoodQty, txtPrintFoodPrice, txtPrintSubTotal, txtPrintMarkup, txtPrintTotalDue;
    Button btnPrint;
    View view;

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
            btnPrint = this.findViewById(R.id.btnPrint);
            //set values
            view = getLayoutInflater().inflate(R.layout.activity_print_order,null);
            try {
                QRGEncoder qrgEncoder = new QRGEncoder(foodOrderDetails.getID(), null, QRGContents.Type.TEXT, 500);
                Bitmap qrImg = qrgEncoder.encodeAsBitmap();
                imgQRCode.setImageBitmap(qrImg);
                ImageView img = view.findViewById(R.id.imgQRCode);
                img.setImageBitmap(qrImg);
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
            TextView txtAddress = view.findViewById(R.id.txtPrintAddress);
            TextView txtCity = view.findViewById(R.id.txtPrintCity);
            TextView txtZipCode = view.findViewById(R.id.txtPrintZipCode);
            TextView txtContactPerson = view.findViewById(R.id.txtPrintContactPerson);
            TextView txtContactPhone = view.findViewById(R.id.txtPrintContactPhone);
            TextView txtFoodDesc = view.findViewById(R.id.txtPrintFoodDesc);
            TextView txtFoodQty = view.findViewById(R.id.txtPrintFoodQty);
            TextView txtFoodPrice = view.findViewById(R.id.txtPrintFoodPrice);
            TextView txtSubTotal = view.findViewById(R.id.txtPrintSubTotal);
            TextView txtMarkup = view.findViewById(R.id.txtPrintMarkup);
            TextView txtTotalDue = view.findViewById(R.id.txtPrintTotalDue);
            txtAddress.setText(foodOrder.getShippingAddress());
            txtCity.setText(foodOrder.getShippingCity());
            txtZipCode.setText(foodOrder.getShippingZipCode());
            txtContactPerson.setText(foodOrder.getShippingContactPerson());
            txtContactPhone.setText(foodOrder.getShippingContactPhone());
            txtFoodDesc.setText(foodOrderDetails.getFoodDesc());
            txtFoodQty.setText(String.valueOf(foodOrderDetails.getFoodQty()));
            txtFoodPrice.setText((foodOrderDetails.getCurrency() + foodOrderDetails.getFoodPrice()));
            txtSubTotal.setText((foodOrderDetails.getCurrency() + dc.format(foodOrderDetails.getFoodPrice() * foodOrderDetails.getFoodQty())));
            txtMarkup.setText((foodOrderDetails.getCurrency() + dc.format(foodOrderDetails.getMarkUpValue())));
            txtTotalDue.setText((foodOrderDetails.getCurrency() + dc.format(foodOrderDetails.getSubTotal())));
            //
            btnPrint.setVisibility(View.VISIBLE);
            btnPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printDoc(view);
                }
            });
            //
           printDoc(this.view);
           //
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void printDoc(View view){
        try{
            Bitmap bitmap = getBitmapFromView(PrintOrderActivity.this, view);
            //And to print:
            PrintHelper photoPrinter = new PrintHelper(PrintOrderActivity.this); // Assume that 'this' is your activity
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            photoPrinter.setOrientation(PrintHelper.ORIENTATION_PORTRAIT);
            photoPrinter.printBitmap("Print Order: " + idGen.getInstance().getUUID(), bitmap);
            //finish();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static Bitmap getBitmapFromView(Context context, View view) {
        view.setLayoutParams(new
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        view.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels,
                View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels,
                        View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return bitmap;
    }

}
package com.mickleentityltdnigeria.resturantapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.print.PrintHelper;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mickleentityltdnigeria.resturantapp.data.MerchantReportHelper;
import com.mickleentityltdnigeria.resturantapp.data.ReportIndicesEventHandler;
import com.mickleentityltdnigeria.resturantapp.data.ReportIndicies;
import com.mickleentityltdnigeria.resturantapp.utils.idGen;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MerchantDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantDashboardFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MerchantDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MerchantDashboardFragment.
     */

    public static MerchantDashboardFragment newInstance(String param1, String param2) {
        MerchantDashboardFragment fragment = new MerchantDashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant_dashboard, container, false);
    }

    BarChart salesChart;
    BarChart revenueChart;
    MerchantReportHelper merchantReportHelper;
    ReportIndicies reportIndicies;
    View view2;
    int year = new Date().getYear();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view2 = view;
        try{
            salesChart = (BarChart) view.findViewById(R.id.salesChart);
            revenueChart = (BarChart) view.findViewById(R.id.revenueChart);

            merchantReportHelper = new MerchantReportHelper(new ReportIndicesEventHandler() {
                @Override
                public void invoke(ReportIndicies mReportIndices) {
                    reportIndicies = mReportIndices;
                    //display sales report here
                    BarData saleData = new BarData((IBarDataSet) getSalesDataSet(reportIndicies));
                    saleData.setValueFormatter(new LargeValueFormatter());
                    salesChart.setData(saleData);
                    Description sd = new Description();
                    sd.setText((String.valueOf(year) + " to " + String.valueOf(year -1) + " Quarterly Sales Report."));
                    salesChart.setDescription(sd);
                    salesChart.animateXY(2000, 2000);
                    salesChart.invalidate();

                    //display revenue report here
                    BarData revenueData = new BarData((IBarDataSet) getSalesDataSet(reportIndicies));
                    revenueData.setValueFormatter(new LargeValueFormatter());
                    revenueChart.setData(revenueData);
                    Description rd = new Description();
                    rd.setText((String.valueOf(year) + " to " + String.valueOf(year -1) + " Quarterly Revenue Report."));
                    revenueChart.setDescription(rd);
                    revenueChart.animateXY(2000, 2000);
                    revenueChart.invalidate();
                }
            });
            //get report data
            this.getReportData(year);
            //
        }catch (Exception e){
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getReportData(int year){
        try{
            module.checkNetwork();
            this.year = year;
            this.merchantReportHelper.getReportData(module.userData.getResturantID(),year);
        }catch (Exception e){
           Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private BarData getSalesDataSet(ReportIndicies reportIndicies) {
        List<BarEntry> valueSet1 = new ArrayList<>();
        //add values for year one
        BarEntry v1e1 = new BarEntry((float) reportIndicies.getFirstQuarterSales(), 0); // 1st Quarter
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry((float) reportIndicies.getSecondQuarterSales(), 1); // 2nd Quarter
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry((float) reportIndicies.getThirdQuarterSales(), 2); // 3rd Quarter
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry((float) reportIndicies.getFirstQuarterSales(), 3); // 4th Quarter
        valueSet1.add(v1e4);


        List<BarEntry> valueSet2 = new ArrayList<>();
        //add values for year 2
        BarEntry v2e1 = new BarEntry((float) reportIndicies.getPrevFirstQuarterSales(), 0); // 1st Quarter
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry((float) reportIndicies.getPrevSecondQuarterSales(), 1); // 2nd Quarter
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry((float) reportIndicies.getPrevThirdQuarterSales(), 2); // 3rd Quarter
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry((float) reportIndicies.getPrevFourthQuarterSales(), 3); // 4th Quarter
        valueSet2.add(v2e4);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, String.valueOf(year) + " Sales");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        //
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, String.valueOf(year -1) + " Sales");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        //
        return new BarData(barDataSet1,barDataSet2);
    }

    private BarData getRevenueDataSet(ReportIndicies reportIndicies) {
        List<BarEntry> valueSet1 = new ArrayList<>();
        //add values for year one
        BarEntry v1e1 = new BarEntry((float) reportIndicies.getFirstQuarterRevenue(), 0); // 1st Quarter
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry((float) reportIndicies.getSecondQuarterRevenue(), 1); // 2nd Quarter
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry((float) reportIndicies.getThirdQuarterRevenue(), 2); // 3rd Quarter
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry((float) reportIndicies.getFourthQuarterRevenue(), 3); // 4th Quarter
        valueSet1.add(v1e4);


        List<BarEntry> valueSet2 = new ArrayList<>();
        //add values for year 2
        BarEntry v2e1 = new BarEntry((float) reportIndicies.getPrevFirstQuarterRevenue(), 0); // 1st Quarter
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry((float) reportIndicies.getPrevSecondQuarterRevenue(), 1); // 2nd Quarter
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry((float) reportIndicies.getPrevThirdQuarterRevenue(), 2); // 3rd Quarter
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry((float) reportIndicies.getPrevFourthQuarterRevenue(), 3); // 4th Quarter
        valueSet2.add(v2e4);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, String.valueOf(year) + " Revenue");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        //
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, String.valueOf(year -1) + " Revenue");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        //
        return new BarData(barDataSet1,barDataSet2);
    }

    private List<String> getXAxisValues() {
        List<String> xAxis = new ArrayList<>();
        xAxis.add("1st Quarter");
        xAxis.add("2nd Quarter");
        xAxis.add("3rd Quarter");
        xAxis.add("4th Quarter");
        return xAxis;
    }

    private void printReport(View view){
        try{
            Bitmap bitmap = getBitmapFromView(requireContext(), view);
            //And to print:
            PrintHelper photoPrinter = new PrintHelper(requireContext()); // Assume that 'this' is your activity
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            photoPrinter.setOrientation(PrintHelper.ORIENTATION_PORTRAIT);
            photoPrinter.printBitmap("Print Order: " + idGen.getInstance().getUUID(), bitmap);
            //finish();
        }catch (Exception e){
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
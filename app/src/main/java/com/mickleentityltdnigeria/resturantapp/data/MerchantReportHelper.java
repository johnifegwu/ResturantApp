package com.mickleentityltdnigeria.resturantapp.data;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderDetailsEventHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MerchantReportHelper {

    public ReportIndicesEventHandler mReportIndicesEventHandler;

    private final FoodOrderDalc firstQuarterDalc;
    private final FoodOrderDalc secondQuarterDalc;
    private final FoodOrderDalc thirdQuarterDalc;
    private final FoodOrderDalc fourthQuarterDalc;


    private final FoodOrderDalc prevFirstQuarterDalc;
    private final FoodOrderDalc prevSecondQuarterDalc;
    private final FoodOrderDalc prevThirdQuarterDalc;
    private final FoodOrderDalc prevFourthQuarterDalc;

    private ReportIndicies reportIndicies;

    public MerchantReportHelper(ReportIndicesEventHandler mReportIndicesEventHandler) {
        this.mReportIndicesEventHandler = mReportIndicesEventHandler;
        this.firstQuarterDalc = new FoodOrderDalc();
        this.secondQuarterDalc = new FoodOrderDalc();
        this.thirdQuarterDalc = new FoodOrderDalc();
        this.fourthQuarterDalc = new FoodOrderDalc();

        //previous year
        this.prevFirstQuarterDalc = new FoodOrderDalc();
        this.prevSecondQuarterDalc = new FoodOrderDalc();
        this.prevThirdQuarterDalc = new FoodOrderDalc();
        this.prevFourthQuarterDalc = new FoodOrderDalc();

        this.reportIndicies = new ReportIndicies(0,0,0,0,0,0,0,0, Calendar.getInstance().get(Calendar.YEAR),0,0,0,0,0,0,0,0,Calendar.getInstance().get(Calendar.YEAR));

    }

    private int countDown = 24;

    public void getReportData(String restaurantID, int year){
        //set Listeners for first quarter
        ReportIndexEventHandler firstQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setFirstQuarterSales(reportIndicies.getFirstQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setFirstQuarterRevenue(reportIndicies.getFirstQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler firstQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.firstQuarterDalc.reportIndexNotFound.addListener(firstQuarterDataNotFound);
        this.firstQuarterDalc.reportIndexFetched.addListener(firstQuarterDataFetched);
        //get January data
        firstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,1,year,false,true,true,true);
        //get February data
        firstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,2,year,false,true,true,true);
        //get March data
        firstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,3,year,false,true,true,true);

        //set Listeners for second quarter
        ReportIndexEventHandler secondQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setSecondQuarterSales(reportIndicies.getSecondQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setSecondQuarterRevenue(reportIndicies.getSecondQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler secondQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.secondQuarterDalc.reportIndexNotFound.addListener(secondQuarterDataNotFound);
        this.secondQuarterDalc.reportIndexFetched.addListener(secondQuarterDataFetched);
        //get April data
        secondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,4,year,false,true,true,true);
        //get May data
        secondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,5,year,false,true,true,true);
        //get June data
        secondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,6,year,false,true,true,true);

        //set Listeners for third quarter
        ReportIndexEventHandler thirdQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setThirdQuarterSales(reportIndicies.getThirdQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setThirdQuarterRevenue(reportIndicies.getThirdQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler thirdQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.thirdQuarterDalc.reportIndexNotFound.addListener(thirdQuarterDataNotFound);
        this.thirdQuarterDalc.reportIndexFetched.addListener(thirdQuarterDataFetched);
        //get July data
        thirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,7,year,false,true,true,true);
        //get August data
        thirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,8,year,false,true,true,true);
        //get September data
        thirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,9,year,false,true,true,true);

        //set Listeners for fourth quarter
        ReportIndexEventHandler fourthQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setFourthQuarterSales(reportIndicies.getFourthQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setFourthQuarterRevenue(reportIndicies.getFourthQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler fourthQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.fourthQuarterDalc.reportIndexNotFound.addListener(fourthQuarterDataNotFound);
        this.fourthQuarterDalc.reportIndexFetched.addListener(fourthQuarterDataFetched);
        //get July data
        fourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,10,year,false,true,true,true);
        //get August data
        fourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,11,year,false,true,true,true);
        //get September data
        fourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,12,year,false,true,true,true);

        //get previous year report
        int prevYear = (year -1);
        //set Listeners for first quarter
        ReportIndexEventHandler prevFirstQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setPrevFirstQuarterSales(reportIndicies.getPrevFirstQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setPrevFirstQuarterRevenue(reportIndicies.getPrevFirstQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler prevFirstQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.prevFirstQuarterDalc.reportIndexNotFound.addListener(prevFirstQuarterDataNotFound);
        this.prevFirstQuarterDalc.reportIndexFetched.addListener(prevFirstQuarterDataFetched);
        //get January data
        prevFirstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,1,prevYear,false,true,true,true);
        //get February data
        prevFirstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,2,prevYear,false,true,true,true);
        //get March data
        prevFirstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,3,prevYear,false,true,true,true);

        //set Listeners for second quarter
        ReportIndexEventHandler prevSecondQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setPrevSecondQuarterSales(reportIndicies.getPrevSecondQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setPrevSecondQuarterRevenue(reportIndicies.getPrevSecondQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler prevSecondQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.prevSecondQuarterDalc.reportIndexNotFound.addListener(prevSecondQuarterDataNotFound);
        this.prevSecondQuarterDalc.reportIndexFetched.addListener(prevSecondQuarterDataFetched);
        //get April data
        prevSecondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,4,prevYear,false,true,true,true);
        //get May data
        prevSecondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,5,prevYear,false,true,true,true);
        //get June data
        prevSecondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,6,prevYear,false,true,true,true);

        //set Listeners for third quarter
        ReportIndexEventHandler prevThirdQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setPrevThirdQuarterSales(reportIndicies.getPrevThirdQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setPrevThirdQuarterRevenue(reportIndicies.getPrevThirdQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler prevThirdQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.prevThirdQuarterDalc.reportIndexNotFound.addListener(prevThirdQuarterDataNotFound);
        this.prevThirdQuarterDalc.reportIndexFetched.addListener(prevThirdQuarterDataFetched);
        //get July data
        prevThirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,7,prevYear,false,true,true,true);
        //get August data
        prevThirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,8,prevYear,false,true,true,true);
        //get September data
        prevThirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,9,prevYear,false,true,true,true);

        //set Listeners for fourth quarter
        ReportIndexEventHandler prevFourthQuarterDataFetched = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                reportIndicies.setPrevFourthQuarterSales(reportIndicies.getPrevFourthQuarterSales() + reportIndex.getTotalSales());
                reportIndicies.setPrevFourthQuarterRevenue(reportIndicies.getPrevFourthQuarterRevenue() + reportIndex.getTotalRevenue());
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        ReportIndexEventHandler prevFourthQuarterDataNotFound = new ReportIndexEventHandler() {
            @Override
            public void invoke(ReportIndex reportIndex) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(reportIndicies);
                }
            }
        };
        this.prevFourthQuarterDalc.reportIndexNotFound.addListener(prevFourthQuarterDataNotFound);
        this.prevFourthQuarterDalc.reportIndexFetched.addListener(prevFourthQuarterDataFetched);
        //get July data
        prevFourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,10,prevYear,false,true,true,true);
        //get August data
        prevFourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,11,prevYear,false,true,true,true);
        //get September data
        prevFourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,12,prevYear,false,true,true,true);

    }

    private void raiseEvent(ReportIndicies reportIndicies){
        mReportIndicesEventHandler.invoke(reportIndicies);
    }
}


package com.mickleentityltdnigeria.resturantapp.data;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderDetailsEventHandler;

import java.util.ArrayList;
import java.util.List;

public class MerchantReportHelper {

    public ReportIndicesEventHandler mReportIndicesEventHandler;

    private final List<FoodOrderDetail> firstQuarterData;
    private final List<FoodOrderDetail> secondQuarterData;
    private final List<FoodOrderDetail> thirdQuarterData;
    private final List<FoodOrderDetail> fourthQuarterData;

    private final FoodOrderDalc firstQuarterDalc;
    private final FoodOrderDalc secondQuarterDalc;
    private final FoodOrderDalc thirdQuarterDalc;
    private final FoodOrderDalc fourthQuarterDalc;

    private final List<FoodOrderDetail> prevFirstQuarterData;
    private final List<FoodOrderDetail> prevSecondQuarterData;
    private final List<FoodOrderDetail> prevThirdQuarterData;
    private final List<FoodOrderDetail> prevFourthQuarterData;

    private final FoodOrderDalc prevFirstQuarterDalc;
    private final FoodOrderDalc prevSecondQuarterDalc;
    private final FoodOrderDalc prevThirdQuarterDalc;
    private final FoodOrderDalc prevFourthQuarterDalc;

    public MerchantReportHelper(ReportIndicesEventHandler mReportIndicesEventHandler) {
        this.mReportIndicesEventHandler = mReportIndicesEventHandler;
        this.firstQuarterDalc = new FoodOrderDalc();
        this.secondQuarterDalc = new FoodOrderDalc();
        this.thirdQuarterDalc = new FoodOrderDalc();
        this.fourthQuarterDalc = new FoodOrderDalc();
        this.firstQuarterData = new ArrayList<>();
        this.secondQuarterData = new ArrayList<>();
        this.thirdQuarterData = new ArrayList<>();
        this.fourthQuarterData = new ArrayList<>();
        //previous year
        this.prevFirstQuarterDalc = new FoodOrderDalc();
        this.prevSecondQuarterDalc = new FoodOrderDalc();
        this.prevThirdQuarterDalc = new FoodOrderDalc();
        this.prevFourthQuarterDalc = new FoodOrderDalc();
        this.prevFirstQuarterData = new ArrayList<>();
        this.prevSecondQuarterData = new ArrayList<>();
        this.prevThirdQuarterData = new ArrayList<>();
        this.prevFourthQuarterData = new ArrayList<>();
    }

    private int countDown = 24;

    public void getReportData(String restaurantID, int year){
        //set Listeners for first quarter
        FoodOrderDetailsEventHandler firstQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                firstQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler firstQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.firstQuarterDalc.foodOrderDetailsNotFound.addListener(firstQuarterDataNotFound);
        this.firstQuarterDalc.foodOrderDetailsFetched.addListener(firstQuarterDataFetched);
        //get January data
        firstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,1,year,false,true,true,true);
        //get February data
        firstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,2,year,false,true,true,true);
        //get March data
        firstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,3,year,false,true,true,true);

        //set Listeners for second quarter
        FoodOrderDetailsEventHandler secondQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                secondQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler secondQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.secondQuarterDalc.foodOrderDetailsNotFound.addListener(secondQuarterDataNotFound);
        this.secondQuarterDalc.foodOrderDetailsFetched.addListener(secondQuarterDataFetched);
        //get April data
        secondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,4,year,false,true,true,true);
        //get May data
        secondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,5,year,false,true,true,true);
        //get June data
        secondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,6,year,false,true,true,true);

        //set Listeners for third quarter
        FoodOrderDetailsEventHandler thirdQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                thirdQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler thirdQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.thirdQuarterDalc.foodOrderDetailsNotFound.addListener(thirdQuarterDataNotFound);
        this.thirdQuarterDalc.foodOrderDetailsFetched.addListener(thirdQuarterDataFetched);
        //get July data
        thirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,7,year,false,true,true,true);
        //get August data
        thirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,8,year,false,true,true,true);
        //get September data
        thirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,9,year,false,true,true,true);

        //set Listeners for fourth quarter
        FoodOrderDetailsEventHandler fourthQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                fourthQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler fourthQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.fourthQuarterDalc.foodOrderDetailsNotFound.addListener(fourthQuarterDataNotFound);
        this.fourthQuarterDalc.foodOrderDetailsFetched.addListener(fourthQuarterDataFetched);
        //get July data
        fourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,10,year,false,true,true,true);
        //get August data
        fourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,11,year,false,true,true,true);
        //get September data
        fourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,12,year,false,true,true,true);

        //get previous year report
        int prevYear = (year -1);
        //set Listeners for first quarter
        FoodOrderDetailsEventHandler prevFirstQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                prevFirstQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler prevFirstQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.prevFirstQuarterDalc.foodOrderDetailsNotFound.addListener(prevFirstQuarterDataNotFound);
        this.prevFirstQuarterDalc.foodOrderDetailsFetched.addListener(prevFirstQuarterDataFetched);
        //get January data
        prevFirstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,1,prevYear,false,true,true,true);
        //get February data
        prevFirstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,2,prevYear,false,true,true,true);
        //get March data
        prevFirstQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,3,prevYear,false,true,true,true);

        //set Listeners for second quarter
        FoodOrderDetailsEventHandler prevSecondQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                prevSecondQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler prevSecondQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.prevSecondQuarterDalc.foodOrderDetailsNotFound.addListener(prevSecondQuarterDataNotFound);
        this.prevSecondQuarterDalc.foodOrderDetailsFetched.addListener(prevSecondQuarterDataFetched);
        //get April data
        prevSecondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,4,prevYear,false,true,true,true);
        //get May data
        prevSecondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,5,prevYear,false,true,true,true);
        //get June data
        prevSecondQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,6,prevYear,false,true,true,true);

        //set Listeners for third quarter
        FoodOrderDetailsEventHandler prevThirdQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                prevThirdQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler prevThirdQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.prevThirdQuarterDalc.foodOrderDetailsNotFound.addListener(prevThirdQuarterDataNotFound);
        this.prevThirdQuarterDalc.foodOrderDetailsFetched.addListener(prevThirdQuarterDataFetched);
        //get July data
        prevThirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,7,prevYear,false,true,true,true);
        //get August data
        prevThirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,8,prevYear,false,true,true,true);
        //get September data
        prevThirdQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,9,prevYear,false,true,true,true);

        //set Listeners for fourth quarter
        FoodOrderDetailsEventHandler prevFourthQuarterDataFetched = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                prevFourthQuarterData.addAll(foodOrderDetails);
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        FoodOrderDetailsEventHandler prevFourthQuarterDataNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                countDown --;
                if(countDown <= 0){
                    raiseEvent(year);
                }
            }
        };
        this.prevFourthQuarterDalc.foodOrderDetailsNotFound.addListener(prevFourthQuarterDataNotFound);
        this.prevFourthQuarterDalc.foodOrderDetailsFetched.addListener(prevFourthQuarterDataFetched);
        //get July data
        prevFourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,10,prevYear,false,true,true,true);
        //get August data
        prevFourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,11,prevYear,false,true,true,true);
        //get September data
        prevFourthQuarterDalc.getFoodOrderDetailsByReportQuery(restaurantID,12,prevYear,false,true,true,true);

    }

    private void raiseEvent(int year){
        //
        int prevYear = (year -1);
        double firstQuarterSales = 0, firstQuarterRevenue = 0;
        double secondQuarterSales = 0, secondQuarterRevenue = 0;
        double thirdQuarterSales = 0, thirdQuarterRevenue = 0;
        double fourthQuarterSales = 0, fourthQuarterRevenue = 0;

        //get first Quarter report indices
        for(FoodOrderDetail f:firstQuarterData){
            firstQuarterSales += f.foodQty;
            firstQuarterRevenue += (f.getFoodQty() * f.foodPrice);
        }
        //get second Quarter report indices
        for(FoodOrderDetail f:secondQuarterData){
            secondQuarterSales += f.foodQty;
            secondQuarterRevenue += (f.getFoodQty() * f.foodPrice);
        }
        //get third Quarter report indices
        for(FoodOrderDetail f:thirdQuarterData){
            thirdQuarterSales += f.foodQty;
            thirdQuarterRevenue += (f.getFoodQty() * f.foodPrice);
        }
        //get fourth Quarter report indices
        for(FoodOrderDetail f:fourthQuarterData){
            fourthQuarterSales += f.foodQty;
            fourthQuarterRevenue += (f.getFoodQty() * f.foodPrice);
        }
        //previous year
        double prevFirstQuarterSales = 0, prevFirstQuarterRevenue = 0;
        double prevSecondQuarterSales = 0, prevSecondQuarterRevenue = 0;
        double prevThirdQuarterSales = 0, prevThirdQuarterRevenue = 0;
        double prevFourthQuarterSales = 0, prevFourthQuarterRevenue = 0;

        //get first Quarter report indices
        for(FoodOrderDetail f:prevFirstQuarterData){
            prevFirstQuarterSales += f.foodQty;
            prevFirstQuarterRevenue += (f.getFoodQty() * f.foodPrice);
        }
        //get second Quarter report indices
        for(FoodOrderDetail f:prevSecondQuarterData){
            prevSecondQuarterSales += f.foodQty;
            prevSecondQuarterRevenue += (f.getFoodQty() * f.foodPrice);
        }
        //get third Quarter report indices
        for(FoodOrderDetail f:prevThirdQuarterData){
            prevThirdQuarterSales += f.foodQty;
            prevThirdQuarterRevenue += (f.getFoodQty() * f.foodPrice);
        }
        String cuCode = "";
        //get fourth Quarter report indices
        for(FoodOrderDetail f:prevFourthQuarterData){
            prevFourthQuarterSales += f.foodQty;
            prevFourthQuarterRevenue += (f.getFoodQty() * f.foodPrice);
            cuCode = f.getCurrency();
        }

        ReportIndicies reportIndicies = new ReportIndicies(firstQuarterSales,firstQuarterRevenue,secondQuarterSales,secondQuarterRevenue, thirdQuarterSales,thirdQuarterRevenue,fourthQuarterSales,fourthQuarterRevenue, year,
                prevFirstQuarterSales,prevFirstQuarterRevenue,prevSecondQuarterSales,prevSecondQuarterRevenue,prevThirdQuarterSales,prevThirdQuarterRevenue,prevFourthQuarterSales,prevFourthQuarterRevenue,prevYear);
        reportIndicies.setCurrencyCode(cuCode);
        mReportIndicesEventHandler.invoke(reportIndicies);
    }
}


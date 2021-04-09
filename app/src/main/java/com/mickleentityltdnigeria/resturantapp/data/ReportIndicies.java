package com.mickleentityltdnigeria.resturantapp.data;

public class ReportIndicies {

    private double firstQuarterSales;
    private double firstQuarterRevenue;

    private double secondQuarterSales;
    private double secondQuarterRevenue;

    private double thirdQuarterSales;
    private double thirdQuarterRevenue;

    private double fourthQuarterSales;
    private double fourthQuarterRevenue;
    private int year;

    private double prevFirstQuarterSales;
    private double prevFirstQuarterRevenue;

    private double prevSecondQuarterSales;
    private double prevSecondQuarterRevenue;

    private double prevThirdQuarterSales;
    private double prevThirdQuarterRevenue;

    private double prevFourthQuarterSales;
    private double prevFourthQuarterRevenue;
    private int prevYear;

    private String currencyCode;

    public ReportIndicies() {
    }

    public ReportIndicies(double firstQuarterSales, double firstQuarterRevenue, double secondQuarterSales, double secondQuarterRevenue, double thirdQuarterSales, double thirdQuarterRevenue, double fourthQuarterSales, double fourthQuarterRevenue, int year, double prevFirstQuarterSales, double prevFirstQuarterRevenue, double prevSecondQuarterSales, double prevSecondQuarterRevenue, double prevThirdQuarterSales, double prevThirdQuarterRevenue, double prevFourthQuarterSales, double prevFourthQuarterRevenue, int prevYear) {
        this.firstQuarterSales = firstQuarterSales;
        this.firstQuarterRevenue = firstQuarterRevenue;
        this.secondQuarterSales = secondQuarterSales;
        this.secondQuarterRevenue = secondQuarterRevenue;
        this.thirdQuarterSales = thirdQuarterSales;
        this.thirdQuarterRevenue = thirdQuarterRevenue;
        this.fourthQuarterSales = fourthQuarterSales;
        this.fourthQuarterRevenue = fourthQuarterRevenue;
        this.year = year;
        this.prevFirstQuarterSales = prevFirstQuarterSales;
        this.prevFirstQuarterRevenue = prevFirstQuarterRevenue;
        this.prevSecondQuarterSales = prevSecondQuarterSales;
        this.prevSecondQuarterRevenue = prevSecondQuarterRevenue;
        this.prevThirdQuarterSales = prevThirdQuarterSales;
        this.prevThirdQuarterRevenue = prevThirdQuarterRevenue;
        this.prevFourthQuarterSales = prevFourthQuarterSales;
        this.prevFourthQuarterRevenue = prevFourthQuarterRevenue;
        this.prevYear = prevYear;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getFirstQuarterSales() {
        return firstQuarterSales;
    }

    public void setFirstQuarterSales(double firstQuarterSales) {
        this.firstQuarterSales = firstQuarterSales;
    }

    public double getFirstQuarterRevenue() {
        return firstQuarterRevenue;
    }

    public void setFirstQuarterRevenue(double firstQuarterRevenue) {
        this.firstQuarterRevenue = firstQuarterRevenue;
    }

    public double getSecondQuarterSales() {
        return secondQuarterSales;
    }

    public void setSecondQuarterSales(double secondQuarterSales) {
        this.secondQuarterSales = secondQuarterSales;
    }

    public double getSecondQuarterRevenue() {
        return secondQuarterRevenue;
    }

    public void setSecondQuarterRevenue(double secondQuarterRevenue) {
        this.secondQuarterRevenue = secondQuarterRevenue;
    }

    public double getThirdQuarterSales() {
        return thirdQuarterSales;
    }

    public void setThirdQuarterSales(double thirdQuarterSales) {
        this.thirdQuarterSales = thirdQuarterSales;
    }

    public double getThirdQuarterRevenue() {
        return thirdQuarterRevenue;
    }

    public void setThirdQuarterRevenue(double thirdQuarterRevenue) {
        this.thirdQuarterRevenue = thirdQuarterRevenue;
    }

    public double getFourthQuarterSales() {
        return fourthQuarterSales;
    }

    public void setFourthQuarterSales(double fourthQuarterSales) {
        this.fourthQuarterSales = fourthQuarterSales;
    }

    public double getFourthQuarterRevenue() {
        return fourthQuarterRevenue;
    }

    public void setFourthQuarterRevenue(double fourthQuarterRevenue) {
        this.fourthQuarterRevenue = fourthQuarterRevenue;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrevFirstQuarterSales() {
        return prevFirstQuarterSales;
    }

    public void setPrevFirstQuarterSales(double prevFirstQuarterSales) {
        this.prevFirstQuarterSales = prevFirstQuarterSales;
    }

    public double getPrevFirstQuarterRevenue() {
        return prevFirstQuarterRevenue;
    }

    public void setPrevFirstQuarterRevenue(double prevFirstQuarterRevenue) {
        this.prevFirstQuarterRevenue = prevFirstQuarterRevenue;
    }

    public double getPrevSecondQuarterSales() {
        return prevSecondQuarterSales;
    }

    public void setPrevSecondQuarterSales(double prevSecondQuarterSales) {
        this.prevSecondQuarterSales = prevSecondQuarterSales;
    }

    public double getPrevSecondQuarterRevenue() {
        return prevSecondQuarterRevenue;
    }

    public void setPrevSecondQuarterRevenue(double prevSecondQuarterRevenue) {
        this.prevSecondQuarterRevenue = prevSecondQuarterRevenue;
    }

    public double getPrevThirdQuarterSales() {
        return prevThirdQuarterSales;
    }

    public void setPrevThirdQuarterSales(double prevThirdQuarterSales) {
        this.prevThirdQuarterSales = prevThirdQuarterSales;
    }

    public double getPrevThirdQuarterRevenue() {
        return prevThirdQuarterRevenue;
    }

    public void setPrevThirdQuarterRevenue(double prevThirdQuarterRevenue) {
        this.prevThirdQuarterRevenue = prevThirdQuarterRevenue;
    }

    public double getPrevFourthQuarterSales() {
        return prevFourthQuarterSales;
    }

    public void setPrevFourthQuarterSales(double prevFourthQuarterSales) {
        this.prevFourthQuarterSales = prevFourthQuarterSales;
    }

    public double getPrevFourthQuarterRevenue() {
        return prevFourthQuarterRevenue;
    }

    public void setPrevFourthQuarterRevenue(double prevFourthQuarterRevenue) {
        this.prevFourthQuarterRevenue = prevFourthQuarterRevenue;
    }

    public int getPrevYear() {
        return prevYear;
    }

    public void setPrevYear(int prevYear) {
        this.prevYear = prevYear;
    }
}

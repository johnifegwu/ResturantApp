package com.mickleentityltdnigeria.resturantapp.data;

public class ReportIndex {
    private double totalSales;
    private double totalRevenue;

    public ReportIndex() {
    }

    public ReportIndex(double totalSales, double totalRevenue) {
        this.totalSales = totalSales;
        this.totalRevenue = totalRevenue;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}

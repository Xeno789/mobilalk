package com.example.kotprog;

public class carInsurance {
    private String licensePlateNumber;
    private String brand;
    private String owner;
    private String paymentSchedule;

    public carInsurance(String licensePlateNumber, String brand, String owner, String paymentSchedule){
        this.licensePlateNumber = licensePlateNumber;
        this.brand = brand;
        this.owner = owner;
        this.paymentSchedule = paymentSchedule;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getOwner() {
        return owner;
    }

    public String getPaymentSchedule() {
        return paymentSchedule;
    }
}

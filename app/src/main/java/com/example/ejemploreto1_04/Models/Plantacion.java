package com.example.ejemploreto1_04.Models;

public class Plantacion {

    private final String ID;

    private int quantity, unitPrice;

    private String month, type, idUser;

    public Plantacion(String ID, int quantity, int unitPrice, String month, String type, String idUser) {
        this.ID = ID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.month = month;
        this.type = type;
        this.idUser = idUser;
    }

    public String getID() {
        return ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdUser() {
        return idUser;
    }

}

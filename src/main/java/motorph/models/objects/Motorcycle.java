/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph.models.objects;

import java.time.LocalDate;

// PARENT CLASS
public class Motorcycle {
    
    // DECLARE VARIABLES TO HOLD THE VALUES PASSED FROM CHILD CLASS
    private final LocalDate createdOn; 
    private final String engineNumber;
    private String brand;
    private String status;
    private String stockLabel;
    private int stockCount;

    // CONSTRUCTOR THAT ACCEPTS PARAMETERS 
    public Motorcycle(String brand, LocalDate createdOn, String engineNumber, String status, String stockLabel, int stockCount) {
        // WHEN THE CLASS IS INITIALIZED, THE PARAMETERS PASSED WILL BE ASSIGNED TO THE PRIVATE VARIABLES
        this.brand = brand;
        this.createdOn = createdOn;
        this.engineNumber = engineNumber;
        this.status = status;
        this.stockLabel = stockLabel;
        this.stockCount = stockCount;
    }

    /**
     * GETTER METHODS FOR ALL FIELDS
     */

    // GETTER METHOD FOR BRAND
    public String GetBrand() {
        return brand;
    }

    // GETTER METHOD FOR DATE CREATED ON
    public LocalDate GetCreatedOn() {
        return createdOn;
    }

    // GETTER METHOD FOR ENGINE NUMBER
    public String GetEngineNumber() {
        return engineNumber;
    }

    // GETTER METHOD FOR STATUS
    public String GetStatus() {
        return status;
    }

    // GETTER METHOD FOR STOCK LABEL
    public String GetStockLabel() {
        return stockLabel;
    }

    // GETTER METHOD FOR STOCK COUNT
    public int GetStockCount() {
        return stockCount;
    }

    /**
     * SETTER METHODS FOR UPDATABLE FIELDS
     */
    
    // SETTER METHOD FOR BRAND
    public void SetBrand(String brand) {
        this.brand = brand;
    }
    
    // SETTER METHOD FOR STATUS
    public void SetStatus(String status) {
        this.status = status;
    }

    // SETTER METHOD FOR STOCK LABEL 
    public void SetStockLabel(String stockLabel) {
        this.stockLabel = stockLabel;
    }

    // SETTER METHOD FOR STOCK COUNT 
    public void SetStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    // OVERRIDES THE EXISTING toString() METHOD WITH CUSTOM LOGIC
    @Override
    public String toString() {
        return String.format("%-10s %-15s %-20s %-10s %-15s %-5s",
                brand, createdOn, engineNumber, status, stockLabel, stockCount);
    }
}

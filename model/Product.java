package model;

public class Product {
    private int id;
    private String name;
    private int inventory;
    private double price;

    private static int productUniqueID = 0;

    public Product(String name,int inventory,double price){
        productUniqueID ++;
        this.id = productUniqueID;
        this.name = name;
        this.inventory = inventory;
        this.price = price;
    }
    /* Part Class Getters */
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getInventory() {
        return inventory;
    }
    public double getPrice() {
        return price;
    }
    /* Part Class Setters*/
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}

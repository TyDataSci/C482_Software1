package model;

public class Part {
    private int id;
    private String name;
    private int inventory;
    private double price;

    private static int partUniqueID = 0;

    public Part(String name,int inventory,double price){
        partUniqueID ++;
        this.id = partUniqueID;
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

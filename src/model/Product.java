package model;

/**
 *
 * @author Tyler Sanders
 */

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;

    public Product(int id, String name,int inventory,double price,int min, int max){
        this.id = id;
        this.name = name;
        this.stock = inventory;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /* Part Class Getters */
    /**
     * @return the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }
    /* Part Class Setters*/
    /**
     * @param associatedParts to set associated parts list
     */
    public void setAssociatedParts(ObservableList associatedParts) {
        this.associatedParts = associatedParts;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @param name the id to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param stock the id to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param price the id to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param min the id to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the id to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * @param part the id to set
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**
     * @param selectedAssociatedPart to validate if part should be removed from product
     *                               if confirmed will then remove association of the part from product
     * @return valid true if the confirmation is made by the user after alert
     *
     * <p>
     *  RUNTIME ERROR Null Pointer Exception is handled by alert isPresent to determine if
     *                               an alert window exists without the need to check for null
     *
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)  {
        boolean valid;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove");
            alert.setTitle("Associated Parts");
            alert.setContentText("Are you sure you want to remove the part " + selectedAssociatedPart.getName() + "?");
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {

                associatedParts.remove(selectedAssociatedPart);
                valid = true;
            }
            else {
                valid = false;
            }

        return valid;
    }

}

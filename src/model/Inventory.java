package model;

/**
 *
 * @author Tyler Sanders
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts  = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int uniquePartId = 0;
    private static int uniqueProductID = 999;
    
    /**
     * @return a list of all parts in the inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return a list of all products in the inventory
     */
    public static  ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @param  newPart adds a new part to the list of parts
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * @param  newProduct adds a new product to the list of products
     */

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /**
     * @param  partId returns the part that holds that id or else returns null
     *
     * @return  specific part
     */
    public static Part lookupPart(int partId) {
       Part lookupPart = null;
       for (Part part: allParts){
            if (part.getId() == partId){
                 lookupPart = part;
                 break;
            }
        }
        return lookupPart;
    }
    /**
     * @param  productId returns the part that holds that id or else returns null
     * @return product with unique id
     */
    public static Product lookupProduct(int productId) {
        Product lookupProduct = null;
        for (Product product : allProducts){
            if (product.getId() == productId){
                lookupProduct= product;
            }
        }
        return lookupProduct;
    }
    /**
     * @param  partName returns a list of all parts by part name
     * @return  list of parts with string in name
     *
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partByNameList = FXCollections.observableArrayList();
        for (Part part: allParts){
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                partByNameList.add(part);
            }
        }
        return partByNameList;
    }
    /**
     * @param  productName returns a list of all products by product name
     *
     * @return list of products containing string name
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productByNameList = FXCollections.observableArrayList();
        for (Product product: allProducts){
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                productByNameList.add(product);
            }
        }
        return productByNameList;
    }
    /**
     * @param index the index of the part to replace in the list of parts
     * @param selectedPart the part to replace in the list of parts
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);
    }
    /**
     * @param index the index of the product to replace in the list of products
     * @param selectedProduct the product to replace in the list of products
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index,selectedProduct);
    }
    /**
     * @param selectedPart removes the selected part from the list of parts
     * @return  valid true if the part is not associated with any product
     *
     */
    public static boolean deletePart(Part selectedPart) {
        boolean valid = true;
        for (Product product : allProducts) {
            for (Part associatedPart : product.getAllAssociatedParts()) {
                if (selectedPart.getId() == associatedPart.getId()) {
                    valid = false;
                    break;
                }
            }
        }

        if (valid) {
            allParts.remove(selectedPart);
        }
        return valid;
    }
    /**
     * @param selectedProduct remove the selected product from the list of products
     * @return valid true if the product has no associated parts
     *       <p>
     * RUNTIME ERROR Null Pointer Exception is handled for a null product
     */
    public static boolean deleteProduct(Product selectedProduct){
        boolean valid;
        try {
            if (selectedProduct.getAllAssociatedParts().size() == 0){
                valid = true;
            }
            else {
                System.out.println();
                valid = false;
            }
        }
        catch (NullPointerException e) {
            valid = true;
            System.out.println("Null pointer Exception caught");
        }

        if (valid){
            allProducts.remove(selectedProduct);
        }

        return valid;
    }

    /**
     * @return a unique part id
     */
    public static int getUniquePartId(){
        uniquePartId++;
        return uniquePartId;
    }
    /**
     * @return a unique product id
     */
    public static int getUniqueProductId() {
        uniqueProductID++;
        return uniqueProductID;
    }

}

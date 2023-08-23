package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that keeps track of which parts/products have been created
 *
 * @author Michael Samp
 */
public class Inventory {
    final private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    final private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds new part to Inventory
     *
     * @param newPart The new part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds new product to Inventory
     *
     * @param newProduct The new product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up a part based on its ID and returns it
     *
     * @param partId The part ID
     * @return The part, if found.
     * @throws Exception Thrown if no part is found
     */
    public static Part lookupPart(int partId) throws Exception {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        throw new Exception("Part not found");
    }

    /**
     * Looks up a product based on its ID and returns it
     *
     * @param productID the product ID
     * @return The product, if found
     * @throws Exception Thrown if no product is found
     */
    public static Product lookupProduct(int productID) throws Exception {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        throw new Exception("Product not found");
    }

    /**
     * Looks up any parts containing a given string and returns them as a list
     *
     * @param partName The search string
     * @return A list containing all parts in Inventory containing the given string
     * @throws Exception Thrown if no parts are found
     */
    public static ObservableList<Part> lookupPart(String partName) throws Exception {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (p.getName().contains(partName)) {
                foundParts.add(p);
            }
        }

        if (!foundParts.isEmpty()) {
            return foundParts;
        } else {
            throw new Exception("Part not found");
        }
    }

    /**
     * Looks up any products containing a given string and returns them as a list
     *
     * @param productName The search string
     * @return A list containing all products in Inventory containing the given string
     * @throws Exception Thrown if no products are found
     */
    public static ObservableList<Product> lookupProduct(String productName) throws Exception {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (p.getName().contains(productName)) {
                foundProducts.add(p);
            }
        }

        if (!foundProducts.isEmpty()) {
            return foundProducts;
        } else {
            throw new Exception("Product not found");
        }
    }

    /**
     * Takes in the index in Inventory.allParts of a part that is going to be updated, and replaces
     * the part at that location with a provided part.
     *
     * @param index Index of part to be replaced/updated
     * @param selectedPart The updated part
     * @throws Exception Thrown if the provided index is outside the range of Inventory.allParts
     */
    public static void updatePart(int index, Part selectedPart) throws Exception {
        ObservableList<Part> allParts = getAllParts();
        if (index >= allParts.size()) {
            throw new Exception("Index out of range.");
        }

        allParts.set(index, selectedPart);
    }

    /**
     *
     * Takes in the index in Inventory.allProducts of a product that is going to be updated, and replaces
     * the product at that location with a provided product.
     *
     * @param index Index of product to be replaced/updated
     * @param newProduct The updated product
     * @throws Exception Thrown if the provided index is outside the range of Inventory.allProducts
     */
    public static void updateProduct(int index, Product newProduct) throws Exception {
        if (index >= allProducts.size()) {
            throw new Exception("Index out of range.");
        }

        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the selected part from Inventory
     *
     * @param selectedPart Part to delete
     * @return A boolean indicating if the deletion was successful
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes the selected product from Inventory
     *
     * @param selectedProduct Product to delete
     * @return A boolean indicating if the deletion was successful
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            return false;
        } else {
            return allProducts.remove(selectedProduct);
        }
    }

    /**
     * Returns a list containing all parts in Inventory
     *
     * @return A list of all parts in Inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns a list containing all products in Inventory
     *
     * @return A list of all products in Inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

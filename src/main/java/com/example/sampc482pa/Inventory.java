package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) throws Exception {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        throw new Exception("Part not found");
    }

    public static Product lookupProduct(int productID) throws Exception {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        throw new Exception("Product not found");
    }

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
            throw new Exception("No parts found");
        }
    }

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
            throw new Exception();
        }
    }

    public static void updatePart(int index, Part selectedPart) throws Exception {
        ObservableList<Part> allParts = getAllParts();
        if (index >= allParts.size()) {
            throw new Exception("Index out of range.");
        }

        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) throws Exception {
        throw new Exception("Not implemented yet");
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

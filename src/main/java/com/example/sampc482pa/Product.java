package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A product which is made up of a collection of parts
 *
 * @author Michael Samp
 */
public class Product {
    final private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Sets the product ID
     *
     * @param id The product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the product name
     *
     * @param name The product name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the stock of the product
     *
     * @param stock Product stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum stock of the product
     *
     * @param min The minimum stock
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum stock of the product
     *
     * @param max The maximum stock
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the product ID
     *
     * @return The product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the product name
     *
     * @return The product name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the product price
     *
     * @return The product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the current stock of the product
     *
     * @return The product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the minimum stock of the product
     *
     * @return The minimum stock
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum stock of the product
     *
     * @return The maximum stock
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the list of parts associated with the product
     *
     * @param part The part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes the given part from the list of parts associated with the product
     *
     * @param selectedAssociatedPart The part to delete
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Returns a list of all parts associated with the product
     *
     * @return A list of all parts associated with the product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}

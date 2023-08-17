package com.example.sampc482pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public static void main(String[] args) {
        InHouse p1 = new InHouse("wheel", 4.20, 69, 1, 4, 69);
        Outsourced p2 = new Outsourced("engine", 420, 4, 1, 1, "Cummins");
        InHouse p3 = new InHouse("window", 54, 69, 1, 4, 9);
        Product prod = new Product(1, "car", 42, 42, 42, 42);

        prod.associatedParts.add(p1);
        prod.associatedParts.add(p2);
        prod.associatedParts.add(p3);

        for (Part component : prod.associatedParts) {
            printPart(component);
        }
        System.out.println();

        boolean partDelete = prod.deleteAssociatedPart(p1);
        if (partDelete) {
            System.out.println("Successfully deleted part");
        } else {
            System.out.println("Part was not deleted.");
        }

        for (Part component : prod.associatedParts) {
            printPart(component);
        }
        System.out.println();

    }

    public static void printPart(Part p) {
        System.out.print("id: " + p.getId() + ", ");
        System.out.print("name: " + p.getName() + ", ");
        System.out.print("price: " + p.getPrice() + ", ");
        System.out.print("stock: " + p.getStock() + ", ");
        System.out.print("min: " + p.getMin() + ", ");
        System.out.println("max: " + p.getMax());
    }
}

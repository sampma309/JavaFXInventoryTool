package com.example.sampc482pa;


public class Outsourced extends Part {
    private static int id = 100;
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(getNextID(), name, price, stock, min, max);
        this.companyName = companyName;
    }

    private static int getNextID() {
        int nextAvailableID = id;
        id++;
        return nextAvailableID;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public static void main(String[] args) {
        Outsourced p = new Outsourced(-1, "wheel", 3.50, 13, 0, 100, "Honda");

        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println(p.getPrice());
        System.out.println(p.getStock());
        System.out.println(p.getMin());
        System.out.println(p.getMax());
        System.out.println(p.getCompanyName());
    }
}

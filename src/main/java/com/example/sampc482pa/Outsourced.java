package com.example.sampc482pa;

import java.util.concurrent.atomic.AtomicInteger;

public class Outsourced extends Part {
    private static final AtomicInteger id = new AtomicInteger(200);
    private String companyName;

    public Outsourced(String name, double price, int stock, int min, int max, String companyName) {
        super(Outsourced.id.getAndIncrement(), name, price, stock, min, max);
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public static void main(String[] args) {
        Outsourced p = new Outsourced("wheel", 3.50, 13, 0, 100, "Honda");

        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println(p.getPrice());
        System.out.println(p.getStock());
        System.out.println(p.getMin());
        System.out.println(p.getMax());
        System.out.println(p.getCompanyName());
    }
}

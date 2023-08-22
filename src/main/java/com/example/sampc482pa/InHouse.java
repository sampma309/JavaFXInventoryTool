package com.example.sampc482pa;


public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getMachineId() {
        return this.machineId;
    }


    public static void main(String[] args) {
        InHouse p = new InHouse(IDCounters.getNextAvailablePartID(), "wheel", 4.00, 1, 0, 5, 42);

        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println(p.getPrice());
        System.out.println(p.getStock());
        System.out.println(p.getMin());
        System.out.println(p.getMax());
        System.out.println(p.getMachineId());
    }
}

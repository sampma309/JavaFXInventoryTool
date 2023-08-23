package com.example.sampc482pa;


/**
 * Subclass of Part.java for parts created in-house.
 *
 * @author Michael Samp
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the machine ID
     *
     * @param machineId The machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets the machine ID
     *
     * @return The machine ID
     */
    public int getMachineId() {
        return this.machineId;
    }

}

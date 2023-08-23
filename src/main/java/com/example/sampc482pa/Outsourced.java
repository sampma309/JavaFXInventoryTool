package com.example.sampc482pa;


/**
 * Subclass of Part.java for parts purchased from an outside source.
 *
 * @author Michael Samp
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the company name of the supplier of this part
     *
     * @param companyName The company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the company name of the supplier of this part
     *
     * @return The company name
     */
    public String getCompanyName() {
        return this.companyName;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progbgolfapp;

/**
 *
 * @author warin
 */

public class GolfCourse {
    private String name;
    private String location;
    private double price9Holes;
    private double price18Holes;
    
    public GolfCourse(String name, String location, double price9Holes, double price18Holes) {
        this.name = name;
        this.location = location;
        this.price9Holes = price9Holes;
        this.price18Holes = price18Holes;
    }
    
    public void displayInfo(int holes) {
        double price = (holes == 9) ? price9Holes : price18Holes;
        System.out.println(name + " - " + holes + " holes: R" + String.format("%.2f", price));
    }
    
    // Getters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public double getPrice9Holes() { return price9Holes; }
    public double getPrice18Holes() { return price18Holes; }
}

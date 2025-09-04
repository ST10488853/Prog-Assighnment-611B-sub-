/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progbgolfapp;

/**
 *
 * @author warin
 */


public class User {
    private String username;
    private String password;
    private double golfIndex;
    
    public User(String username, String password, double golfIndex) {
        this.username = username;
        this.password = password;
        this.golfIndex = golfIndex;
    }
    
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
    
    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Golf Index: " + golfIndex);
        
        // Display a joke based on golf index
        if (golfIndex <= 10) {
            System.out.println("Joke: Why do golfers carry an extra pair of pants?");
            System.out.println("      In case they get a hole in one!");
        } else {
            System.out.println("Joke: What's the difference between a golfer and a skydiver?");
            System.out.println("      A golfer goes Whack! 'Darn!' and a skydiver goes 'Darn!' Whack!");
        }
    }
    
    // Getters
    public String getUsername() { return username; }
    public double getGolfIndex() { return golfIndex; }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package progbgolfapp;

/**
 *
 * @author warin
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProgBgolfApp {
    private User currentUser;
    private List<GolfCourse> localCourses;
    private List<GolfCourse> internationalCourses;
    private Scanner scanner;
    
    public ProgBgolfApp() {
        scanner = new Scanner(System.in);
        localCourses = new ArrayList<>();
        internationalCourses = new ArrayList<>();
        initializeCourses();
    }
    
    private void initializeCourses() {
        // Local courses with random names and prices
        String[] localNames = {
            "Pine Valley Golf Club", 
            "Augusta National", 
            "Pebble Beach Links",
            "Cypress Point Club",
            "Shinnecock Hills",
            "Oakmont Country Club",
            "Merion Golf Club",
            "Sand Hills Golf Club",
            "National Golf Links",
            "Winged Foot Golf Club"
        };
        
        for (String name : localNames) {
            // Generate random prices (9 holes: $50-$100, 18 holes: $90-$180)
            double price9 = 50 + (int)(Math.random() * 50);
            double price18 = 90 + (int)(Math.random() * 90);
            localCourses.add(new GolfCourse(name, "Local", price9, price18));
        }
        
        // International courses (not accessible yet)
        String[] intlNames = {
            "St. Andrews Links (Scotland)",
            "Royal County Down (N. Ireland)",
            "Royal Melbourne (Australia)",
            "Muirfield (Scotland)",
            "Royal Dornoch (Scotland)",
            "Cape Kidnappers (New Zealand)",
            "Kingston Heath (Australia)",
            "Ballybunion (Ireland)",
            "Royal Portrush (N. Ireland)",
            "Turnberry (Scotland)"
        };
        
        for (String name : intlNames) {
            internationalCourses.add(new GolfCourse(name, "International", 0, 0));
        }
    }
    
    private boolean validateUsername(String username) {
        if (username.length() < 8) {
            System.out.println("Username must be at least 8 characters long");
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(username).find()) {
            System.out.println("Username must contain at least one capital letter");
            return false;
        }
        return true;
    }
    
    private boolean validatePassword(String password) {
        if (password.length() < 4) {
            System.out.println("Password must be at least 4 characters long");
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            System.out.println("Password must contain at least one capital letter");
            return false;
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            System.out.println("Password must contain at least one number");
            return false;
        }
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            System.out.println("Password must contain at least one special character");
            return false;
        }
        return true;
    }
    
    private void registerUser() {
        System.out.println("=== Create Your Account ===");
        
        // Get username with validation
        String username;
        while (true) {
            System.out.print("Enter a username (min 8 chars, at least 1 capital): ");
            username = scanner.nextLine();
            if (validateUsername(username)) {
                break;
            }
        }
        
        // Get password with validation
        String password;
        while (true) {
            System.out.print("Enter a password (min 4 chars, 1 capital, 1 number, 1 special char): ");
            password = scanner.nextLine();
            if (validatePassword(password)) {
                System.out.print("Confirm your password: ");
                String confirmPassword = scanner.nextLine();
                if (password.equals(confirmPassword)) {
                    break;
                } else {
                    System.out.println("Passwords do not match!");
                }
            }
        }
        
        // Get golf index
        double golfIndex = 0;
        while (true) {
            try {
                System.out.print("Enter your golf index: ");
                golfIndex = Double.parseDouble(scanner.nextLine());
                if (golfIndex >= 0) {
                    break;
                } else {
                    System.out.println("Golf index must be a positive number");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for your golf index");
            }
        }
        
        // Create user
        currentUser = new User(username, password, golfIndex);
        System.out.println("Account created successfully!");
        System.out.println("You can now login with your credentials.");
    }
    
    private boolean login() {
        System.out.println("=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        // Check if user exists and password matches
        if (currentUser != null && currentUser.getUsername().equals(username) && 
            currentUser.verifyPassword(password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password");
            return false;
        }
    }
    
    private void displayLocalCourses() {
        System.out.println("\n=== Local Golf Courses ===");
        for (int i = 0; i < localCourses.size(); i++) {
            System.out.println((i + 1) + ". " + localCourses.get(i).getName());
        }
        
        // Let user select a course
        while (true) {
            try {
                System.out.print("\nSelect a course (1-10) or 0 to go back: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    return;
                }
                if (choice >= 1 && choice <= 10) {
                    bookCourse(localCourses.get(choice - 1));
                    break;
                } else {
                    System.out.println("Please select a valid option (1-10)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
    
    private void bookCourse(GolfCourse course) {
        System.out.println("\nBooking: " + course.getName());
        System.out.println("1. 9 holes");
        System.out.println("2. 18 holes");
        
        while (true) {
            try {
                System.out.print("Select option (1-2) or 0 to go back: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    return;
                }
                if (choice == 1) {
                    course.displayInfo(9);
                    System.out.println("Booking confirmed! Enjoy your game.");
                    break;
                } else if (choice == 2) {
                    course.displayInfo(18);
                    System.out.println("Booking confirmed! Enjoy your game.");
                    break;
                } else {
                    System.out.println("Please select a valid option (1-2)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
    
    private void displayInternationalCourses() {
        System.out.println("\n=== International Golf Courses ===");
        System.out.println("This feature is still in development.");
        System.out.println("Coming soon!");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void viewProfile() {
        System.out.println("\n=== Profile ===");
        System.out.print("Enter your password to access profile: ");
        String password = scanner.nextLine();
        
        if (currentUser.verifyPassword(password)) {
            currentUser.displayInfo();
        } else {
            System.out.println("Incorrect password. Access denied.");
        }
        
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void run() {
        System.out.println("Welcome to the Golf Course Booking Application!");
        
        // Registration or login
        while (currentUser == null) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            
            System.out.print("Select an option (1-3): ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    if (currentUser != null) {
                        if (login()) {
                            break;
                        }
                    } else {
                        System.out.println("No account found. Please register first.");
                    }
                    break;
                case "3":
                    System.out.println("Thank you for using the Golf Course Booking Application!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        // Main menu
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Local Courses");
            System.out.println("2. International Courses");
            System.out.println("3. View Profile");
            System.out.println("4. Logout");
            System.out.println("5. Exit");
            
            System.out.print("Select an option (1-5): ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    displayLocalCourses();
                    break;
                case "2":
                    displayInternationalCourses();
                    break;
                case "3":
                    viewProfile();
                    break;
                case "4":
                    currentUser = null;
                    System.out.println("Logged out successfully!");
                    run();  // Restart the application
                    return;
                case "5":
                    System.out.println("Thank you for using the Golf Course Booking Application!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    public static void main(String[] args) {
        ProgBgolfApp app = new ProgBgolfApp();
        app.run();
    }
}

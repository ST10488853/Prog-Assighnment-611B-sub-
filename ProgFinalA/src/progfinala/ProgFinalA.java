/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package progfinala;

/**
 *
 * @author warin
 */
import java.util.ArrayList;
import java.util.Scanner;

class SeriesModel {
    public String SeriesId;
    public String SeriesName;
    public String SeriesAge;
    public String SeriesNumberOfEpisodes;
}

public class ProgFinalA{
    private ArrayList<SeriesModel> seriesList;
    private Scanner scanner;
    
    public ProgFinalA() {
        seriesList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    public void DisplayMenu() {
        System.out.println("LATEST SERIES - 2025" +"\n"+"*****************************************");
        System.out.println("Enter (1) to launch menu or any other key to exit");
        
        String input = scanner.nextLine();
        if (!input.equals("1")) {
            exitApplication();
            return;
        }
        
        boolean exit = false;
        while (!exit) {
            System.out.println("\nPlease select one of the following menu items:");
            System.out.println("(1) Capture a new series.");
            System.out.println("(2) Search for a series.");
            System.out.println("(3) Update series age restriction");
            System.out.println("(4) Delete a series.");
            System.out.println("(5) Print series report - 2025");
            System.out.println("(6) Exit Application.");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    captureSeries();
                    break;
                case "2":
                    searchSeries();
                    break;
                case "3":
                    UpdateSeries();
                    break;
                case "4":
                    DeleteSeries();
                    break;
                case "5":
                    seriesReport();
                    break;
                case "6":
                    exit = true;
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            
            if (!exit) {
                System.out.println("\nEnter (1) to launch menu or any other key to exit");
                    input = scanner.nextLine();
                         if (!input.equals("1")) {
                           exit = true;
                              exitApplication();
                }
            }
        }
    }
    
    public void captureSeries() {
        System.out.println("\nCAPTURE A NEW SERIES");
        System.out.println("***************");
        
        SeriesModel series = new SeriesModel();
        
        System.out.print("Enter the series id: ");
        series.SeriesId = scanner.nextLine();
        
        System.out.print("Enter the series name: ");
        series.SeriesName = scanner.nextLine();
        
        // Validate age restriction
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter the series age restriction: ");
            String ageInput = scanner.nextLine();
            
            try {
                int age = Integer.parseInt(ageInput);
                if (age >= 2 && age <= 18) {
                    series.SeriesAge = ageInput;
                    validAge = true;
                } else {
                    System.out.println("You have entered an incorrect series age!!!");
                    System.out.print("Please re-enter the series age >> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("You have entered an incorrect series age!!!");
                System.out.print("Please re-enter the series age >> ");
            }
        }
        
        System.out.print("Enter the number of episodes for " + series.SeriesName + ": ");
        series.SeriesNumberOfEpisodes = scanner.nextLine();
        
        seriesList.add(series);
        System.out.println("Series processed successfully!!!");
    }
    
    public void searchSeries() {
        System.out.print("\nEnter the series id to search: ");
        String searchId = scanner.nextLine();
        
        boolean found = false;
        for (SeriesModel series : seriesList) {
            if (series.SeriesId.equals(searchId)) {
                System.out.println("---");
                System.out.println("SERIES ID: " + series.SeriesId);
                System.out.println("SERIES NAME: " + series.SeriesName);
                System.out.println("SERIES AGE RESTRICTION: " + series.SeriesAge);
                System.out.println("SERIES NUMBER OF EPISODES: " + series.SeriesNumberOfEpisodes);
                System.out.println("---");
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("---");
            System.out.println("Series with Series Id: " + searchId + " was not found!");
            System.out.println("---");
        }
    }
    
    public void UpdateSeries() {
        System.out.print("\nEnter the series id to update: ");
        String updateId = scanner.nextLine();
        
        SeriesModel foundSeries = null;
        for (SeriesModel series : seriesList) {
            if (series.SeriesId.equals(updateId)) {
                foundSeries = series;
                break;
            }
        }
        
        if (foundSeries == null) {
            System.out.println("Series with Series Id: " + updateId + " was not found!");
            return;
        }
        
        System.out.print("Enter the series name: ");
        foundSeries.SeriesName = scanner.nextLine();
        
        // Validate age restriction
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter the age restriction: ");
            String ageInput = scanner.nextLine();
            
            try {
                int age = Integer.parseInt(ageInput);
                if (age >= 2 && age <= 18) {
                    foundSeries.SeriesAge = ageInput;
                    validAge = true;
                } else {
                    System.out.println("You have entered an incorrect series age!!!");
                    System.out.print("Please re-enter the series age >> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("You have entered an incorrect series age!!!");
                System.out.print("Please re-enter the series age >> ");
            }
        }
        
        System.out.print("Enter the number of episodes: ");
        foundSeries.SeriesNumberOfEpisodes = scanner.nextLine();
        
        System.out.println("Series updated successfully!");
    }
    
    public void DeleteSeries() {
        System.out.print("\nEnter the series id to delete: ");
        String deleteId = scanner.nextLine();
        
        SeriesModel foundSeries = null;
        for (SeriesModel series : seriesList) {
            if (series.SeriesId.equals(deleteId)) {
                foundSeries = series;
                break;
            }
        }
        
        if (foundSeries == null) {
            System.out.println("Series with Series Id: " + deleteId + " was not found!");
            return;
        }
        
        System.out.print("Are you sure you want to delete series " + deleteId + " from the system? Yes (y) to delete: ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("y")) {
            seriesList.remove(foundSeries);
            System.out.println("---");
            System.out.println("Series with Series Id: " + deleteId + " WAS deleted!");
            System.out.println("---");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
   public void seriesReport() {
    if (seriesList.isEmpty()) {
        System.out.println("\nNo series data available.");
        return;
    }
    
    System.out.println("\nSERIES REPORT - 2025");
    System.out.println("#========+++=========#");
    
    for (int i = 0; i < seriesList.size(); i++) {
        SeriesModel series = seriesList.get(i);
        System.out.println("Series " + (i + 1));
        System.out.println("---");
        System.out.println("SERIES ID: " + series.SeriesId);
        System.out.println("SERIES NAME: " + series.SeriesName);
        System.out.println("SERIES AGE RESTRICTION: " + series.SeriesAge);
        System.out.println("NUMBER OF EPISODES: " + series.SeriesNumberOfEpisodes);
        System.out.println("---\n");
    }   
 }
    
    public void exitApplication() {
        System.out.println("Exiting application. Goodbye!");
        scanner.close();
    }
    
    public static void main(String[] args) {
      ProgFinalA seriesApp = new ProgFinalA();
        seriesApp.DisplayMenu();
    } 
}
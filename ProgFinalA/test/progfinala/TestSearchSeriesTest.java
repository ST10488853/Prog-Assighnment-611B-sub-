/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package progfinala;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestSearchSeriesTest {
    private ProgFinalA progFinalA;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    
    @BeforeEach
    public void setUp() {
        progFinalA = new ProgFinalA();
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
    
    // Helper method to access private seriesList field
    private ArrayList<SeriesModel> getSeriesList() throws Exception {
        Field field = ProgFinalA.class.getDeclaredField("seriesList");
        field.setAccessible(true);
        return (ArrayList<SeriesModel>) field.get(progFinalA);
    }
    
    // Helper method to set seriesList field
    private void setSeriesList(ArrayList<SeriesModel> list) throws Exception {
        Field field = ProgFinalA.class.getDeclaredField("seriesList");
        field.setAccessible(true);
        field.set(progFinalA, list);
    }
    
    // Helper method to create test series
    private SeriesModel createTestSeries(String id, String name, String age, String episodes) {
        SeriesModel series = new SeriesModel();
        series.SeriesId = id;
        series.SeriesName = name;
        series.SeriesAge = age;
        series.SeriesNumberOfEpisodes = episodes;
        return series;
    }
    
    @Test
    public void testSearchSeriesFound() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("101", "Extreme Sports", "12", "10"));
        setSeriesList(testSeriesList);
        
        // Prepare input
        String input = "101";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.searchSeries();
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("SERIES ID: 101"));
        assertTrue(output.contains("SERIES NAME: Extreme Sports"));
        assertTrue(output.contains("SERIES AGE RESTRICTION: 12"));
        assertTrue(output.contains("SERIES NUMBER OF EPISODES: 10"));
    }
    
    @Test
    public void testSearchSeriesNotFound() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("101", "Extreme Sports", "12", "10"));
        setSeriesList(testSeriesList);
        
        // Prepare input
        String input = "999";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.searchSeries();
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series Id: 999 was not found!"));
    }
    
    @Test
    public void testCaptureSeriesValid() throws Exception {
        // Prepare input for captureSeries
        String input = "103\nTest Series\n15\n20\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.captureSeries();
        
        // Verify the series was added
        ArrayList<SeriesModel> seriesList = getSeriesList();
        assertEquals(1, seriesList.size());
        assertEquals("103", seriesList.get(0).SeriesId);
        assertEquals("Test Series", seriesList.get(0).SeriesName);
        assertEquals("15", seriesList.get(0).SeriesAge);
        assertEquals("20", seriesList.get(0).SeriesNumberOfEpisodes);
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Series processed successfully!!!"));
    }
    
    @Test
    public void testCaptureSeriesInvalidAgeThenValid() throws Exception {
        // Prepare input with invalid age first, then valid
        String input = "104\nTest Series 2\ninvalid\n5\n25\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.captureSeries();
        
        // Verify the series was added with correct age
        ArrayList<SeriesModel> seriesList = getSeriesList();
        assertEquals(1, seriesList.size());
        assertEquals("5", seriesList.get(0).SeriesAge);
        
        // Verify output contains error message
        String output = outputStream.toString();
        assertTrue(output.contains("You have entered an incorrect series age!!!"));
        assertTrue(output.contains("Series processed successfully!!!"));
    }
    
    @Test
    public void testUpdateSeriesFound() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("105", "Old Name", "10", "5"));
        setSeriesList(testSeriesList);
        
        // Prepare input for update
        String input = "105\nNew Name\n12\n8\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.UpdateSeries();
        
        // Verify the series was updated
        ArrayList<SeriesModel> seriesList = getSeriesList();
        assertEquals("New Name", seriesList.get(0).SeriesName);
        assertEquals("12", seriesList.get(0).SeriesAge);
        assertEquals("8", seriesList.get(0).SeriesNumberOfEpisodes);
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Series updated successfully!"));
    }
    
    @Test
    public void testUpdateSeriesNotFound() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("105", "Test Series", "10", "5"));
        setSeriesList(testSeriesList);
        
        // Prepare input for update (non-existent ID)
        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.UpdateSeries();
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series Id: 999 was not found!"));
    }
    
    @Test
    public void testDeleteSeriesConfirmed() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("106", "To Delete", "12", "10"));
        setSeriesList(testSeriesList);
        
        // Prepare input for delete (confirm with 'y')
        String input = "106\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.DeleteSeries();
        
        // Verify the series was deleted
        ArrayList<SeriesModel> seriesList = getSeriesList();
        assertTrue(seriesList.isEmpty());
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("WAS deleted!"));
    }
    
    @Test
    public void testDeleteSeriesCancelled() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("107", "Not Deleted", "12", "10"));
        setSeriesList(testSeriesList);
        
        // Prepare input for delete (cancel with 'n')
        String input = "107\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.DeleteSeries();
        
        // Verify the series was NOT deleted
        ArrayList<SeriesModel> seriesList = getSeriesList();
        assertEquals(1, seriesList.size());
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Deletion cancelled."));
    }
    
    @Test
    public void testDeleteSeriesNotFound() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("108", "Existing Series", "12", "10"));
        setSeriesList(testSeriesList);
        
        // Prepare input for delete (non-existent ID)
        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.DeleteSeries();
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series Id: 999 was not found!"));
    }
    
    @Test
    public void testSeriesReportWithData() throws Exception {
        // Setup test data
        ArrayList<SeriesModel> testSeriesList = new ArrayList<>();
        testSeriesList.add(createTestSeries("109", "Report Series 1", "12", "10"));
        testSeriesList.add(createTestSeries("110", "Report Series 2", "15", "20"));
        setSeriesList(testSeriesList);
        
        // Call the method
        progFinalA.seriesReport();
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("SERIES REPORT - 2025"));
        assertTrue(output.contains("SERIES ID: 109"));
        assertTrue(output.contains("SERIES ID: 110"));
        assertTrue(output.contains("Report Series 1"));
        assertTrue(output.contains("Report Series 2"));
    }
    
    @Test
    public void testSeriesReportEmpty() throws Exception {
        // Setup empty list
        setSeriesList(new ArrayList<>());
        
        // Call the method
        progFinalA.seriesReport();
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("No series data available."));
    }
    
    @Test
    public void testExitApplication() throws Exception {
        // Use reflection to test private method
        Method exitMethod = ProgFinalA.class.getDeclaredMethod("exitApplication");
        exitMethod.setAccessible(true);
        
        // Call the method
        exitMethod.invoke(progFinalA);
        
        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Exiting application. Goodbye!"));
    }
    
    @Test
    public void testDisplayMenuExitImmediately() throws Exception {
        // Test menu exit without entering menu
        String input = "2\n"; // Any key other than 1
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.DisplayMenu();
        
        // Verify output contains exit message
        String output = outputStream.toString();
        assertTrue(output.contains("Exiting application. Goodbye!"));
    }
    
    @Test
    public void testAgeValidationInvalidThenValid() throws Exception {
        // Test age validation with invalid input followed by valid input
        String input = "111\nAge Test\ninvalid\n25\n5\n15\n"; // ID, Name, invalid age, too high age, valid age
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        // Call the method
        progFinalA.captureSeries();
        
        // Verify output contains error messages and success
        String output = outputStream.toString();
        assertTrue(output.contains("You have entered an incorrect series age!!!"));
        assertTrue(output.contains("Series processed successfully!!!"));
        
        // Verify the correct age was stored
        ArrayList<SeriesModel> seriesList = getSeriesList();
        assertEquals("15", seriesList.get(0).SeriesAge);
    }
}
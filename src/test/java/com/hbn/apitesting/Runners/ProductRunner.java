package com.hbn.apitesting.Runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/hbn/apitesting/Features",
        glue = "com.hbn.apitesting.StepDefinitions", // Ensure correct package name
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json"
        },
        monochrome = true
)
public class ProductRunner {

    @AfterClass
    public static void tearDown() {
        // Database cleanup logic to remove test data
        String url = "jdbc:mysql://localhost:3306/inventory_db";
        String user = "root";
        String password = "0000";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            String deleteTestData = "DELETE FROM products WHERE name IN ('Smartphone', 'RunningShoes', 'Watch');";
            stmt.executeUpdate(deleteTestData);
            System.out.println("Test data deleted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


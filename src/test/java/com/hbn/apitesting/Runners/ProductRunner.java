package com.hbn.apitesting.Runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                // feature file order specified -> This alone is not working
                "src/test/java/com/hbn/apitesting/Features/ProductPostGet.feature",
                "src/test/java/com/hbn/apitesting/Features/ProductUpdate.feature",
                "src/test/java/com/hbn/apitesting/Features/ProductDelete.feature" },

        glue = "com.hbn.apitesting.StepDefinitions", // Ensure correct package name
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json",
                "json:target/cucumber.json"
        },
        monochrome = true,
        dryRun = true
)

//forcing to run in the specified order
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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


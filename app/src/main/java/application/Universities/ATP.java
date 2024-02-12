package application.Universities;

import application.Components;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.json.JSONTokener;

public class ATP {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;

    public ATP() {
        try (FileReader fileReader = new FileReader("C:\\Users\\njabulo\\Documents\\code\\application\\app\\src\\main\\java\\application\\Universities\\info.json")) {
            // Parse the JSON file
            JSONTokener tokener = new JSONTokener(fileReader);
            data = new JSONObject(tokener);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Create a new instance of the HTMLUnitDriver
        // this.driver = new HtmlUnitDriver();
        
        // Create a new instance of the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\njabulo\\Documents\\code\\application\\app\\src\\main\\java\\application\\chromedriver.exe");

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();

        this.driver = new ChromeDriver(options);
        this.driver.get("http://192.168.8.1/html/index.html?version=22.001.29.01.03");
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }


    public void run() {
        c.sendXpath("//*[@id=\"login_password\"]", "Njabulo0.");
        c.clickId("login_btn");
        c.clickId("menu_top_wanconfig");
        int count = 1;
        while (true) {
            c.clickXpath("//*[@id=\"apn_list_name_"+Integer.toString(count)+"\"]/pre");
            c.clickXpath("//*[@id=\"apn_list_input_set_default_profile\"]");
            c.clickXpath("//*[@id=\"apn_item_save\"]");
            try {
                Thread.sleep(45000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            count++;
        }
    }


}

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Uwc {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;

    public Uwc() {
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

        // Disable the "Save Password" prompt
        options.addArguments("--disable-save-password-bubble");
        // Disable save address prompt
        options.addArguments("--disable-autofill-keyboard-accessory-view[8]");
        // Disable the "Chrome is being controlled by automated test software" prompt
        options.addArguments("disable-infobars");
        // options.setExperimentalOption("auto-fill", "false");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        this.driver = new ChromeDriver(options);
        this.driver.get("https://student.uwc.ac.za/"); 
        
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }

    public void newApplication() {
        // #CEWFR_RNDR1_eaBTNNEWun1 click new application
        c.clickId("CEWFR_RNDR1_eaBTNNEWun1");

        // #CEWFR_RNDR1_eaBTNACCEPTun1 click accept terms
        c.clickId("CEWFR_RNDR1_eaBTNACCEPTun1");
    }

    public void applicationDetail(){
        // #CEWFR_RNDR1_eaISURNAME surname
        c.sendId("CEWFR_RNDR1_eaISURNAME", data.getString("surname"));

        // #CEWFR_RNDR1_eaIFIRSTNAME firstname
        c.sendId("CEWFR_RNDR1_eaIFIRSTNAME", data.getString("firstName"));

        // #CEWFR_RNDR1_eaIEMAIL email
        c.sendId("CEWFR_RNDR1_eaIEMAIL", data.getString("Email"));

        // #CEWFR_RNDR1_eaIEMAIL2 confirm email
        c.sendId("CEWFR_RNDR1_eaIEMAIL2", data.getString("Email"));

        // #CEWFR_RNDR1_eaIIDNUMBER
        c.sendId("CEWFR_RNDR1_eaIIDNUMBER", data.getString("id"));
        
        // #CEWFR_RNDR1_eaBTNSUBMITun1 click submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void personalInfo(){ 
        // #CEWFR_RNDR1_eaITITLE title
        c.selectValue("CEWFR_RNDR1_eaITITLE", data.getString("title").toUpperCase());
        
        // # CEWFR_RNDR1_eaIINITIALS initials 
        c.sendId("CEWFR_RNDR1_eaIINITIALS", data.getString("initials"));

        // #CEWFR_RNDR1_eaIGENDER
        if (data.getString("title").toLowerCase() == "mr") {
        } else {
            c.selectValue("CEWFR_RNDR1_eaIGENDER", "F");
        }
        
        // #CEWFR_RNDR1_eaIMARTLSTAT marital status
        c.selectElement("id","CEWFR_RNDR1_eaIMARTLSTAT", "Single");

        // #CEWFR_RNDR1_eaILANGHOME home language
        c.selectElement("id", "CEWFR_RNDR1_eaILANGHOME", data.getString("h_language"));

        // #CEWFR_RNDR1_eaIMTRYEAR year of matric
        c.selectElement("id", "CEWFR_RNDR1_eaIMTRYEAR", data.getString("Matric Year"));

        // #CEWFR_RNDR1_eaIPRVACTVTY previous activity
        c.selectElement("id", "CEWFR_RNDR1_eaIPRVACTVTY", "Matriculant");

        // #CEWFR_RNDR1_eaIEXAMAUTH exam authority
        c.selectElement("id", "CEWFR_RNDR1_eaIEXAMAUTH", data.getString("Education Department"));
        
        // #CEWFR_RNDR1_eaIPOPGROUP population group
        c.selectElement("id", "CEWFR_RNDR1_eaIPOPGROUP", data.getString("ethnic"));

        // #CEWFR_RNDR1_eaIRESAREA residence area
        c.selectElement("id", "CEWFR_RNDR1_eaIRESAREA", data.getString("province"));

        c.selectElement("id", "CEWFR_RNDR1_eaIEXAMAUTH", data.getString("Education Department"));

        // #CEWFR_RNDR1_eaIAREATYP_0 area type
        c.clickId("CEWFR_RNDR1_eaIAREATYP_0");

        // #CEWFR_RNDR1_eaBTNSUBMITun1 click submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }
 
    public void run() {
        try {
            System.out.println("new application");
            newApplication();
        } catch (Exception e) {
            System.out.println("new application");
            // TODO: handle exception
            this.driver.navigate().refresh();
            newApplication();
        }
        
        try {
            System.out.println("application Detail");
            applicationDetail();
        } catch (Exception e) {
            System.out.println("application Detail");
            this.driver.navigate().refresh();
            applicationDetail();
            // TODO: handle exception
        }

        try {
            System.out.println("personal Info");
            personalInfo();
        } catch (Exception e) {
            System.out.println("personal Info");
            this.driver.navigate().refresh();
            personalInfo();
            // TODO: handle exception
        }
    }
}

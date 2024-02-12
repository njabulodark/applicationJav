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

public class Uct {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;

    public Uct() {
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
        // make driver wait for 2 seconds before executing another instruction
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.driver.get("https://publicaccess.uct.ac.za/psc/public/EMPLOYEE/SA/c/UCT_PUBLIC_MENU.UCT_ONL_HOME_FL.GBL?PAGE=UCT_ONL_ACC_FL"); 
        System.out.println(this.driver.getTitle());
        
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }

    public void CreateAccount() {
        System.out.println(this.driver.getTitle());
        System.out.println("first()");

        // first Name
        c.sendId("SCC_NUR_WRK_FIRST_NAME", data.getString("firstName"));

        // last Name
        c.sendId("SCC_NUR_WRK_LAST_NAME", data.getString("surname"));

        // date of birth
        String[] date = data.getString("dateOfBirth").split("-");
        c.sendId("UCT_DERIVED_ONL_BIRTHDATE", date[2]+ "/" + date[1] + "/" + date[0]);

        // email
        c.sendId("SCC_NUR_WRK_EMAIL_ADDR$10$", data.getString("Email"));

        // username 
        c.sendId("SCC_NUR_WRK_OPRID", data.getString("username") + date[2] + date[1] + date[0]);

        // ID
        c.sendId("UCT_DERIVED_ONL_NATIONAL_ID", data.getString("identification number"));

        // password
        String password = "";
        password = data.getString("firstName").substring(0, 1).toUpperCase() + data.getString("surname").substring(0, 1).toUpperCase() + "&." + date[2] + date[1] + date[0];
        c.sendId("SCC_NUR_WRK_OPERPSWD", password);

        // confirm password
        c.sendId("SCC_NUR_WRK_OPERPSWDCONF", password);

        // click create
        c.clickId("UCT_DERIVED_ONL_CREATE_PB");

        // confirm email
        // check if cornfirm email page is displayed
        try {
            this.wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("Confirm Email Address")));

            // get user input
            
        } catch (Exception e) {
            System.out.println("Email confirmation page not displayed");
        }
        
    }
    
    public void run() {

        try {
            CreateAccount();
        } catch (Exception e) {
            this.driver.navigate().refresh();
            CreateAccount();
        }
        
    }
}

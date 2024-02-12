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

public class Ufs {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;

    public Ufs() {
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
        this.driver.get("https://apply.ufs.ac.za/Application/Verification"); 
        
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }

    public void newApplication() {
        // #Title
        c.selectValue("Title", data.getString("title"));

        // #FirstName
        c.sendId("FirstName", data.getString("firstName"));

        // #MiddleName
        c.sendId("MiddleName", data.getString("middleName"));
        // #LastName
        c.sendId("LastName", data.getString("surname"));
        // #Cellnumber
        c.sendId("Cellnumber", data.getString("cellNum"));
        // #EmailAddress
        c.sendId("EmailAddress", data.getString("Email"));

        // #verify-cell
        // self.driver.find_element(By.XPATH, '//*[@id="verify-cell"]').send_keys(Keys.ENTER)
        
        // #get the verification code from user
        // self.driver.find_element(By.ID, "CellphoneVerificationCode").send_keys(input("Enter the verification code from SMS: "))
        // #click verify find by partial text
        // self.driver.find_element(By.XPATH, '//*[@id="verify-cellphone-container"]/div/div[1]/button').send_keys(Keys.ENTER)
        // #check if code is valid
        
        // while len(self.driver.find_elements(By.XPATH, '//*[@id="page-content"]/div/div/div/div/div/div[2]/div/form/div/div[1]/div[1]/div/div[7]/div/div/button'))>1:
        //     try:
        //         #/html/body/div[3]/div[7]/div[1]/button
        //         self.driver.find_element(By.XPATH, '/html/body/div[3]/div[7]/div[1]/button').send_keys(Keys.ENTER)
        //     except:
        //         pass
        //     #clear field
        //     self.driver.find_element(By.ID, "CellphoneVerificationCode").send_keys(Keys.CONTROL + "a")
        //     self.driver.find_element(By.ID, "CellphoneVerificationCode").send_keys(Keys.DELETE)

        //     self.driver.find_element(By.ID, "CellphoneVerificationCode").send_keys(input("Enter the verification code from SMS: ").upper())
        //     #click verify find by partial text
        //     self.driver.find_element(By.XPATH, '//*[@id="verify-cellphone-container"]/div/div[1]/button').send_keys(Keys.ENTER)
        
        // #verify email
        // self.driver.find_element(By.XPATH, '//*[@id="page-content"]/div/div/div/div/div/div[2]/div/form/div/div[1]/div[1]/div/div[7]/div/div/button').send_keys(Keys.ENTER)

        // #EmailVerificationCode
        // self.driver.find_element(By.ID, "EmailVerificationCode").send_keys(input(f"Enter the code sent on email {self.data['Email']}: "))
        // self.driver.find_element(By.XPATH, '//*[@id="verify-email-container"]/div/div[1]/button').send_keys(Keys.ENTER)

        // #self.driver.find_element(By.XPATH, '//*[@id="page-content"]/div/div/div/div/div/div[2]/div/form/div/div[2]/button').send_keys(Keys.ENTER)
        // while self.driver.find_element(By.XPATH, '//*[@id="page-content"]/div/div/div/div/div/div[2]/div/form/div/div[1]/div[1]/div/div[7]/div/div/button').is_displayed() > 0:
        //     try:
        //         #/html/body/div[3]/div[7]/div[1]/button
        //         self.driver.find_element(By.XPATH, '/html/body/div[3]/div[7]/div[1]/button').send_keys(Keys.ENTER)
        //     except:
        //         pass
        //     #clear field
        //     time.sleep(1)
        //     self.driver.find_element(By.ID, "EmailVerificationCode").send_keys(Keys.CONTROL + "a")
        //     self.driver.find_element(By.ID, "EmailVerificationCode").send_keys(Keys.DELETE)

        //     self.driver.find_element(By.ID, "EmailVerificationCode").send_keys(input(f"Enter the code sent on email {self.data['Email']}: ").upper())
        //     #click verify find by partial text
        //     self.driver.find_element(By.XPATH, '//*[@id="verify-email-container"]/div/div[1]/button').send_keys(Keys.ENTER)
        
        // #contains text Next
        // self.driver.find_element(By.XPATH, '//*[contains(text(), "Aviato")]').send_keys(Keys.ENTER)
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
    }
}

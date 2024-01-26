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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Nwu {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;

    public Nwu() {
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
        // C:\Users\njabulo\Documents\code\application\app\src\main\java\application\chromedriver.exe
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

        this.driver = new ChromeDriver(options);
        this.driver.get("https://vssweb.nwu.ac.za/aaa-webclient/AAAUnsecuredNewApplicationMenuWin.do#/top"); 
        
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }

    public void firstPage(){
        // okay button waitdriver
        c.clickXpath("/html/body/div[4]/div[3]/div/button/span");

        // new application
        // driver contains text
        c.clickXpath("//*[contains(text(), 'Create new University Number')]");

        // make input variable
        List<WebElement> inputs = this.driver.findElements(By.tagName("input")).subList(6, this.driver.findElements(By.tagName("input")).size());
        List<WebElement> inputElements = this.driver.findElements(By.tagName("select"));
        List<WebElement> selects = inputElements.subList(1, inputElements.size());

        // title
        c.selectElement(selects.get(0), this.data.getString("title").toUpperCase());

        // #language
        c.selectElement(selects.get(1), this.data.getString("p_language").toLowerCase());

        // defLanguage
        c.selectElement(selects.get(2), "English");

        // #surname
        inputs.get(0).sendKeys(this.data.getString("surname"));

        // #initials
        inputs.get(1).sendKeys(this.data.getString("initials"));

        // #FirstName
        inputs.get(2).sendKeys(this.data.getString("firstName"));

        // #DateOfBirth
        inputs.get(3).sendKeys(this.data.getString("dateOfBirth").replace("-", ""));

        // #Id
        inputs.get(4).sendKeys(this.data.getString("id"));

        // #Email
        inputs.get(6).sendKeys(this.data.getString("Email"));

        // #CellCode
        inputs.get(7).sendKeys("+27");

        // #CellNum
        inputs.get(8).sendKeys(this.data.getString("cellNum").substring(1));

        // #Password
        inputs.get(inputs.size() - 2).sendKeys(this.data.getString("nwu_password"));
        inputs.get(inputs.size() - 1).sendKeys(this.data.getString("nwu_password"));

        // #next
        c.clickXpath("//*[@id=\"DefaultButton\"]");
    }

    public void AlreadyRegistered(){
        // okay button waitdriver
        c.clickXpath("/html/body/div[4]/div[3]/div/button/span");

        // already have UniNum
        c.clickXpath("//*[contains(text(), 'Already have a University Number')]");

        // input UniNumber
        c.sendtagName("input", this.data.getString("UniNumberNWU"), 5);

        // input password
        c.sendtagName("input", this.data.getString("nwu_password"), 6);

        // click next
        c.clickXpath("//button[contains(text(), 'Login')]");

        // new application
        c.clickXpath("//*[contains(text(), 'New Application')]");
    }

    public void getStudentNo() {
        // get value
        String number = this.driver.findElements(By.tagName("input")).get(this.driver.findElements(By.tagName("input")).size() - 1).getAttribute("value");
        System.out.println(number);
        // save number on info.json
        try (FileWriter file = new FileWriter("C:\\Users\\NA khumalo\\Documents\\code\\application\\app\\src\\main\\java\\application\\Universities\\info.json")) {
            // write UniNumberNWU to file
            this.data.put("UniNumberNWU", number);
            file.write(this.data.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // click next
        this.driver.findElement(By.tagName("button")).click();
    }

    public void secondPage() {
        List<WebElement>selects = this.driver.findElements(By.tagName("select"));

        // matric year
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("input")));
        } catch (Exception e) {
            // refresh page
            this.driver.navigate().refresh();
            secondPage();
        }
        c.sendtagName("input", this.data.getString("Matric Year"), this.driver.findElements(By.tagName("input")).size() - 1);

        // #select by text Undergraduate Qualification
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("select")));
        } catch (Exception e) {
            // refresh page
            this.driver.navigate().refresh();
            secondPage();
        }

        // // Select the option by visible text
        c.selectClosestCss("select.form-control", "Undergraduate Qualification");
        // c.selectElement(this.driver.findElement(By.cssSelector("select.form-control")), "Undergraduate Qualification");

        // #select by text contact
        c.selectElement(this.driver.findElements(By.tagName("select")).get(1), "Contact");

        // #select by text Higher Cert oth Ter
        c.selectElement(this.driver.findElements(By.tagName("select")).get(2), "Higher Cert oth Ter");

        // #click for ress
        WebElement ress = this.driver.findElements(By.tagName("input")).get(this.driver.findElements(By.tagName("input")).size() - 1);
        ress.click();

        // #click nsfas
        c.selectElement(this.driver.findElements(By.tagName("select")).get(6), "NSFAS");

        // #accomodation
        c.selectElement(this.driver.findElements(By.tagName("select")).get(7), "NWU Residence");

        // #click next
        c.clickXpath("//*[contains(text(), 'Next >>')]");
    }

    public void run() {
        // firstPage();
        // getStudentNo();
        
        AlreadyRegistered();
        secondPage();
    }

}

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

import org.checkerframework.checker.units.qual.A;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import application.Components;

public class Flight {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;
    private ArrayList<String> odds = new ArrayList<String>();
    private int frequentBets = 0;

    public Flight(){
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
        //  Disable sound
        options.addArguments("--mute-audio");
        // options.setExperimentalOption("auto-fill", "false");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // headless mode
        // options.addArguments("--headless");

        this.driver = new ChromeDriver(options);
        // make driver wait for 2 seconds before executing another instruction
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.driver.get("https://www.hollywoodbets.net/");
        
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }

    public void login() {
        c.clickPartialLinkText("Log In");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // send username
        c.sendCss("#Username", "0695977023");
        // password
        c.sendId("password", "Njabulo0.");

        // click login
        c.clickId("btnLogin");

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.driver.get("https://www.hollywoodbets.net/aviator");

        // c.clickXpath("//p[contains(text(), \"Aviator\")]");
    }

    public void aviation() {
        // switch to iframe
        this.driver.switchTo().frame(this.driver.findElement(By.tagName("iframe")));

        // click auto
        boolean done = false;

        while (!done) {
            try {
                c.clickXpath("//Button[contains(text(), 'Auto')]");
                // Thread.sleep(1000);
                // this.driver.findElement(By.xpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/app-navigation-switcher/div/button[2]")).sendKeys(Keys.ENTER);
                done = true;
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Trying again");
            }
            
        }

        try {
            Thread.sleep(2000);
            // send betting amount
            c.sendXpath("//input[@class='font-weight-bold']", "1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click auto Cash out
        try {
            c.clickXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div[2]/div[1]/app-ui-switcher/div/span");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // send cash out amount
        c.sendXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div[2]/div[2]/div/app-spinner/div/div[2]/input", "1.05");
        
        // get the latest odds
        String odd = c.getTextXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/app-bubble-multiplier[1]/div");
        // "/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/app-bubble-multiplier[1]/div"
        // /html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/app-bubble-multiplier[2]/div
        System.out.println("Latest odd: "+odd);
        this.odds.add(odd);
    }

    public void dataCollection() {
        // get the latest odds
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("payouts-block")));
        while (true) {
            for (int i = 20; i > 0; i--) {
                try {
                    String odd = this.driver.findElement(By.xpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/app-bubble-multiplier["+i+"]/div")).getText();
                    if (odd.equals("")) {
                        continue;
                    }
                    this.odds.add(odd);
                } catch (Exception e) {
                    break;
                }
            }
            break;
        }
    }

    public void CalcFreq() {
        int length = this.odds.size();
        if (Integer.parseInt(this.odds.get(length-2)) >= 2 && Integer.parseInt(this.odds.get(length-1)) >= 2) {
            this.frequentBets++;
        } else if (Integer.parseInt(this.odds.get(length-2)) >= 2 && Integer.parseInt(this.odds.get(length-1)) < 2) {
            this.frequentBets--;
        } else if (this.frequentBets < 0) {
            this.frequentBets = 0;
        }
    }
    public void bet() {
        float balance = Float.parseFloat(c.getTextXpath("//span[contains(@class, 'amount')]"));
        int length = this.odds.size();

        while (true) {
            dataCollection();
            CalcFreq();

            if (this.frequentBets > 3 && Integer.parseInt(this.odds.get(length-2)) >= 2) {
                // click bet
                c.clickXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/button");
            } else if (this.frequentBets > 1 && Integer.parseInt(this.odds.get(length-1)) < 2 && Integer.parseInt(this.odds.get(length-2)) >= 2) {
                // click bet
                c.clickXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/button");
            }
            
        }
  
    }

    public void run() {
        login();
        aviation();
        dataCollection();

        // print the odds
        for (String odd : this.odds) {
            System.out.println(odd);
        }
    }
    
}

package application;


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
import org.w3c.dom.css.Counter;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
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
import org.htmlunit.corejs.javascript.json.JsonParser;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Flight {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;
    private ArrayList<String> odds = new ArrayList<String>();
    private int frequentBets = 0;
    private String username;
    private String password;
    private int bet_amount;
    private int x_amount;
    private float initBalance;
    public boolean failure = false;
    private String host = "http://102.37.33.157";

    public Flight(){
        // Create a new instance of the ChromeDriver
        // driver location is current directory
        
        
        // System.setProperty("webdriver.chrome.driver", "C:\\Users\\njabulo\\Documents\\code\\application\\app\\src\\main\\java\\application\\chromedriver.exe");
        WebDriverManager.firefoxdriver().setup();
        
        // Configure Chrome options
        // ChromeOptions options = new ChromeOptions();

        // // Disable the "Save Password" prompt
        // options.addArguments("--disable-save-password-bubble");
        // // Disable save address prompt
        // options.addArguments("--disable-autofill-keyboard-accessory-view[8]");
        // // Disable the "Chrome is being controlled by automated test software" prompt
        // options.addArguments("disable-infobars");
        // //  Disable sound
        // options.addArguments("--mute-audio");
        // // options.setExperimentalOption("auto-fill", "false");
        // Map<String, Object> prefs = new HashMap<String, Object>();
        // prefs.put("credentials_enable_service", false);
        // prefs.put("profile.password_manager_enabled", false);
        // prefs.put("autofill.profile_enabled", false);

        // options.setExperimentalOption("prefs", prefs);

        // // headless mode
        // // options.addArguments("--headless");

        // this.driver = new ChromeDriver(options);
        // make driver wait for 2 seconds before executing another instruction

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        this.driver = new FirefoxDriver( options );
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
        c.sendCss("#Username", username);
        // password
        c.sendId("password", password);

        // click login
        c.clickId("btnLogin");

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.driver.get("https://www.hollywoodbets.net/aviator");
    }

    public boolean aviation() {
        // switch to iframe
        this.driver.switchTo().frame(this.driver.findElement(By.tagName("iframe")));

        // click auto
        boolean done = false;

        try {
            c.clickXpath("//Button[contains(text(), 'Auto')]");
        } catch (Exception e) {
            e.printStackTrace();
            c.clickXpath("//Button[contains(text(), 'Auto')]");

        }


        try {
            Thread.sleep(2000);
            // send betting amount
            c.sendXpath("//input[@class='font-weight-bold']", Integer.toString(this.bet_amount));
            // c.sendXpath("//input[@class='font-weight-bold']", "1");
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.failure = true;
            return false;
            
        }


        try {
            // click auto Cash out
            c.clickXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div[2]/div[1]/app-ui-switcher/div/span");
                
            // send cash out amount
            c.sendXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div[2]/div[2]/div/app-spinner/div/div[2]/input", Integer.toString(this.x_amount));
            // c.sendXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[3]/div[2]/div[2]/div/app-spinner/div/div[2]/input", "2");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Trying again to set cash out amount");
            this.failure = true;
            return false;

        }

        
        // get the latest odds
        String odd = c.getTextXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/app-bubble-multiplier[1]/div");

        System.out.println("Latest odd: "+odd);
        this.odds.add(odd);
        return true;
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

    public String getData() {
        try {
            URL url = new URL(host+":8081/api/get"); // Replace with the API endpoint
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject output = new JSONObject();
            String outputString;
            while ((outputString = br.readLine()) != null) {
                // remove []
                outputString = outputString.replace("[", "");
                outputString = outputString.replace("]", "");
                output = new JSONObject(outputString);
            }

            
            this.username = output.getString("av_username");
            this.password = output.getString("av_password");
            this.bet_amount = output.getInt("bet_amount");
            this.x_amount = output.getInt("x_amount");
            
            String name = "Njabulo";
            conn.disconnect();
            return name;
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
            return ("Exception in NetClientGet:- " + e);
        }
    }

    public void sendData() {
        try {
            URL url = new URL(host+":8081/api/oddslist");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject input = new JSONObject();
            input.put("bets", this.odds); // Insert the odds here

            String inputString = input.toString();

            conn.getOutputStream().write(inputString.getBytes("UTF-8"));

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                break;
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }
    
    public void sendBetTime() {
        try {
            URL url = new URL(host+":8081/api/updateBetTime");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // get current time in hours:minutes:seconds format
            String time = java.time.LocalTime.now().toString();


            JSONObject input = new JSONObject();
            input.put("betTime", time); // Insert the odds here

            String inputString = input.toString();

            conn.getOutputStream().write(inputString.getBytes("UTF-8"));

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                break;
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }
    
    public void sendStatus(String status) {
        try {
            URL url = new URL(host+":8081/api/updateStatus");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // get current time in hours:minutes:seconds format
            String time = java.time.LocalTime.now().toString();


            JSONObject input = new JSONObject();
            input.put("status", status); // Insert the odds here

            String inputString = input.toString();

            conn.getOutputStream().write(inputString.getBytes("UTF-8"));

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                break;
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }
    
    public void sendAmount( String balance) {
        try {
            URL url = new URL(host+":8081/api/updateBalance");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject input = new JSONObject();
            input.put("balance", balance); // Insert the here

            String inputString = input.toString();

            conn.getOutputStream().write(inputString.getBytes("UTF-8"));

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

    public void CalcFreq() {
        int length = this.odds.size();
        float lastOdd = Float.parseFloat(this.odds.get(length-1).substring(0, this.odds.get(length-1).length()-1 ));
        float secondLastOdd = Float.parseFloat(this.odds.get(length-2).substring(0, this.odds.get(length-2).length()-1 ));

        System.out.println("Last odd: "+lastOdd);
        System.out.println("Second last odd: "+secondLastOdd);

        if (secondLastOdd >= 2 && lastOdd >= 2) {
            this.frequentBets++;
        } else if (secondLastOdd >= 2 && lastOdd < 2) {
            this.frequentBets--;
        }
        
        if (this.frequentBets < 0) {
            this.frequentBets = 0;
        }
    }

    public void CalcFirstFreq() {
        // calculate frequency using the odds list
        int length = this.odds.size();
        
        for (int i = 0; i < length-1; i++) {
            if (i +1 == length) {
                break;
            }

            float odd = Float.parseFloat(this.odds.get(i).substring(0, this.odds.get(i).length()-1 ));
            float nextOdd = Float.parseFloat(this.odds.get(i+1).substring(0, this.odds.get(i+1).length()-1 ));
            if (odd >= 2 && nextOdd >= 2) {
                this.frequentBets++;
                System.out.println("Frequent bets from firstCalculation: "+this.frequentBets);
            } else if (odd >= 2 && nextOdd < 2) {
                this.frequentBets--;
                System.out.println("Frequent bets from firstCalculation: "+this.frequentBets);
            } else if (odd < 0) {
                this.frequentBets = 0;
            }
            
            
        }
    }

    public String updateData() {
        int length = this.odds.size();
        String odd = c.getTextXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/app-bubble-multiplier[1]/div");
        float newOdd = Float.parseFloat(odd.substring(0, odd.length()-1 ));

        float lastOdd = Float.parseFloat(this.odds.get(length-1).substring(0, this.odds.get(length-1).length()-1 ));

        if ( lastOdd != newOdd  && newOdd >= 2) {
            this.odds.add(odd);
            return "Increased";
        } else if (lastOdd != newOdd && newOdd < 2) {
            this.odds.add(odd);
            return "Decreased";
        } 

        return "No change";
    }
    
    public void clean() {
        // removing repeated odds that follow each other
        int length = this.odds.size();
        for (int i = 0; i < length-1; i++) {
            try {
                if (this.odds.get(i).equals(this.odds.get(i+1))) {
                    this.odds.remove(i);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void setInitBalance() {
        this.initBalance = Float.parseFloat(c.getTextXpath("//span[contains(@class, 'amount')]"));
    }

    public void bet() {
        float balance = Float.parseFloat(c.getTextXpath("//span[contains(@class, 'amount')]"));
        setInitBalance();
        int length = this.odds.size();
        int trade = 0;
        dataCollection();
        CalcFirstFreq();
        CalcFreq();

        System.out.println("Startup Frequency: " + this.frequentBets);
        
        System.out.println("Initial balance: " + balance);
        int count = 0;
        
        
        while (true) {
            // clean ram every 20 runs
            if (count > 20) {
                // clean ram with Process
                try {
                    Process process = Runtime.getRuntime().exec("free -h && sudo sysctl -w vm.drop_caches=3 && sudo sync && echo 3 | sudo tee /proc/sys/vm/drop_caches && free -h\r\n");
                    process.waitFor();
                    if (process.exitValue() == 0) {
                        System.out.println("RAM cleared.");
                    } else {
                        System.out.println("Failed to clear RAM.");
                    }
                    count = 0;
                } catch (Exception e) {
                    // e.printStackTrace();
                }    
            }

            if (balance >= this.initBalance + 25) {
                this.driver.close();
                break;
            }

            // if current time is between 04:00 and 05:00, stop betting
            String time = java.time.LocalTime.now().toString();
            if (time.compareTo("04:00:00") > 0 && time.compareTo("05:00:00") < 0) {
                this.driver.close();
                break;
            }
            // dataCollection();
            sendData();
            sendBetTime();
            sendAmount( balance + "");
            length = this.odds.size();

            float lastOdd = Float.parseFloat(this.odds.get(length-1).substring(0, this.odds.get(length-1).length()-1 ));
            float secondLastOdd = Float.parseFloat(this.odds.get(length-2).substring(0, this.odds.get(length-2).length()-1 ));

            String odd = this.driver.findElement(By.xpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[1]/app-stats-widget/div/div[1]/div/app-bubble-multiplier[1]/div")).getText();
            String lastOdString = lastOdd+"x";

            if ( lastOdString.equals(odd) ) {
                try {
                    Thread.sleep(2000);
                    System.out.println("No new odd");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            if (this.frequentBets > 3 && secondLastOdd >= 2 && !lastOdString.equals(odd)) {
                // click bet
                c.clickXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/button");

                // make a bet
                trade++;
                System.out.println("Traded");
                System.out.println("Number of trades: "+trade);

                balance = Float.parseFloat(c.getTextXpath("//span[contains(@class, 'amount')]"));


                // String results = "";
                
                // do {
                //     results = updateData();
                //     if (results.equals("Increased")) {
                //         balance = balance + 1;
                //     } else if (results.equals("Decreased")) {
                //         balance = balance - 1;
                //     } else if (results.equals("No change")) {
                //         continue;
                //     }
                //     System.out.println("Balance: "+balance);
                // } while (results.equals("No change"));
                
            
            } else if (this.frequentBets > 1 && lastOdd < 2 && secondLastOdd >= 2 && !lastOdString.equals(odd)) {
                // click bet
                c.clickXpath("/html/body/app-root/app-game/div/div[1]/div[2]/div/div[2]/div[3]/app-bet-controls/div/app-bet-control[1]/div/div[1]/div[2]/button");
                
                // make a bet
                trade++;
                System.out.println("Traded");
                balance = Float.parseFloat(c.getTextXpath("//span[contains(@class, 'amount')]"));
                // sendAmount( balance + "");
                // System.out.println("Balance: "+balance);
                // System.out.println("Number of trades: "+trade);

                // String results = "";

                // do {
                //     results = updateData();
                //     if (results.equals("Increased")) {
                //         balance = balance + 1;
                //     } else if (results.equals("Decreased")) {
                //         balance = balance - 1;
                //     } else if (results.equals("No change")) {
                //         continue;
                //     }
                //     System.out.println("Balance: "+balance);
                // } while (results.equals("No change"));
            }
            
            
            if (!odd.equals("")) {
                this.odds.add(odd);
            }
            sendAmount(balance +"");
            CalcFreq();
            System.out.println("new frequence: "+this.frequentBets);
            clean();

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            
        }
  
    }

    public void run() {
        System.out.println("Getting data...");
        sendStatus("Getting data...");
        getData();
        System.out.println("Logging in...");
        sendStatus("Logging in...");
        login();
        System.out.println("Logged in");
        sendStatus("Logged in");
        System.out.println("Starting aviation ...");
        sendStatus("Starting aviation...");
        if (aviation()) {
            System.out.println("Aviation started ....");
            sendStatus("Aviation started ....");
            System.out.println("Data Collection started....");
            dataCollection();
            System.out.println("betting started....");
            sendStatus("betting started....");
            bet();
            sendStatus("betting done....");
            System.out.println("betting done....");
            this.failure = false;

        }

    }
    
}

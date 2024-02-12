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
 
    public void matriculationDetail(){
        // #CEWFR_RNDR1_eaISCHOOL school
        c.selectElement("id", "CEWFR_RNDR1_eaISCHOOL", data.getString("school"));
        
        // #CEWFR_RNDR1_eaISCHRESIND result submitted grade
        if (data.getString("matricCompleted").toLowerCase().equals("yes")) {
            c.selectElement("id", "CEWFR_RNDR1_eaISCHRESIND", "Final Grade 12");
        } else {
            c.selectElement("id", "CEWFR_RNDR1_eaISCHRESIND", "Final Grade 11");
        }

        String grade = data.getString("highestGrade");
        // if grade == "12":
        if (grade.equals("12")) {
            // #CEWFR_RNDR1_eaIMTREXAMNO matric exam number))
            c.sendId("CEWFR_RNDR1_eaIMTREXAMNO", data.getString("examinationNumber"));

            // #CEWFR_RNDR1_eaIMTRSTAT matric status
            c.selectValue("CEWFR_RNDR1_eaIMTRSTAT", "FE");
        }
        
        // #CEWFR_RNDR1_eaIYSUBJunATun00   CEWFR_RNDR1_eaIYNSCLEVELunATun00
        // #CEWFR_RNDR1_eaIYSUBJunATun01   CEWFR_RNDR1_eaIYNSCLEVELunATun01
        // #CEWFR_RNDR1_eaIYSUBJunATun02   CEWFR_RNDR1_eaIYNSCLEVELunATun02

        // subjects generation
        JSONObject subjects = new JSONObject();
        for (String key : data.keySet()) {
            if (key.startsWith("subject")) {
                if(!data.getString(key).equals("")) {
                    subjects.put(data.getString(key), data.getString("percentage"+ key.charAt(key.length()-1)));
                }
            }
        }

        int count = 0;
        for (String key : subjects.keySet()) {
            c.selectElement("id", "CEWFR_RNDR1_eaIYSUBJunATun0" + count, key);

            // choose level for grade
            String level = "";
            if (0 < Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 20) {
                level = "0";
            } else if (20 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 30) {
                level = "1";
            } else if (30 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 40) {
                level = "2";
            } else if (40 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 50) {
                level = "3";
            } else if (50 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 60) {
                level = "4";
            } else if (60 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 70) {
                level = "5";
            } else if (70 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 80) {
                level = "6";
            } else if (80 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 90) {
                level = "7";
            } else if (90 <= Integer.parseInt(subjects.getString(key)) && Integer.parseInt(subjects.getString(key)) < 100) {
                level = "8";
            }

            c.selectElement("id", "CEWFR_RNDR1_eaIYNSCLEVELunATun0" + count, level);

            count ++;
        }

        // #CEWFR_RNDR1_eaBTNSUBMITun1 click submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void programmeSelection() {
        // #select application year
        c.selectElement("id", "CEWFR_RNDR1_eaIYEAR", data.getString("applicationYear"));

        // #select application period
        c.selectElement("id", "CEWFR_RNDR1_eaISEMESTER", "Whole Year");

        // #select faculty ////////////////////////////////////////////////////////////
        c.selectElement("id", "CEWFR_RNDR1_eaIFCLTY1", data.getString("uwc_faculty1"));

        // #select qualification ///////////////////////////////////////////////////////
        c.selectElement("id", "CEWFR_RNDR1_eaIPROG1", "uwc_course2");
        // #select faculty ////////////////////////////////////////////////////////////
        c.selectElement("id", "CEWFR_RNDR1_eaIFCLTY2", "uwc_faculty1");

        // #select qualification ///////////////////////////////////////////////////////
        c.selectElement("id", "CEWFR_RNDR1_eaIPROG2", "uwc_course2");

        // #click submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void applicantContact(){
        // #fill box number
        c.sendId("CEWFR_RNDR1_eaIPADR1", data.getString("boxnumber"));

        // #city
        c.sendId("CEWFR_RNDR1_eaIPADR4", data.getString("city"));

        // #postal code
        c.sendId("CEWFR_RNDR1_eaIPPCODE", data.getString("postalCode"));

        // #cell number
        c.sendId("CEWFR_RNDR1_eaIPCELL", "0"+data.getString("cellNum"));

        // #select same as postal address
        c.selectValue("CEWFR_RNDR1_eaIADDRTYP", "P");

        // #country
        c.selectValue("CEWFR_RNDR1_eaIPCOUNTRY", "ZAF");

        // #country2
        c.selectValue("CEWFR_RNDR1_eaIRCOUNTRY", "ZAF");
        
        // #click submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void nextofKin(){
        // #select title of next of kin
        c.selectValue("CEWFR_RNDR1_eaINTITLE", "MR");

        // #initials
        c.sendId("CEWFR_RNDR1_eaININITIALS", data.getString("guadianInitials"));

        // #surname
        c.sendId("CEWFR_RNDR1_eaINSURNAME", data.getString("guadianSurname"));

        // #name
        c.sendId("CEWFR_RNDR1_eaINFIRSTNAM", data.getString("guadianName"));

        // #relationship
        c.selectValue("CEWFR_RNDR1_eaIRELSHIP", "G");

        // #select postal address
        c.selectValue("CEWFR_RNDR1_eaIADDRTYP", "P");

        // #click submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void financialInformation(){
        // #select guardian
        c.selectValue("CEWFR_RNDR1_eaIRESPERSON", "2");

        // #select title
        c.selectValue("CEWFR_RNDR1_eaIFTITLE", "MR");

        // #initials
        c.sendId("CEWFR_RNDR1_eaIFINITIALS", data.getString("guadianInitials"));

        // #surname
        c.sendId("CEWFR_RNDR1_eaIFSURNAME", data.getString("guadianSurname"));

        // #name
        c.sendId("CEWFR_RNDR1_eaIFFIRSTNAM", data.getString("guadianName"));

        // Id
        c.sendId("CEWFR_RNDR1_eaIFIDNO", data.getString("guadianID"));

        // #date of birth
        c.sendId("CEWFR_RNDR1_eaIFDATEBIRT", data.getString("dateOfBirth"));

        // #select income range
        String income = data.getString("guadianIncome");

        if (0 < Integer.parseInt(income) && Integer.parseInt(income) < 2000) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "1");
        } else if (2000 <= Integer.parseInt(income) && Integer.parseInt(income) < 6000) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "2");
        } else if (6000 <= Integer.parseInt(income) && Integer.parseInt(income) < 10000) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "3");
        } else if (10000 <= Integer.parseInt(income) && Integer.parseInt(income) < 20000) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "4");
        } else if (20000 <= Integer.parseInt(income) && Integer.parseInt(income) < 40000) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "5");
        } else if (40000 <= Integer.parseInt(income) && Integer.parseInt(income) < 60000) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "6");
        } else if (60000 <= Integer.parseInt(income) && Integer.parseInt(income) < 100000) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "7");
        } else if (100000 <= Integer.parseInt(income)) {
            c.selectValue("CEWFR_RNDR1_eaIANLINCOM", "8");
        }

        // #box number
        c.sendId("CEWFR_RNDR1_eaIFADR1",data.getString("boxnumber"));

        // #city
        c.sendId("CEWFR_RNDR1_eaIFADR4", data.getString("city"));

        // #postal code
        c.sendId("CEWFR_RNDR1_eaIFPCODE", data.getString("postalCode"));

        // #telephon number
        c.sendId("CEWFR_RNDR1_eaIFTEL", data.getString("guadianNumber"));

        // #submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void busary(){
        // "want busary"
        c.clickId("CEWFR_RNDR1_eaIGRNTAPLY_0");

        // #name of busary
        c.clickId("CEWFR_RNDR1_eaIGRANTNAME");

        // #vaue of busary ////////////////////////////////////////////////////////////////
        c.sendId("CEWFR_RNDR1_eaIGRANTAMT", "90000");

        // #click what it covers
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CEWFR_RNDR1_eaIGRANTCOVR_0")));
        WebElement checkbox = driver.findElement(By.id("CEWFR_RNDR1_eaIGRANTCOVR_0"));

        if (!checkbox.isSelected()) {
            c.clickId("CEWFR_RNDR1_eaIGRANTCOVR_0");
            c.clickId("CEWFR_RNDR1_eaIGRANTCOVR_1");
            c.clickId("CEWFR_RNDR1_eaIGRANTCOVR_2");
            c.clickId("CEWFR_RNDR1_eaIGRANTCOVR_3");
            c.clickId("CEWFR_RNDR1_eaIGRANTCOVR_4");
        }

        // #apply for financila aid
        c.clickId("CEWFR_RNDR1_eaIFINAID_0");

        // #foward to sponser
        c.clickId("CEWFR_RNDR1_eaIFWDINFIND_0");

        // #submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void allum() {
        // #submit
        c.clickId("CEWFR_RNDR1_eaBTNSUBMITun1");
    }

    public void conditions() {
        // #CEWFR_RNDR1_eaIACCEPT_1
        c.clickId("CEWFR_RNDR1_eaIACCEPT_1");

        // #submit
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

        try {
            System.out.println("matriculation Detail");
            matriculationDetail();
        } catch (Exception e) {
            System.out.println("matriculation Detail");
            this.driver.navigate().refresh();
            matriculationDetail();
        }

        try {
            System.out.println("programme Selection");
            programmeSelection();
        } catch (Exception e) {
            // TODO: handle exception
            programmeSelection();
        }
    }
}

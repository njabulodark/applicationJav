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

public class Uj {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;

    public Uj() {
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
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        this.driver.get("https://registration.uj.ac.za/pls/prodi41/gen.gw1pkg.gw1startup?x_processcode=ITS_OAP"); 
        System.out.println(this.driver.getTitle());
        
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }

    public void first() {
        System.out.println(this.driver.getTitle());
        System.out.println("first()");

        // already have a student number
        c.selectElement("name", "oapOldNew", "No");

        // returing for application
        c.selectElement( "id", "oapReturnYesNo", "No");

        // click accept rules
        try {
            c.clickXpath("//*[@id='oapAcceptPopi']");
        } catch (Exception e) {
            c.clickId("oapAcceptPopi");
        }
        
        // click next
        c.clickXpath("//*[@id='oapNextBtn1']");
    }

    public void biographical() {
        System.out.println("biographical()");

        // are you a south african citizen
        c.selectElement("name", "oapCitizenType", "Yes");
        
        // enter id number
        c.sendXpath("//input[@id='oapIDnumber']", data.getString("id"));

        // citizinship
        c.clickImageUj("//*[@id=\"LOVHref_14\"]/div/img", "South Africa");
        
        // select gender
        c.selectElement("name", "oapGender", data.getString("gender"));

        // Date of birth
        String dateString = data.getString("dateOfBirth");
        LocalDate date = LocalDate.parse(dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String formattedDate = date.format(formatter);

        c.sendXpath("//input[@id='oapBirthdate']", formattedDate);

        // #select title
        c.selectElement("name", "oapTitle", data.getString("title"));

        // #initials
        c.sendXpath("//input[@id='oapInitials']", data.getString("initials"));

        // #surname
        c.sendXpath("//input[@id='oapSurname']", data.getString("surname"));

        // #first names
        c.sendXpath("//input[@id='oapFirstNames']", data.getString("middleName"));

        // #marital status
        c.selectElement("name", "oapMaritalStatus", "Single");

        // #home language
        c.selectElement("name", "oapHomeLang", data.getString("p_language"));

        // #ethnic group
        c.selectElement("name", "oapEthnic", data.getString("ethnic"));

        // #address line 1
        c.sendXpath("//input[@id='oapStreetAddr1']", data.getString("address1"));

        // #city/town/village
        c.sendXpath("//*[@id='oapStreetAddr2']", data.getString("city"));

        // #click to put in postal code
        c.clickXpath("//*[@id='LOVHref_68']/div/img");
        // #switch to new window
        // Get the window handles as a set
        Set<String> windowHandles = driver.getWindowHandles();

        // Convert the set to a list for indexing
        List<String> handlesList = new ArrayList<>(windowHandles);

        // Switch to the second window (index 1 in zero-based index)
        driver.switchTo().window(handlesList.get(1));

        // #enter postal code
        c.sendName("x_thefilter", data.getString("postalCode"));

        // #click search
        c.clickXpath("//*[@id='searchGrp']/div[2]/input");
        
        // #click on the first result
        c.clickXpath("//*[@id='listOfValues']/table/tbody/tr[2]/td[1]/a");

        // #switch back to main window
        // Get the window handles as a set
        windowHandles = driver.getWindowHandles();

        // Convert the set to a list for indexing
        handlesList = new ArrayList<>(windowHandles);

        // Switch to the second window (index 1 in zero-based index)
        driver.switchTo().window(handlesList.get(0));

        // #phone number
        c.sendXpath("//*[@id='oapSACell']", data.getString("cellNum"));

        // #email address
        c.sendXpath("//*[@id='itsEmail']", data.getString("Email"));
        // #confirm email address
        c.sendXpath("//*[@id='verifyEmail']", data.getString("Email"));

        // #select residential status
        c.selectElement("xpath", "//*[@id='oapResReq']", "Yes");

        if (data.getString("disability") == null || data.getString("disability") == "none" || data.getString("disability") == "non" || data.getString("disability").toLowerCase() == "no") {
            c.clickId("//*[@id=\"oapNextBtn2\"]");
        } else {
            // click disabled
            c.clickId("oapApplyDisability");

            // click disability icon
            c.clickXpath("//*[@id=\"LOVHref_113\"]/div/img");

            // Get the window handles as a set
            windowHandles = driver.getWindowHandles();

            // Convert the set to a list for indexing
            handlesList = new ArrayList<>(windowHandles);

            // Switch to the second window (index 1 in zero-based index)
            driver.switchTo().window(handlesList.get(1));

            // #enter postal code
            c.sendName("x_thefilter", data.getString("disability"));

            // #click search
            c.clickXpath("//*[@id='searchGrp']/div[2]/input");
        
            // #click on the first result
            try {
                c.clickXpath("//*[@id='listOfValues']/table/tbody/tr[2]/td[1]/a");
                
                // #switch back to main window
                // Get the window handles as a set
                windowHandles = driver.getWindowHandles();

                // Convert the set to a list for indexing
                handlesList = new ArrayList<>(windowHandles);

                // Switch to the second window (index 1 in zero-based index)
                driver.switchTo().window(handlesList.get(0));

                // click add disability
                c.clickId("oapAddDisability");
                
            } catch (Exception e) {
                // #switch back to main window
                // Get the window handles as a set
                windowHandles = driver.getWindowHandles();

                // Convert the set to a list for indexing
                handlesList = new ArrayList<>(windowHandles);

                // Switch to the second window (index 1 in zero-based index)
                driver.switchTo().window(handlesList.get(0));

            }

            // click next
            c.clickId("oapNextBtn2");

        }
    }

    public void nextofkin() {
        System.out.println("nextofkin()");
        // #next of kin name
        c.sendXpath("//input[@id=\"oapNokName\"]", data.getString("guadianName"));
        
        // #next of kin phone number
        c.sendXpath("//input[@id=\"oapNokMobileNr\"]", data.getString("guadianNumber"));

        // # accound holder name
        c.sendXpath("//input[@id=\"oapAcntName\"]", data.getString("guadianName"));

        // #adress line 1
        c.sendXpath("//input[@id=\"oapAcntPostalAddr1\"]", data.getString("address1"));

        // #city/town/village
        c.sendId("oapAcntPostalAddr2", data.getString("city"));

        // #click to put in postal code
        c.clickImageUj("//*[@id='LOVHref_26']/div/img", data.getString("postalCode"));

        // #email address
        c.sendId("oapAcntEmail", data.getString("Email"));

        // #click next
        c.clickId("oapNextBtn2_1");

    }

    public void results() {
        System.out.println("results()");
        // #check if field value is empty
        this.driver.navigate().refresh();

        // #matric year
        c.sendId("oapMatYear", data.getString("Matric Year"));

        // #graduate status
        c.selectElement("id", "oapUGPG", "Undergraduate");

        // #are you upgrading
        c.selectElement("id", "oapStudUpgrade", data.getString("matricUpgrading"));

        // #select SA matric
        c.selectValue("oapTypeMatric", "S");

        // click degree
        c.clickImageUj("//*[@id='LOVHref_22']/div/img", "NSC/IEB/ISC/SACAI- Degree");

        // subjects generation
        JSONObject subjects = new JSONObject();
        for (String key : data.keySet()) {
            if (key.startsWith("subject")) {
                if(!data.getString(key).equals("")) {
                    subjects.put(data.getString(key), data.getString("percentage"+ key.charAt(key.length()-1)));
                }
            }
        }

        // submit the results
        for( String subject: subjects.keySet()) {
            // add subject
            try {
                if (subject.toLowerCase().equals("MATHEMATICS".toLowerCase())) {
                    String newSubject = "MATHEMATICS (NSC/NCV/ISC)";
                    c.clickImageUj("//*[@id=\"LOVHref_25\"]/div/img", newSubject);
                }
                else {
                    c.clickImageUj("//*[@id=\"LOVHref_25\"]/div/img", subject);
                }
            } catch (Exception e) {
                // close window 2
                // Get the window handles as a set
                Set<String> windowHandles = driver.getWindowHandles();
                
                // Convert the set to a list for indexing
                List<String> handlesList = new ArrayList<>(windowHandles);

                // Switch to the second window
                driver.switchTo().window(handlesList.get(1));

                // close window
                driver.close();

                // #switch back to main window
                // Get the window handles as a set
                windowHandles = driver.getWindowHandles();

                // Convert the set to a list for indexing
                handlesList = new ArrayList<>(windowHandles);

                // Switch to the second window 
                driver.switchTo().window(handlesList.get(0));
                // if subject has First replace with 1st if Second replace with 2nd if Third replace with 3rd if addition replace with add if language replace with lang
                String[] subjectArray = subject.split(" ");
                String newSubject = "";
                for (String word: subjectArray) {
                    if (word.equals("First")) {
                        newSubject += "1st ";
                    } else if (word.equals("Second")) {
                        newSubject += "2nd ";
                    } else if (word.equals("Third")) {
                        newSubject += "3rd ";
                    } else if (word.equals("addition")) {
                        newSubject += "add ";
                    } else if (word.equals("language")) {
                        newSubject += "lang ";
                    } else {
                        newSubject += word + " ";
                    }
                }

                // cut " " from the end of the string
                newSubject = newSubject.substring(0, newSubject.length() - 1);
                c.clickImageUj("//*[@id=\"LOVHref_25\"]/div/img", newSubject);
            }

            // click NSC
            c.clickImageUj("//*[@id=\"LOVHref_26\"]/div/img", "NSC"); 

            // add percentage
            c.clickImageUj("//*[@id=\"LOVHref_37\"]/div/img", subjects.getString(subject));

            // click add subject
            c.clickId("oapAddMatric");
        }

        // click next
        c.clickId("oapNextBtn3");
    }

    public void previousStudies(){
        System.out.println("previousStudies()");
        // #last school attended
        c.clickImageUj("//*[@id=\"LOVHref_5\"]/div/img", data.getString("school"));

        // WebDriverWait(self.driver, 15).until(EC.presence_of_element_located((By.XPATH, ''))).click()
        try {
            c.clickImageUj("//*[@id=\"LOVHref_6\"]/div/img", "GRADE 12 PUPIL");
        } catch (Exception e) {
            // close window 2
            // Get the window handles as a set
            Set<String> windowHandles = driver.getWindowHandles();
                
            // Convert the set to a list for indexing
            List<String> handlesList = new ArrayList<>(windowHandles);

            // Switch to the second window
            driver.switchTo().window(handlesList.get(1));

            // close window
            driver.close();

            // #switch back to main window
            // Get the window handles as a set
            windowHandles = driver.getWindowHandles();

            // Convert the set to a list for indexing
            handlesList = new ArrayList<>(windowHandles);

            // Switch to the second window 
            driver.switchTo().window(handlesList.get(0));

            c.clickImageUj("//*[@id=\"LOVHref_6\"]/div/img", "UNEMPLOYED");
        }

        // #have you studied in another university
        c.selectElement("id", "oapPrevQualInd", "no");

        boolean control = true;

        while (control) {
            try {
                // #click next
                c.clickId("oapNextBtn4");
            } catch (Exception e) {
                control = false;
            }
        }
    
    }

    public void qualifications() {
        System.out.println("qualifications()");
        for (int i = 0; i < 2; i++) {
            System.out.println("qualifications");
                
            c.clickId("ITS_OAP");

            
            
            if (i == 0) {
                c.clickId("ITS_OAP");
                
                // select academic year
                c.selectElement("xpath", "//*[@id=\"oapAcademicYear\"]", data.getString("applicationYear"));
                // select course type //////// to be changed
                c.selectElement("xpath", "//*[@id=\"oapECSLP\"]", "Short Learning Programs (SLP)");
                // select faculty /////////////////////////////////////////////////////////////////
                c.clickImageUj("//*[@id=\"LOVHref_11\"]/div/img", data.getString("uj_faculty1"));
                
                // search the course
                c.clickImageUj("//*[@id=\"LOVHref_12\"]/div/img", data.getString("uj_course1"));
            } else {
                // go to another window
                // #switch back to main window
                // Get the window handles as a set
                Set<String> windowHandles = driver.getWindowHandles();

                // Convert the set to a list for indexing
                List<String> handlesList = new ArrayList<>(windowHandles);

                // Switch to the second window 
                driver.switchTo().window(handlesList.get(0));

                this.driver.navigate().refresh();


                c.clickId("ITS_OAP");
                
                // select academic year
                c.selectElement("xpath", "//*[@id=\"oapAcademicYear\"]", data.getString("applicationYear"));
                this.driver.navigate().refresh();
                c.selectElement("xpath", "//*[@id=\"oapAcademicYear\"]", data.getString("applicationYear"));
                this.driver.navigate().refresh();
                c.selectElement("xpath", "//*[@id=\"oapAcademicYear\"]", data.getString("applicationYear"));
                c.clickId("ITS_OAP");

                // select course type //////// to be changed
                c.selectElement("xpath", "//*[@id=\"oapECSLP\"]", "Short Learning Programs (SLP)");

                // select faculty /////////////////////////////////////////////////////////////////
                c.clickImageUj("//*[@id=\"LOVHref_11\"]/div/img", data.getString("uj_faculty2"));

                // search the course
                c.clickImageUj("//*[@id=\"LOVHref_12\"]/div/img", data.getString("uj_course2"));
            }
            // select year of study
            c.clickImageUj("//*[@id=\"LOVHref_13\"]/div/img", "FIRST YEAR");
        
            // #click add qualification
            c.clickXpath("//*[@id=\"oapAddQual\"]");
        
        }
        // #click next
        c.clickId("oapNextBtn6");
    }
    
    public void run() {

        try {
            first();
        } catch (Exception e) {
            this.driver.navigate().refresh();
            first();
        }
        
        try {
            biographical();
        } catch (Exception e) {
            this.driver.navigate().refresh();
            biographical();
        }

        try {
            nextofkin();
        } catch (Exception e) {
            this.driver.navigate().refresh();
            nextofkin();
        }

        try {
            results();
        } catch (Exception e) {
            // this.driver.navigate().refresh();
            // results();
            System.out.println("AN ERROR HAPPENED IN RESULTS()");
        }

        try {
            previousStudies();
        } catch (Exception e) {
            this.driver.navigate().refresh();
            previousStudies();
        }

        try {
            qualifications();
        } catch (Exception e) {
            this.driver.navigate().refresh();
            qualifications();
        }
    }
}

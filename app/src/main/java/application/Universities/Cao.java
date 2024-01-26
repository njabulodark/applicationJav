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
import java.util.List;
import java.time.Duration;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Cao {
    private WebDriver driver;
    private JSONObject data;
    private WebDriverWait wait;
    private Components c;

    public Cao() {
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
        this.driver = new ChromeDriver();
        this.driver.get("https://www.cao.ac.za/IDLookup.aspx"); 
        
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(12));
        this.c = new Components(this.driver);
    }

    public void first() {
        System.out.println(driver.getTitle());
        WebElement idInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ContentPlaceHolder1_test")));
        idInput.sendKeys(data.getString("id"));

        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ContentPlaceHolder1_btnSubmit")));
        submitButton.click();
        
        c.clickXpath("//*[contains(text(),'Continue with Application')]");

        // first name
        c.sendId("ctl00_ContentPlaceHolder1_txtFirstName", data.getString("firstName"));

        // surname
        c.sendId("ctl00_ContentPlaceHolder1_txtSurname", data.getString("surname"));
    
        // select title
        c.select("ctl00_ContentPlaceHolder1_cbTitle", data.getString("title"));

        // email
        c.sendId("ctl00_ContentPlaceHolder1_txtEmail", data.getString("Email"));

        // postal code
        c.sendEnter("ctl00_ContentPlaceHolder1_txtPostCode", data.getString("postalCode"));

        // select surbub
        c.selectIndex("ctl00_ContentPlaceHolder1_cbSuburb", 1);

        // box number
        c.sendId("ctl00_ContentPlaceHolder1_txtPoBox", data.getString("boxnumber"));

        // cellphone
        c.sendId("ctl00_ContentPlaceHolder1_txtCellphone", data.getString("cellNum"));

        // tick conditions
        c.clickId("ctl00_ContentPlaceHolder1_chkTermsAndConditions");
        
        // tick conditions
        c.clickId("ctl00_ContentPlaceHolder1_chkApplicantDeclaration");

        // click next
        c.clickId("ctl00_ContentPlaceHolder1_btnSubmit");

        // click text continu
        c.clickXpath("//*[contains(text(),'Continue with online application')]");

        String cao_number = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblCAONumber"))).getText();
        String password = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblPassword"))).getText();

        // Specify the file name
        String fileName = "account.txt";

        try {
            // Create a FileWriter object to write to the file
            FileWriter fileWriter = new FileWriter(fileName);

            // Write the CAO number and password to the file
            fileWriter.write(cao_number + "\n");
            fileWriter.write(password);

            // Close the FileWriter to save and close the file
            fileWriter.close();

            System.out.println("Data has been written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // click continue
        c.clickXpath("//*[contains(text(),'Continue with online application')]");

    }

    public void login() {
        System.out.println(driver.getTitle());

        this.driver.get("https://www.cao.ac.za/login.aspx");

        String filePath = "account.txt";
        String caoNumber= "";
        String password = "";

        try {
            // Create a FileReader and BufferedReader to read from the file
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read the lines from the file
            caoNumber = bufferedReader.readLine();
            password = bufferedReader.readLine();

            // Close the BufferedReader
            bufferedReader.close();

            // Remove newline characters if needed
            caoNumber = caoNumber.trim();
            password = password.trim();

            System.out.println("CAO Number: " + caoNumber);
            System.out.println("Password: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.sendId("ctl00_ContentPlaceHolder1_LoginControl_UserName", caoNumber);
        c.sendId("ctl00_ContentPlaceHolder1_LoginControl_Password", password);


        // click login
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ctl00_ContentPlaceHolder1_LoginControl_LoginButton"))).click();
        c.clickId("ctl00_ContentPlaceHolder1_LoginControl_LoginButton");


        // click text continu
        c.clickXpath("//*[contains(text(),'Continue your online application')]");

    }

    public void bio() {
        System.out.println(driver.getTitle());
        // tick nsfas
        c.clickId("ctl00_ContentPlaceHolder1_chkbxFinAid");

        // select gender
        c.select("ctl00_ContentPlaceHolder1_ddlGender", data.getString("gender"));

        // marital status
        c.selectValue("ctl00_ContentPlaceHolder1_ddlMaritalStatus", "S");

        // population group
        c.select("ctl00_ContentPlaceHolder1_ddlPopulationGroup", data.getString("race"));

        // home language
        c.select("ctl00_ContentPlaceHolder1_ddlHomeLanguage", c.closest(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ddlHomeLanguage"))), data.getString("p_language")));

        // currently doing
        c.select("ctl00_ContentPlaceHolder1_ddlActivity", "Other");

        // disability
        try {
            c.selectClosest("ctl00_ContentPlaceHolder1_ddlDisability", "disability");
        } catch (Exception e) {
            //
            System.out.println("Failed to select disability");
            c.select("ctl00_ContentPlaceHolder1_ddlDisability", "Other");
        }

        // click next
        c.clickId("ctl00_ContentPlaceHolder1_btnNext");
    }

    public void nextOfKin() {
        System.out.println(driver.getTitle());
        // first name
        c.sendId("ctl00_ContentPlaceHolder1_txtFirstName", data.getString("guadianName"));

        // surname
        c.sendId("ctl00_ContentPlaceHolder1_txtSurname", data.getString("guadianSurname"));

        // title
        c.select("ctl00_ContentPlaceHolder1_cbTitle", data.getString("guadiantitle"));
        
        // relationship
        c.select("ctl00_ContentPlaceHolder1_cbRelationship", data.getString("guadian"));

        // postal code
        c.sendEnter("ctl00_ContentPlaceHolder1_txtPostCode", data.getString("postalCode"));

        // select suburb
        c.selectIndex("ctl00_ContentPlaceHolder1_cbSuburb", 2);

        
        // street address
        try {
            // sleeep 5 seconds
            Thread.sleep(2000);
            c.sendCss("input[id*='ctl00_ContentPlaceHolder1_txtAddress1']", data.getString("streatAddress"));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Failed to enter street address");
        }

        // save html to file
        String html = driver.getPageSource();

        try (FileWriter file= new FileWriter("aa.html")){
            file.write(html);
            System.out.println("Successfully Copied HTML Object to File...");
        } catch (IOException e) {
            System.out.println("Failed to write to file");
        }

        // phone number
        c.sendId("ctl00_ContentPlaceHolder1_txtCellphone", data.getString("guadianNumber"));
        
        try {
            if (data.getString("guadian").toLowerCase() == "father"){
                // select title
                c.select( "ctl00_ContentPlaceHolder1_ddlFatherTitle", "Mr");
                
                // Surname
                c.sendId("ctl00_ContentPlaceHolder1_txtFatherSurname", data.getString("guadianSurname"));
                
                // first name
                c.sendId("ctl00_ContentPlaceHolder1_txtFatherFirstname", data.getString("guadianName"));
                
                // id number
                c.sendId("ctl00_ContentPlaceHolder1_txtFatherIdNumber", data.getString("guadianID"));
                
                // cellphone
                c.sendId("ctl00_ContentPlaceHolder1_txtFatherCellphone", data.getString("guadianNumber"));
                
            } else if (data.getString("guadian").toLowerCase() == "mother") {
                // select title
                c.select( "ctl00_ContentPlaceHolder1_ddlMotherTitle", "Ms");
                
                // Surname
                c.sendId("ctl00_ContentPlaceHolder1_txtMotherSurname", data.getString("guadianSurname"));
                
                // first name
                c.sendId("ctl00_ContentPlaceHolder1_txtMotherFirstname", data.getString("guadianName"));
                
                // id number
                c.sendId("ctl00_ContentPlaceHolder1_txtMotherIdNumber", data.getString("guadianID"));
                
                // cellphone
                c.sendId("ctl00_ContentPlaceHolder1_txtMotherCellphone", data.getString("guadianNumber"));
            }
            else {
                // select title
                c.sendId("ctl00_ContentPlaceHolder1_txtGuardianTitle", data.getString("guadiantitle"));
                
                // Surname
                c.sendId("ctl00_ContentPlaceHolder1_txtGuardianSurname", data.getString("guadianSurname"));
                
                // first name
                c.sendId("ctl00_ContentPlaceHolder1_txtGuardianFirstname", data.getString("guadianName"));
                
                // id number
                c.sendId("ctl00_ContentPlaceHolder1_txtGuardianIdNumber", data.getString("guadianID"));
                
                // cellphone
                c.sendId("ctl00_ContentPlaceHolder1_txtGuardianCellphone", data.getString("guadianNumber"));
            }
        } catch( Exception e) {
            //
        }

        // next
        c.clickId("ctl00_ContentPlaceHolder1_btnNext");
    }

    public void school() {
        System.out.println(driver.getTitle());

        //System.out.println(html);
        
        try {
            // school name
            c.sendId("ctl00_ContentPlaceHolder1_txtSchoolSearch", data.optString("school"));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Failed to enter school name");
        }

        // search
        c.clickId("ctl00_ContentPlaceHolder1_btnSearch");

        // add
        c.clickXpath("//a[contains(text(),'Add')]");

        // school start year
        c.sendId("ctl00_ContentPlaceHolder1_gvSelectedSchools_ctl02_txtFromYear", data.getString("schoolStartYear"));

        // school end year
        c.sendId("ctl00_ContentPlaceHolder1_gvSelectedSchools_ctl02_txtToYear", data.getString("schoolEndYear"));

        // next
        c.clickId("ctl00_ContentPlaceHolder1_btnNext");
    }

    public void results() {
        System.out.println(driver.getTitle());
        // matric year
        c.sendId("ctl00_ContentPlaceHolder1_gvExamTypes_ctl07_txtExamYear", data.getString("Matric Year"));

        // province
        c.selectClosest("ctl00_ContentPlaceHolder1_gvExamTypes_ctl07_ddlExamAuth", data.getString("province"));

        // examination number
        c.sendId("ctl00_ContentPlaceHolder1_gvExamTypes_ctl07_txtExamNo", data.getString("examinationNumber"));

        // #upgrading
        if (data.getString("matricUpgrading").toLowerCase() != "no"){
            c.clickId("ctl00_ContentPlaceHolder1_gvExamTypes_ctl07_chkbxRewrite");
        }

        // next
        c.clickId("ctl00_ContentPlaceHolder1_btnNext");
    }

    public void course() {
        // get page title
        System.out.println(driver.getTitle());

        List<String> courses = new ArrayList<>();
        courses.add("B Sc Augmented (Biochemistry and Botany) - University of Zululand");

        for (String course : courses) {
            // search
            c.sendId("ctl00_ContentPlaceHolder1_txtProgrammeName", course.substring(0, course.indexOf('-') - 1));
            c.clickId("ctl00_ContentPlaceHolder1_btnSearch");

            // add
            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_gvSearchResults']/tbody")));
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                if (row.getText().contains(course.substring(course.indexOf('-') + 2))) {
                    row.findElement(By.xpath("//a[contains(text(),'Add')]")).click();
                    break;
                }
            }

        }

    }


    public void run() {
        // first();
        login();
        System.out.println("Bio:");
        bio();
        System.out.println("Next of Kin:");
        nextOfKin();
        System.out.println("School:");
        school();
        System.out.println("Results:");
        results();
        System.out.println("Course:");
        course();
    }
}

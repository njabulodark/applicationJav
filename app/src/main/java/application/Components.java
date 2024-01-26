package application;

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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.Duration;
import java.util.stream.Collectors;

import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Components {
    private WebDriver driver;
    private WebDriverWait wait;
    
    public Components(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(20));
    }

    public String closest(WebElement select, String value) {
        List<WebElement> options = select.findElements(By.tagName("option"));
        List<String> optionTexts = options.stream().map(WebElement::getText).collect(Collectors.toList());

        int minDistance = Integer.MAX_VALUE;
        String closestMatch = null;

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        for (String optionText : optionTexts) {
            int distance = levenshteinDistance.apply(value.toLowerCase(), optionText.toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = optionText;
            }
        }

        return closestMatch;
    }

    public void closestATag(WebElement select, String value) {
        List<WebElement> aTag = select.findElements(By.tagName("a"));
        List<String> aTexts = aTag.stream().map(WebElement::getText).collect(Collectors.toList());

        int minDistance = Integer.MAX_VALUE;
        String closestMatch = null;

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        for (String optionText : aTexts) {
            int distance = levenshteinDistance.apply(value.toLowerCase(), optionText.toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = optionText;
            }
        }

        // find the index of the closest match
        int index = aTexts.indexOf(closestMatch);

        // click the closest match
        aTag.get(index).click();
    }

    public void clickName(String key) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(key))).click();
    }

    public void clickXpath(String key) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(key)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(key))).click();
    }

    public void clickId(String key) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key))).click();
    }

    public void clickImageUj(String key, String search) {
        // click image
        clickXpath(key);

        // Get the window handles as a set
        Set<String> windowHandles = driver.getWindowHandles();

        // Convert the set to a list for indexing
        List<String> handlesList = new ArrayList<>(windowHandles);

        // Switch to the second window (index 1 in zero-based index)
        driver.switchTo().window(handlesList.get(1));

        // #enter postal code
        sendName("x_thefilter", search);

        // #click search
        clickXpath("//*[@id='searchGrp']/div[2]/input");
        
        // #click on the first result
        // clickXpath("//*[@id='listOfValues']/table/tbody/tr[2]/td[1]/a");

        // click the closest match
        closestATag(driver.findElement(By.id("listOfValues")), search);

        // #switch back to main window
        // Get the window handles as a set
        windowHandles = driver.getWindowHandles();

        // Convert the set to a list for indexing
        handlesList = new ArrayList<>(windowHandles);

        // Switch to the second window (index 1 in zero-based index)
        driver.switchTo().window(handlesList.get(0));
    }

    public void clickCssSelector(String key) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(key)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(key))).click();
    }

    public void clickPartialLinkText(String key) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(key)));
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(key))).click();
    }

    public void sendId(String key, String value) {
        // Wait for the element with the specified id to be present on the page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));

        // Send the input value to the element
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        // element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value);
    }

    public void sendName(String key, String value) {
        // Wait for the element with the specified id to be present on the page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(key)));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(key)));

        // Send the input value to the element
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        // element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value);
    }

    public void sendtagName(String key, String value) {
        // Wait for the element with the specified id to be present on the page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(key)));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(key)));

        // Send the input value to the element
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        // element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value);
    }

    public void sendtagName(String key, String value, int index) {
        // Wait for the element with the specified id to be present on the page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(key)));
        List<WebElement> element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(key)));

        // Send the input value to the element
        element.get(index).sendKeys(Keys.CONTROL + "a");
        element.get(index).sendKeys(Keys.DELETE);
        // element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.get(index).sendKeys(value);
    }

    public void sendCss(String key, String value) {
        // Wait for the element with the specified id to be present on the page
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(key)));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(key)));

        // Send the input value to the element
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        // element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value);
    }
    
    public void sendEnter(String key, String value) {
        // Wait for the element with the specified id to be present on the page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(key)));
        
        // Send the input value to the element
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value + Keys.ENTER);
    }

    public void sendXpath(String key, String value) {
        // Wait for the element with the specified id to be present on the page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(key)));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(key)));
        
        // Send the input value to the element
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        element.sendKeys(value + Keys.ENTER);
    }

    public void select(String key, String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(key)));

        // Create a Select object to work with the dropdown
        Select select = new Select(element);

        // Select the option by visible text
        select.selectByVisibleText(value);
    }

    public void selectElement(WebElement key, String value) {

        // Create a Select object to work with the dropdown
        Select select = new Select(key);

        // Select the option by visible text
        select.selectByVisibleText(closest(key, value));
    }

    public void selectElement(String type ,String key, String value) {
        WebElement element;

        switch (type.toLowerCase()) {
            case "id":
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.id(key)));
                break;
            case "css":
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(key)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(key)));
                break;
            case "xpath":
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(key)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(key)));
                break;
            case "tagname":
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(key)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.tagName(key)));
                break;
            case "classname":
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className(key)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.className(key)));
                break;
            case "name":
                wait.until(ExpectedConditions.presenceOfElementLocated(By.name(key)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.name(key)));
                break;
            default:
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.id(key)));
                break;
        }

        // Create a Select object to work with the dropdown
        Select select = new Select(element);

        // Select the option by visible text
        select.selectByVisibleText(closest(element, value));
    }

    public void selectIndex(String key, int value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(key)));

        // Create a Select object to work with the dropdown
        Select select = new Select(element);

        // Select the option by visible text
        select.selectByIndex(value);
    }

    public void selectValue(String key, String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(key)));

        // Create a Select object to work with the dropdown
        Select select = new Select(element);

        // Select the option by visible text
        select.selectByValue(value);
    }

    public void selectClosest(String key, String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(key)));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(key)));

        // Create a Select object to work with the dropdown
        Select select = new Select(element);

        // Select the option by visible text
        select.selectByVisibleText(closest(element, value));
    }

    public void selectClosestCss(String key, String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(key)));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(key)));

        // Create a Select object to work with the dropdown
        Select select = new Select(element);

        // Select the option by visible text
        select.selectByVisibleText(closest(element, value));
    }



}

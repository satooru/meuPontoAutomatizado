package utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * WebDriverManager takes care of WebDriver (ChromeDriver)
 */
@Deprecated
public class WebDriverManager {

    private static WebDriverManager manager = null;

    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Constructor identifies which OS the application is running on, in order to specify if it should include ".exe" for windows
     * Constructor assumes ChromeDriver is located at src/main/resources/
     */
    public WebDriverManager() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("running on " + os);
        if (os.contains("win"))
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        else if (os.contains("osx") || os.contains("nix") || os.contains("aix") || os.contains("nux"))
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        else
            new Exception("OS desconhecido ao utils.WebDriverManager");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=pt-BR");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 900L);
    }

    public static WebDriverManager getInstance() {
        if (manager == null)
            manager = new WebDriverManager();
        return manager;
    }

    /**
     * returns WebDriver
     * @return WebDriver
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * sets WebDriver
     * @param WebDriver driver
     */
    public void setWebDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * returns Page Title
     * @return String title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * opens page given an address
     * @param String url
     */
    public void openPage(String url) {
        driver.get(url);
    }

    /**
     * closes all tabs and quit browser
     */
    public void quit() {
        driver.quit();
    }

    public void triggerMouseEventAt(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();;
        action.moveToElement(element).click().perform();
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void waitExplicitly(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public void waitPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    private void waitUntilElementIsPresent(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void waitPresenceOfElementById(String id) {
        waitUntilElementIsPresent(By.id(id));
    }

    public void waitPresenceOfElementByName(String name) {
        waitUntilElementIsPresent(By.name(name));
    }

    public void waitPresenceOfElementByXPath(String xPath) {
        waitUntilElementIsPresent(By.xpath(xPath));
    }

    public void waitPresenceOfElementByXPath(String xPath, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
    }

    private void waitUntilElementIsVisible(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void waitVisibilityOfElementById(String id) {
        waitUntilElementIsVisible(By.id(id));
    }

    public void waitVisibilityOfElementByName(String name) {
        waitUntilElementIsVisible(By.name(name));
    }

    public void waitVisibilityOfElementByXPath(String xPath) {
        waitUntilElementIsVisible(By.xpath(xPath));
    }

    public void waitVisibilityOfElementByXPath(String xPath, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xPath))));
    }

    public void waitElementToBeClickableByXPath(String xPath, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(xPath))));
    }

    private void clickElement(WebElement element) {
        element.click();
    }

    public void clickElementById(String id) {
        WebElement element = driver.findElement(By.id(id));
        clickElement(element);
    }

    public void clickElementByName(String name) {
        WebElement element = driver.findElement(By.name(name));
        clickElement(element);
    }

    public void clickElementByXPath(String xPath) {
        WebElement element = driver.findElement(By.xpath(xPath));
        clickElement(element);
    }

    private void inputText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    public void inputTextByElementId(String id, String text) {
        WebElement element = driver.findElement(By.id(id));
        inputText(element,text);
    }

    public void inputTextByElementName(String name, String text) {
        WebElement element = driver.findElement(By.name(name));
        inputText(element, text);
    }

    public void inputTextByElementXPath(String xPath, String text) {
        WebElement element = driver.findElement(By.xpath(xPath));
        inputText(element, text);
    }

    private String getTextFromElement(WebElement element) {
        return element.getText();
    }

    public String getTextFromElementById(String id) {
        WebElement element = driver.findElement(By.id(id));
        return getTextFromElement(element);
    }

    public String getTextFromElementByName(String name) {
        WebElement element = driver.findElement(By.name(name));
        return getTextFromElement(element);
    }

    public String getTextFromElementByXPath(String xpath) throws NoSuchElementException {
        WebElement element = driver.findElement(By.xpath(xpath));
        return getTextFromElement(element);
    }

    public WebElement getWebElementByXPath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public void selectComboIndexByXPath(String xpath, int index) {
        Select combo = new Select( driver.findElement( By.xpath( xpath ) ) );
        combo.selectByIndex( index );
    }
}

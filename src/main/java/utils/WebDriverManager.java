package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * WebDriverManager is a singleton, it assures there is only one instance of WebDriver running
 */

public class WebDriverManager {

    private static WebDriverManager manager = null;

    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    /**
     * Constructor assumes ChromeDriver is located at src/main/resources/
     */
    private WebDriverManager() {
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
        webDriver = new ChromeDriver(options);
        webDriverWait = new WebDriverWait(webDriver, 10);
    }

    public static WebDriverManager getInstance() {
        if (manager == null)
            manager = new WebDriverManager();
        return manager;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public void setWebDriverWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }
}

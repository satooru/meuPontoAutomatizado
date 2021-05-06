package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.WebDriverManager;

public class Page {
    WebDriver driver;
    WebDriverWait wait;

    public Page() {
        driver = WebDriverManager.getInstance().getWebDriver();
        wait = WebDriverManager.getInstance().getWebDriverWait();
    }
}

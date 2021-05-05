package pages;

import utils.WebDriverManager;

import static utils.PropertiesReader.getPontoProperties;

import org.openqa.selenium.WebElement;

public class Login {

    private static WebDriverManager driver = WebDriverManager.getInstance();

    // By - WebElements
    static String xpathIframe = "//iframe";
    static String xpathIframeMainLogin = "//frame[@id = 'MainLogin']";
    static String xpathLogin = "//form//input[@id='Login']";
    static String xpathPassword = "//form//input[@id='Senha']";
    static String xpathConectar = "//form//input[@type = 'submit' and @value = 'ENTRAR' ]";

    public static void loginPonto() {
        String url = getPontoProperties("ponto.url");
        String login = getPontoProperties("ponto.login");
        String password = getPontoProperties("ponto.password");

        driver.openPage(url);
        driver.waitPageLoad();

        driver.waitElementToBeClickableByXPath(xpathLogin, 10);
        driver.inputTextByElementXPath(xpathLogin, login);
        driver.inputTextByElementXPath(xpathPassword, password);

        driver.clickElementByXPath(xpathConectar);
        driver.waitPageLoad();
    }
}

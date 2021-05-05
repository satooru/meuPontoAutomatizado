package pages;

import utils.WebDriverManager;

import static utils.PropertiesReader.getPontoProperties;

import org.openqa.selenium.WebElement;

public class Menu {

    private static WebDriverManager driver = WebDriverManager.getInstance();

    // By - WebElements
    static String xpathApontamentoMensal = "//div[@id='conteudo']//a[contains(@href, 'Lancamento')]";

    public static void abrirDigitacaoDeMarcacao() {
        driver.waitElementToBeClickableByXPath(xpathApontamentoMensal, 3);
        driver.clickElementByXPath(xpathApontamentoMensal);
        driver.waitPageLoad();
    }
}

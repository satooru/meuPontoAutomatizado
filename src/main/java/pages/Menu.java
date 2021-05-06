package pages;

import utils.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Menu {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[@id='conteudo']//a[contains(@href, 'Lancamento')]")
    private WebElement linkApontamentoMensal;

    public Menu() {
        driver = WebDriverManager.getInstance().getWebDriver();
        wait = WebDriverManager.getInstance().getWebDriverWait();
        PageFactory.initElements( driver, this);
    }

    public void clickDigitacaoDeMarcacao() {
        wait.until(ExpectedConditions.elementToBeClickable(linkApontamentoMensal));
        linkApontamentoMensal.click();
    }
}

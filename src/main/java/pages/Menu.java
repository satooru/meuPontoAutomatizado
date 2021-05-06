package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Menu extends Page {

    @FindBy(xpath = "//div[@id='conteudo']//a[contains(@href, 'Lancamento')]")
    private WebElement linkApontamentoMensal;

    public Menu() {
        super();
        PageFactory.initElements( driver, this);
    }

    public void clickDigitacaoDeMarcacao() {
        wait.until(ExpectedConditions.elementToBeClickable(linkApontamentoMensal));
        linkApontamentoMensal.click();
    }
}

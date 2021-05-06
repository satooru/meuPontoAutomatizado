package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import domain.Jornada;

public class Apontamentos extends Page {

    @FindBy(xpath= "//div[@id='gridMVC']//table")
    private WebElement tableApontamentos;

    @FindBys({@FindBy(xpath = "//div[@id='gridMVC']//table//tbody//tr")})
    private List<WebElement> tableRowApontamentos;

    public Apontamentos() {
        super();
        PageFactory.initElements(driver, this);
    }

    public boolean apontamentoPresente(Jornada jornada) {
        wait.until(ExpectedConditions.visibilityOf(tableApontamentos));

        //TODO if date from td is after jornada.getDia() then I should break
        for (WebElement tableRow : tableRowApontamentos) {
            if (tableRow.findElements(By.tagName("td")).get(0).getText().equals(jornada.getDia())) {
                System.out.println("dia " + jornada.getDia() + " encontrado nos apontamentos");
                return true;
            }
        }

        System.out.println("dia da jornada nao encontrada nos lancamentos. dia da jornada: " + jornada.getDia());
        return false;
    }
}

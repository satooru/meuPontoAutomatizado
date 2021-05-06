package pages;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import domain.Jornada;

public class Lancamento extends Page {

    @FindBy(xpath = "//form//input[@id='ddtApontamento']")
    private WebElement inputData;

    @FindBy(xpath = "//form//input[@id='nvlEntrada']")
    private WebElement inputEntrada;

    @FindBy(xpath = "//form//input[@id='nvlAlmoco']")
    private WebElement inputInicioAlmoco;

    @FindBy(xpath = "//form//input[@id='nvlRetorno']")
    private WebElement inputFimAlmoco;

    @FindBy(xpath = "//form//input[@id='nvlSaida']")
    private WebElement inputSaida;

    @FindBy(xpath = "//form//select[@id='ccdComposto']")
    private WebElement selectProjeto;

    @FindBy(xpath = "//form//input[@type='submit' and @value='Gravar e sair']")
    private WebElement buttonGravar;

    @FindBy(xpath = "//div[@id='exampleModal']//div[@class='modal-body']")
    private WebElement modal;

    @FindBys({@FindBy(xpath = "//div[@id='MensagemGravarContinuar']//label[@id='MensagensERRO']")})
    private List<WebElement> labelAlert;

    @FindBy(xpath = "//form//a[contains(@href, 'Grade')]")
    private WebElement linkGradeApontamentos;

    public Lancamento() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void inputData(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(inputData));
        inputData.clear();
        inputData.sendKeys(date);
    }

    public void inputEntrada(String startOfWork) {
        wait.until(ExpectedConditions.elementToBeClickable(inputEntrada));
        inputEntrada.sendKeys(Keys.HOME);
        inputEntrada.sendKeys(startOfWork);
    }

    public void inputInicioAlmoco(String startOfLunch) {
        wait.until(ExpectedConditions.elementToBeClickable(inputInicioAlmoco));
        inputInicioAlmoco.sendKeys(Keys.HOME);
        inputInicioAlmoco.sendKeys(startOfLunch);
    }

    public void inputFimAlmoco(String endOfLunch) {
        wait.until(ExpectedConditions.elementToBeClickable(inputFimAlmoco));
        inputFimAlmoco.sendKeys(Keys.HOME);
        inputFimAlmoco.sendKeys(endOfLunch);
    }

    public void inputSaida(String endOFWork) {
        wait.until(ExpectedConditions.elementToBeClickable(inputSaida));
        inputSaida.sendKeys(Keys.HOME);
        inputSaida.sendKeys(endOFWork);
    }

    public void selectProjetoIndexOption(int index) {
        wait.until(ExpectedConditions.elementToBeClickable(selectProjeto));
        Select select = new Select(selectProjeto);
        select.selectByIndex(index);
    }

    public void preencherDigitacaoDeMarcacao(Jornada jornada) {

        if (jornada.getDiaLocalDate().getDayOfWeek().getValue() > 5) {
            System.out.println("dia " + jornada.getDia() + " e " + DayOfWeek.of(jornada.getDiaLocalDate().getDayOfWeek().getValue()));
            return;
        }

        inputData(jornada.getDia());
        inputEntrada(jornada.getEntrada());
        inputInicioAlmoco(jornada.getInicioAlmoco());
        inputFimAlmoco(jornada.getFimAlmoco());
        inputSaida(jornada.getSaida());

        selectProjetoIndexOption(1);
    }

    public void clickGravar() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonGravar));
        buttonGravar.click();
    }

    public void validateModalMessage() {
        wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(modal),
                                         ExpectedConditions.visibilityOfAllElements(labelAlert)));
        if (labelAlert.size() > 0 && labelAlert.get(0).isDisplayed()) {
            throw new RuntimeException("Erro ao salvar marcacao. Erro: " + Optional.ofNullable(labelAlert.get(0).getText())
                                                                                   .orElse("[nao foi possivel resgatar o erro da pagina, tente manualmente]"));
        }
        if (!modal.getText().contains("sucesso"))
            throw new RuntimeException("Esperado 'sucesso' no modal do apontamento. mensagem do modal: " + modal.getText());
    }

    public void salvarMarcacao() {
        clickGravar();
        validateModalMessage();
    }
}

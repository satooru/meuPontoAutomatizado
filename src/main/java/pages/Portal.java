package pages;

import utils.PropertiesReader;
import utils.WebDriverManager;

import static utils.PropertiesReader.getPontoProperties;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import domain.Jornada;

public class Portal {

    private static WebDriverManager driver = WebDriverManager.getInstance();

    private static String apontamentosPath = "/Apontamento/Grade";

    // By - WebElements
    // Lancamento
    private static String xpathInputData = "//form//input[@id='ddtApontamento']";
    private static String xpathInputEntrada = "//form//input[@id='nvlEntrada']";
    private static String xpathInputInicioAlmoco = "//form//input[@id='nvlAlmoco']";
    private static String xpathInputFimAlmoco = "//form//input[@id='nvlRetorno']";
    private static String xpathInputSaida = "//form//input[@id='nvlSaida']";
    private static String xpathSelectProjeto = "//form//select[@id='ccdComposto']";
    private static String xpathButtonGravar = "//form//input[@type='submit' and @value='Gravar e sair']";
    private static String xpathModal = "//div[@id='exampleModal']//div[@class='modal-body']";
    private static String xpathLabelAlert = "//div[@id='MensagemGravarContinuar']//label[@id='MensagensERRO']";
    // Apontamentos
    private static String xpathTableApontamentos = "//div[@id='gridMVC']//table";

    public static void preencherDigitacaoDeMarcacao() {
        preencherDigitacaoDeMarcacao(new Jornada());
    }

    public static void preencherDigitacaoDeMarcacao(Jornada jornada) {

        if (jornada.getDiaLocalDate().getDayOfWeek().getValue() > 5) {
            System.out.println("dia " + jornada.getDia() + " e " + DayOfWeek.of(jornada.getDiaLocalDate().getDayOfWeek().getValue()));
            return;
        }

        driver.waitVisibilityOfElementByXPath(xpathInputEntrada, 10);

        WebElement inputData = driver.getWebElementByXPath(xpathInputData);
        WebElement inputEntrada = driver.getWebElementByXPath(xpathInputEntrada);
        WebElement inputInicioAlmoco = driver.getWebElementByXPath(xpathInputInicioAlmoco);
        WebElement inputFimAlmoco = driver.getWebElementByXPath(xpathInputFimAlmoco);
        WebElement inputSaida = driver.getWebElementByXPath(xpathInputSaida);

        //TODO melhorar essa parte aqui. ta dando nojo
        if (inputEntrada.getAttribute("readonly") != null
         || inputInicioAlmoco.getAttribute("readonly") != null
         || inputFimAlmoco.getAttribute("readonly") != null
         || inputSaida.getAttribute("readonly") != null) {
            String msg = "um dos campos da marcacao ja estao preenchidos para o dia " + jornada.getDia() + ":\n";
            if (inputEntrada.getAttribute("readonly") != null)
                msg = msg + "    inputEntrada\n";
            if (inputInicioAlmoco.getAttribute("readonly") != null)
                msg = msg + "    inputInicioAlmoco\n";
            if (inputFimAlmoco.getAttribute("readonly") != null)
                msg = msg + "    inputFimAlmoco\n";
            if (inputSaida.getAttribute("readonly") != null)
                msg = msg + "    inputSaida\n";
            System.out.println(msg);
            return;
        }

        inputData.clear();
        inputData.sendKeys(jornada.getDia());
        inputEntrada.sendKeys(Keys.HOME);
        inputEntrada.sendKeys(jornada.getEntrada());
        inputInicioAlmoco.sendKeys(Keys.HOME);
        inputInicioAlmoco.sendKeys(jornada.getInicioAlmoco());
        inputFimAlmoco.sendKeys(Keys.HOME);
        inputFimAlmoco.sendKeys(jornada.getFimAlmoco());
        inputSaida.sendKeys(Keys.HOME);
        inputSaida.sendKeys(jornada.getSaida());

        driver.selectComboIndexByXPath(xpathSelectProjeto, 1);
    }

    public static void salvarMarcacao() {
        driver.waitPresenceOfElementByXPath(xpathButtonGravar, 10);
        driver.clickElementByXPath(xpathButtonGravar);
        driver.waitPageLoad();
        driver.getWait().until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathModal)),
                                                     ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLabelAlert))));
        if (driver.getWebDriver().findElements(By.xpath(xpathLabelAlert)).size() > 0
         && driver.getWebDriver().findElement(By.xpath(xpathLabelAlert)).isDisplayed()) {
            throw new RuntimeException("Erro ao salvar marcacao. Erro: " + Optional.ofNullable(driver.getTextFromElementByXPath(xpathLabelAlert))
                                                                                   .orElse("[nao foi possivel resgatar o erro da pagina, tente manualmente]"));
        }
        if (!driver.getTextFromElementByXPath(xpathModal).contains("sucesso"))
            throw new RuntimeException("Esperado 'sucesso' no modal do apontamento. mensagem do modal: " + driver.getTextFromElementByXPath(xpathModal));
    }

    public static void salvarLastWorkedDate() {
        PropertiesReader.setPontoProperties("lastWorked.date", LocalDate.now().toString());
    }

    public static boolean apontamentoPresente(Jornada jornada) {
        driver.openPage(getPontoProperties("ponto.url") + apontamentosPath);
        driver.waitVisibilityOfElementByXPath(xpathTableApontamentos);

        List<WebElement> linhas = driver.getWebElementByXPath(xpathTableApontamentos).findElements(By.xpath(".//tbody//tr"));
        for (WebElement linha : linhas) {
            if (linha.findElements(By.tagName("td")).get(0).getText().equals(jornada.getDia())) {
                System.out.println("dia " + jornada.getDia() + " encontrado nos apontamentos");
                return true;
            }
        }

        if (jornada.getDiaLocalDate().getMonthValue() != LocalDate.now().getMonthValue()
         && jornada.getDiaLocalDate().getDayOfWeek().getValue() == 5) {
            System.out.println("jornada foi em outro mes");
            return true;
        }

        System.out.println("dia da jornada nao encontrada nos lancamentos. dia da jornada: " + jornada.getDia());
        return false;
    }
}

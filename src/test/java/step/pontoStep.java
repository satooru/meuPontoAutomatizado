package step;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import domain.Jornada;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.Apontamentos;
import pages.Lancamento;
import pages.Login;
import utils.WebDriverManager;

public class pontoStep {

    WebDriver driver = WebDriverManager.getInstance().getWebDriver();

    Jornada jornada;
    Login loginPage;
    Apontamentos apontamentosPage;
    Lancamento lancamentoPage;

    @Before
    public void setup() {
        jornada = new Jornada();
        loginPage = new Login();
        apontamentosPage = new Apontamentos();
        lancamentoPage = new Lancamento();
    }

    @Dado("que eu realize login no portal de ponto")
    public void queEuRealizeLoginNoPortalDePonto() {
        //TODO change arguments from mvn test
        driver.get(url);
        loginPage.loginPonto(login, password);
    }

    @E("que o ponto não esteja preenchido")
    public void queOPontoNaoEstejaPreenchido() {
        driver.get(url);
        Assert.assertFalse(apontamentosPage.apontamentoPresente(jornada));
    }

    @Quando("preencher o ponto")
    public void preencherOPonto() {
        driver.get(url);
        lancamentoPage.preencherDigitacaoDeMarcacao(jornada);
        lancamentoPage.salvarMarcacao();
    }

    @Então("o apontamento deverá ser contabilizado")
    public void oApontamentoDeveraSerContabilizado() {
        driver.get(url);
        Assert.assertTrue(apontamentosPage.apontamentoPresente(jornada));
    }
}

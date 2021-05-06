package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Login extends Page {

    @FindBy(xpath = "//form//input[@id='Login']")
    private WebElement inputLogin;

    @FindBy(xpath = "//form//input[@id='Senha']")
    private WebElement inputSenha;

    @FindBy(xpath = "//form//input[@type = 'submit' and @value = 'ENTRAR' ]")
    private WebElement buttonEntrar;

    public Login() {
        super();
        PageFactory.initElements( driver, this);
    }

    public void inputLogin(String login) {
        wait.until(ExpectedConditions.elementToBeClickable(inputLogin));
        inputLogin.sendKeys(login);
    }

    public void inputSenha(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(inputSenha));
        inputSenha.sendKeys(password);
    }

    public void clickEntrar() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonEntrar));
        buttonEntrar.click();
    }

    public void loginPonto(String login, String password) {
        inputLogin(login);
        inputSenha(password);
        clickEntrar();
    }
}

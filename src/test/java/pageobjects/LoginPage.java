package pageobjects;

import configs.Configs;
import io.qameta.allure.Step;
import model.User;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By mail = By.xpath("//label[text()='Email']/parent::div/input");
    private final By password = By.xpath("//label[text()='Пароль']/parent::div/input");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerButton = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By restorePasswordButton = By.xpath(".//a[text()='Восстановить пароль']");

    public LoginPage fillMail(String input){   driver.findElement(mail).sendKeys(input);  return this;  }
    public LoginPage fillPassword(String input){   driver.findElement(password).sendKeys(input); return this;   }
    public RegistrationPage clickRegisterButton(){  driver.findElement(registerButton).click(); return new RegistrationPage(driver); }
    public PasswordRestorePage clickRestorePasswordButton(){  driver.findElement(restorePasswordButton).click(); return new PasswordRestorePage(driver); }

    public BasePage clickLoginButton() { driver.findElement(loginButton).click();  return this;  }

    //проверка находимся ли мы на ЭФ входа
    public boolean onLoginPageNow(){
        return driver.getCurrentUrl().equals(Configs.SITE_URL + Configs.LOGIN_PAGE_URL_PART);
    }

    public LoginPage waitLoginButtonIsVisible(){
        waitLoaderIsHidden();
        waitButtonIsAvailable(loginButton);
        return this;
    }

    @Step("Проверка элементов ЭФ входа")
    public LoginPage checkLoginPage(){
        Assert.assertTrue(onLoginPageNow());
        Assert.assertTrue(driver.findElement(mail).isDisplayed());
        Assert.assertTrue(driver.findElement(password).isDisplayed());
        Assert.assertTrue(driver.findElement(registerButton).isDisplayed());
        Assert.assertTrue(driver.findElement(loginButton).isDisplayed());
        return this;
    }


    @Step("заполнение полей данными зарегистрированного пользователя, вход")
    public LoginPage login(User user){
        this.fillMail(user.getEmail())
                .fillPassword(user.getPassword())
                .clickLoginButton()
                .waitLoaderIsHidden();
        return this;
    }

    @Step("переход на форму регистрации")
    public RegistrationPage goToRegistrationForm(){
        driver.findElement(registerButton).click();
        return new RegistrationPage(driver);
    }
}



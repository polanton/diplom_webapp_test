package pageobjects;

import io.qameta.allure.Step;
import model.User;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) { super(driver);   }


    private final By name = By.xpath("//label[text()='Имя']/parent::div/input");
    private final By mail = By.xpath("//label[text()='Email']/parent::div/input");
    private final By password = By.xpath("//label[text()='Пароль']/parent::div/input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginButton = By.className("Auth_link__1fOlj");
    private final By badPasswordNotification = By.xpath("//p[text()='Некорректный пароль']");

    public RegistrationPage fillName(String input){   driver.findElement(name).sendKeys(input);  return this;  }
    public RegistrationPage fillMail(String input){   driver.findElement(mail).sendKeys(input);  return this;  }
    public RegistrationPage fillPassword(String input){   driver.findElement(password).sendKeys(input); return this;   }
    public LoginPage clickRegisterButton(){
        waitLoaderIsHidden();
        driver.findElement(registerButton).click();
        return new LoginPage(driver); }

    public LoginPage clickLoginButton(){
        waitLoaderIsHidden();
        driver.findElement(loginButton).click();
        return new LoginPage(driver);
    }

    @Step("Проверка элементов ЭФ регистрации")
    public RegistrationPage checkRegistrationPage(){
        Assert.assertTrue(driver.findElement(name).isDisplayed());
        Assert.assertTrue(driver.findElement(mail).isDisplayed());
        Assert.assertTrue(driver.findElement(password).isDisplayed());
        Assert.assertTrue(driver.findElement(registerButton).isDisplayed());
        Assert.assertTrue(driver.findElement(loginButton).isDisplayed());
        return this;
    }

    @Step("Заполнение полей формы регистрации и выполение регистрации")
    public LoginPage registerUser(User user){
        this.fillName(user.getName())
                .fillMail(user.getEmail())
                .fillPassword(user.getPassword())
                .clickRegisterButton();
        return new LoginPage(driver);
    }



    @Step("проверка отображения ошибки при вводе пароля менее 6 символов")
    public void checkResultOfRegistrationWithShortPassword(){
        Assert.assertTrue(driver.findElement(badPasswordNotification).isDisplayed());
    }

}

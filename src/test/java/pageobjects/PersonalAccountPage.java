package pageobjects;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonalAccountPage extends BasePage {

    public PersonalAccountPage(WebDriver driver) { super(driver);   }

    private final By name = By.xpath("//label[text()='Имя']/parent::div/input");
    private final By mail = By.xpath("//label[text()='Логин']/parent::div/input");
    private final By password = By.xpath("//label[text()='Пароль']/parent::div/input");
    private final By exitButton = By.xpath("//button[text()='Выход']");
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    private final By centralLogoButton = By.className("AppHeader_header__logo__2D0X2");


    public PersonalAccountPage fillName(String input){   driver.findElement(name).sendKeys(input);  return this;  }
    public PersonalAccountPage fillMail(String input){   driver.findElement(mail).sendKeys(input);  return this;  }
    public PersonalAccountPage fillPassword(String input){   driver.findElement(password).sendKeys(input); return this;   }

    public LoginPage clickExitButton(){
        waitLoaderIsHidden();
        driver.findElement(exitButton).click();
        LoginPage loginPageObj = new  LoginPage(driver);
        loginPageObj.waitLoginButtonIsVisible();
        return new LoginPage(driver); }

    @Step("Нажатие кнопки 'Конструктор' в личном кабинете")
    public MainPage clickConstructorButton(){
        waitLoaderIsHidden();
        driver.findElement(constructorButton).click();
        return new MainPage(driver); }

    @Step("Нажатие на логотип Stellar Burgers в личном кабинете")
    public MainPage clickCentralLogoButton(){
        waitLoaderIsHidden();
        driver.findElement(centralLogoButton).click();
        return new MainPage(driver); }

    @Step("Проверка состава ЭФ 'личный кабинет'")
    public void checkPersonalAccountPage(){
        Assert.assertTrue(driver.findElement(name).isDisplayed());
        Assert.assertTrue(driver.findElement(mail).isDisplayed());
        Assert.assertTrue(driver.findElement(password).isDisplayed());
        Assert.assertTrue(driver.findElement(exitButton).isDisplayed());
        Assert.assertTrue(driver.findElement(constructorButton).isDisplayed());
        Assert.assertTrue(driver.findElement(centralLogoButton).isDisplayed());
    }
}

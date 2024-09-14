package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class BasePage {
    final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage asLoginPage()
    {
        return new LoginPage(driver);
    }
    public MainPage asMainPage()
    {
        return new MainPage(driver);
    }
    public PersonalAccountPage asPersonalAccountPage(){
        return new PersonalAccountPage(driver);
    }
    public RegistrationPage asRegistrationPage(){
        return new RegistrationPage(driver);
    }

    private final By loader = By.className("Modal_modal_overlay__x2ZCr");

    public BasePage waitLoaderIsHidden() {
        new WebDriverWait(driver, Duration.ofSeconds(25)).until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(loader)));
        new WebDriverWait(driver, Duration.ofSeconds(25)).until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(loader)));
        return this;
    }

    public BasePage waitButtonIsAvailable(By by) {
        new WebDriverWait(driver, Duration.ofSeconds(37)).until(elementToBeClickable(by));
        return this;
    }


}

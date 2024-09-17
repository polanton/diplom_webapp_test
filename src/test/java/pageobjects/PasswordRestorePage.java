package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRestorePage extends BasePage {

    public PasswordRestorePage(WebDriver driver) { super(driver);   }
        private final By mail = By.xpath("//label[text()='Email']/parent::div/input");
        private final By restoreButton = By.xpath("//button[text()='Восстановить']");
        private final By loginButton = By.className("Auth_link__1fOlj");

        public PasswordRestorePage fillMail(String input){   driver.findElement(mail).sendKeys(input);  return this;  }
        public BasePage clickRestoreButton(){driver.findElement(restoreButton).click(); return this;}
        public LoginPage clickLoginButton(){driver.findElement(loginButton).click(); return new LoginPage(driver);}

}

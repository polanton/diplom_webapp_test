
import configs.Configs;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;
import utils.Generate;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class LoginTests {

    String driverName;

    public LoginTests( String driverName) {
        this.driverName = driverName;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"chrome"},
                {"firefox"}
        };
    }


    User user;
    WebDriver driver;

    @Before
    public void setUp(){
        RestAssured.baseURI = Configs.BASE_API_URL;
        driver = Generate.webDriver(driverName);
        assert driver != null;
        driver.get(Configs.SITE_URL);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        user = Generate.user();
        User.registerByAPI(user);
    }

    @DisplayName("вход по кнопке «Войти в аккаунт» на главной")
    @Test
    public void loginFromMainPageIsSuccessful() {
        MainPage mainPageObj =  new MainPage(driver);
        mainPageObj.clickLoginButton()
                .login(user)
                .asMainPage().checkMainPageElementsVisibilityAfterAuthorisation();
    }

    @DisplayName("вход через кнопку «Личный кабинет»")
    @Test
    public void loginFromPersonalAccountPageIsSuccessful() {
        MainPage mainPageObj =  new MainPage(driver);
        mainPageObj.clickPersonalAccountButton()
                .asLoginPage()
                .login(user)
                .asMainPage().checkMainPageElementsVisibilityAfterAuthorisation();
    }

    @DisplayName("вход через кнопку в форме регистрации")
    @Test
    public void loginFromRegistrationPageIsSuccessful() {
        MainPage mainPageObj =  new MainPage(driver);
        mainPageObj.clickPersonalAccountButton()
                .asLoginPage()
                .clickRegisterButton()
                .clickLoginButton()
                .login(user)
                .asMainPage().checkMainPageElementsVisibilityAfterAuthorisation();
    }

    @DisplayName("вход через кнопку в форме восстановления пароля")
    @Test
    public void loginFromPasswordRestorePageIsSuccessful() {
        MainPage mainPageObj =  new MainPage(driver);
        mainPageObj.clickLoginButton()
                .clickRestorePasswordButton()
                .clickLoginButton()
                .login(user)
                .asMainPage().checkMainPageElementsVisibilityAfterAuthorisation();

    }



    @After
    public  void tearDown(){
        driver.quit();
        user.delete();

    }
}

import configs.Configs;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.MainPage;
import utils.Generate;
import utils.SendRequest;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

    @RunWith(Parameterized.class)
    public class RegistrationTests {

        String driverName;

        public RegistrationTests( String driverName) {
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
        boolean userWasCreated = false;

        @Before
        public void setUp(){
            RestAssured.baseURI = Configs.BASE_API_URL;
            driver = Generate.webDriver(driverName);
            assert driver != null;
            driver.get(Configs.SITE_URL);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            user = Generate.user();
        }

        @DisplayName("Успешная регистрация и вход")
        @Test
        public void registrationPositiveScenario() {
            MainPage mainPageObj =  new MainPage(driver);
            mainPageObj.clickPersonalAccountButton()
                    .asLoginPage().clickRegisterButton()
                    .checkRegistrationPage()
                    .registerUser(user)
                    .waitLoginButtonIsVisible()
                    .checkLoginPage()
                    .login(user)
                    .asMainPage()
                    .checkMainPageElementsVisibilityAfterAuthorisation();
            userWasCreated = true;
        }

        @DisplayName("Попытка регистрации пользователя с некорректным паролем")
        @Test
        public void registrationWithShortPasswordDisplaysError() {
            user = Generate.userWithShortPassword();
            MainPage mainPageObj =  new MainPage(driver);
            mainPageObj.clickPersonalAccountButton()
                    .asLoginPage().clickRegisterButton()
                    .checkRegistrationPage()
                    .registerUser(user)
                    .asRegistrationPage()
                    .checkResultOfRegistrationWithShortPassword();
        }


        @After
        public  void tearDown(){
            driver.quit();
            if (userWasCreated) {
                user.delete();
            }
            userWasCreated = false;
        }
}

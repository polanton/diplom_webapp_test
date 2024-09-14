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
    public class PersonalAccountTests {

        String driverName;

        public PersonalAccountTests(String driverName) {
            this.driverName = driverName;
        }

        @Parameterized.Parameters
        public static Object[][] getTestData() {
            return new Object[][]{
                    {"chrome"},
                    {"firefox"}
            };
        }


        User user;
        WebDriver driver;

        @Before
        public void setUp() {
            RestAssured.baseURI = Configs.BASE_API_URL;
            driver = Generate.webDriver(driverName);
            assert driver != null;
            driver.get(Configs.SITE_URL);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

            user = Generate.user();
            User.registerByAPI(user);
        }

        @DisplayName("Переход в личный кабинет без авторизации")
        @Test
        public void withoutAuthorisationLoginPageIsOpened() {
            MainPage mainPageObj = new MainPage(driver);
            mainPageObj.clickPersonalAccountButton()
                    .asLoginPage().checkLoginPage();
        }

        @DisplayName("Переход в личный кабинет после авторизации")
        @Test
        public void withAuthorisationPersonalAccountOpens() throws InterruptedException {
            MainPage mainPageObj = new MainPage(driver);
            mainPageObj.clickPersonalAccountButton()
                    .asLoginPage().login(user)
                    .asMainPage().checkMainPageElementsVisibilityAfterAuthorisation()
                    .waitLoaderIsHidden()
                    .asMainPage().clickPersonalAccountButton()
                    .asPersonalAccountPage().checkPersonalAccountPage();



        }

        @DisplayName("Переход в конструктор по кнопке")
        @Test
        public void constructorOpensByButton() {
            MainPage mainPageObj = new MainPage(driver);
            mainPageObj.clickPersonalAccountButton()
                    .asLoginPage().login(user)
                    .asMainPage().checkMainPageElementsVisibilityAfterAuthorisation()
                    .waitLoaderIsHidden()
                    .asMainPage().clickPersonalAccountButton()
                    .waitLoaderIsHidden()
                    .asPersonalAccountPage().clickConstructorButton()
                    .checkMainPageElementsVisibilityAfterAuthorisation();
        }

        @DisplayName("Переход в конструктор через логотип")
        @Test
        public void constructorOpensByLogo() {
            MainPage mainPageObj = new MainPage(driver);
            mainPageObj.clickPersonalAccountButton()
                    .asLoginPage().login(user)
                    .waitLoaderIsHidden()
                    .asMainPage().checkMainPageElementsVisibilityAfterAuthorisation()
                    .asMainPage().clickPersonalAccountButton()
                    .asPersonalAccountPage().clickCentralLogoButton()
                    .checkMainPageElementsVisibilityAfterAuthorisation();
        }

        @DisplayName("Выход из системы через личный кабинет")
        @Test
        public void onLogoutLoginPageIsDisplayed() {
            MainPage mainPageObj = new MainPage(driver);
            mainPageObj.clickLoginButton()
                    .login(user)
                    .waitLoaderIsHidden()
                    .asMainPage().clickPersonalAccountButton()
                    .asPersonalAccountPage().clickExitButton()
                    .checkLoginPage();
        }

        @After
        public void tearDown() {
            driver.quit();
            user.delete();

        }
    }

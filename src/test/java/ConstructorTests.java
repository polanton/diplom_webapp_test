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
public class ConstructorTests {

    String driverName;

    public ConstructorTests( String driverName) {
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



    @DisplayName("Переход между разделами в списке ингридиентов")
    @Test
    public void byTabClickScrollsToSection() {
        MainPage mainPageObj =  new MainPage(driver);
        mainPageObj.waitLoaderIsHidden()
                .asMainPage().clickSauceTab()
                .checkScrollToSauces()
                .clickBunTab()
                .checkScrollToBuns()
                .clickFillingsTab()
                .checkScrollToFillings();
    }



    @After
    public  void tearDown(){
        driver.quit();
        user.delete();

    }
}

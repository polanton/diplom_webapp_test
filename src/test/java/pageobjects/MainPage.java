package pageobjects;

import configs.Configs;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) { super(driver);   }

    private final String CLICKED_TAB_CLASSNAME = "tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect";
    private final String NOTCLICKED_TAB_CLASSNAME = "tab_tab__1SPyG  pt-4 pr-10 pb-4 pl-10 noselect";

    private final By personalAccountButton = By.xpath("//p[@class='AppHeader_header__linkText__3q_va ml-2' and text()='Личный Кабинет']/parent::a");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By createOrderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By centralLogoButton = By.xpath("//a[@aria-current = 'page' and @class = 'active']");
    private final By burgerIngredientsSection = By.className("BurgerIngredients_ingredients__1N8v2");
    private final By bun = By.xpath("//p[@class = 'BurgerIngredient_ingredient__text__yp3dH' and contains(text(),'булка')]/parent::a");
    private final By sauce = By.xpath("//p[@class = 'BurgerIngredient_ingredient__text__yp3dH' and contains(text(),'Соус')]/parent::a");
    private final By filling = By.xpath("//p[@class = 'BurgerIngredient_ingredient__text__yp3dH' and not(contains(text(),'булка') or contains(text(),'Соус'))]/parent::a");

    private final By notClickedBunTab = By.xpath(String.format("//span[@class = 'text text_type_main-default' and text() = 'Булки']/parent::div[@class=%s]",NOTCLICKED_TAB_CLASSNAME));
    private final By clickedBunTab = By.xpath(String.format("//span[@class = 'text text_type_main-default' and text() = 'Булки']/parent::div[@class=%s]",CLICKED_TAB_CLASSNAME));
    private final By notClickedSauceTab = By.xpath(String.format("//span[@class = 'text text_type_main-default' and text() = 'Соусы']/parent::div[@class=%s]",NOTCLICKED_TAB_CLASSNAME));
    private final By clickedSauceTab = By.xpath(String.format("//span[@class = 'text text_type_main-default' and text() = 'Соусы']/parent::div[@class=%s]",CLICKED_TAB_CLASSNAME));
    private final By notClickedFillingsTab = By.xpath(String.format("//span[@class = 'text text_type_main-default' and text() = 'Начинки']/parent::div[@class=%s]",NOTCLICKED_TAB_CLASSNAME));
    private final By clickedFillingsTab = By.xpath(String.format("//span[@class = 'text text_type_main-default' and text() = 'Начинки']/parent::div[@class=%s]",CLICKED_TAB_CLASSNAME));
    private final By ingredient = By.xpath(".//a[@class='BurgerIngredient_ingredient__1TVf6 ml-4 mr-4 mb-8']");
    private final By bunSectionHeader = By.xpath(".//h2[@class='text text_type_main-medium mb-6 mt-10' and text()='Булки']");
    private final By sauceSectionHeader = By.xpath(".//h2[@class='text text_type_main-medium mb-6 mt-10' and text()='Соусы']");
    private final By fillingsSectionHeader = By.xpath(".//h2[@class='text text_type_main-medium mb-6 mt-10' and text()='Начинки']");
    private final By ingredientSubSection = By.className("BurgerIngredients_ingredients__list__2A-mT");

    private final String BUN = "Булки";
    private final String SAUSE = "Соусы";
    private final String FILLING = "Начинки";



    public MainPage clickBunTab(){driver.findElement(notClickedBunTab).click(); return this;}
    public MainPage clickSauceTab(){driver.findElement(notClickedSauceTab).click(); return this;}
    public MainPage clickFillingsTab(){driver.findElement(notClickedFillingsTab).click(); return this;}

    public BasePage clickPersonalAccountButton() {
        waitButtonIsAvailable(personalAccountButton);
        waitLoaderIsHidden();
        driver.findElement(personalAccountButton).click();
        waitLoaderIsHidden();

        return this; }

    public LoginPage clickLoginButton(){   driver.findElement(loginButton).click();   return new LoginPage(driver);}

    public MainPage waitSectionIsVisible(String ingridTypeName) {
        new WebDriverWait(driver, Duration.ofSeconds(7)).until(ExpectedConditions.visibilityOf(driver.findElements(ingredientSubSection).get(getIngredientTypeIndexByName(ingridTypeName))));
        return this;
    }

    public int getIngredientTypeIndexByName(String ingrTypeName){
        switch (ingrTypeName) {
            case BUN:
                return 0;
            case SAUSE:
                return 1;
            case FILLING:
                return 2;
        }
        return 0;
    }

    @Step("открытие главной страницы")
    public  MainPage openMainPage(){
        driver.get(Configs.SITE_URL);
        this.waitLoaderIsHidden();
        return this;
    }

    public void checkMainPageDefaultElementsVisibility(){
        Assert.assertTrue(driver.findElement(clickedBunTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedSauceTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedFillingsTab).isDisplayed());
        Assert.assertTrue(driver.findElement(burgerIngredientsSection).isDisplayed());
        Assert.assertFalse(driver.findElements(ingredient).isEmpty());
        Assert.assertTrue(driver.findElement(personalAccountButton).isDisplayed());
        Assert.assertTrue(driver.findElement(centralLogoButton).isDisplayed());

    }
    @Step("Проверка наличия элементов на главной странице до авторизации")
    public void checkMainPageElementsVisibilityBeforeAuthorisation(){
        checkMainPageDefaultElementsVisibility();
        Assert.assertTrue(driver.findElement(loginButton).isDisplayed());
    }

    @Step("Проверка наличия элементов на главной странице после авторизации")
    public MainPage checkMainPageElementsVisibilityAfterAuthorisation(){
        checkMainPageDefaultElementsVisibility();
        Assert.assertTrue(driver.findElement(createOrderButton).isDisplayed());
        return this;
    }

    @Step("Переход в личный кабинет")
    public BasePage goToPersonalAccount(){
       return clickPersonalAccountButton().waitLoaderIsHidden();
    }

    @Step("Проверка прокрутки ингридиентов до соусов")
    public MainPage checkScrollToSauces(){
        Assert.assertTrue(driver.findElement(sauceSectionHeader).isDisplayed());
        Assert.assertTrue(driver.findElements(ingredientSubSection).get(getIngredientTypeIndexByName(SAUSE)).isDisplayed());

        Assert.assertTrue(driver.findElement(clickedSauceTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedBunTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedFillingsTab).isDisplayed());
        return this;
    }

    @Step("Проверка прокрутки ингридиентов до булок")
    public MainPage checkScrollToBuns(){
        Assert.assertTrue(driver.findElement(bunSectionHeader).isDisplayed());
        Assert.assertTrue(driver.findElements(ingredientSubSection).get(getIngredientTypeIndexByName(BUN)).isDisplayed());

        Assert.assertTrue(driver.findElement(clickedBunTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedSauceTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedFillingsTab).isDisplayed());
        return this;
    }

    @Step("Проверка прокрутки ингридиентов до начинок")
    public MainPage checkScrollToFillings(){
        Assert.assertTrue(driver.findElement(fillingsSectionHeader).isDisplayed());
        Assert.assertTrue(driver.findElements(filling).get(0).isDisplayed());

        Assert.assertTrue(driver.findElement(clickedFillingsTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedSauceTab).isDisplayed());
        Assert.assertTrue(driver.findElement(notClickedBunTab).isDisplayed());
        return this;
    }
}

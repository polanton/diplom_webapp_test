package utils;


import model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import static io.restassured.RestAssured.options;

public class Generate {

    public  static String userMail(){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        return "coolmail"+ timeStamp+"@mail.ru";
    }

    public  static String userName(){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        return "fancyname"+ timeStamp;
    }

    public static String password(){
        return "1234567";
    }
    public static String shortPassword(){
        return "12345";
    }

    public static User user(){
        return new User(Generate.userMail(), Generate.userName(), Generate.password());
    }

    public static User userWithShortPassword(){
        return new User(Generate.userMail(), Generate.userName(), Generate.shortPassword());
    }

    public static WebDriver webDriver(String browserName) {
        WebDriver driver;
        switch (browserName) {
            case "chrome":
                driver = new ChromeDriver();
                return driver;
            case "firefox":
                driver = new FirefoxDriver();
                return driver;
        }
        return null;
    }
}

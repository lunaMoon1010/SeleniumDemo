package com.ecust.companyInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 根据不同的页面进行不同的处理
 */
public class CompanyInfoPageHandle {

    public static void handleDifferentPage(WebDriver driver){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pageStatus = CompanyInfoPageClassification.PageClassificationMain(driver);
        switch (pageStatus){
            case "验证码":
                //1、如果是验证码页面那么需要在这里进行处理，例如手动输入验证码。如果验证码比较简单可以使用字符识别的方法
                System.out.println("验证码页面");
                //2、登录完了之后再判断是什么页面，然后再对当前页面进行处理
                handleDifferentPage(driver);
                break;
            case "登录":
                //1、如果是登录页面那就进行登录
                login(driver, "用户名","密码");
                //2、登录完了之后再判断是什么页面，然后再对当前页面进行处理
                handleDifferentPage(driver);
                break;
            case  "404":
                break;


        }
    }

    private static void login(WebDriver driver, String userName, String password){
        WebElement phoneElement = driver.findElement(By.cssSelector("input[placeholder=\"请输入手机号码\"]"));
        phoneElement.sendKeys(userName);
        WebElement passwordElement = driver.findElement(By.cssSelector("input[placeholder=\"请输入密码\"]"));
        passwordElement.sendKeys(password);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> elements2 = driver.findElements(By.cssSelector("div a.btn"));
        WebElement login = elements2.get(0);
        login.click();
    }

}

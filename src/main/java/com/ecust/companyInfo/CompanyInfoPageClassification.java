package com.ecust.companyInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 用来判别页面的情况
 */
public class CompanyInfoPageClassification {

    /**
     * 页面判断的主方法，用来判断当前的页面是什么页面
     * @param driver
     * @return
     */
    public static String PageClassificationMain(WebDriver driver){
        if (isVerificationCodePage(driver)){
            return "验证码";
        }

        if (is404Page(driver)){
            return "404";
        }

        if (isLoginPage(driver)){
            return "登录";
        }
        return "正常";
    }

    /**
     * 判断当前页面是否是验证码页面
     * @param driver
     * @return
     */
    private static boolean isVerificationCodePage(WebDriver driver){
        List<WebElement> buttonList = driver.findElements(By.cssSelector("button.btn4"));
        if (buttonList.size() != 0 && buttonList.get(0).getText().equals("点击按钮进行验证")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前页面是否是404
     * @param driver
     * @return
     */
    private static boolean is404Page(WebDriver driver){
        List<WebElement> buttonList = driver.findElements(By.cssSelector("div.error-container.error-404"));
        if (buttonList.size() != 0 ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前页面是否是登录页面
     * @param driver
     * @return
     */
    private static boolean isLoginPage(WebDriver driver){
        List<WebElement> buttonList = driver.findElements(By.cssSelector("input[placeholder=\"请输入手机号码\"]"));
        if (buttonList.size() != 0 ) {
            return true;
        } else {
            return false;
        }
    }



}

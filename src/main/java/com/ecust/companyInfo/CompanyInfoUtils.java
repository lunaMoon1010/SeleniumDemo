package com.ecust.companyInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蔡正 on 2018/3/26.
 */
public class CompanyInfoUtils {
    //去登陆页面并且登录
    public static void toLoginAndLogin(WebDriver driver) {
        //1、去登陆页面
        List<WebElement> elements = driver.findElements(By.cssSelector("div.pull-right a"));
        WebElement loginElement = elements.get(elements.size() - 1);
        loginElement.click();
        //2、该方法判断当前页面是登录页面，则进行登录
        CompanyInfoPageHandle.handleDifferentPage(driver);
    }

    //查询公司名称
    public static void searchCompanyName(WebDriver driver, String CompanyName) {
        //1、查询公司名称
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //2、选取输入框
        WebElement search = driver.findElement(By.cssSelector("input[placeholder=\"请输入企业名、人名、产品名等关键词，多关键词用空格隔开，如：上海 平安\"]"));
        search.sendKeys(CompanyName);
        //3、选取按钮
        WebElement submit = driver.findElement(By.cssSelector("i.input-group-addon.search-btn.icon.icon-search"));
        submit.click();
        //4、判断当前页面的情况并进行处理
        CompanyInfoPageHandle.handleDifferentPage(driver);
    }
}

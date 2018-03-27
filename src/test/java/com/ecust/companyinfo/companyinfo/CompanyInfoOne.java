package com.ecust.companyinfo.companyinfo;

import com.ecust.companyInfo.CompanyInfoPageHandle;
import com.ecust.companyInfo.CompanyInfoUtils;
import com.ecust.utils.FileUtils;
import com.ecust.utils.PageUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CompanyInfoOne {

    /**
     * 用来爬取公司的一些信息
     */
    @Test
    public void CrawlingCompanyInfo() throws Exception {
        String companyName = "安信证券股份有限公司";
        //开启个浏览器并且输入链接
        WebDriver driver = PageUtils.getChromeDriver("https://www.qixin.com/");
        Thread.sleep(5000);
        //1、去登录页面，并且登录
        CompanyInfoUtils.toLoginAndLogin(driver);
        //2、查询公司名称
        CompanyInfoUtils.searchCompanyName(driver, companyName);
        //3、遍历每个页面
        List<WebElement> pageElements = driver.findElements(By.cssSelector("nav ul.pagination li a"));
        //4、保存当前页面信息
        saveResult(driver, companyName);
        for (int i = 2; i < pageElements.size() - 1; ) {
            //5、进入下一页
            List<WebElement> nextElements = driver.findElements(By.cssSelector("nav ul.pagination li"));
            if (
                    !nextElements.get(nextElements.size() - 1).getAttribute("class").equals("disabled")
                            && i >= 7
                    ) {
                pageElements.get(7).click();
            } else {
                pageElements.get(i).click();
                Thread.sleep(2000);
                i = i + 1;
            }
            //6、判断当前页面的情况并进行处理
            CompanyInfoPageHandle.handleDifferentPage(driver);

            //7、保存当前页面信息
            saveResult(driver, companyName);

        }


    }

    /**
     * 保存信息
     * @param driver
     * @param companyName
     */
    private void saveResult(WebDriver driver, String companyName) {
        try {
            Thread.sleep(2000);
            //1、首先遍历每行的公司信息
            List<WebElement> companyElementList = driver.findElements(By.cssSelector("div.col-xs-24.padding-v-1x.margin-0-0x.border-b-b4.company-item"));
            //2、保存公司的信息
            for (WebElement companyElement : companyElementList) {//保存信息
                try {
                    List<WebElement> companyInfoElementList = companyElement.findElements(By.cssSelector("div"));
                    List<String> infoList = new ArrayList<>();
                    for (WebElement companyInfo : companyInfoElementList) {
                        infoList.add(companyInfo.getText().replace("\n", ""));
                    }
                    String result = String.join("\t", infoList);
                    FileUtils.saveLineAppend(companyName + ".txt", result);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

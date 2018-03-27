package com.ecust.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Set;

/**
 * 这里定义了打开浏览器的方法
 */
public class PageUtils {


    /**
     * 在碰到登录的时候，如果没有验证码的情况下，你是不是很想让浏览器自动输入信息帮你登陆呢？
     * 这个方法可以帮你完成这件事情。
     *
     * 干这件事的实质上是让浏览器执行js代码
     * @param driver：传入的浏览器对象
     */
    public static  void inputStrByJS(WebDriver driver, String id, String value){
        //1、js代码，用id来得到 input 标签，并且对该标签赋值
        String setValueJS = "document.getElementById('"+id+"').value='" + value + "';";

        //2、使用driver执行该js代码
        ((JavascriptExecutor) driver).executeScript(setValueJS);

    }

    /**
     * 我们在开启浏览器的时候里面有很多的标签页，但是一个driver只能对应一个标签页。
     * 而每一个driver的handle也是对应一个标签页，如果我们想跳转到别的标签页，需要
     * 首先得到另一个标签页的handle，然后调用driver.switchTo().window(handle);
     * 方法。
     *
     * 例如，我们访问淘宝的时候，有一个商品列表页面，然后你点击其中一个商品，它会新开
     * 一个标签页，这个时候你想控制那个新的标签页，那么你需要首先得到那个新的标签页的
     * handle，因为每一个handle对应一个浏览器的标签页。然后调用
     * driver.switchTo().window(handle);
     *
     *
     * 这个方法是当有两个标签页时切换到另一个标签页的方法
     * @param driver
     */
    public static void getAnotherPage(WebDriver driver){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1、首先获得当前标签页的 handle
        String currentPageHandle = driver.getWindowHandle();

        //2、获得所有标签页的 handle
        Set<String> totalPageHandle = driver.getWindowHandles();

        //3、遍历handle，如果不是当前的 handle 那么切换到另一个handle的标签页
        for (String handleStr : totalPageHandle){
            if (!handleStr.equals(currentPageHandle)){
                //跳转到另一个标签页，这个时候你就可以用driver控制当前页面了
                driver.switchTo().window(handleStr);
            }
        }
    }

    /**
     * 我们在开启浏览器的时候里面有很多的标签页，但是一个driver只能对应一个标签页。
     * 而每一个driver的handle也是对应一个标签页，如果我们想跳转到别的标签页，需要
     * 首先得到另一个标签页的handle，然后调用driver.switchTo().window(handle);
     * 方法。
     *
     * 例如，我们访问淘宝的时候，有一个商品列表页面，然后你点击其中一个商品，它会新开
     * 一个标签页，这个时候你想控制那个新的标签页，那么你需要首先得到那个新的标签页的
     * handle，因为每一个handle对应一个浏览器的标签页。然后调用
     * driver.switchTo().window(handle);
     *
     *
     * 这个方法是当有两个标签页时切换到另一个标签页的方法，并且关闭之前的页面
     * @param driver
     */
    public static void getAnotherPageAndCloseCurrentPage(WebDriver driver){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1、首先获得当前标签页的 handle
        String currentPageHandle = driver.getWindowHandle();

        //2、关闭当前页面
        driver.close();

        //3、获得所有标签页的 handle
        Set<String> totalPageHandle = driver.getWindowHandles();

        //4、遍历handle，如果不是当前的 handle 那么切换到另一个handle的标签页
        for (String handleStr : totalPageHandle){
            if (!handleStr.equals(currentPageHandle)){
                //跳转到另一个标签页，这个时候你就可以用driver控制当前页面了
                driver.switchTo().window(handleStr);
            }
        }

    }


    /**
     * 根据传入的元素滑动到对应的元素，然后点击这个元素，可以防止点击不到的情况
     *
     * @param element : 传入的元素，注意这里的元素必须是可以点击的
     *                例如a标签中有href，或者某个元素中有个onclick事件
     * @param driver  : 传入个driver，可以当作传入个浏览器对象，因为对浏览器的 操作都需要这个对象，因此必须传入它
     */
    public static void scrollToElementAndClick(WebElement element, WebDriver driver) {
        //得到传入元素的y轴坐标
        int yScrollPosition = element.getLocation().getY() - 100;
        //得到传入元素的x轴坐标
        int xScrollPosition = element.getLocation().getX();
        //将浏览器对象强制转为可以执行js的对象
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        //页面上执行js代码，用来操控页面，该js代码为：window.scroll(xScrollPosition ,  yScrollPosition );  意思是窗口跳转到x，y的位置，可以防止窗口没到那里点击不到的情况
        executor.executeScript("window.scroll(" + xScrollPosition + ", " + yScrollPosition + ");");
        element.click();
    }


    /**
     * 获取PhantomJsDriver，需要下载PhantomJsDriver，这里不做演示
     *
     * @param url
     * @return
     */
    public static WebDriver getPhantomJSDriver(String url) {

        DesiredCapabilities dcaps = new DesiredCapabilities();
        //不加载图片
        dcaps.setCapability("phantomjs.page.settings.loadImages", false);
        //设置浏览器的访问样式
        //dcaps.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        //dcaps.setCapability("phantomjs.page.settings.XSSAuditingEnabled", true);
        //dcaps.setCapability("phantomjs.page.settings.localToRemoteUrlAccessEnabled", true);
        //dcaps.setCapability();
        System.setProperty("phantomjs.binary.path", "D:/Program Files (x86)/phantomjs-2.1.1-windows/bin/phantomjs.exe");


        WebDriver driver = new PhantomJSDriver(dcaps);

        //System.setProperty("webdriver.firefox.bin", "D:\\Program Files\\Mozilla Firefox\\firefox.exe");
        //WebDriver driver = new FirefoxDriver();
        //System.setProperty("webdriver.chrome.driver","C:/Users/HP/AppData/Local/Google/Chrome/Application/chromedriver.exe");
        //WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.get(url);

        return driver;
    }


    /**
     * 打开谷歌浏览器，返回一个WebDriver，对浏览器的操作通过webDriver来执行
     *
     * @param url
     * @return
     */
    public static WebDriver getChromeDriver(String url) {

        //设置谷歌浏览器驱动，我放在项目的路径下，这个驱动可以帮你打开本地的谷歌浏览器
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");


        // 设置对谷歌浏览器的初始配置           开始
        HashMap<String, Object> prefs = new HashMap<String, Object>();
        //设置禁止图片
        //prefs.put("profile.managed_default_content_settings.images", 2);
        //设置禁止cookies
        //prefs.put("profile.default_content_settings.cookies", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities chromeCaps = DesiredCapabilities.chrome();
        chromeCaps.setCapability(ChromeOptions.CAPABILITY, options);
        // 设置对谷歌浏览器的初始配置           结束

        //新建一个谷歌浏览器对象（driver）
        WebDriver driver = new ChromeDriver(chromeCaps);

        //通过driver控制浏览器打开链接（url）
        driver.get(url);
        return driver;
    }


}

package com.ecovacs.test.common;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ecosqa on 16/7/27.
 * description:common function
 */
public class Common {
    //parameter
    private static Logger logger = LoggerFactory.getLogger(Common.class);
    private static Common common = null;
    //
    private Common(){

    }

    public enum DATE{
        SUN,
        MON,
        TUES,
        WEDN,
        TURS,
        FRI,
        SAT
    }

    /*public enum PERIOD{
        AM,
        PM
    }*/

    public static Common getInstance(){
        if(common == null){
            common = new Common();
        }
        return common;
    }

    public IOSDriver getDriver(){
        IOSDriver driver = null;
        // set up appium
        /*File classpathRoot = new File(System.getProperty("user.dir"));
        System.out.println(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/src/app/");
        File app = new File(appDir, "NetDeeAnbot.app");*/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.VERSION, "9.2");
        capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS,true);
        capabilities.setCapability(MobileCapabilityType.PLATFORM, "Mac");
        capabilities.setCapability("deviceName","iphone 4s");
        capabilities.setCapability("platformName", "ios");
        /*capabilities.setCapability("app", app.getAbsolutePath());
        System.out.println(app.getAbsolutePath());*/
        //capabilities.setCapability(MobileCapabilityType.APP,"com.iqiyi.reliao");
        try {
            driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        }catch (MalformedURLException e){
            e.printStackTrace();
            logger.info("exception: " + e.toString());
        }
        return driver;
    }

    private boolean delAllFile(String path) {
        File file = new File(path);
        File temp;
            String[] tempList = file.list();
            if(tempList == null){
                return false;
            }
            for(String strFile:tempList){
                if (path.endsWith(File.separator)) {
                    temp = new File(path + strFile);
                } else {
                    temp = new File(path + File.separator + strFile);
                }
                if (temp.isFile()) {
                    if(!temp.delete()){
                        return false;
                    }
                }
            }
        return true;
    }

    public boolean screenShot(String strFileName, IOSDriver driver){
        TakesScreenshot screen = (TakesScreenshot ) new Augmenter().augment(driver);
        String strPath = getClass().getResource("/").getPath()
                        + "../" + "screenShots/";
        //check
        File folder = new File(strPath);
        if(!folder.exists() && !folder.isDirectory()){
            if(!folder.mkdir()){
                return false;
            }
        }else {
            delAllFile(strPath);
        }
        File ss = new File(strPath + strFileName);
        return screen.getScreenshotAs(OutputType.FILE).renameTo(ss);
    }

    public void waitForSencond(int iMillSencond){
        try {
            Thread.sleep(iMillSencond);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void goback(IOSDriver driver, int iLoop){
        for(int i = 0; i < iLoop; i++){
            driver.navigate().back();
            waitForSencond(300);
        }

    }

    public enum Direction {
        //UP,
        //DOWN,
        LEFT,
        RIGHT
    }

    public void swipeCleanMode(IOSDriver driver, MobileElement mobileEle, Direction direction){
        Point start = mobileEle.getLocation();
        int startX = start.x;
        int startY = start.y;
        //size of scrtollView
        Dimension q = mobileEle.getSize();
        int x = q.getWidth();
        int y = q.getHeight();
        // end of scrtollView
        int endX = x + startX;
        int endY = y + startY;
        // middle of scrtollView
        int centreX = (endX + startX) / 2;

        switch (direction){
            case LEFT:
                driver.swipe(centreX + x/4, endY - 10, -x/2, 0, 500);
                //driver.swipe(250, 422, -100, 0, 500);
                break;
            case RIGHT:
                driver.swipe(x/4, endY - 10, x/2, 0, 500);
                break;
            default:
                break;

        }
    }

    /*public void swipeTimePicker(IOSDriver driver, MobileElement mobileEle, Direction direction){
        Point start = mobileEle.getLocation();
        int startX = start.x;
        int startY = start.y;
        //size of scrtollView
        Dimension q = mobileEle.getSize();
        int x = q.getWidth();
        int y = q.getHeight();
        // end of scrtollView
        int endX = x + startX;
        int endY = y + startY;
        // middle of scrtollView
        int centreY = (endY + startY) / 2;

        switch (direction){
            case UP:
                driver.swipe(endX - 10, centreY + y/4, 0, -y/4, 500);
                break;
            case DOWN:
                driver.swipe(endX - 10, y/4, 0, y/4, 500);
                break;
            default:
                break;

        }
    }*/

}

package com.ecovacs.test.activity;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecosqa on 16/8/1.
 * Personal center activity
 */
public class PersonalCenterActivity {

    private static Logger logger = LoggerFactory.getLogger(PersonalCenterActivity.class);

    private IOSDriver driver = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIAStaticText[4]")
    private WebElement textUserID = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIAButton[1]")
    private WebElement btnDataManage = null;

    public void init(IOSDriver driver){
        this.driver = driver;
    }

    /**
     * Check if load personal center or not
     * @return boolean
     */

    public boolean loadPersonalCenter(){
        boolean bSleep = false;
        int i =  0;
        boolean bResult = false;

        while (!bSleep){
            if(i > 20){
                break;
            }
            try {
                bSleep = textUserID.isDisplayed();
            }catch (NoSuchElementException e){
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                System.out.println("Try to wait for PersonalCenterPage: " + String.valueOf(i++));
            }
        }
        if(textUserID.getText().length() > 0){
            bResult = true;
            logger.info("****Fun:loadPersonalCenter****user id: " + textUserID.getText());
            //Common.screenShot("loadPersonalCenter.png", driver);
        }
        else {
            logger.error("****Fun:loadPersonalCenter****Can not show user id!!!");
        }
        return bSleep && bResult;
    }

    /**
     * click button of data and address manage
     */

    public void clickDataManage(){
        btnDataManage.click();
    }

}

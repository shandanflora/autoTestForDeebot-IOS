package com.ecovacs.test.activity;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecosqa on 16/7/29.
 * description: MainPage Activity
 */
public class MainPageActivity {

    private static Logger logger = LoggerFactory.getLogger(MainPageActivity.class);
    private IOSDriver driver;


    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private WebElement tableview = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[5]")
    private WebElement btnUserDetail = null;

    public void init(IOSDriver driver){
        this.driver = driver;
    }

    /**
     * get of device list, if not show in 20 seconds, return not find element
     * @return WebElement
     */

    private WebElement getTableview(){
        boolean bSleep = false;
        int i =  0;

        while (!bSleep){
            if(i > 20){
                break;
            }
            try {
                bSleep = tableview.isDisplayed();
            }catch (NoSuchElementException e){
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                System.out.println("Try to wait for load MainPage: " + String.valueOf(i++));
            }
        }
        return tableview;
    }

    /**
     * check the device in the table view
     * @param strDevice string of device
     * @return boolean
     */

    public boolean DeviceInTableView(String strDevice){
        getTableview();
        boolean bResult = false;
        List<WebElement> cellList= tableview.findElements(By.className("UIATableCell"));
        System.out.println("The size of tableview is: " + cellList.size());
        for (WebElement elementCell:cellList){
            List<WebElement> textList = elementCell.findElements(By.className("UIAStaticText"));
            for(WebElement staticText:textList){
                /*logger.info("****DeviceInTableView****The text of device is: "
                        + staticText.getText().trim());*/
                if(staticText.getText().trim().equals(strDevice)){
                    logger.info("****DeviceInTableView****The text of device is: "
                            + staticText.getText().trim());
                    bResult = true;
                }
            }
        }
        if(!bResult){
            logger.error("****DeviceInTableView****Can not find the device: " + strDevice);
        }
        return bResult;
    }

    public void clickDeebot(String strDevice){
        List<WebElement> cellList= tableview.findElements(By.className("UIATableCell"));
        for (WebElement elementCell:cellList){
            List<WebElement> textList = elementCell.findElements(By.className("UIAStaticText"));
            for(WebElement staticText:textList){
                if(staticText.getText().trim().equals(strDevice)){
                    elementCell.click();
                }
            }
        }
    }

    /**
     * click the button of user center
     */

    public void btnUserCenterClick(){
        btnUserDetail.click();
    }





}

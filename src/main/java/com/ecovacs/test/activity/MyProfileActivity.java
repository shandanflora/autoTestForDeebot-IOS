package com.ecovacs.test.activity;

import com.ecovacs.test.common.PropertyData;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ecosqa on 16/8/1.
 * my profile activity
 */
public class MyProfileActivity {

    private static Logger logger = LoggerFactory.getLogger(MyProfileActivity.class);
    private IOSDriver driver = null;
    private static MyProfileActivity myProfileActivity = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIAButton[1]")
    private WebElement btnLogOut = null;
    //
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[4]/UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[2]/UIAButton[1]")
    private WebElement btnConfirm = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private WebElement tableViewData = null;

    private MyProfileActivity(){

    }

    public static MyProfileActivity getInstance(){
        if(myProfileActivity == null){
            myProfileActivity = new MyProfileActivity();
        }
        return myProfileActivity;
    }

    public boolean loadMyProfile(){
        return getPhoneNumber().length() > 0;
    }

    public void init(IOSDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Get user phone number in my profile activity
     * @return String
     */

    private String getPhoneNumber(){
        String strPhoneNumber = "";
        List<WebElement> cellList= tableViewData.findElements(By.className("UIATableCell"));
        System.out.println("The size of tableview is: " + cellList.size());
        for (WebElement elementCell:cellList){
            List<WebElement> textList = elementCell.findElements(By.className("UIAStaticText"));
            for(WebElement staticText:textList){
                if(staticText.getText().equals(PropertyData.getProperty("userid"))){
                    strPhoneNumber = staticText.getText();
                    logger.info("****getPhoneNumber****phone number: " + strPhoneNumber);
                    break;
                }
            }
        }

        if(strPhoneNumber.length() == 0){
            logger.error("****getPhoneNumber****Can not get phone number!!!");
        }

        return strPhoneNumber;
    }

    /**
     * logout from APP
     * @return boolean
     */

    public boolean logOut(){
        boolean bSleep = false;
        boolean bResult = false;
        int i =  0;

        btnLogOut.click();
        while(!bSleep){
            if(i > 20){
                break;
            }

            try {
                bSleep = btnConfirm.isDisplayed();
            }catch (NoSuchElementException e){
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                System.out.println("Try to wait for alert message: " + String.valueOf(i++));
            }
        }
        if(bSleep){
            bResult = true;
        }
        btnConfirm.click();
        return bResult;
    }
}

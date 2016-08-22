package com.ecovacs.test.activity;

import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ecosqa on 16/8/2.
 * xianbot ui controller activity
 */
public class XianBotUIControllerActivity {

    private static Logger logger = LoggerFactory.getLogger(XianBotUIControllerActivity.class);
    private static XianBotUIControllerActivity controllerActivity = null;
    private IOSDriver driver = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAStaticText[3]")
    private MobileElement txtCurrentStatus = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAStaticText[1]")
    private MobileElement txtValueStatus = null;

    /*
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAStaticText[2]")
    private MobileElement txtElectricValue = null;
    */

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[3]")
    private MobileElement btnDirectionTop = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[6]")
    private MobileElement btnDirectionBottom = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[4]")
    private MobileElement btnDirectionLeft = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[5]")
    private MobileElement btnDirectionRight = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[7]")
    private MobileElement btnContinue = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]")
    private MobileElement scrtollViewCleanMode = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[3]")
    private MobileElement btnXianBoSet = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[1]")
    private MobileElement btnTimeAppoint = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]")
    private MobileElement btnReturnMain = null;

    private XianBotUIControllerActivity(){

    }

    public static XianBotUIControllerActivity getInstance(){
        if(controllerActivity == null){
            controllerActivity = new XianBotUIControllerActivity();
        }
        return controllerActivity;
    }

    public void init(IOSDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean loadControllerActivity(){

        boolean bSleep = false;
        int iTime = 0;

        if(txtCurrentStatus.getText().trim().
                equals(PropertyData.getProperty("currentStatus"))){
            while (!bSleep){
                bSleep = txtValueStatus.getText().trim().equals("--");
                if(bSleep){
                    Common.getInstance().waitForSencond(1000);
                    iTime++;
                    if(iTime > 20){
                        btnReturnMain.click();
                        break;
                    }
                }else {
                    break;
                }
            }
            Common.getInstance().screenShot("loadControllerActivity.png", driver);
            return !bSleep;
        }
        else{
           logger.error("The text of current status is not match, "
                   + txtCurrentStatus.getText().trim());
            btnReturnMain.click();
            return false;
        }
    }

    /**
     * Configure clean mode: standard or strong
     * @param strMode String
     * @return boolean
     */
    private boolean setCleanMode(String strMode){
        boolean bClickMode = false;
        //scrtollViewCleanMode.swipe(SwipeElementDirection.LEFT, 50, 10, 500);
        Common.getInstance().swipeCleanMode(driver, scrtollViewCleanMode, Common.Direction.LEFT);
        List<MobileElement> btnModeList = scrtollViewCleanMode.findElementsByClassName("UIAButton");
        for(MobileElement btnMode:btnModeList){
            if(btnMode.getText().trim().equals(strMode)){
                btnMode.click();
                bClickMode = true;
                break;
            }
        }
        if(!bClickMode){
            logger.error("****standardClean****Can not find the button is: " + strMode);
            //btnContinue.click();
            btnReturnMain.click();
        }
        return bClickMode;
    }

    /**
     * DEEBOT clean
     * @param strStyle String: auto/single...
     * @param strMode String: standard/strong
     * @param strStatus String status of clean
     * @return boolean
     */
    public boolean clean(String strStyle, String strMode, String strStatus){
        //select clean mode
        if(!setCleanMode(strMode)){
            return false;
        }
        boolean bClickStyle = false;
        //scrtollViewCleanMode.swipe(SwipeElementDirection.RIGHT, 500);
        Common.getInstance().swipeCleanMode(driver, scrtollViewCleanMode, Common.Direction.RIGHT);
        List<MobileElement> btnStyleList = scrtollViewCleanMode.findElementsByClassName("UIAButton");
        for(MobileElement btnMode:btnStyleList){
            if(btnMode.getText().trim().equals(strStyle)){
                btnMode.click();
                bClickStyle = true;
                break;
            }
        }
        if(!bClickStyle){
            logger.error("****standardClean****Can not find the button is: " + strStyle);
            btnReturnMain.click();
            return false;
        }
        //check
        boolean bResult = false;
        Common.getInstance().waitForSencond(2000);
        if(txtValueStatus.getText().trim().equals(strStatus)){
            bResult = true;
            logger.info("****standardClean****The current status is: " + txtValueStatus.getText());
        }else {
            logger.error("****standardClean****The current status is not match!!!");
            logger.error("****standardClean****The current status is: " + txtValueStatus.getText());
        }
        btnContinue.click();
        //btnReturnMain.click();
        return bResult;
    }

    public boolean manualCleanTop(){
        btnDirectionTop.click();
        return Common.getInstance().screenShot("manualCleanTop.png", driver);
    }

    public boolean manualCleanBottom(){
        btnDirectionBottom.click();
        Common.getInstance().screenShot("manualCleanBottom.png", driver);
        return true;
    }

    public boolean manualCleanLeft(){
        btnDirectionLeft.click();
        Common.getInstance().screenShot("manualCleanLeft.png", driver);
        return true;
    }

    public boolean manualCleanRight(){
        btnDirectionRight.click();
        Common.getInstance().screenShot("manualCleanRight.png", driver);
        return true;
    }

    public void clickSetting(){
        btnXianBoSet.click();
    }

    public boolean timeAppointDisplay(){
       return btnTimeAppoint.isDisplayed();
    }


}

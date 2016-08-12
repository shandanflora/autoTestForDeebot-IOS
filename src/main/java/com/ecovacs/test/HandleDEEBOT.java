package com.ecovacs.test;

import com.ecovacs.test.activity.*;
import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/7/26.
 * description:handle DEEBOT
 */
public class HandleDEEBOT {
    //parameter
    private static Logger logger = LoggerFactory.getLogger(HandleDEEBOT.class);
    private static HandleDEEBOT handleDEEBOT = null;
    private IOSDriver driver = null;

    private MainPageActivity mainPageActivity = null;
    private PersonalCenterActivity personalCenterActivity = null;
    private MyProfileActivity myProfileActivity = null;
    private LoginActivity loginActivity = null;
    private SettingActivity settingActivity = null;
    private AppointmentActivity appointmentActivity = null;

    private HandleDEEBOT(){

    }

    public static HandleDEEBOT getInstance(){
        if(handleDEEBOT == null){
            handleDEEBOT = new HandleDEEBOT();
        }
        return handleDEEBOT;
    }

    public void init(IOSDriver driver){

        this.driver = driver;

        loginActivity = PageFactory.initElements(driver, LoginActivity.class);

        mainPageActivity = PageFactory.initElements(driver, MainPageActivity.class);
        mainPageActivity.init(driver);

        personalCenterActivity = PageFactory.initElements(driver, PersonalCenterActivity.class);
        personalCenterActivity.init(driver);

        myProfileActivity = PageFactory.initElements(driver, MyProfileActivity.class);
        myProfileActivity.init(driver);

        XianBotUIControllerActivity.getInstance().init(driver);

        settingActivity = PageFactory.initElements(driver, SettingActivity.class);
        appointmentActivity = PageFactory.initElements(driver, AppointmentActivity.class);
        AppointAddActivity.getInstance().init(driver);
        AppointRepeatActivity.getInstance().init(driver);

    }
//java.lang.IllegalArgumentException: Can not set io.appium.java_client.MobileElement field  to com.sun.proxy.$Proxy6
    public boolean Login(){
        return loginActivity.loginClick() &&
                mainPageActivity.DeviceInTableView(PropertyData.getProperty("deebot"));
    }

    public boolean LoadControllActivity(){
        mainPageActivity.clickDeebot(PropertyData.getProperty("deebot"));
        return XianBotUIControllerActivity.getInstance().loadControllerActivity();
    }

    public boolean standardAutoClean(){
        return XianBotUIControllerActivity.getInstance().clean
                (PropertyData.getProperty("cleanAuto"),
                PropertyData.getProperty("cleanStandard"),
                PropertyData.getProperty("statusAuto"));
    }

    public boolean standardSingleClean(){
        return XianBotUIControllerActivity.getInstance().clean
                (PropertyData.getProperty("cleanSingleRoom"),
                        PropertyData.getProperty("cleanStandard"),
                        PropertyData.getProperty("statusSingleRoom"));
    }

    public boolean standardBorderClean(){
        return XianBotUIControllerActivity.getInstance().clean
                (PropertyData.getProperty("cleanBorder"),
                        PropertyData.getProperty("cleanStandard"),
                        PropertyData.getProperty("statusBorder"));
    }

    public boolean standardFixedClean(){
        return XianBotUIControllerActivity.getInstance().clean
                (PropertyData.getProperty("cleanFixed"),
                        PropertyData.getProperty("cleanStandard"),
                        PropertyData.getProperty("statusFixed"));
    }

    public boolean standardChargeClean(){
        return XianBotUIControllerActivity.getInstance().clean
                (PropertyData.getProperty("cleanCharge"),
                        PropertyData.getProperty("cleanStandard"),
                        PropertyData.getProperty("statusCharge"));
    }

    public boolean manualCleanTop(){
        return XianBotUIControllerActivity.getInstance().manualCleanTop();
    }

    public boolean manualCleanBottom(){
        return XianBotUIControllerActivity.getInstance().manualCleanBottom();
    }

    public boolean manualCleanLeft(){
        return XianBotUIControllerActivity.getInstance().manualCleanLeft();
    }

    public boolean manualCleanRight(){
        return XianBotUIControllerActivity.getInstance().manualCleanRight();
    }

    public boolean getHardwareVer(){
        boolean bResult = true;
        XianBotUIControllerActivity.getInstance().clickSetting();
        if(!settingActivity.loadSettingActivity()){
            logger.error("****getHardwareVer****Can not load setting activity!!!");
            Common.getInstance().goback(driver, 1);
            bResult = false;
        }
        if(settingActivity.getHardWareVer(PropertyData
                .getProperty("hardwareVersion")).length() == 0){
            Common.getInstance().goback(driver, 1);
            bResult = false;
            logger.error("****getHardwareVer****Can not find hardware version!!!");

        }
        Common.getInstance().goback(driver, 1);
        return bResult;
    }

    public boolean addTimeAppoint(){
        XianBotUIControllerActivity.getInstance().clickSetting();
        if(!settingActivity.loadSettingActivity()){
            logger.error("****addTimeAppoint****Can not load setting activity!!!");
            return false;
        }
        if(!settingActivity.clickTableCell(PropertyData.getProperty("timeAppoint"))){
            logger.error("****addTimeAppoint****Can not find the cell in table view!!!");
            return false;
        }
        appointmentActivity.clickAdd();
        if(!AppointAddActivity.getInstance().selectTimeAppoint("00", "50")){
            return false;
        }
        if(!AppointAddActivity.getInstance().clickRepeat()){
            Common.getInstance().goback(driver, 3);
            return false;
        }
        if(!AppointRepeatActivity.getInstance().selectDate(Common.DATE.SUN)){
            Common.getInstance().goback(driver, 4);
            return false;
        }
        AppointRepeatActivity.getInstance().clickBack();
        AppointAddActivity.getInstance().clickConfirm();
        appointmentActivity.timeInTableView(Common.DATE.SUN, "14", "40");
        appointmentActivity.clickBackSetting();
        driver.navigate().back();
        return XianBotUIControllerActivity.getInstance().timeAppointDisplay() &&
                delAllTimeAppoint();
    }

    public boolean delAllTimeAppoint(){
        XianBotUIControllerActivity.getInstance().clickSetting();
        if(!settingActivity.loadSettingActivity()){
            logger.error("****addTimeAppoint****Can not load setting activity!!!");
            return false;
        }
        if(!settingActivity.clickTableCell(PropertyData.getProperty("timeAppoint"))){
            logger.error("****addTimeAppoint****Can not find the cell in table view!!!");
            return false;
        }
        appointmentActivity.clickEdit();
        return appointmentActivity.deleTimeAppoint();
    }


    public boolean Logout(IOSDriver driver){
        Common.getInstance().goback(driver, 1);
        mainPageActivity.btnUserCenterClick();
        if (personalCenterActivity.loadPersonalCenter()){
            personalCenterActivity.clickDataManage();
            if(myProfileActivity.loadMyProfile()){
                return myProfileActivity.logOut();
            }else {
                logger.error("****Logout****Can not my profile!!!");
                return false;
            }
        }
        else {
            logger.error("****Logout****Can not load personal center!!!");
            return false;
        }
    }

}

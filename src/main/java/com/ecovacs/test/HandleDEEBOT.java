package com.ecovacs.test;

import com.ecovacs.test.activity.*;
import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.MailSender;
import com.ecovacs.test.common.PropertyData;
import com.ecovacs.test.common.ZipUtil;
import io.appium.java_client.ios.IOSDriver;
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

        LoginActivity.getInstance().init(driver);
        MainPageActivity.getInstance().init(driver);
        PersonalCenterActivity.getInstance().init(driver);
        MyProfileActivity.getInstance().init(driver);
        XianBotUIControllerActivity.getInstance().init(driver);
        SettingActivity.getInstance().init(driver);
        AppointmentActivity.getInstance().init(driver);
        AppointAddActivity.getInstance().init(driver);
        AppointRepeatActivity.getInstance().init(driver);

    }
//java.lang.IllegalArgumentException: Can not set io.appium.java_client.MobileElement field  to com.sun.proxy.$Proxy6
    public boolean Login(){
        return LoginActivity.getInstance().loginClick() &&
                MainPageActivity.getInstance().DeviceInTableView(PropertyData.getProperty("deebot"));
    }

    public boolean LoadControllActivity(){
        MainPageActivity.getInstance().clickDeebot(PropertyData.getProperty("deebot"));
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
        if(!SettingActivity.getInstance().loadSettingActivity()){
            logger.error("****getHardwareVer****Can not load setting activity!!!");
            Common.getInstance().goback(driver, 1);
            bResult = false;
        }
        if(SettingActivity.getInstance().getHardWareVer(PropertyData
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
        if(!SettingActivity.getInstance().loadSettingActivity()){
            logger.error("****addTimeAppoint****Can not load setting activity!!!");
            return false;
        }
        if(!SettingActivity.getInstance().clickTableCell(PropertyData.getProperty("timeAppoint"))){
            logger.error("****addTimeAppoint****Can not find the cell in table view!!!");
            return false;
        }
        AppointmentActivity.getInstance().clickAdd();
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
        AppointmentActivity.getInstance().timeInTableView(Common.DATE.SUN, "14", "40");
        AppointmentActivity.getInstance().clickBackSetting();
        driver.navigate().back();
        return XianBotUIControllerActivity.getInstance().timeAppointDisplay() &&
                delAllTimeAppoint();
    }

    public boolean delAllTimeAppoint(){
        XianBotUIControllerActivity.getInstance().clickSetting();
        if(!SettingActivity.getInstance().loadSettingActivity()){
            logger.error("****addTimeAppoint****Can not load setting activity!!!");
            return false;
        }
        if(!SettingActivity.getInstance().clickTableCell(PropertyData.getProperty("timeAppoint"))){
            logger.error("****addTimeAppoint****Can not find the cell in table view!!!");
            return false;
        }
        AppointmentActivity.getInstance().clickEdit();
        return AppointmentActivity.getInstance().deleTimeAppoint();
    }

    private String compressReport(){
        String strPath = "";
        String strZipPath = "";
        boolean bResult = false;
        try {
            strZipPath = getClass().getResource("/").getPath() + "../";
            String strSrcPath = strZipPath + "surefire-reports/html";
            bResult = ZipUtil.zip(strSrcPath, strZipPath, "zipReport.zip");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (bResult){
            strPath = strZipPath + "/" + "zipReport.zip";
        }else {
            logger.error("****compressReport****Compress report failed!!!");
        }
        return strPath;
    }

    public boolean senEmail(){
        String strZipPath = compressReport();
        if(strZipPath.length() == 0){
            return false;
        }
        //smtp.sina.com
        String smtp = PropertyData.getProperty("mail-smtp");
        String from = PropertyData.getProperty("mail-from");
        String to = PropertyData.getProperty("mail-to");
        String subject = "test";
        String content = "test content";
        String username = PropertyData.getProperty("mail-username");
        String password = PropertyData.getProperty("mail-password");

        return MailSender.sendAndCcAndAttach(smtp, from, to, "", subject, content, username, password, strZipPath);
    }

    public boolean Logout(IOSDriver driver){
        Common.getInstance().goback(driver, 1);
        MainPageActivity.getInstance().btnUserCenterClick();
        if (PersonalCenterActivity.getInstance().loadPersonalCenter()){
            PersonalCenterActivity.getInstance().clickDataManage();
            if(MyProfileActivity.getInstance().loadMyProfile()){
                return MyProfileActivity.getInstance().logOut();
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

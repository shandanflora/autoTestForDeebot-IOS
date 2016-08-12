package com.ecovacs.test.activity;

import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecosqa on 16/8/9.
 * appoint add activity
 */
public class AppointAddActivity {

    private static AppointAddActivity appointAddActivity = null;
    private IOSDriver driver = null;
    private static Logger logger = LoggerFactory.getLogger(AppointAddActivity.class);

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]")
    private MobileElement btnBack = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[3]")
    private MobileElement btnConfirm = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAStaticText[1]")
    private MobileElement txtTitle = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[3]/UIAStaticText[1]")
    private MobileElement cellRepeat = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private MobileElement tableVeiw = null;

    public static AppointAddActivity getInstance(){
        if(appointAddActivity == null){
            appointAddActivity = new AppointAddActivity();
        }
        return appointAddActivity;
    }

    public void init(IOSDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

    }

    public boolean clickRepeat(){
        /*List<MobileElement> cellList = tableVeiw.findElementsByClassName("UIATableCell");
        for(MobileElement cell:cellList){
            List<MobileElement> txtList = cell.findElementsByClassName("UIAStaticText");
            for(MobileElement txt:txtList){
                System.out.println(txt.getText());
                if(txt.getText().trim().equals(PropertyData.getProperty("repeat"))){
                    txt.click();
                }
            }
        }*/
        cellRepeat.click();
        try {
            if(txtTitle.getText().trim().equals(PropertyData.getProperty("appointAddTile"))){
                return true;
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void clickConfirm(){
        btnConfirm.click();
    }

    public void clickBack(){
        btnBack.click();
    }

    /*private String periodToStr(Common.PERIOD period){
        String strPeriod = "";
        switch (period){
            case AM:
                strPeriod = PropertyData.getProperty("am");
                break;
            case PM:
                strPeriod = PropertyData.getProperty("pm");
            default:
                    break;
        }
        return strPeriod;
    }*/

    public boolean selectTimeAppoint(String strHour, String strMin){
        List<IOSElement> list = new ArrayList<IOSElement>();
        int iLoop = 0;
        while(list.size() == 0){
            Common.getInstance().waitForSencond(1000);
            if(iLoop > 20){
                break;
            }
            list = driver.findElementsByClassName("UIAPickerWheel");
            iLoop++;
        }
        System.out.println("The size of table view is:" + Integer.valueOf(list.size()));
        //System.out.println("****size:list[0]****" + Integer.valueOf(list.get(0).getAttribute("values").length()));
        //System.out.println("****size:list[1]****" + Integer.valueOf(list.get(1).getAttribute("values").length()));
        Common.getInstance().waitForSencond(1500);
        System.out.println("****list[0]****" + list.get(0).getAttribute("values"));
        System.out.println("****list[1]****" + list.get(1).getAttribute("values"));
        /*IOSElement eleHour = (IOSElement)driver.findElementsByClassName("UIAPickerWheel").get(0);
        eleHour.sendKeys(strHour);*/
        try {
            list.get(0).sendKeys(strHour);
            System.out.println("****list[0]****" + list.get(0).getAttribute("value"));
            Common.getInstance().waitForSencond(2000);
            list.get(1).sendKeys(strMin);
            System.out.println("****list[1]****" + list.get(1).getAttribute("value"));
        }catch (WebDriverException e){
            e.printStackTrace();
            logger.error("****selectTimeAppoint****Can not config picker value!!!");
            Common.getInstance().goback(driver, 3);
            return false;
        }
        //System.out.println("The size of table view is:" + Integer.valueOf(list.size()));
        //IOSElement eleMin = (IOSElement)driver.findElementsByClassName("UIAPickerWheel").get(1);
        //eleMin.sendKeys(strMin);
        //list.get(1).sendKeys(strMin);
        return true;
    }
}

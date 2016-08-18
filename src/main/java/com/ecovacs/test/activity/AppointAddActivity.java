package com.ecovacs.test.activity;

import com.ecovacs.test.common.Common;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ecosqa on 16/8/9.
 * appoint add activity
 */
public class AppointAddActivity {

    private static AppointAddActivity appointAddActivity = null;
    private IOSDriver driver = null;
    private static Logger logger = LoggerFactory.getLogger(AppointAddActivity.class);

    /*@FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]")
    private MobileElement btnBack = null;*/
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[3]")
    private MobileElement btnConfirm = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[3]")
    private MobileElement cellRepeat = null;
    /*@FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private MobileElement tableVeiw = null;*/

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
        return AppointRepeatActivity.getInstance().loadAppointRepeatActivity();
    }

    public void clickConfirm(){
        btnConfirm.click();
    }

    /*public void clickBack(){
        btnBack.click();
    }*/

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

    private void waitForListSize(){
        List<IOSElement> list;
        int iLoop = 0;
        do {
            list = driver.findElementsByClassName("UIAPickerWheel");
            Common.getInstance().waitForSencond(1000);
            iLoop++;
            if(iLoop > 20){
                break;
            }
            System.out.println("****waitForListSize****try time: " + Integer.toString(iLoop));
            System.out.println("****waitForListSize****list size: " + Integer.toString(list.size()));
        }while (list.size() == 0);
    }

    public boolean selectTimeAppoint(String strHour, String strMin){

        Common.getInstance().waitForSencond(3000);
        waitForListSize();
        //System.out.println("The size of table view is:" + Integer.valueOf(list.size()));
        //System.out.println("****size:list[0]****" + Integer.valueOf(list.get(0).getAttribute("values").length()));
        //System.out.println("****size:list[1]****" + Integer.valueOf(list.get(1).getAttribute("values").length()));
        //Common.getInstance().waitForSencond(3000);
        //System.out.println("****list[0]****" + list.get(0).getAttribute("values"));
        //System.out.println("****list[1]****" + list.get(1).getAttribute("values"));
        //System.out.println("****list[2]****" + list.get(2).getAttribute("values"));
        /*IOSElement eleHour = (IOSElement)driver.findElementsByClassName("UIAPickerWheel").get(0);
        eleHour.sendKeys(strHour);*/
        try {
            /*list.get(0).sendKeys(strHour);
            list.get(1).sendKeys(strMin);
            list.get(2).sendKeys("40");*/
            WebElement elePeriod = (WebElement) driver.findElements(By.className("UIAPickerWheel")).get(0);
            elePeriod.sendKeys(strHour);
            waitForListSize();
            WebElement eleHour = (WebElement) driver.findElements(By.className("UIAPickerWheel")).get(1);
            //System.out.println("****list[1]****" + eleHour.getAttribute("value"));
            eleHour.sendKeys(strMin);
            /*Common.getInstance().waitForSencond(3000);
            WebElement eleMin = (WebElement) driver.findElements(By.className("UIAPickerWheel")).get(2);
            eleMin.sendKeys("50");*/
            //System.out.println("****list[0]****" + list.get(0).getAttribute("value"));
            //System.out.println("****list[1]****" + list.get(1).getAttribute("value"));
            //System.out.println("****list[2]****" + list.get(1).getAttribute("value"));
        }
        catch (WebDriverException e){
            e.printStackTrace();
            logger.error("****selectTimeAppoint****Can not config picker value!!!");
            Common.getInstance().goback(driver, 3);
            return false;
        }
        return true;
    }
}

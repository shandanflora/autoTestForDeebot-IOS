package com.ecovacs.test.activity;

import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecosqa on 16/8/9.
 * appoint activity
 */
public class AppointmentActivity {

    private static Logger logger = LoggerFactory.getLogger(AppointmentActivity.class);

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAImage[1]")
    private WebElement imageTimerNo = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]")
    private WebElement btnBackSetting = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[3]")
    private WebElement btnEditAppoint = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[4]")
    private WebElement btnAddAppoint = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private WebElement tableViewAppoint = null;

    public boolean loadAppointActivityTimeNo(){
        boolean bResult = false;
        int iLoop = 0;
        while(!bResult){
            if(iLoop > 20){
                break;
            }
            bResult = imageTimerNo.isDisplayed();
            if(!bResult){
                Common.getInstance().waitForSencond(1000);
                iLoop++;
            }
        }

        List<WebElement> cellList = tableViewAppoint.findElements(By.className("UIATableCell"));
        if(cellList.size() == 0){
            bResult = imageTimerNo.isDisplayed();
        }
        return bResult;
    }

    public int tableViewRountCount(){
        List<WebElement> cellList = new ArrayList<WebElement>();
        int iLoop = 0;
        while(cellList.size() == 0){
            Common.getInstance().waitForSencond(1000);
            iLoop++;
            if(iLoop > 20){
                break;
            }
            cellList = tableViewAppoint.findElements(By.className("UIATableCell"));
        }
        return cellList.size();
    }


    public void clickAdd(){
        btnAddAppoint.click();
    }

    public boolean clickEdit(){
        boolean bShowBtn = true;
        btnEditAppoint.click();
        List<WebElement> cellList = tableViewAppoint.findElements(By.className("UIATableCell"));
        for(WebElement cell:cellList){
            try {
                WebElement eleBtn = cell.findElement(By.className("UIAButton"));
                bShowBtn = eleBtn.isDisplayed();
                if(!bShowBtn){
                    break;
                }
            }catch (NoSuchElementException e){
                e.printStackTrace();
                logger.error("****clickEdit****Not show delete icon after click edit!!!");
                bShowBtn = false;
                break;
            }
        }
        return bShowBtn;
    }

    public boolean deleTimeAppoint(){
        List<WebElement> cellList = tableViewAppoint.findElements(By.className("UIATableCell"));
        while(cellList.size() != 0){
            //click delete icon
            cellList.get(0).findElement(By.className("UIAButton")).click();
            List<WebElement> btnList = cellList.get(0).findElements(By.className("UIAButton"));
            for (WebElement eleBtn:btnList){
                if(eleBtn.getText().trim().equals(PropertyData.getProperty("btnDel"))){
                    eleBtn.click();
                }
            }
            cellList = tableViewAppoint.findElements(By.className("UIATableCell"));
        }
        return loadAppointActivityTimeNo();
    }

    public void clickBackSetting(){
        btnBackSetting.click();
    }

    private String dateToStr(Common.DATE date){
        String strDate = "";
        switch (date){
            case SUN:
                strDate = PropertyData.getProperty("txtSun");
                break;
            case MON:
                strDate = PropertyData.getProperty("txtMon");
                break;
            case TUES:
                strDate = PropertyData.getProperty("txtTues");
                break;
            case WEDN:
                strDate = PropertyData.getProperty("txtWedn");
                break;
            case TURS:
                strDate = PropertyData.getProperty("txtTurs");
                break;
            case FRI:
                strDate = PropertyData.getProperty("txtFri");
                break;
            case SAT:
                strDate = PropertyData.getProperty("txtSat");
                break;
            default:
                break;
        }
        return strDate;
    }
/*
    private String timeToStr(Common.PERIOD period, String strHour, String strMin){
        String strTime;
        if(period == Common.PERIOD.AM){
            if(strHour.length() == 1 ){
                strHour = "0" + strHour;
            }
        }else if(period == Common.PERIOD.PM){
            int iHour = Integer.parseInt(strHour) + 12;
            strHour = String.valueOf(iHour);
            if(iHour == 24){
                strHour = "00";
            }
        }
        strTime = strHour + ":" + strMin;
        logger.info("****timeToStr****" + strTime);
        return strTime;
    }*/

    public boolean timeInTableView(Common.DATE date, /*Common.PERIOD period,*/ String strHour, String strMin){
        boolean bResult = false;
        String strTime = strHour + ":" + strMin;
        if(strTime.length() - 1 == 0){
            logger.error("****timeInTableView****Can not set hour and minute!!!");
            return false;
        }
        List<WebElement> cellList = tableViewAppoint.findElements(By.className("UIATableCell"));
        for (WebElement cell:cellList){
            List<WebElement> txtList = cell.findElements(By.className("UIAStaticText"));
            int iCount = txtList.size();
            for (int i = 0; i < iCount; i++){
                logger.info("****timeInTableView****" + " AllCell" + txtList.get(i).getText());
                if(txtList.get(i).getText().trim().equals(dateToStr(date))){
                    if(txtList.get(i+1).getText().trim().equals(strTime)){
                        bResult = true;
                        logger.info("****timeInTableView****" + txtList.get(i).getText());
                        logger.info("****timeInTableView****" + txtList.get(i+1).getText());
                        break;
                    }
                }
            }

        }
        return bResult;
    }
}

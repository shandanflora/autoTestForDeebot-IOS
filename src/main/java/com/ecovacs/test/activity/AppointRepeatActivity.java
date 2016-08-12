package com.ecovacs.test.activity;

import com.ecovacs.test.common.Common;
import com.ecovacs.test.common.PropertyData;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by ecosqa on 16/8/9.
 * appointment repeat activity
 */
public class AppointRepeatActivity {

    private static AppointRepeatActivity appointRepeatActivity = null;
    @FindBy(xpath = " //UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]")
    private MobileElement btnBack = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private MobileElement tableView = null;

    //UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[8]/UIAStaticText[1]

    public static AppointRepeatActivity getInstance(){
        if(appointRepeatActivity == null){
            appointRepeatActivity = new AppointRepeatActivity();
        }
        return appointRepeatActivity;
    }

    public void init(IOSDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private String getStrDate(Common.DATE date){
        String strDate = "";
        switch (date){
            case SUN:
                strDate = PropertyData.getProperty("sun");
                break;
            case MON:
                strDate = PropertyData.getProperty("mon");
                break;
            case TUES:
                strDate = PropertyData.getProperty("tues");
                break;
            case WEDN:
                strDate = PropertyData.getProperty("wedn");
                break;
            case TURS:
                strDate = PropertyData.getProperty("turs");
                break;
            case FRI:
                strDate = PropertyData.getProperty("fri");
                break;
            case SAT:
                strDate = PropertyData.getProperty("sat");
                break;
            default:
                break;
        }
        return strDate;
    }

    /**
     * select repeat date
     * @param date DATE
     * @return boolean
     */
    public boolean selectDate(Common.DATE date){
        boolean bResult = false;
        List<MobileElement> cellList = tableView.findElementsByClassName("UIATableCell");
        for(int i = 1; i < cellList.size(); i++){
            MobileElement txt = cellList.get(i).findElementByClassName("UIAStaticText");
            System.out.println(txt.getText());
            if(txt.getText().trim().equals(getStrDate(date))){
                cellList.get(i).click();
                bResult = true;
            }
        }
        return bResult;
    }

    public void clickBack(){
        btnBack.click();
    }


}

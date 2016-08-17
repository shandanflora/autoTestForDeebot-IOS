package com.ecovacs.test.activity;

import com.ecovacs.test.common.PropertyData;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by ecosqa on 16/8/8.
 * setting activity
 */
public class SettingActivity {
    ////UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAStaticText[1]
    private static SettingActivity settingActivity = null;
    private IOSDriver driver = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private WebElement tableView = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAStaticText[1]")
    private WebElement txtTitle = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]")
    private WebElement btnBack = null;

    public static SettingActivity getInstance(){
        if(settingActivity == null){
            settingActivity = new SettingActivity();
        }
        return settingActivity;
    }

    public void init(IOSDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.driver = driver;
    }

    public boolean loadSettingActivity(){
        return txtTitle.getText().equals(PropertyData.getProperty("settingTitle"));
    }

    public void goBack(){
        btnBack.click();
    }

    public boolean clickTableCell(String strCell){
        boolean bResult = false;
        List<WebElement> cellLists = tableView.findElements(By.className("UIATableCell"));
        for(WebElement cell:cellLists){
            List<WebElement> txtLists = cell.findElements(By.className("UIAStaticText"));
            for(WebElement txt:txtLists){
                if(txt.getText().trim().equals(strCell)){
                    txt.click();
                    bResult = true;
                }
            }
        }
        return bResult;
    }

    public String  getHardWareVer(String strCell){
        String strVer = "";
        List<WebElement> cellLists = tableView.findElements(By.className("UIATableCell"));
        for(WebElement cell:cellLists){
            List<WebElement> txtLists = cell.findElements(By.className("UIAStaticText"));
            for(int i = 0; i < txtLists.size(); i++){
                if(txtLists.get(i).getText().trim().equals(strCell)){
                    strVer = txtLists.get(i + 1).getText();
                }
            }
        }
        return strVer;
    }
}

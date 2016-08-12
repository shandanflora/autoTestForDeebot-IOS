package com.ecovacs.test.activity;

import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by ecosqa on 16/8/8.
 * setting activity
 */
public class SettingActivity {
    ////UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAStaticText[1]
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATableView[1]")
    private WebElement tableView = null;

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAStaticText[1]")
    private WebElement txtTitle = null;

    public boolean loadSettingActivity(){
        return txtTitle.getText().equals(PropertyData.getProperty("settingTitle"));
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

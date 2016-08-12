package com.ecovacs.test.activity;

import com.ecovacs.test.common.PropertyData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ecosqa on 16/8/1.
 * Login activity
 */
public class LoginActivity {

    private static Logger logger = LoggerFactory.getLogger(LoginActivity.class);

    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[2]")
    private WebElement btnWelcomeLogin = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIATextField[2]")
    private WebElement textUserID = null;
    @FindBy(className = "UIASecureTextField")
    private WebElement textPassword = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAButton[4]")
    private WebElement btnLoginLogin = null;
    @FindBy(xpath = "//UIAApplication[1]/UIAWindow[1]/UIAImage[3]")
    private WebElement iconUser = null;

    public boolean loginClick(){

        if (!btnWelcomeLogin.getText().equals(PropertyData.getProperty("login"))){
            logger.error("*******Login*******The button of login in welcome page is: "
                    + btnWelcomeLogin.getText());
            return false;
        }
        btnWelcomeLogin.click();
        //check login activity
        if (!btnLoginLogin.getText().equals(PropertyData.getProperty("login"))){
            //logger.error("*******Login*******button text is: " + btnLoginLogin.getText());
            //logger.error("*******Login*******expected text is: " + PropertyData.getProperty("login"));
            logger.error("*******Login*******The button of login in login page is: "
                    + btnLoginLogin.getText());
            return false;
        }
        //input user id
        textUserID.clear();
        textUserID.sendKeys(PropertyData.getProperty("userid"));
        iconUser.click();
        //input password
        textPassword.clear();
        textPassword.sendKeys(PropertyData.getProperty("pwd"));
        iconUser.click();
        btnLoginLogin.click();

        return true;
    }
}

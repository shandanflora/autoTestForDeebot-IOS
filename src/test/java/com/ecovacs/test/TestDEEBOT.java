package com.ecovacs.test;

import com.ecovacs.test.common.Common;
import io.appium.java_client.ios.IOSDriver;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//import javax.annotation.Resource;

/**
 * Created by ecosqa on 16/7/26.
 * description: test for DEEBOT
 *
 */
//@ContextConfiguration(locations = "classpath:spring.xml")
public class TestDEEBOT /*extends AbstractTestNGSpringContextTests*/{
    //para
    private IOSDriver driver = null;
    /*@Resource
    private HandleDEEBOT handleDEEBOT = null;
    @Resource
    private Common common = null;*/

    @BeforeClass
    public void setUp(){
        /*String strPath = getClass().getResource("/").getPath();
        strPath = strPath + "../";*/
        driver = Common.getInstance().getDriver();
        if(driver == null){
            return;
        }
        HandleDEEBOT.getInstance().init(driver);
    }

    @Test(priority = 1)
    public void testLogin(){
        Assert.assertTrue(HandleDEEBOT.getInstance().Login());
    }

    @Test(priority = 2)
    public void testLoadControllActivity() {
        Assert.assertTrue(HandleDEEBOT.getInstance().LoadControllActivity());
    }

    /*@Test(priority = 3)
    public void testStandardAutoClean() {
        Assert.assertTrue(HandleDEEBOT.getInstance().standardAutoClean());
    }

    @Test(priority = 4)
    public void testStandardSingleClean(){
        Assert.assertTrue(HandleDEEBOT.getInstance().standardSingleClean());
    }

    @Test(priority = 5)
    public void testStandardBorderClean(){
        Assert.assertTrue(HandleDEEBOT.getInstance().standardBorderClean());
    }

    @Test(priority = 6)
    public void testStandardFixedClean(){
        Assert.assertTrue(HandleDEEBOT.getInstance().standardFixedClean());
    }

    @Test(priority = 7)
    public void testStandardChargeClean(){
        Assert.assertTrue(HandleDEEBOT.getInstance().standardChargeClean());
    }

    @Test(priority = 9)
    public void testManualCleanTop(){
        Assert.assertTrue(HandleDEEBOT.getInstance().manualCleanTop());
    }

    @Test(priority = 10)
    public void testManualCleanBottom(){
        Assert.assertTrue(HandleDEEBOT.getInstance().manualCleanBottom());
    }

    @Test(priority = 11)
    public void testManualCleanLeft(){
        Assert.assertTrue(HandleDEEBOT.getInstance().manualCleanLeft());
    }

    @Test(priority = 12)
    public void testManualCleanRight(){
        Assert.assertTrue(HandleDEEBOT.getInstance().manualCleanRight());
    }

    @Test(priority = 13)
    public void testGetHardwareVer(){
        Assert.assertTrue(HandleDEEBOT.getInstance().getHardwareVer());
    }
*/
    @Test(priority = 14)
    public void testTimeAppoint(){
        Assert.assertTrue(HandleDEEBOT.getInstance().addTimeAppoint());
    }

    @Test(priority = 100)
    public void testLogout(){
        Assert.assertTrue(HandleDEEBOT.getInstance().Logout(driver));
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

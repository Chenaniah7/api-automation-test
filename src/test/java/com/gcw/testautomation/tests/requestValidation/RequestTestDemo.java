package com.gcw.testautomation.tests.requestValidation;

import com.gcw.apiautomation.core.BaseStep;
import com.gcw.apiautomation.core.BaseStepFactory;
import com.gcw.apiautomation.listenner.ValidationTestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ValidationTestListener.class)
@Test(groups = "cert")
public class RequestTestDemo {

    @Test(groups = "phoneAddress")
    public void Test1(){
        Assert.assertEquals("1","3");
        System.out.println("request validation test1");
    }

    @Test(groups = "phoneAddress")
    public void Test2(){
        System.out.println("request validation test2");
    }

}

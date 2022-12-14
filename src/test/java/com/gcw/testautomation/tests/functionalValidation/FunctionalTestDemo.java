package com.gcw.testautomation.tests.functionalValidation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcw.apiautomation.core.BaseStep;
import com.gcw.apiautomation.core.BaseStepFactory;
import com.gcw.apiautomation.listenner.ValidationTestListener;
import com.gcw.testautomation.support.beans.PhoneAddress;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ValidationTestListener.class)
@Test(groups = "cert")
public class FunctionalTestDemo {

    private static BaseStep baseStep = BaseStepFactory.getBaseStep();
    private String uri = "http://apis.juhe.cn/mobile/get";

    private String key = "63e0ce7cbab5d7fd305e25746da514ea";

    private String phone ="13808898544";


    @Test(groups = "phoneAddress",enabled = false)
    public void testFrameworkHappyCase() throws JsonProcessingException {
        baseStep.setBaseUri(uri);
        baseStep.updateQueryParam("key",key);
        baseStep.updateQueryParam("phone",phone);
        baseStep.getResource();
        Assert.assertEquals(baseStep.getResourceCode(), HttpStatus.SC_OK);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(baseStep.getResponseJson());
        PhoneAddress phoneAddress = objectMapper.readValue(jsonNode.get("result").toString(), new TypeReference<PhoneAddress>() {});
        Assert.assertEquals(phoneAddress.getProvince(),"广东");
    }


    @Test(groups = "phoneAddress")
    public void TestCertSmoke(){
        System.out.println("cert smoke test rrrrrrrr");
    }

    @Test(groups = "phoneAddress")
    public void TestCertSmoke2(){
        System.out.println("cert smoke test 22222222222");
    }

}

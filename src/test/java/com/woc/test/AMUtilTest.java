package com.woc.test;

import com.woc.am.AMUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class AMUtilTest {

    @Test
    public void getHash(){
        String wocHash = AMUtil.hash("woc");
        System.out.println("wocHash=" + wocHash);
    }

    @Test
    public void hash(){
        String wocHash = AMUtil.hash("woc");
        Assert.assertEquals("138014aadcc837d4f473dd0e2333b9f4", wocHash);

        String pass = AMUtil.hash("pass");
        Assert.assertEquals("1a1dc91c907325c69271ddf0c944bc72", pass);
    }

    @Test
    public void parseDate(){
        String inputDate = "2019-12-13";
        Date date = AMUtil.parseDate(inputDate);
        Assert.assertNotNull(date);
    }
}

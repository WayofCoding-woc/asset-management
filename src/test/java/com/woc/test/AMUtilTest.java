package com.woc.test;

import com.woc.am.AMUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class AMUtilTest {

    @Test
    public void hash(){
        String vivek = AMUtil.hash("vivek");
        Assert.assertEquals("061a01a98f80f415b1431236b62bb10b", vivek);

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

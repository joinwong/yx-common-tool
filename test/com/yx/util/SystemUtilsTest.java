package com.yx.util;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by join on 2018/9/14.
 */
public class SystemUtilsTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetLocalAddr() throws Exception {
        String ip = SystemUtils.getLocalAddr();
        System.out.println(ip);

        Assert.assertNotNull(ip);
    }

    public void testGetServerName() throws Exception {
        String name = SystemUtils.getServerName();
        System.out.println(name);

        Assert.assertNotNull(name);
    }

    public void testCheckIPAddress() throws Exception {
        String ip = "127.0.0.2";
        boolean isIp = SystemUtils.checkIPAddress(ip);

        Assert.assertNotNull(isIp);
        Assert.assertEquals(true,isIp);

        ip = "127.8";
        isIp = SystemUtils.checkIPAddress(ip);
        Assert.assertNotNull(isIp);
        Assert.assertEquals(false,isIp);


    }

    public void testMd5() throws Exception {
        String md5Str = "c5c95c328aa75d16c3f1e2c91cf8f11a";
        String str = SystemUtils.md5("Java is good!");
        System.out.println(str);

        Assert.assertNotNull(str);
        Assert.assertEquals(md5Str,str);
    }

    public void testGetPublicIP() throws Exception {
        String realPubIp = "147.23.53.63";
        String pubIp = SystemUtils.getPublicIP(new String[]{"127.0.0.1","192.168.1.4","147.23.53.63"});

        System.out.println(pubIp);
        Assert.assertNotNull(pubIp);
        Assert.assertEquals(realPubIp,pubIp);
    }

}
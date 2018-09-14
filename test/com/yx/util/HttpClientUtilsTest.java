package com.yx.util;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by join on 2018/9/14.
 */
public class HttpClientUtilsTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGet() throws Exception {

        String actual = HttpClientUtils.get("https://www.baidu.com");

        Assert.assertNotNull(actual);
    }

    public void testGet1() throws Exception {
    }

    public void testGet2() throws Exception {
    }

    public void testGet3() throws Exception {
    }

    public void testPost() throws Exception {
    }

    public void testPost1() throws Exception {
    }

}
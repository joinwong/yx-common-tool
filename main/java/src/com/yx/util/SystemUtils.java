package com.yx.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.util.SystemUtil;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统工具类
 *
 * Created by join on 2018/9/14.
 */
public class SystemUtils {
    private final static Logger logger = LoggerFactory.getLogger(SystemUtil.class);

    /**
     * 获取本机的ip
     * @return
     */
    public static String getLocalAddr() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            return ip;
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }

    /**
     * 本地计算机名
     *
     * @return
     */
    public static String getServerName() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostName();
        } catch (Exception e) {
            logger.error("获取本机名时错误:{}", e);
        }
        return "";
    }

    /**
     * 验证Ip是否合法
     *
     * @param ipaddr
     * @return
     */
    public static boolean checkIPAddress(final String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile(
                "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }

    /**
     * MD5加密算法
     *
     * @param s
     * @return
     */
    public static String md5(final String s) {
        return md5(s, "UTF-8");
    }


    /**
     * MD5加密算法
     *
     * @param s
     *            the s
     * @param charset
     *            null或者""时使用默认字符集
     * @return the string
     */
    public static String md5(final String s, final String charset) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput;
            if (StringUtils.isEmpty(charset)) {
                btInput = s.getBytes();
            } else {
                btInput = s.getBytes(Charset.forName(charset));
            }
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception ex) {
            logger.error("MD5加密算法:", ex);
            return null;
        }
    }

    /**
     * 从IP列表获取公众IP
     * @param ipList
     * @return
     */
    public static String getPublicIP(final String[] ipList) {
        if (ipList == null || ipList.length <= 0) {
            return "";
        }

        String regPattern = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        String ip = "";
        String[] ipArray;
        for (int i = 0; i < ipList.length; i++) {
            if (StringUtils.isEmpty(ipList[i]))
                continue;

            ip = ipList[i].trim();
            if (Pattern.matches(regPattern, ip) && !ip.startsWith("192.168.") && !ip.startsWith("255.")
                    && !ip.startsWith("127.") && !ip.startsWith("10.")) {
                if (ip.startsWith("172.")) {
                    ipArray = ip.split("\\.");
                    if (ipArray.length > 1 && Integer.parseInt(ipArray[1]) >= 16
                            && Integer.parseInt(ipArray[1]) <= 31) {
                        continue;
                    }
                }
                return ip;
            }
        }

        return "";
    }
}

package com.complex.practice.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密
 * @author admin
 *
 */
public class MD5Util {
	private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
    private static final String[] strDigits = { "0", "1", "2", "3", "4", "5",
             "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	public static String getMD5(String source) throws NoSuchAlgorithmException{
		LOGGER.debug("source:"+source);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytes = md.digest(source.getBytes());
		StringBuffer sb = new StringBuffer();
		for(byte b : bytes){
			sb.append(byteToArrayString(b));
		}
		LOGGER.debug("getMD5:"+sb.toString());
		return sb.toString();
	}
	
	/**
	 *  返回形式为数字跟字符串
	 * @param bByte
	 * @return
	 */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
    
}

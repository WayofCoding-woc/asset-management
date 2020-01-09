package com.woc.am;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AMUtil {
    private static final Logger logger = LoggerFactory.getLogger(AMUtil.class);

    public static final String USER_ID_PREFIX = "AM-";

    public static Date parseDate(String date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            logger.error("Unable to parse input date={}", date, e);
        }
        return null;
    }

    public static String formatToDatePartOnly(Date date){
        return new SimpleDateFormat("dd-MMM-yyyy").format(date);
    }


    public static String hash(String input){
        //refer this link for more details
        //https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(input.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Unable to do the hash for given input");

    }
}

package com.xingbingxuan.blog.utils;

import java.util.Random;

/**
 * 验证码工具
 * @author : xbx
 * @date : 2022/8/12 22:48
 */
public class CodeUtil {



    /**
     * 功能描述:
     * <p>生成一个6位的验证码，包含数字和字母</p>
     *
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/8/12 22:50
     */
    public static String createSixVerificationCode(){
        //生成验证码
        String codeNum = "";
        int [] code = new int[3];
        Random random = new Random();
        //自动生成验证码
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum+=(char)code[random.nextInt(3)];
        }
        return codeNum;
    }
}

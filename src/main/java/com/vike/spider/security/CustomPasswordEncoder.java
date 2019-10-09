package com.vike.spider.security;

import com.vike.spider.utils.EncryptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: lsl
 * @createDate: 2019/9/29
 */
public class CustomPasswordEncoder implements PasswordEncoder {


    @Override
    public String encode(CharSequence charSequence) {
        return EncryptUtils.MD5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String[] split = s.split("0000");
        if(split.length!=2) return false;
        return EncryptUtils.MD5(charSequence.toString()+split[1]).equals(split[0]);
    }
}

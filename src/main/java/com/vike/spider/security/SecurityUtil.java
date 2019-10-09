package com.vike.spider.security;

import com.vike.spider.common.Common;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author: lsl
 * @createDate: 2019/9/29
 */
public class SecurityUtil {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public static Collection<? extends GrantedAuthority> getAllPermission(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }

    public static boolean hasPermission(String permission){
        if(StringUtils.isEmpty(permission)){
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = false;
        for(GrantedAuthority grantedAuthority : authorities){
            String authority = grantedAuthority.getAuthority();
            if(authority.equals(permission)){
                hasPermission =true;
            }
        }
        return hasPermission;
    }


    public static ClientDetail getClientDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ClientDetail) authentication.getPrincipal();
    }

    public static ClientDetail getClientDetail(String menu){

        ClientDetail clientDetail = getClientDetail();

        return Common.parse(clientDetail,menu);
    }


    public static void logout(){
        SecurityContextHolder.clearContext();
    }
}

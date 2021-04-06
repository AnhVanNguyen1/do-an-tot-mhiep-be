package com.shop.shop.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lê Thị Thúy
 * @created 3/31/2021
 * @project shop
 */
public class Utitily {
    public static String getSiteURL(HttpServletRequest request){
        String siteUrl =request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(),"");
    }
}

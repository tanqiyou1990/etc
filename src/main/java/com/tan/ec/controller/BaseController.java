
package com.tan.ec.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


public class BaseController {

    @Autowired
    protected HttpServletRequest request;

}

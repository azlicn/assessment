package com.maybank.assessment.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ILoggingService {
    void displayReq(HttpServletRequest request, Object body);
    void displayResp(HttpServletRequest request, HttpServletResponse response, Object body);
}

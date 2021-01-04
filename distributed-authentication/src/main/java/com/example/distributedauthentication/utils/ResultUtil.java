package com.example.distributedauthentication.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.custom.SystemStatusCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class ResultUtil {
    public static void writeJavaScript(HttpServletResponse response,int httpServletResponseStatus, SystemStatusCode systemStatusCode, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 状态

        response.setStatus(httpServletResponseStatus);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONObject.toJSONString(new ResultMessage().error(systemStatusCode.getCode(), msg)));
        printWriter.flush();
    }
}

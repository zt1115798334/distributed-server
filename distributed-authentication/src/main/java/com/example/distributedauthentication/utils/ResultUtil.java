package com.example.distributedauthentication.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.custom.SystemStatusCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultUtil {
    public static void writeJavaScript(HttpServletResponse response, SystemStatusCode systemStatusCode, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 状态
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONObject.toJSONString(new ResultMessage().error(systemStatusCode.getCode())));
        printWriter.flush();
    }
}

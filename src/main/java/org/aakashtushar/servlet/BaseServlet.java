package org.aakashtushar.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class BaseServlet extends HttpServlet {
    protected ObjectMapper objectMapper = new ObjectMapper();

    protected void success(HttpServletResponse resp, Object body) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(resp.SC_OK);

        String content = objectMapper.writeValueAsString(body);
        resp.getWriter().print(content);
        resp.getWriter().flush();
    }

    protected <T> T read(HttpServletRequest req, Class<T> valueType) throws IOException {
        return objectMapper.readValue(req.getInputStream(), valueType);
    }
}

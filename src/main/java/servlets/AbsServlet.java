package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static utils.Constants.APPLICATION_JSON;


public abstract class AbsServlet extends HttpServlet {
    protected UserService userService;
    protected ObjectMapper objectMapper;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("USER_SERVICE");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("OBJECT_MAPPER");
    }

    protected <T> void writeResponse(HttpServletResponse resp, T body) throws IOException {
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        out.print(objectMapper.writeValueAsString(body));
        out.flush();
    }

    protected <T> T readRequest(BufferedReader br, Class<T> type) throws IOException {
        return objectMapper.readValue(br, type);
    }
}

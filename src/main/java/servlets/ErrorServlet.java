package servlets;

import errors.ErrorResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends AbsServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handle(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ErrorResponse.ErrorResponseBuilder errorResponseBuilder = ErrorResponse.builder();

        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String message = (String) req.getAttribute("javax.servlet.error.message");
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");

        super.writeResponse(
                resp,
                errorResponseBuilder
                    .code(statusCode)
                    .uri(requestUri)
                    .message(message)
                    .build()
        );
    }


}

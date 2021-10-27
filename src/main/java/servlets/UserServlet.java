package servlets;

import dao.models.User;
import dao.models.dto.NewUser;
import utils.RegistrationRequest;
import utils.RegistrationResponse;
import utils.StatusCode;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/api/users/*"})
public class UserServlet extends AbsServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {

            try {
                resp.setStatus(StatusCode.OK.getCode());
                super.writeResponse(resp, userService.findById(id));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                resp.setStatus(StatusCode.OK.getCode());
                super.writeResponse(resp, userService.getAll());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RegistrationRequest registrationRequest = super.readRequest(req.getReader(), RegistrationRequest.class);

        NewUser user = NewUser.builder()
                .login(registrationRequest.getLogin())
                .password(registrationRequest.getPassword())
                .build();

        try {
            String userId = userService.create(user);

            resp.setStatus(StatusCode.CREATED.getCode());
            super.writeResponse(resp, new RegistrationResponse(userId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                userService.deleteById(id);

                resp.setStatus(StatusCode.OK.getCode());
                super.writeResponse(resp, "");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                User user = userService.findById(id);
                RegistrationRequest registrationRequest = super.readRequest(req.getReader(), RegistrationRequest.class);

                for (Field field : registrationRequest.getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    Field userField = user.getClass().getDeclaredField(field.getName());
                    userField.setAccessible(true);
                    Object value = field.get(registrationRequest);
                    userField.set(user, value);
                }

                userService.update(user);

                resp.setStatus(StatusCode.OK.getCode());
                super.writeResponse(resp, userService.findById(id));

            } catch (NoSuchFieldException | IllegalAccessException | SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

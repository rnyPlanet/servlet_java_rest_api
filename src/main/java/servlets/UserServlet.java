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

@WebServlet(urlPatterns = {"/api/users/*"})
public class UserServlet extends AbsServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            resp.setStatus(StatusCode.OK.getCode());
            super.writeResponse(resp, userService.findById(id));
        } else {
            resp.setStatus(StatusCode.OK.getCode());
            super.writeResponse(resp, userService.getAll());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RegistrationRequest registrationRequest = super.readRequest(req.getReader(), RegistrationRequest.class);

        NewUser user = NewUser.builder()
                .login(registrationRequest.getLogin())
                .password(registrationRequest.getPassword())
                .build();

        String userId = userService.create(user);

        resp.setStatus(StatusCode.CREATED.getCode());
        super.writeResponse(resp, new RegistrationResponse(userId));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            userService.deleteById(id);

            resp.setStatus(StatusCode.OK.getCode());
            super.writeResponse(resp, "");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            User user = userService.findById(id);
            req.getParameterMap().forEach((k, v) -> {
                try {
                    Field field = user.getClass().getDeclaredField(k);
                    field.setAccessible(true);
                    field.set(user, v[0]);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            resp.setStatus(StatusCode.OK.getCode());
            super.writeResponse(resp, userService.findById(id));
        }
    }
}

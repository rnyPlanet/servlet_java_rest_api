package listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.data.UserRepositoryImpl;
import dao.repository.UserRepository;
import dao.service.UserService;
import dao.service.impl.UserServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        final UserRepository USER_REPOSITORY = new UserRepositoryImpl();
        final UserService USER_SERVICE = new UserServiceImpl(USER_REPOSITORY);

        ctx.setAttribute("USER_SERVICE", USER_SERVICE);
        ctx.setAttribute("OBJECT_MAPPER", OBJECT_MAPPER);

        System.out.println("USER_SERVICE Connection initialized successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

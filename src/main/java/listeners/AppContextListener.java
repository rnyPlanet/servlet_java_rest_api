package listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.connection.ConnectionDB;
import dao.repository.impl.UserRepositoryImpl;
import dao.repository.UserRepository;
import dao.service.UserService;
import dao.service.impl.UserServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {
    private ConnectionDB connection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        try {
            this.connection = new ConnectionDB();


            final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
            final UserRepository USER_REPOSITORY = new UserRepositoryImpl(connection);
            final UserService USER_SERVICE = new UserServiceImpl(USER_REPOSITORY);

            ctx.setAttribute("USER_SERVICE", USER_SERVICE);
            ctx.setAttribute("OBJECT_MAPPER", OBJECT_MAPPER);

            System.out.println("USER_SERVICE Connection initialized successfully.");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            this.connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

package servlets;

import DataBase.Points;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main") //основной сервлет для передачи параметров
public class ControllerServlet extends HttpServlet {
    @EJB
    private Points points;

    @Override //Добавляет точку и возвращает json со всеми точками
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        String answer = points.getPoints(token);
        resp.getWriter().write(answer);
    }

    @Override //Возвращает json со всеми точками
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        String x = req.getParameter("x");
        String y = req.getParameter("y");
        String r = req.getParameter("r");
        String answer = points.addPoint(token, x, y, r);
        if (answer.equals("Unauthorized") || answer.equals("Bad format")) {
            resp.setStatus(403);
        }
        resp.getWriter().write(answer);
    }
}

package servlets;

import DataBase.Points;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Stateful
@SessionScoped
@Path("/main") //основной сервлет для передачи параметров
public class ControllerServlet{
    @EJB
    private Points points;

    @GET
    @Path("/")
    //Добавляет точку и возвращает json со всеми точками
    public void getPoints(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        String answer = points.getPoints(token);
        resp.getWriter().write(answer);
    }

    @POST
    @Path("/")
    //Возвращает json со всеми точками
    public void getJsonAllPoints(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException {
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

package navigation;

import DataBase.Auth;
import DataBase.Points;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/main")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MainPath {
    @EJB
    private Points points;

    @POST
    //Дафак здесь вообще происходит а
    public String getJsonAllPoints(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        String pointsFlag = object.optString("pointsFlag");

        String token = object.optString("token");
        String x = object.optString("x");
        String y = object.optString("y");
        String r = object.optString("r");

        if (Integer.parseInt(pointsFlag) == 1 || pointsFlag == "true") {
            String result = points.addPoint(token, x, y, r);
            if (result.equals("Unauthorized") || result.equals("Bad format")) {
                return "false";
            } else return result;
        }
        else {
            return points.getPoints(token);
        }
    }
}

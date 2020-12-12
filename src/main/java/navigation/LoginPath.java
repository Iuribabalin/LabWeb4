package navigation;

import DataBase.Auth;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class LoginPath {
    @EJB
    private Auth auth;

    @POST
    public String checkAuth(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        String username = object.optString("username");
        String password = object.optString("pass");

        String result = auth.login(username, password);

        if (result.equals("No such user found") || result.equals("Not authorized")){
            return "403";
        }
        else return result;
    }
}

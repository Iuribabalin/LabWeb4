package DataBase;

import model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Stateless //Вся бизнес логика про авторизацию
public class Auth {
    @EJB
    private UserDB userDB;

    public String register(String login, String password) { //Регситрация
        String user = userDB.ifExist(login);
        if (user.equals("false1") || user.equals("false2")) { //Проверка на существующего пользователя
            User user2 = userDB.createUser(login, password);
            if (user2 == null) {
                return "false";
            }
            else return "true";
        }
        return "false";

    }

    public String login(String login, String password) { //Вход - передача логина и пароля
        String user = userDB.ifExist(login);
        if (user.equals("false1") || user.equals("false2")){
            return "false;-2";
        }
        else if (userDB.checkPassword(login, password)) {
            //userDB.findUser(user).generateAccessToken();
            //userDB.saveUser(userDB.findUser(user));
            //Кодирование логина и пароля
            return "true;" + Base64.getEncoder().encodeToString((login + (char) (31) + userDB.findUser(user).getAccessToken()).getBytes(StandardCharsets.UTF_8));
        }
        return "false;-1";
    }

    public User getUserByToken(String data){ //Пооск пользователя по переданным данным дальнейшего взятия точек
        /*String token = new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
        String[] split = token.split(String.valueOf((char) (31)));
        String username = split[0];
        return userDB.findUser(username);*/
        return null;
    }
}

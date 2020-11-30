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
        User user = userDB.findUser(login);
        if (user != null) { //Проверка на существующего пользователя
            return "User already exists";
        }
        user = userDB.createUser(login, password);
        if (user == null) {
            return "Error";
        }
        return user.getId().toString();
    }

    public String login(String login, String password) { //Вход - передача логина и пароля
        User user = userDB.findUser(login);
        if (user == null) {
            return "No such user";
        }
        if (userDB.checkPassword(user, password)) {
            user.generateAccessToken();
            userDB.saveUser(user);
            //Кодирование логина и пароля
            return Base64.getEncoder().encodeToString((login + (char) (31) + user.getAccessToken()).getBytes(StandardCharsets.UTF_8));
        }
        return "Not authorized";
    }

    public User getUserByToken(String data){ //Пооск пользователя по переданным данным дальнейшего взятия точек
        String token = new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
        String[] split = token.split(String.valueOf((char) (31)));
        String username = split[0];
        return userDB.findUser(username);
    }
}

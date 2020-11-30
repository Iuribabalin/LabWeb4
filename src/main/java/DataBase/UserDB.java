package DataBase;

import model.User;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.Base64;

@Stateful // Методы управленя пользователями
public class UserDB {
    private final EntityManager entityManager = Persistence.
            createEntityManagerFactory("default").
            createEntityManager();

    public User createUser(String login, String password) { //Создание пользователя
        try {
            final User entity = new User();
            entity.setLogin(login);
            entity.setPassword(Base64.getEncoder().encodeToString((password).getBytes())); //Кодирование пароля
            entityManager.persist(entity);
            entityManager.flush();

            return entity;

        } catch (PersistenceException e) {
            return null;
        }
    }

    public boolean saveUser(User user) { //Обновление сведений о пользователе
        try {
            entityManager.merge(user);
            entityManager.flush();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkPassword(User login, String password) { //Проверка пароля
        return login.getPassword().equals(new String(Base64.getDecoder().decode((password)))); //Декодирование
    }

    public User findUser(String login) { //Поиск существующего логина
        return entityManager.createQuery(" from User where login = :login", User.class)
                .setParameter("login", login).getSingleResult();
    }
}

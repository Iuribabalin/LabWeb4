package DataBase;

import model.User;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.Base64;

@Stateful // Методы управленя пользователями
public class UserDB {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager em = factory.createEntityManager();

    public User createUser(String login, String password) { //Создание пользователя
        try {

            final User entity = new User();
            entity.setLogin(login);
            entity.setPassword(Base64.getEncoder().encodeToString((password).getBytes())); //Кодирование пароля
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

            return entity;

        } catch (PersistenceException e) {
            return null;

        }
    }

    public boolean saveUser(User user) { //Обновление сведений о пользователе
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkPassword(User login, String password) { //Проверка пароля
        return login.getPassword().equals(new String(Base64.getDecoder().decode((password)))); //Декодирование
    }

    public String findUser(String login) { //Поиск существующего логина
        return String.valueOf(em.createQuery(" from User where login = :login").getResultList());
    }
}

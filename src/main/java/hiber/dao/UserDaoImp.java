package hiber.dao;

import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public void add(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(User.class, user.getId()));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void update(long id, User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.
                createNativeQuery("UPDATE users SET firstName = :firstName, lastName = :lastName, age = :age WHERE id = :id");
        query.setParameter("firstName", user.getFirstName());
        query.setParameter("lastName", user.getLastName());
        query.setParameter("age", user.getAge());
        query.setParameter("id", id);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = entityManagerFactory.createEntityManager().createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(long id) {
         TypedQuery<User> query = entityManagerFactory.createEntityManager().createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
         query.setParameter("id", id);
         return query.getSingleResult();
    }
}

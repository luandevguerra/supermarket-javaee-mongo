package me.shockyng.api.daos;

import me.shockyng.api.data.collections.Product;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T, E> {

    private final EntityManager entityManager;

    protected abstract Class<T> clazz();

    public BaseDAO() {
        this.entityManager = Persistence
                .createEntityManagerFactory("supermarketPU")
                .createEntityManager();
    }

    public List<T> getAll() {
        ArrayList<T> list = (ArrayList<T>) this.entityManager
                .createQuery("from " + clazz().getSimpleName())
                .getResultList();

        return list;
    }

    public T getById(E id) throws NoResultException {
        T entity = (T) this.entityManager
                .createQuery("select o from " + clazz().getSimpleName() + " o where o.id = :id")
                .setParameter("id", id)
                .getSingleResult();

        return entity;
    }

    public T save(T e) {
        beginTransaction();
        entityManager.merge(e);
        commitTransaction();
        return e;
    }

    public T update(T e) {
        return save(e);
    }

    public void deleteById(E id) {
        T e = getById(id);
        beginTransaction();
        entityManager.remove(e);
        commitTransaction();
    }

    private void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    private void commitTransaction() {
        entityManager.getTransaction().begin();
    }

    @SuppressWarnings("unchecked")
    public List<Product> searchProductByName(String productName) {
        String query = "select p from Product p where p.name like :productName";
        Query jpql = entityManager.createQuery(query, Product.class);
        jpql.setParameter("productName", '%' + productName + '%');
        return jpql.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Product> nativeSearchProductByName(String productName) {
        String query = "db.%s.find({ name: {$eq: '%s'}})";
        Query jpql = entityManager.createNativeQuery(
                String.format(query, clazz().getSimpleName(), productName
                ), Product.class);
        return jpql.getResultList();
    }
}

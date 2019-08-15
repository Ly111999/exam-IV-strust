package com.exam.model;

import com.exam.entity.Product;
import com.exam.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private List<Product> products;

    public ProductModel() {
        this.products = new ArrayList<Product>();
        this.products.add(new Product("Sp1", 2, 5, "Sp1"));
        this.products.add(new Product("Sp2", 2, 5, "Sp2"));
        this.products.add(new Product("Sp3", 2, 5, "Sp3"));
        this.products.add(new Product("Sp4", 2, 5, "Sp4"));
        this.products.add(new Product("Sp5", 2, 5, "Sp5"));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Product> findAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("from Product");
            products = query.list();
            transaction.commit();
        } catch (Exception e) {
            products = null;
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return products;
    }

    @SuppressWarnings({"rawtypes"})
    public Product find(int id) {
        Product product = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("from Product where id = :id");
            query.setParameter("id", id);
            product = (Product) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            product = null;
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return product;
    }
}

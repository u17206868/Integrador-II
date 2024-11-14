/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.dto.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dto.Servicio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HP
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getServicioList() == null) {
            categoria.setServicioList(new ArrayList<Servicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Servicio> attachedServicioList = new ArrayList<Servicio>();
            for (Servicio servicioListServicioToAttach : categoria.getServicioList()) {
                servicioListServicioToAttach = em.getReference(servicioListServicioToAttach.getClass(), servicioListServicioToAttach.getId());
                attachedServicioList.add(servicioListServicioToAttach);
            }
            categoria.setServicioList(attachedServicioList);
            em.persist(categoria);
            for (Servicio servicioListServicio : categoria.getServicioList()) {
                Categoria oldCategoriaIdOfServicioListServicio = servicioListServicio.getCategoriaId();
                servicioListServicio.setCategoriaId(categoria);
                servicioListServicio = em.merge(servicioListServicio);
                if (oldCategoriaIdOfServicioListServicio != null) {
                    oldCategoriaIdOfServicioListServicio.getServicioList().remove(servicioListServicio);
                    oldCategoriaIdOfServicioListServicio = em.merge(oldCategoriaIdOfServicioListServicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getId());
            List<Servicio> servicioListOld = persistentCategoria.getServicioList();
            List<Servicio> servicioListNew = categoria.getServicioList();
            List<Servicio> attachedServicioListNew = new ArrayList<Servicio>();
            for (Servicio servicioListNewServicioToAttach : servicioListNew) {
                servicioListNewServicioToAttach = em.getReference(servicioListNewServicioToAttach.getClass(), servicioListNewServicioToAttach.getId());
                attachedServicioListNew.add(servicioListNewServicioToAttach);
            }
            servicioListNew = attachedServicioListNew;
            categoria.setServicioList(servicioListNew);
            categoria = em.merge(categoria);
            for (Servicio servicioListOldServicio : servicioListOld) {
                if (!servicioListNew.contains(servicioListOldServicio)) {
                    servicioListOldServicio.setCategoriaId(null);
                    servicioListOldServicio = em.merge(servicioListOldServicio);
                }
            }
            for (Servicio servicioListNewServicio : servicioListNew) {
                if (!servicioListOld.contains(servicioListNewServicio)) {
                    Categoria oldCategoriaIdOfServicioListNewServicio = servicioListNewServicio.getCategoriaId();
                    servicioListNewServicio.setCategoriaId(categoria);
                    servicioListNewServicio = em.merge(servicioListNewServicio);
                    if (oldCategoriaIdOfServicioListNewServicio != null && !oldCategoriaIdOfServicioListNewServicio.equals(categoria)) {
                        oldCategoriaIdOfServicioListNewServicio.getServicioList().remove(servicioListNewServicio);
                        oldCategoriaIdOfServicioListNewServicio = em.merge(oldCategoriaIdOfServicioListNewServicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = categoria.getId();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<Servicio> servicioList = categoria.getServicioList();
            for (Servicio servicioListServicio : servicioList) {
                servicioListServicio.setCategoriaId(null);
                servicioListServicio = em.merge(servicioListServicio);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Categoria findCategoria(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

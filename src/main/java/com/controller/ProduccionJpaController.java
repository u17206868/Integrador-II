/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dto.Sala;
import com.dto.Servicio;
import com.dto.Persona;
import com.dto.Produccion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HP
 */
public class ProduccionJpaController implements Serializable {

    public ProduccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produccion produccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sala salaId = produccion.getSalaId();
            if (salaId != null) {
                salaId = em.getReference(salaId.getClass(), salaId.getId());
                produccion.setSalaId(salaId);
            }
            Servicio servicioId = produccion.getServicioId();
            if (servicioId != null) {
                servicioId = em.getReference(servicioId.getClass(), servicioId.getId());
                produccion.setServicioId(servicioId);
            }
            Persona tecnicoId = produccion.getTecnicoId();
            if (tecnicoId != null) {
                tecnicoId = em.getReference(tecnicoId.getClass(), tecnicoId.getId());
                produccion.setTecnicoId(tecnicoId);
            }
            em.persist(produccion);
            if (salaId != null) {
                salaId.getProduccionList().add(produccion);
                salaId = em.merge(salaId);
            }
            if (servicioId != null) {
                servicioId.getProduccionList().add(produccion);
                servicioId = em.merge(servicioId);
            }
            if (tecnicoId != null) {
                tecnicoId.getProduccionList().add(produccion);
                tecnicoId = em.merge(tecnicoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produccion produccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produccion persistentProduccion = em.find(Produccion.class, produccion.getId());
            Sala salaIdOld = persistentProduccion.getSalaId();
            Sala salaIdNew = produccion.getSalaId();
            Servicio servicioIdOld = persistentProduccion.getServicioId();
            Servicio servicioIdNew = produccion.getServicioId();
            Persona tecnicoIdOld = persistentProduccion.getTecnicoId();
            Persona tecnicoIdNew = produccion.getTecnicoId();
            if (salaIdNew != null) {
                salaIdNew = em.getReference(salaIdNew.getClass(), salaIdNew.getId());
                produccion.setSalaId(salaIdNew);
            }
            if (servicioIdNew != null) {
                servicioIdNew = em.getReference(servicioIdNew.getClass(), servicioIdNew.getId());
                produccion.setServicioId(servicioIdNew);
            }
            if (tecnicoIdNew != null) {
                tecnicoIdNew = em.getReference(tecnicoIdNew.getClass(), tecnicoIdNew.getId());
                produccion.setTecnicoId(tecnicoIdNew);
            }
            produccion = em.merge(produccion);
            if (salaIdOld != null && !salaIdOld.equals(salaIdNew)) {
                salaIdOld.getProduccionList().remove(produccion);
                salaIdOld = em.merge(salaIdOld);
            }
            if (salaIdNew != null && !salaIdNew.equals(salaIdOld)) {
                salaIdNew.getProduccionList().add(produccion);
                salaIdNew = em.merge(salaIdNew);
            }
            if (servicioIdOld != null && !servicioIdOld.equals(servicioIdNew)) {
                servicioIdOld.getProduccionList().remove(produccion);
                servicioIdOld = em.merge(servicioIdOld);
            }
            if (servicioIdNew != null && !servicioIdNew.equals(servicioIdOld)) {
                servicioIdNew.getProduccionList().add(produccion);
                servicioIdNew = em.merge(servicioIdNew);
            }
            if (tecnicoIdOld != null && !tecnicoIdOld.equals(tecnicoIdNew)) {
                tecnicoIdOld.getProduccionList().remove(produccion);
                tecnicoIdOld = em.merge(tecnicoIdOld);
            }
            if (tecnicoIdNew != null && !tecnicoIdNew.equals(tecnicoIdOld)) {
                tecnicoIdNew.getProduccionList().add(produccion);
                tecnicoIdNew = em.merge(tecnicoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = produccion.getId();
                if (findProduccion(id) == null) {
                    throw new NonexistentEntityException("The produccion with id " + id + " no longer exists.");
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
            Produccion produccion;
            try {
                produccion = em.getReference(Produccion.class, id);
                produccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produccion with id " + id + " no longer exists.", enfe);
            }
            Sala salaId = produccion.getSalaId();
            if (salaId != null) {
                salaId.getProduccionList().remove(produccion);
                salaId = em.merge(salaId);
            }
            Servicio servicioId = produccion.getServicioId();
            if (servicioId != null) {
                servicioId.getProduccionList().remove(produccion);
                servicioId = em.merge(servicioId);
            }
            Persona tecnicoId = produccion.getTecnicoId();
            if (tecnicoId != null) {
                tecnicoId.getProduccionList().remove(produccion);
                tecnicoId = em.merge(tecnicoId);
            }
            em.remove(produccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produccion> findProduccionEntities() {
        return findProduccionEntities(true, -1, -1);
    }

    public List<Produccion> findProduccionEntities(int maxResults, int firstResult) {
        return findProduccionEntities(false, maxResults, firstResult);
    }

    private List<Produccion> findProduccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produccion.class));
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

    public Produccion findProduccion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getProduccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produccion> rt = cq.from(Produccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

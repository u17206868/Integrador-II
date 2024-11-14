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
import com.dto.Comprobante;
import com.dto.DetalleComprobante;
import com.dto.Servicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HP
 */
public class DetalleComprobanteJpaController implements Serializable {

    public DetalleComprobanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleComprobante detalleComprobante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobante comprobanteId = detalleComprobante.getComprobanteId();
            if (comprobanteId != null) {
                comprobanteId = em.getReference(comprobanteId.getClass(), comprobanteId.getId());
                detalleComprobante.setComprobanteId(comprobanteId);
            }
            Servicio servicioId = detalleComprobante.getServicioId();
            if (servicioId != null) {
                servicioId = em.getReference(servicioId.getClass(), servicioId.getId());
                detalleComprobante.setServicioId(servicioId);
            }
            em.persist(detalleComprobante);
            if (comprobanteId != null) {
                comprobanteId.getDetalleComprobanteList().add(detalleComprobante);
                comprobanteId = em.merge(comprobanteId);
            }
            if (servicioId != null) {
                servicioId.getDetalleComprobanteList().add(detalleComprobante);
                servicioId = em.merge(servicioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleComprobante detalleComprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleComprobante persistentDetalleComprobante = em.find(DetalleComprobante.class, detalleComprobante.getId());
            Comprobante comprobanteIdOld = persistentDetalleComprobante.getComprobanteId();
            Comprobante comprobanteIdNew = detalleComprobante.getComprobanteId();
            Servicio servicioIdOld = persistentDetalleComprobante.getServicioId();
            Servicio servicioIdNew = detalleComprobante.getServicioId();
            if (comprobanteIdNew != null) {
                comprobanteIdNew = em.getReference(comprobanteIdNew.getClass(), comprobanteIdNew.getId());
                detalleComprobante.setComprobanteId(comprobanteIdNew);
            }
            if (servicioIdNew != null) {
                servicioIdNew = em.getReference(servicioIdNew.getClass(), servicioIdNew.getId());
                detalleComprobante.setServicioId(servicioIdNew);
            }
            detalleComprobante = em.merge(detalleComprobante);
            if (comprobanteIdOld != null && !comprobanteIdOld.equals(comprobanteIdNew)) {
                comprobanteIdOld.getDetalleComprobanteList().remove(detalleComprobante);
                comprobanteIdOld = em.merge(comprobanteIdOld);
            }
            if (comprobanteIdNew != null && !comprobanteIdNew.equals(comprobanteIdOld)) {
                comprobanteIdNew.getDetalleComprobanteList().add(detalleComprobante);
                comprobanteIdNew = em.merge(comprobanteIdNew);
            }
            if (servicioIdOld != null && !servicioIdOld.equals(servicioIdNew)) {
                servicioIdOld.getDetalleComprobanteList().remove(detalleComprobante);
                servicioIdOld = em.merge(servicioIdOld);
            }
            if (servicioIdNew != null && !servicioIdNew.equals(servicioIdOld)) {
                servicioIdNew.getDetalleComprobanteList().add(detalleComprobante);
                servicioIdNew = em.merge(servicioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = detalleComprobante.getId();
                if (findDetalleComprobante(id) == null) {
                    throw new NonexistentEntityException("The detalleComprobante with id " + id + " no longer exists.");
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
            DetalleComprobante detalleComprobante;
            try {
                detalleComprobante = em.getReference(DetalleComprobante.class, id);
                detalleComprobante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleComprobante with id " + id + " no longer exists.", enfe);
            }
            Comprobante comprobanteId = detalleComprobante.getComprobanteId();
            if (comprobanteId != null) {
                comprobanteId.getDetalleComprobanteList().remove(detalleComprobante);
                comprobanteId = em.merge(comprobanteId);
            }
            Servicio servicioId = detalleComprobante.getServicioId();
            if (servicioId != null) {
                servicioId.getDetalleComprobanteList().remove(detalleComprobante);
                servicioId = em.merge(servicioId);
            }
            em.remove(detalleComprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleComprobante> findDetalleComprobanteEntities() {
        return findDetalleComprobanteEntities(true, -1, -1);
    }

    public List<DetalleComprobante> findDetalleComprobanteEntities(int maxResults, int firstResult) {
        return findDetalleComprobanteEntities(false, maxResults, firstResult);
    }

    private List<DetalleComprobante> findDetalleComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleComprobante.class));
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

    public DetalleComprobante findDetalleComprobante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleComprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleComprobante> rt = cq.from(DetalleComprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

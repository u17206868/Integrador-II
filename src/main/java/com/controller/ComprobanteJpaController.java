/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.dto.Comprobante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.dto.Persona;
import com.dto.DetalleComprobante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luciano
 */
public class ComprobanteJpaController implements Serializable {

    public ComprobanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comprobante comprobante) {
        if (comprobante.getDetalleComprobanteList() == null) {
            comprobante.setDetalleComprobanteList(new ArrayList<DetalleComprobante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = comprobante.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getId());
                comprobante.setPersonaId(personaId);
            }
            List<DetalleComprobante> attachedDetalleComprobanteList = new ArrayList<DetalleComprobante>();
            for (DetalleComprobante detalleComprobanteListDetalleComprobanteToAttach : comprobante.getDetalleComprobanteList()) {
                detalleComprobanteListDetalleComprobanteToAttach = em.getReference(detalleComprobanteListDetalleComprobanteToAttach.getClass(), detalleComprobanteListDetalleComprobanteToAttach.getId());
                attachedDetalleComprobanteList.add(detalleComprobanteListDetalleComprobanteToAttach);
            }
            comprobante.setDetalleComprobanteList(attachedDetalleComprobanteList);
            em.persist(comprobante);
            if (personaId != null) {
                personaId.getComprobanteList().add(comprobante);
                personaId = em.merge(personaId);
            }
            for (DetalleComprobante detalleComprobanteListDetalleComprobante : comprobante.getDetalleComprobanteList()) {
                Comprobante oldComprobanteIdOfDetalleComprobanteListDetalleComprobante = detalleComprobanteListDetalleComprobante.getComprobanteId();
                detalleComprobanteListDetalleComprobante.setComprobanteId(comprobante);
                detalleComprobanteListDetalleComprobante = em.merge(detalleComprobanteListDetalleComprobante);
                if (oldComprobanteIdOfDetalleComprobanteListDetalleComprobante != null) {
                    oldComprobanteIdOfDetalleComprobanteListDetalleComprobante.getDetalleComprobanteList().remove(detalleComprobanteListDetalleComprobante);
                    oldComprobanteIdOfDetalleComprobanteListDetalleComprobante = em.merge(oldComprobanteIdOfDetalleComprobanteListDetalleComprobante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comprobante comprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobante persistentComprobante = em.find(Comprobante.class, comprobante.getId());
            Persona personaIdOld = persistentComprobante.getPersonaId();
            Persona personaIdNew = comprobante.getPersonaId();
            List<DetalleComprobante> detalleComprobanteListOld = persistentComprobante.getDetalleComprobanteList();
            List<DetalleComprobante> detalleComprobanteListNew = comprobante.getDetalleComprobanteList();
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
                comprobante.setPersonaId(personaIdNew);
            }
            List<DetalleComprobante> attachedDetalleComprobanteListNew = new ArrayList<DetalleComprobante>();
            for (DetalleComprobante detalleComprobanteListNewDetalleComprobanteToAttach : detalleComprobanteListNew) {
                detalleComprobanteListNewDetalleComprobanteToAttach = em.getReference(detalleComprobanteListNewDetalleComprobanteToAttach.getClass(), detalleComprobanteListNewDetalleComprobanteToAttach.getId());
                attachedDetalleComprobanteListNew.add(detalleComprobanteListNewDetalleComprobanteToAttach);
            }
            detalleComprobanteListNew = attachedDetalleComprobanteListNew;
            comprobante.setDetalleComprobanteList(detalleComprobanteListNew);
            comprobante = em.merge(comprobante);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getComprobanteList().remove(comprobante);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getComprobanteList().add(comprobante);
                personaIdNew = em.merge(personaIdNew);
            }
            for (DetalleComprobante detalleComprobanteListOldDetalleComprobante : detalleComprobanteListOld) {
                if (!detalleComprobanteListNew.contains(detalleComprobanteListOldDetalleComprobante)) {
                    detalleComprobanteListOldDetalleComprobante.setComprobanteId(null);
                    detalleComprobanteListOldDetalleComprobante = em.merge(detalleComprobanteListOldDetalleComprobante);
                }
            }
            for (DetalleComprobante detalleComprobanteListNewDetalleComprobante : detalleComprobanteListNew) {
                if (!detalleComprobanteListOld.contains(detalleComprobanteListNewDetalleComprobante)) {
                    Comprobante oldComprobanteIdOfDetalleComprobanteListNewDetalleComprobante = detalleComprobanteListNewDetalleComprobante.getComprobanteId();
                    detalleComprobanteListNewDetalleComprobante.setComprobanteId(comprobante);
                    detalleComprobanteListNewDetalleComprobante = em.merge(detalleComprobanteListNewDetalleComprobante);
                    if (oldComprobanteIdOfDetalleComprobanteListNewDetalleComprobante != null && !oldComprobanteIdOfDetalleComprobanteListNewDetalleComprobante.equals(comprobante)) {
                        oldComprobanteIdOfDetalleComprobanteListNewDetalleComprobante.getDetalleComprobanteList().remove(detalleComprobanteListNewDetalleComprobante);
                        oldComprobanteIdOfDetalleComprobanteListNewDetalleComprobante = em.merge(oldComprobanteIdOfDetalleComprobanteListNewDetalleComprobante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = comprobante.getId();
                if (findComprobante(id) == null) {
                    throw new NonexistentEntityException("The comprobante with id " + id + " no longer exists.");
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
            Comprobante comprobante;
            try {
                comprobante = em.getReference(Comprobante.class, id);
                comprobante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprobante with id " + id + " no longer exists.", enfe);
            }
            Persona personaId = comprobante.getPersonaId();
            if (personaId != null) {
                personaId.getComprobanteList().remove(comprobante);
                personaId = em.merge(personaId);
            }
            List<DetalleComprobante> detalleComprobanteList = comprobante.getDetalleComprobanteList();
            for (DetalleComprobante detalleComprobanteListDetalleComprobante : detalleComprobanteList) {
                detalleComprobanteListDetalleComprobante.setComprobanteId(null);
                detalleComprobanteListDetalleComprobante = em.merge(detalleComprobanteListDetalleComprobante);
            }
            em.remove(comprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comprobante> findComprobanteEntities() {
        return findComprobanteEntities(true, -1, -1);
    }

    public List<Comprobante> findComprobanteEntities(int maxResults, int firstResult) {
        return findComprobanteEntities(false, maxResults, firstResult);
    }

    private List<Comprobante> findComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comprobante.class));
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

    public Comprobante findComprobante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comprobante> rt = cq.from(Comprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

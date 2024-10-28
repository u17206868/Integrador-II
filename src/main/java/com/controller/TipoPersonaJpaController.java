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
import com.dto.Persona;
import com.dto.TipoPersona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luciano
 */
public class TipoPersonaJpaController implements Serializable {

    public TipoPersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPersona tipoPersona) {
        if (tipoPersona.getPersonaList() == null) {
            tipoPersona.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : tipoPersona.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getId());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            tipoPersona.setPersonaList(attachedPersonaList);
            em.persist(tipoPersona);
            for (Persona personaListPersona : tipoPersona.getPersonaList()) {
                TipoPersona oldTipoPersonaIdOfPersonaListPersona = personaListPersona.getTipoPersonaId();
                personaListPersona.setTipoPersonaId(tipoPersona);
                personaListPersona = em.merge(personaListPersona);
                if (oldTipoPersonaIdOfPersonaListPersona != null) {
                    oldTipoPersonaIdOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldTipoPersonaIdOfPersonaListPersona = em.merge(oldTipoPersonaIdOfPersonaListPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPersona tipoPersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPersona persistentTipoPersona = em.find(TipoPersona.class, tipoPersona.getId());
            List<Persona> personaListOld = persistentTipoPersona.getPersonaList();
            List<Persona> personaListNew = tipoPersona.getPersonaList();
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getId());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            tipoPersona.setPersonaList(personaListNew);
            tipoPersona = em.merge(tipoPersona);
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    personaListOldPersona.setTipoPersonaId(null);
                    personaListOldPersona = em.merge(personaListOldPersona);
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    TipoPersona oldTipoPersonaIdOfPersonaListNewPersona = personaListNewPersona.getTipoPersonaId();
                    personaListNewPersona.setTipoPersonaId(tipoPersona);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldTipoPersonaIdOfPersonaListNewPersona != null && !oldTipoPersonaIdOfPersonaListNewPersona.equals(tipoPersona)) {
                        oldTipoPersonaIdOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldTipoPersonaIdOfPersonaListNewPersona = em.merge(oldTipoPersonaIdOfPersonaListNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoPersona.getId();
                if (findTipoPersona(id) == null) {
                    throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.");
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
            TipoPersona tipoPersona;
            try {
                tipoPersona = em.getReference(TipoPersona.class, id);
                tipoPersona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.", enfe);
            }
            List<Persona> personaList = tipoPersona.getPersonaList();
            for (Persona personaListPersona : personaList) {
                personaListPersona.setTipoPersonaId(null);
                personaListPersona = em.merge(personaListPersona);
            }
            em.remove(tipoPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPersona> findTipoPersonaEntities() {
        return findTipoPersonaEntities(true, -1, -1);
    }

    public List<TipoPersona> findTipoPersonaEntities(int maxResults, int firstResult) {
        return findTipoPersonaEntities(false, maxResults, firstResult);
    }

    private List<TipoPersona> findTipoPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPersona.class));
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

    public TipoPersona findTipoPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPersona> rt = cq.from(TipoPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

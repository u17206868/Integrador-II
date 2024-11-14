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
import com.dto.Cita;
import java.util.ArrayList;
import java.util.List;
import com.dto.Produccion;
import com.dto.Sala;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HP
 */
public class SalaJpaController implements Serializable {

    public SalaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sala sala) {
        if (sala.getCitaList() == null) {
            sala.setCitaList(new ArrayList<Cita>());
        }
        if (sala.getProduccionList() == null) {
            sala.setProduccionList(new ArrayList<Produccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona clienteId = sala.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                sala.setClienteId(clienteId);
            }
            Persona tecnicoId = sala.getTecnicoId();
            if (tecnicoId != null) {
                tecnicoId = em.getReference(tecnicoId.getClass(), tecnicoId.getId());
                sala.setTecnicoId(tecnicoId);
            }
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : sala.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getId());
                attachedCitaList.add(citaListCitaToAttach);
            }
            sala.setCitaList(attachedCitaList);
            List<Produccion> attachedProduccionList = new ArrayList<Produccion>();
            for (Produccion produccionListProduccionToAttach : sala.getProduccionList()) {
                produccionListProduccionToAttach = em.getReference(produccionListProduccionToAttach.getClass(), produccionListProduccionToAttach.getId());
                attachedProduccionList.add(produccionListProduccionToAttach);
            }
            sala.setProduccionList(attachedProduccionList);
            em.persist(sala);
            if (clienteId != null) {
                clienteId.getSalaList().add(sala);
                clienteId = em.merge(clienteId);
            }
            if (tecnicoId != null) {
                tecnicoId.getSalaList().add(sala);
                tecnicoId = em.merge(tecnicoId);
            }
            for (Cita citaListCita : sala.getCitaList()) {
                Sala oldSalaIdOfCitaListCita = citaListCita.getSalaId();
                citaListCita.setSalaId(sala);
                citaListCita = em.merge(citaListCita);
                if (oldSalaIdOfCitaListCita != null) {
                    oldSalaIdOfCitaListCita.getCitaList().remove(citaListCita);
                    oldSalaIdOfCitaListCita = em.merge(oldSalaIdOfCitaListCita);
                }
            }
            for (Produccion produccionListProduccion : sala.getProduccionList()) {
                Sala oldSalaIdOfProduccionListProduccion = produccionListProduccion.getSalaId();
                produccionListProduccion.setSalaId(sala);
                produccionListProduccion = em.merge(produccionListProduccion);
                if (oldSalaIdOfProduccionListProduccion != null) {
                    oldSalaIdOfProduccionListProduccion.getProduccionList().remove(produccionListProduccion);
                    oldSalaIdOfProduccionListProduccion = em.merge(oldSalaIdOfProduccionListProduccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sala sala) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sala persistentSala = em.find(Sala.class, sala.getId());
            Persona clienteIdOld = persistentSala.getClienteId();
            Persona clienteIdNew = sala.getClienteId();
            Persona tecnicoIdOld = persistentSala.getTecnicoId();
            Persona tecnicoIdNew = sala.getTecnicoId();
            List<Cita> citaListOld = persistentSala.getCitaList();
            List<Cita> citaListNew = sala.getCitaList();
            List<Produccion> produccionListOld = persistentSala.getProduccionList();
            List<Produccion> produccionListNew = sala.getProduccionList();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                sala.setClienteId(clienteIdNew);
            }
            if (tecnicoIdNew != null) {
                tecnicoIdNew = em.getReference(tecnicoIdNew.getClass(), tecnicoIdNew.getId());
                sala.setTecnicoId(tecnicoIdNew);
            }
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getId());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            sala.setCitaList(citaListNew);
            List<Produccion> attachedProduccionListNew = new ArrayList<Produccion>();
            for (Produccion produccionListNewProduccionToAttach : produccionListNew) {
                produccionListNewProduccionToAttach = em.getReference(produccionListNewProduccionToAttach.getClass(), produccionListNewProduccionToAttach.getId());
                attachedProduccionListNew.add(produccionListNewProduccionToAttach);
            }
            produccionListNew = attachedProduccionListNew;
            sala.setProduccionList(produccionListNew);
            sala = em.merge(sala);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getSalaList().remove(sala);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getSalaList().add(sala);
                clienteIdNew = em.merge(clienteIdNew);
            }
            if (tecnicoIdOld != null && !tecnicoIdOld.equals(tecnicoIdNew)) {
                tecnicoIdOld.getSalaList().remove(sala);
                tecnicoIdOld = em.merge(tecnicoIdOld);
            }
            if (tecnicoIdNew != null && !tecnicoIdNew.equals(tecnicoIdOld)) {
                tecnicoIdNew.getSalaList().add(sala);
                tecnicoIdNew = em.merge(tecnicoIdNew);
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    citaListOldCita.setSalaId(null);
                    citaListOldCita = em.merge(citaListOldCita);
                }
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Sala oldSalaIdOfCitaListNewCita = citaListNewCita.getSalaId();
                    citaListNewCita.setSalaId(sala);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldSalaIdOfCitaListNewCita != null && !oldSalaIdOfCitaListNewCita.equals(sala)) {
                        oldSalaIdOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldSalaIdOfCitaListNewCita = em.merge(oldSalaIdOfCitaListNewCita);
                    }
                }
            }
            for (Produccion produccionListOldProduccion : produccionListOld) {
                if (!produccionListNew.contains(produccionListOldProduccion)) {
                    produccionListOldProduccion.setSalaId(null);
                    produccionListOldProduccion = em.merge(produccionListOldProduccion);
                }
            }
            for (Produccion produccionListNewProduccion : produccionListNew) {
                if (!produccionListOld.contains(produccionListNewProduccion)) {
                    Sala oldSalaIdOfProduccionListNewProduccion = produccionListNewProduccion.getSalaId();
                    produccionListNewProduccion.setSalaId(sala);
                    produccionListNewProduccion = em.merge(produccionListNewProduccion);
                    if (oldSalaIdOfProduccionListNewProduccion != null && !oldSalaIdOfProduccionListNewProduccion.equals(sala)) {
                        oldSalaIdOfProduccionListNewProduccion.getProduccionList().remove(produccionListNewProduccion);
                        oldSalaIdOfProduccionListNewProduccion = em.merge(oldSalaIdOfProduccionListNewProduccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = sala.getId();
                if (findSala(id) == null) {
                    throw new NonexistentEntityException("The sala with id " + id + " no longer exists.");
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
            Sala sala;
            try {
                sala = em.getReference(Sala.class, id);
                sala.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sala with id " + id + " no longer exists.", enfe);
            }
            Persona clienteId = sala.getClienteId();
            if (clienteId != null) {
                clienteId.getSalaList().remove(sala);
                clienteId = em.merge(clienteId);
            }
            Persona tecnicoId = sala.getTecnicoId();
            if (tecnicoId != null) {
                tecnicoId.getSalaList().remove(sala);
                tecnicoId = em.merge(tecnicoId);
            }
            List<Cita> citaList = sala.getCitaList();
            for (Cita citaListCita : citaList) {
                citaListCita.setSalaId(null);
                citaListCita = em.merge(citaListCita);
            }
            List<Produccion> produccionList = sala.getProduccionList();
            for (Produccion produccionListProduccion : produccionList) {
                produccionListProduccion.setSalaId(null);
                produccionListProduccion = em.merge(produccionListProduccion);
            }
            em.remove(sala);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sala> findSalaEntities() {
        return findSalaEntities(true, -1, -1);
    }

    public List<Sala> findSalaEntities(int maxResults, int firstResult) {
        return findSalaEntities(false, maxResults, firstResult);
    }

    private List<Sala> findSalaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sala.class));
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

    public Sala findSala(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sala.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sala> rt = cq.from(Sala.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

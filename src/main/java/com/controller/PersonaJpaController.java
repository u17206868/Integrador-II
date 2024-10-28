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
import com.dto.Distrito;
import com.dto.TipoPersona;
import com.dto.Sala;
import java.util.ArrayList;
import java.util.List;
import com.dto.Comprobante;
import com.dto.Cita;
import com.dto.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luciano
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getSalaList() == null) {
            persona.setSalaList(new ArrayList<Sala>());
        }
        if (persona.getSalaList1() == null) {
            persona.setSalaList1(new ArrayList<Sala>());
        }
        if (persona.getComprobanteList() == null) {
            persona.setComprobanteList(new ArrayList<Comprobante>());
        }
        if (persona.getCitaList() == null) {
            persona.setCitaList(new ArrayList<Cita>());
        }
        if (persona.getCitaList1() == null) {
            persona.setCitaList1(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Distrito distritoId = persona.getDistritoId();
            if (distritoId != null) {
                distritoId = em.getReference(distritoId.getClass(), distritoId.getId());
                persona.setDistritoId(distritoId);
            }
            TipoPersona tipoPersonaId = persona.getTipoPersonaId();
            if (tipoPersonaId != null) {
                tipoPersonaId = em.getReference(tipoPersonaId.getClass(), tipoPersonaId.getId());
                persona.setTipoPersonaId(tipoPersonaId);
            }
            List<Sala> attachedSalaList = new ArrayList<Sala>();
            for (Sala salaListSalaToAttach : persona.getSalaList()) {
                salaListSalaToAttach = em.getReference(salaListSalaToAttach.getClass(), salaListSalaToAttach.getId());
                attachedSalaList.add(salaListSalaToAttach);
            }
            persona.setSalaList(attachedSalaList);
            List<Sala> attachedSalaList1 = new ArrayList<Sala>();
            for (Sala salaList1SalaToAttach : persona.getSalaList1()) {
                salaList1SalaToAttach = em.getReference(salaList1SalaToAttach.getClass(), salaList1SalaToAttach.getId());
                attachedSalaList1.add(salaList1SalaToAttach);
            }
            persona.setSalaList1(attachedSalaList1);
            List<Comprobante> attachedComprobanteList = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListComprobanteToAttach : persona.getComprobanteList()) {
                comprobanteListComprobanteToAttach = em.getReference(comprobanteListComprobanteToAttach.getClass(), comprobanteListComprobanteToAttach.getId());
                attachedComprobanteList.add(comprobanteListComprobanteToAttach);
            }
            persona.setComprobanteList(attachedComprobanteList);
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : persona.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getId());
                attachedCitaList.add(citaListCitaToAttach);
            }
            persona.setCitaList(attachedCitaList);
            List<Cita> attachedCitaList1 = new ArrayList<Cita>();
            for (Cita citaList1CitaToAttach : persona.getCitaList1()) {
                citaList1CitaToAttach = em.getReference(citaList1CitaToAttach.getClass(), citaList1CitaToAttach.getId());
                attachedCitaList1.add(citaList1CitaToAttach);
            }
            persona.setCitaList1(attachedCitaList1);
            em.persist(persona);
            if (distritoId != null) {
                distritoId.getPersonaList().add(persona);
                distritoId = em.merge(distritoId);
            }
            if (tipoPersonaId != null) {
                tipoPersonaId.getPersonaList().add(persona);
                tipoPersonaId = em.merge(tipoPersonaId);
            }
            for (Sala salaListSala : persona.getSalaList()) {
                Persona oldClienteIdOfSalaListSala = salaListSala.getClienteId();
                salaListSala.setClienteId(persona);
                salaListSala = em.merge(salaListSala);
                if (oldClienteIdOfSalaListSala != null) {
                    oldClienteIdOfSalaListSala.getSalaList().remove(salaListSala);
                    oldClienteIdOfSalaListSala = em.merge(oldClienteIdOfSalaListSala);
                }
            }
            for (Sala salaList1Sala : persona.getSalaList1()) {
                Persona oldTecnicoIdOfSalaList1Sala = salaList1Sala.getTecnicoId();
                salaList1Sala.setTecnicoId(persona);
                salaList1Sala = em.merge(salaList1Sala);
                if (oldTecnicoIdOfSalaList1Sala != null) {
                    oldTecnicoIdOfSalaList1Sala.getSalaList1().remove(salaList1Sala);
                    oldTecnicoIdOfSalaList1Sala = em.merge(oldTecnicoIdOfSalaList1Sala);
                }
            }
            for (Comprobante comprobanteListComprobante : persona.getComprobanteList()) {
                Persona oldPersonaIdOfComprobanteListComprobante = comprobanteListComprobante.getPersonaId();
                comprobanteListComprobante.setPersonaId(persona);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
                if (oldPersonaIdOfComprobanteListComprobante != null) {
                    oldPersonaIdOfComprobanteListComprobante.getComprobanteList().remove(comprobanteListComprobante);
                    oldPersonaIdOfComprobanteListComprobante = em.merge(oldPersonaIdOfComprobanteListComprobante);
                }
            }
            for (Cita citaListCita : persona.getCitaList()) {
                Persona oldClienteIdOfCitaListCita = citaListCita.getClienteId();
                citaListCita.setClienteId(persona);
                citaListCita = em.merge(citaListCita);
                if (oldClienteIdOfCitaListCita != null) {
                    oldClienteIdOfCitaListCita.getCitaList().remove(citaListCita);
                    oldClienteIdOfCitaListCita = em.merge(oldClienteIdOfCitaListCita);
                }
            }
            for (Cita citaList1Cita : persona.getCitaList1()) {
                Persona oldTecnicoIdOfCitaList1Cita = citaList1Cita.getTecnicoId();
                citaList1Cita.setTecnicoId(persona);
                citaList1Cita = em.merge(citaList1Cita);
                if (oldTecnicoIdOfCitaList1Cita != null) {
                    oldTecnicoIdOfCitaList1Cita.getCitaList1().remove(citaList1Cita);
                    oldTecnicoIdOfCitaList1Cita = em.merge(oldTecnicoIdOfCitaList1Cita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            Distrito distritoIdOld = persistentPersona.getDistritoId();
            Distrito distritoIdNew = persona.getDistritoId();
            TipoPersona tipoPersonaIdOld = persistentPersona.getTipoPersonaId();
            TipoPersona tipoPersonaIdNew = persona.getTipoPersonaId();
            List<Sala> salaListOld = persistentPersona.getSalaList();
            List<Sala> salaListNew = persona.getSalaList();
            List<Sala> salaList1Old = persistentPersona.getSalaList1();
            List<Sala> salaList1New = persona.getSalaList1();
            List<Comprobante> comprobanteListOld = persistentPersona.getComprobanteList();
            List<Comprobante> comprobanteListNew = persona.getComprobanteList();
            List<Cita> citaListOld = persistentPersona.getCitaList();
            List<Cita> citaListNew = persona.getCitaList();
            List<Cita> citaList1Old = persistentPersona.getCitaList1();
            List<Cita> citaList1New = persona.getCitaList1();
            if (distritoIdNew != null) {
                distritoIdNew = em.getReference(distritoIdNew.getClass(), distritoIdNew.getId());
                persona.setDistritoId(distritoIdNew);
            }
            if (tipoPersonaIdNew != null) {
                tipoPersonaIdNew = em.getReference(tipoPersonaIdNew.getClass(), tipoPersonaIdNew.getId());
                persona.setTipoPersonaId(tipoPersonaIdNew);
            }
            List<Sala> attachedSalaListNew = new ArrayList<Sala>();
            for (Sala salaListNewSalaToAttach : salaListNew) {
                salaListNewSalaToAttach = em.getReference(salaListNewSalaToAttach.getClass(), salaListNewSalaToAttach.getId());
                attachedSalaListNew.add(salaListNewSalaToAttach);
            }
            salaListNew = attachedSalaListNew;
            persona.setSalaList(salaListNew);
            List<Sala> attachedSalaList1New = new ArrayList<Sala>();
            for (Sala salaList1NewSalaToAttach : salaList1New) {
                salaList1NewSalaToAttach = em.getReference(salaList1NewSalaToAttach.getClass(), salaList1NewSalaToAttach.getId());
                attachedSalaList1New.add(salaList1NewSalaToAttach);
            }
            salaList1New = attachedSalaList1New;
            persona.setSalaList1(salaList1New);
            List<Comprobante> attachedComprobanteListNew = new ArrayList<Comprobante>();
            for (Comprobante comprobanteListNewComprobanteToAttach : comprobanteListNew) {
                comprobanteListNewComprobanteToAttach = em.getReference(comprobanteListNewComprobanteToAttach.getClass(), comprobanteListNewComprobanteToAttach.getId());
                attachedComprobanteListNew.add(comprobanteListNewComprobanteToAttach);
            }
            comprobanteListNew = attachedComprobanteListNew;
            persona.setComprobanteList(comprobanteListNew);
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getId());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            persona.setCitaList(citaListNew);
            List<Cita> attachedCitaList1New = new ArrayList<Cita>();
            for (Cita citaList1NewCitaToAttach : citaList1New) {
                citaList1NewCitaToAttach = em.getReference(citaList1NewCitaToAttach.getClass(), citaList1NewCitaToAttach.getId());
                attachedCitaList1New.add(citaList1NewCitaToAttach);
            }
            citaList1New = attachedCitaList1New;
            persona.setCitaList1(citaList1New);
            persona = em.merge(persona);
            if (distritoIdOld != null && !distritoIdOld.equals(distritoIdNew)) {
                distritoIdOld.getPersonaList().remove(persona);
                distritoIdOld = em.merge(distritoIdOld);
            }
            if (distritoIdNew != null && !distritoIdNew.equals(distritoIdOld)) {
                distritoIdNew.getPersonaList().add(persona);
                distritoIdNew = em.merge(distritoIdNew);
            }
            if (tipoPersonaIdOld != null && !tipoPersonaIdOld.equals(tipoPersonaIdNew)) {
                tipoPersonaIdOld.getPersonaList().remove(persona);
                tipoPersonaIdOld = em.merge(tipoPersonaIdOld);
            }
            if (tipoPersonaIdNew != null && !tipoPersonaIdNew.equals(tipoPersonaIdOld)) {
                tipoPersonaIdNew.getPersonaList().add(persona);
                tipoPersonaIdNew = em.merge(tipoPersonaIdNew);
            }
            for (Sala salaListOldSala : salaListOld) {
                if (!salaListNew.contains(salaListOldSala)) {
                    salaListOldSala.setClienteId(null);
                    salaListOldSala = em.merge(salaListOldSala);
                }
            }
            for (Sala salaListNewSala : salaListNew) {
                if (!salaListOld.contains(salaListNewSala)) {
                    Persona oldClienteIdOfSalaListNewSala = salaListNewSala.getClienteId();
                    salaListNewSala.setClienteId(persona);
                    salaListNewSala = em.merge(salaListNewSala);
                    if (oldClienteIdOfSalaListNewSala != null && !oldClienteIdOfSalaListNewSala.equals(persona)) {
                        oldClienteIdOfSalaListNewSala.getSalaList().remove(salaListNewSala);
                        oldClienteIdOfSalaListNewSala = em.merge(oldClienteIdOfSalaListNewSala);
                    }
                }
            }
            for (Sala salaList1OldSala : salaList1Old) {
                if (!salaList1New.contains(salaList1OldSala)) {
                    salaList1OldSala.setTecnicoId(null);
                    salaList1OldSala = em.merge(salaList1OldSala);
                }
            }
            for (Sala salaList1NewSala : salaList1New) {
                if (!salaList1Old.contains(salaList1NewSala)) {
                    Persona oldTecnicoIdOfSalaList1NewSala = salaList1NewSala.getTecnicoId();
                    salaList1NewSala.setTecnicoId(persona);
                    salaList1NewSala = em.merge(salaList1NewSala);
                    if (oldTecnicoIdOfSalaList1NewSala != null && !oldTecnicoIdOfSalaList1NewSala.equals(persona)) {
                        oldTecnicoIdOfSalaList1NewSala.getSalaList1().remove(salaList1NewSala);
                        oldTecnicoIdOfSalaList1NewSala = em.merge(oldTecnicoIdOfSalaList1NewSala);
                    }
                }
            }
            for (Comprobante comprobanteListOldComprobante : comprobanteListOld) {
                if (!comprobanteListNew.contains(comprobanteListOldComprobante)) {
                    comprobanteListOldComprobante.setPersonaId(null);
                    comprobanteListOldComprobante = em.merge(comprobanteListOldComprobante);
                }
            }
            for (Comprobante comprobanteListNewComprobante : comprobanteListNew) {
                if (!comprobanteListOld.contains(comprobanteListNewComprobante)) {
                    Persona oldPersonaIdOfComprobanteListNewComprobante = comprobanteListNewComprobante.getPersonaId();
                    comprobanteListNewComprobante.setPersonaId(persona);
                    comprobanteListNewComprobante = em.merge(comprobanteListNewComprobante);
                    if (oldPersonaIdOfComprobanteListNewComprobante != null && !oldPersonaIdOfComprobanteListNewComprobante.equals(persona)) {
                        oldPersonaIdOfComprobanteListNewComprobante.getComprobanteList().remove(comprobanteListNewComprobante);
                        oldPersonaIdOfComprobanteListNewComprobante = em.merge(oldPersonaIdOfComprobanteListNewComprobante);
                    }
                }
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    citaListOldCita.setClienteId(null);
                    citaListOldCita = em.merge(citaListOldCita);
                }
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Persona oldClienteIdOfCitaListNewCita = citaListNewCita.getClienteId();
                    citaListNewCita.setClienteId(persona);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldClienteIdOfCitaListNewCita != null && !oldClienteIdOfCitaListNewCita.equals(persona)) {
                        oldClienteIdOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldClienteIdOfCitaListNewCita = em.merge(oldClienteIdOfCitaListNewCita);
                    }
                }
            }
            for (Cita citaList1OldCita : citaList1Old) {
                if (!citaList1New.contains(citaList1OldCita)) {
                    citaList1OldCita.setTecnicoId(null);
                    citaList1OldCita = em.merge(citaList1OldCita);
                }
            }
            for (Cita citaList1NewCita : citaList1New) {
                if (!citaList1Old.contains(citaList1NewCita)) {
                    Persona oldTecnicoIdOfCitaList1NewCita = citaList1NewCita.getTecnicoId();
                    citaList1NewCita.setTecnicoId(persona);
                    citaList1NewCita = em.merge(citaList1NewCita);
                    if (oldTecnicoIdOfCitaList1NewCita != null && !oldTecnicoIdOfCitaList1NewCita.equals(persona)) {
                        oldTecnicoIdOfCitaList1NewCita.getCitaList1().remove(citaList1NewCita);
                        oldTecnicoIdOfCitaList1NewCita = em.merge(oldTecnicoIdOfCitaList1NewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Distrito distritoId = persona.getDistritoId();
            if (distritoId != null) {
                distritoId.getPersonaList().remove(persona);
                distritoId = em.merge(distritoId);
            }
            TipoPersona tipoPersonaId = persona.getTipoPersonaId();
            if (tipoPersonaId != null) {
                tipoPersonaId.getPersonaList().remove(persona);
                tipoPersonaId = em.merge(tipoPersonaId);
            }
            List<Sala> salaList = persona.getSalaList();
            for (Sala salaListSala : salaList) {
                salaListSala.setClienteId(null);
                salaListSala = em.merge(salaListSala);
            }
            List<Sala> salaList1 = persona.getSalaList1();
            for (Sala salaList1Sala : salaList1) {
                salaList1Sala.setTecnicoId(null);
                salaList1Sala = em.merge(salaList1Sala);
            }
            List<Comprobante> comprobanteList = persona.getComprobanteList();
            for (Comprobante comprobanteListComprobante : comprobanteList) {
                comprobanteListComprobante.setPersonaId(null);
                comprobanteListComprobante = em.merge(comprobanteListComprobante);
            }
            List<Cita> citaList = persona.getCitaList();
            for (Cita citaListCita : citaList) {
                citaListCita.setClienteId(null);
                citaListCita = em.merge(citaListCita);
            }
            List<Cita> citaList1 = persona.getCitaList1();
            for (Cita citaList1Cita : citaList1) {
                citaList1Cita.setTecnicoId(null);
                citaList1Cita = em.merge(citaList1Cita);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

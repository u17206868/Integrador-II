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
import com.dto.Categoria;
import com.dto.DetalleComprobante;
import java.util.ArrayList;
import java.util.List;
import com.dto.Cita;
import com.dto.Produccion;
import com.dto.Servicio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author HP
 */
public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        if (servicio.getDetalleComprobanteList() == null) {
            servicio.setDetalleComprobanteList(new ArrayList<DetalleComprobante>());
        }
        if (servicio.getCitaList() == null) {
            servicio.setCitaList(new ArrayList<Cita>());
        }
        if (servicio.getProduccionList() == null) {
            servicio.setProduccionList(new ArrayList<Produccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoriaId = servicio.getCategoriaId();
            if (categoriaId != null) {
                categoriaId = em.getReference(categoriaId.getClass(), categoriaId.getId());
                servicio.setCategoriaId(categoriaId);
            }
            List<DetalleComprobante> attachedDetalleComprobanteList = new ArrayList<DetalleComprobante>();
            for (DetalleComprobante detalleComprobanteListDetalleComprobanteToAttach : servicio.getDetalleComprobanteList()) {
                detalleComprobanteListDetalleComprobanteToAttach = em.getReference(detalleComprobanteListDetalleComprobanteToAttach.getClass(), detalleComprobanteListDetalleComprobanteToAttach.getId());
                attachedDetalleComprobanteList.add(detalleComprobanteListDetalleComprobanteToAttach);
            }
            servicio.setDetalleComprobanteList(attachedDetalleComprobanteList);
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : servicio.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getId());
                attachedCitaList.add(citaListCitaToAttach);
            }
            servicio.setCitaList(attachedCitaList);
            List<Produccion> attachedProduccionList = new ArrayList<Produccion>();
            for (Produccion produccionListProduccionToAttach : servicio.getProduccionList()) {
                produccionListProduccionToAttach = em.getReference(produccionListProduccionToAttach.getClass(), produccionListProduccionToAttach.getId());
                attachedProduccionList.add(produccionListProduccionToAttach);
            }
            servicio.setProduccionList(attachedProduccionList);
            em.persist(servicio);
            if (categoriaId != null) {
                categoriaId.getServicioList().add(servicio);
                categoriaId = em.merge(categoriaId);
            }
            for (DetalleComprobante detalleComprobanteListDetalleComprobante : servicio.getDetalleComprobanteList()) {
                Servicio oldServicioIdOfDetalleComprobanteListDetalleComprobante = detalleComprobanteListDetalleComprobante.getServicioId();
                detalleComprobanteListDetalleComprobante.setServicioId(servicio);
                detalleComprobanteListDetalleComprobante = em.merge(detalleComprobanteListDetalleComprobante);
                if (oldServicioIdOfDetalleComprobanteListDetalleComprobante != null) {
                    oldServicioIdOfDetalleComprobanteListDetalleComprobante.getDetalleComprobanteList().remove(detalleComprobanteListDetalleComprobante);
                    oldServicioIdOfDetalleComprobanteListDetalleComprobante = em.merge(oldServicioIdOfDetalleComprobanteListDetalleComprobante);
                }
            }
            for (Cita citaListCita : servicio.getCitaList()) {
                Servicio oldServicioIdOfCitaListCita = citaListCita.getServicioId();
                citaListCita.setServicioId(servicio);
                citaListCita = em.merge(citaListCita);
                if (oldServicioIdOfCitaListCita != null) {
                    oldServicioIdOfCitaListCita.getCitaList().remove(citaListCita);
                    oldServicioIdOfCitaListCita = em.merge(oldServicioIdOfCitaListCita);
                }
            }
            for (Produccion produccionListProduccion : servicio.getProduccionList()) {
                Servicio oldServicioIdOfProduccionListProduccion = produccionListProduccion.getServicioId();
                produccionListProduccion.setServicioId(servicio);
                produccionListProduccion = em.merge(produccionListProduccion);
                if (oldServicioIdOfProduccionListProduccion != null) {
                    oldServicioIdOfProduccionListProduccion.getProduccionList().remove(produccionListProduccion);
                    oldServicioIdOfProduccionListProduccion = em.merge(oldServicioIdOfProduccionListProduccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getId());
            Categoria categoriaIdOld = persistentServicio.getCategoriaId();
            Categoria categoriaIdNew = servicio.getCategoriaId();
            List<DetalleComprobante> detalleComprobanteListOld = persistentServicio.getDetalleComprobanteList();
            List<DetalleComprobante> detalleComprobanteListNew = servicio.getDetalleComprobanteList();
            List<Cita> citaListOld = persistentServicio.getCitaList();
            List<Cita> citaListNew = servicio.getCitaList();
            List<Produccion> produccionListOld = persistentServicio.getProduccionList();
            List<Produccion> produccionListNew = servicio.getProduccionList();
            if (categoriaIdNew != null) {
                categoriaIdNew = em.getReference(categoriaIdNew.getClass(), categoriaIdNew.getId());
                servicio.setCategoriaId(categoriaIdNew);
            }
            List<DetalleComprobante> attachedDetalleComprobanteListNew = new ArrayList<DetalleComprobante>();
            for (DetalleComprobante detalleComprobanteListNewDetalleComprobanteToAttach : detalleComprobanteListNew) {
                detalleComprobanteListNewDetalleComprobanteToAttach = em.getReference(detalleComprobanteListNewDetalleComprobanteToAttach.getClass(), detalleComprobanteListNewDetalleComprobanteToAttach.getId());
                attachedDetalleComprobanteListNew.add(detalleComprobanteListNewDetalleComprobanteToAttach);
            }
            detalleComprobanteListNew = attachedDetalleComprobanteListNew;
            servicio.setDetalleComprobanteList(detalleComprobanteListNew);
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getId());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            servicio.setCitaList(citaListNew);
            List<Produccion> attachedProduccionListNew = new ArrayList<Produccion>();
            for (Produccion produccionListNewProduccionToAttach : produccionListNew) {
                produccionListNewProduccionToAttach = em.getReference(produccionListNewProduccionToAttach.getClass(), produccionListNewProduccionToAttach.getId());
                attachedProduccionListNew.add(produccionListNewProduccionToAttach);
            }
            produccionListNew = attachedProduccionListNew;
            servicio.setProduccionList(produccionListNew);
            servicio = em.merge(servicio);
            if (categoriaIdOld != null && !categoriaIdOld.equals(categoriaIdNew)) {
                categoriaIdOld.getServicioList().remove(servicio);
                categoriaIdOld = em.merge(categoriaIdOld);
            }
            if (categoriaIdNew != null && !categoriaIdNew.equals(categoriaIdOld)) {
                categoriaIdNew.getServicioList().add(servicio);
                categoriaIdNew = em.merge(categoriaIdNew);
            }
            for (DetalleComprobante detalleComprobanteListOldDetalleComprobante : detalleComprobanteListOld) {
                if (!detalleComprobanteListNew.contains(detalleComprobanteListOldDetalleComprobante)) {
                    detalleComprobanteListOldDetalleComprobante.setServicioId(null);
                    detalleComprobanteListOldDetalleComprobante = em.merge(detalleComprobanteListOldDetalleComprobante);
                }
            }
            for (DetalleComprobante detalleComprobanteListNewDetalleComprobante : detalleComprobanteListNew) {
                if (!detalleComprobanteListOld.contains(detalleComprobanteListNewDetalleComprobante)) {
                    Servicio oldServicioIdOfDetalleComprobanteListNewDetalleComprobante = detalleComprobanteListNewDetalleComprobante.getServicioId();
                    detalleComprobanteListNewDetalleComprobante.setServicioId(servicio);
                    detalleComprobanteListNewDetalleComprobante = em.merge(detalleComprobanteListNewDetalleComprobante);
                    if (oldServicioIdOfDetalleComprobanteListNewDetalleComprobante != null && !oldServicioIdOfDetalleComprobanteListNewDetalleComprobante.equals(servicio)) {
                        oldServicioIdOfDetalleComprobanteListNewDetalleComprobante.getDetalleComprobanteList().remove(detalleComprobanteListNewDetalleComprobante);
                        oldServicioIdOfDetalleComprobanteListNewDetalleComprobante = em.merge(oldServicioIdOfDetalleComprobanteListNewDetalleComprobante);
                    }
                }
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    citaListOldCita.setServicioId(null);
                    citaListOldCita = em.merge(citaListOldCita);
                }
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Servicio oldServicioIdOfCitaListNewCita = citaListNewCita.getServicioId();
                    citaListNewCita.setServicioId(servicio);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldServicioIdOfCitaListNewCita != null && !oldServicioIdOfCitaListNewCita.equals(servicio)) {
                        oldServicioIdOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldServicioIdOfCitaListNewCita = em.merge(oldServicioIdOfCitaListNewCita);
                    }
                }
            }
            for (Produccion produccionListOldProduccion : produccionListOld) {
                if (!produccionListNew.contains(produccionListOldProduccion)) {
                    produccionListOldProduccion.setServicioId(null);
                    produccionListOldProduccion = em.merge(produccionListOldProduccion);
                }
            }
            for (Produccion produccionListNewProduccion : produccionListNew) {
                if (!produccionListOld.contains(produccionListNewProduccion)) {
                    Servicio oldServicioIdOfProduccionListNewProduccion = produccionListNewProduccion.getServicioId();
                    produccionListNewProduccion.setServicioId(servicio);
                    produccionListNewProduccion = em.merge(produccionListNewProduccion);
                    if (oldServicioIdOfProduccionListNewProduccion != null && !oldServicioIdOfProduccionListNewProduccion.equals(servicio)) {
                        oldServicioIdOfProduccionListNewProduccion.getProduccionList().remove(produccionListNewProduccion);
                        oldServicioIdOfProduccionListNewProduccion = em.merge(oldServicioIdOfProduccionListNewProduccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = servicio.getId();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
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
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            Categoria categoriaId = servicio.getCategoriaId();
            if (categoriaId != null) {
                categoriaId.getServicioList().remove(servicio);
                categoriaId = em.merge(categoriaId);
            }
            List<DetalleComprobante> detalleComprobanteList = servicio.getDetalleComprobanteList();
            for (DetalleComprobante detalleComprobanteListDetalleComprobante : detalleComprobanteList) {
                detalleComprobanteListDetalleComprobante.setServicioId(null);
                detalleComprobanteListDetalleComprobante = em.merge(detalleComprobanteListDetalleComprobante);
            }
            List<Cita> citaList = servicio.getCitaList();
            for (Cita citaListCita : citaList) {
                citaListCita.setServicioId(null);
                citaListCita = em.merge(citaListCita);
            }
            List<Produccion> produccionList = servicio.getProduccionList();
            for (Produccion produccionListProduccion : produccionList) {
                produccionListProduccion.setServicioId(null);
                produccionListProduccion = em.merge(produccionListProduccion);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

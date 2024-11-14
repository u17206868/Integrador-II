<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="citasJson" value="${citasJson}" />

<t:template title="Ventas">
  <jsp:attribute name="head_area">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />

    <!--<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>-->

    <script src='assets/fullcalendar/dist/index.global.js'></script>
    <script src='assets/fullcalendar/core/locales/es.global.js'></script>
    <script>
      document.addEventListener('DOMContentLoaded', function () {
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
          locale: 'es',
          initialView: 'dayGridMonth',
          headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
          },
          eventClick: function (arg) {
            console.log(arg.event.extendedProps.uuid);
            var eventId = arg.event.id + arg.event.extendedProps.uuid + arg.event.id;
            console.log(eventId);
            $('#' + eventId).modal();
          },
          //          editable: true,
          dayMaxEvents: true,
          events: []
        });
        for (let cita of ${citasJson}) {
          calendar.addEvent({
            id: cita.id,
            title: 'Sala: ' + cita.salaId.nroSala + ' - ' + cita.servicioId.descripcion + ' -> ' + cita.clienteId.nombres + ' ' +cita.clienteId.apellidos,
            start: cita.fechaHora,
            end: cita.fechaHoraEnd,
            extendedProps: {
              department: 'BioChemistry',
              uuid: cita.uniqueId
            },
          });
        }
        calendar.render();
      });
    </script>
  </jsp:attribute>
<jsp:attribute name="body_area">


  <div class="col-md-12">
    <div id='calendar'>

    </div>
  </div>

  <div class="col-md-12">
    <div class="card">
      <div class="card-header">
        <div class="d-flex align-items-center">
          <h4 class="card-title">Ventas Registradas</h4>
          <div class="float-right ml-auto">
            <a class="btn btn-primary btn-border btn-round" href="ReporteResumenServiciosServlet?id=${miPersonaObtenida.id}">
              <i class="far fa-file-pdf"></i> 	
              <i class="fas fa-file-export	"></i>
              Resumen de Ventas
            </a>
            <a class="btn btn-primary btn-border btn-round" href="ReporteCitasServlet">
              <i class="far fa-file-pdf "></i>
              Reporte General de Ventas
            </a>
            <button class="btn btn-primary btn-round" data-toggle="modal" data-target="#addRowModal">
              <i class="fa fa-plus"></i>
              Añadir Nueva Venta
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">

        <!-- Modal Crear venta  -->
        <!--<div class="modal fade" id="addRowModal" tabindex="-1" role="dialog" aria-hidden="true">-->

        <div class="modal" id="addRowModal" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title">
                  <span class="fw-mediumbold">Nueva</span>
                  <span class="fw-light">Venta</span>
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <form action="CitaCreateServlet" method="post">
                  <div class="row">
                    <div class="col-sm-6">
                      <div class="form-group form-group-default">
                        <label>Fecha Inicio</label>
                        <input required name="addFecha" type="date" class="form-control">
                      </div>
                      <div class="form-group form-group-default">
                        <label>Fecha Fin</label>
                        <input required name="addFechaEnd" type="date" class="form-control">
                      </div>
                      <div class="form-group form-group-default">
                        <label>Cliente</label>
                        <select class="form-control js-example-basic-single" name="addClienteId" style="width: 100%">
                          <c:forEach var="temp" items="${mi_lista_de_personas}">
                            <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Cliente')}">
                              <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                            </c:if>
                          </c:forEach>
                        </select>
                      </div>

                      <div class="form-group form-group-default">
                        <label>Inventario</label>
                        <select class="form-control" name="addSalaId">
                          <c:forEach var="temp" items="${salas}">
                            <option value="${temp.id}">${temp.nroSala}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="col-sm-6">
                      <div class="form-group form-group-default">
                        <label>Hora Inicio</label>
                        <input required name="addHora" type="time" class="form-control">
                      </div>
                      <div class="form-group form-group-default">
                        <label>Hora Fin</label>
                        <input required name="addHoraEnd" type="time" class="form-control">
                      </div>

                      <div class="form-group form-group-default">
                        <label>Técnico</label>
                        <select class="form-control" name="addTecnicoId">
                          <c:forEach var="temp" items="${mi_lista_de_personas}">
                            <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Técnico')}">
                              <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                            </c:if>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="col-md-12">

                      <div class="form-group form-group-default">
                        <label>Producto</label>
                        <select class="form-control" name="addServicioId">
                          <c:forEach var="tempC" items="${servicios}">
                            <option value="${tempC.id}">S/${tempC.precio}:  ${tempC.descripcion}: </option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>

                    <div class="col-md-6">
                      <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>


        <!-- Listar -->
        <div class="table-responsive">
          <table id="add-row" class="display table table-striped table-hover">
            <thead>
              <tr>
                <th>Inicio</th>
                <th>Fin</th>
                <th>Cliente</th>
                <th>Técnico</th>
                <th>Producto</th>
                <th>Estado</th>
                <th>Creado</th>
                <th>Modificado</th>
                <th style="width: 10%">Acciones</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="tempObjeto" items="${mi_lista_de_citas }">
                <c:choose>
                  <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                    <tr>
                      <td>${tempObjeto.fechaHora}</td>
                      <td>${tempObjeto.fechaHoraEnd}</td>
                      <td>${tempObjeto.clienteId.apellidos}, ${tempObjeto.clienteId.nombres}</td>
                      <td>${tempObjeto.tecnicoId.apellidos}, ${tempObjeto.tecnicoId.nombres}</td>
                      <td>
                        ${tempObjeto.servicioId.descripcion}<br>${tempObjeto.servicioId.minutos}min<br>S/${tempObjeto.servicioId.precio}
                      </td>
                      <td>${tempObjeto.estado}</td>
                      <td>
                        <fmt:formatDate value="${tempObjeto.createdAt }" type="both" dateStyle="medium" timeStyle="short" />
                      </td>
                      <td>
                        <fmt:formatDate value="${tempObjeto.updatedAt }" type="both" dateStyle="medium" timeStyle="short" />
                      </td>
                      <td>
                        <div class="form-button-action">
                          <button type="button" data-toggle="modal" class="btn btn-link btn-primary" data-target="#${tempObjeto.uniqueId}">
                            <i class="fa fa-edit"></i>
                          </button>
                          <button type="button" data-toggle="modal" class="btn btn-link btn-danger" data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                            <i class="fa fa-times"></i>
                          </button>
                        </div>
                      </td>
                    </tr>

                    <!--Modal detalles-->
                  <div class="modal" id="${tempObjeto.id}${tempObjeto.uniqueId}${tempObjeto.id}" role="dialog">
                    <div class="modal-dialog modal-lg" role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">
                            <span class="fw-mediumbold">Detalles de la </span>
                            <span class="fw-light">Venta</span>
                          </h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <form>
                          <div class="modal-body">
                            <div class="container-fluid">
                              <div class="row">
                                <div class="col-sm-6">
                                  <div class="form-group form-group-default">
                                    <label>Id</label>
                                    <input name="showId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                  </div>
                                </div>
                              </div>
                              <div class="row">
                                <div class="col-md-6">
                                  <div class="form-group form-group-default">
                                    <label>Fecha Inicio</label>
                                    <input name="showFecha" type="date" class="form-control" value="${tempObjeto.fechaHora.substring(0,10)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Fecha Fin</label>
                                    <input name="showFechaEnd" type="date" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(0,10)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Cliente</label>
                                    <input name="showClienteId" type="text" class="form-control" value="${tempObjeto.clienteId.apellidos}, ${tempObjeto.clienteId.nombres}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Inventario</label>
                                    <input name="showSalaId" type="text" class="form-control" value="${tempObjeto.salaId.nroSala}" readonly>
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <div class="form-group form-group-default">
                                    <label>Hora Inicio</label>
                                    <input name="showHora" type="time" class="form-control" value="${tempObjeto.fechaHora.substring(11,16)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Hora Fin</label>
                                    <input name="showHoraEnd" type="time" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(11,16)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Técnico</label>
                                    <input name="showTecnicoId" type="text" class="form-control" value="${tempObjeto.tecnicoId.apellidos}, ${tempObjeto.tecnicoId.nombres}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Estado</label>
                                    <input name="showEstado" type="text" class="form-control" value="${tempObjeto.estado}" readonly>
                                  </div>
                                </div>
                                <div class="col-md-12">
                                  <div class="form-group form-group-default">
                                    <label>Producto</label>
                                    <input name="showServicio" type="text" class="form-control" value="S/${tempObjeto.servicioId.precio} - ${tempObjeto.servicioId.minutos}minutos - ${tempObjeto.servicioId.descripcion}" readonly>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button type="button" data-toggle="modal" class="btn btn-primary" data-target="#${tempObjeto.uniqueId}">Editar</button>
                            <button type="button" class="btn btn-danger" onclick="miFuncionEliminar(${tempObjeto.id})">Eliminar Cita</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                  <!--Fin Modal detalles-->


                  <!-- Modal Eliminar -->
                  <div class="modal fade" id="${tempObjeto.id}${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                      <div class="modal-content">
                        <div class="modal-header no-bd">
                          <h5 class="modal-title">
                            <span class="fw-light">¿Está relamente seguro de querer</span>
                            <span class="fw-mediumbold"> eliminar </span>
                            <span class="fw-light">esta Venta?</span>
                          </h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <form action="CitaDestroyServlet" method="post">
                            <div class="row">
                              <div class="col-sm-12">
                                <div class="form-group form-group-default">
                                  <label>Id</label>
                                  <input name="destroyId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Inicio</label>
                                  <input type="text" class="form-control" value="${tempObjeto.fechaHora}" readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Fin</label>
                                  <input type="text" class="form-control" value="${tempObjeto.fechaHoraEnd }" readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Cliente</label>
                                  <input type="text" class="form-control"
                                         value="${tempObjeto.clienteId.nombres} - ${tempObjeto.clienteId.apellidos}"
                                         readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Técnico</label>
                                  <input type="text" class="form-control"
                                         value="${tempObjeto.tecnicoId.nombres} - ${tempObjeto.tecnicoId.apellidos}"
                                         readonly>
                                </div>

                              </div>
                              <div class="col-md-6">
                                <button type="submit" class="btn btn-danger">Borrar</button>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Modal Editar -->
                  <!--<div class="modal fade" id="${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">-->
                  <div class="modal" id="${tempObjeto.uniqueId}" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">
                            <span class="fw-mediumbold">Editar</span>
                            <span class="fw-light">Venta</span>
                          </h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <form action="CitaEditServlet" method="post">
                          <div class="modal-body">
                            <div class="container-fluid">
                              <div class="row">
                                <div class="col-sm-6">
                                  <div class="form-group form-group-default">
                                    <label>Id</label>
                                    <input name="editId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                  </div>
                                </div>
                              </div>
                              <div class="row">
                                <div class="col-sm-6">
                                  <div class="form-group form-group-default">
                                    <label>Fecha Inicio</label>
                                    <input required name="editFecha" type="date" class="form-control" value="${tempObjeto.fechaHora.substring(0,10)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Fecha Fin</label>
                                    <input required name="editFechaEnd" type="date" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(0,10)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Cliente</label>
                                    <select class="form-control js-example-basic-single" name="editClienteId" style="width: 100%">
                                      <c:forEach var="temp" items="${mi_lista_de_personas}">
                                        <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Cliente')}">
                                          <c:choose>
                                            <c:when test="${tempObjeto.clienteId.id == temp.id}">
                                              <option value="${temp.id}" selected>${temp.nombres} - ${temp.apellidos}</option>
                                            </c:when>
                                            <c:otherwise>
                                              <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                            </c:otherwise>
                                          </c:choose>
                                        </c:if>
                                      </c:forEach>
                                    </select>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Inventario</label>
                                    <select class="form-control" name="editSalaId">
                                      <c:forEach var="temp" items="${salas}">
                                        <c:choose>
                                          <c:when test="${tempObjeto.salaId.id == temp.id}">
                                            <option value="${temp.id}" selected>${temp.nroSala}</option>
                                          </c:when>
                                          <c:otherwise>
                                            <option value="${temp.id}">${temp.nroSala}</option>
                                          </c:otherwise>
                                        </c:choose>
                                      </c:forEach>
                                    </select>
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <div class="form-group form-group-default">
                                    <label>Hora Inicio</label>
                                    <input required name="editHora" type="time" class="form-control" value="${tempObjeto.fechaHora.substring(11,16)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Hora Fin</label>
                                    <input required name="editHoraEnd" type="time" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(11,16)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Técnico</label>
                                    <select class="form-control" name="editTecnicoId">
                                      <c:forEach var="temp" items="${mi_lista_de_personas}">
                                        <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Técnico')}">
                                          <c:choose>
                                            <c:when test="${tempObjeto.tecnicoId.id == temp.id}">
                                              <option value="${temp.id}" selected>${temp.nombres} - ${temp.apellidos}</option>
                                            </c:when>
                                            <c:otherwise>
                                              <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                            </c:otherwise>
                                          </c:choose>
                                        </c:if>
                                      </c:forEach>
                                    </select>
                                  </div>

                                  <div class="form-group form-group-default">
                                    <label>Estado</label>
                                    <select class="form-control" name="editEstado">
                                      <c:choose>
                                        <c:when test="${tempObjeto.estado.equalsIgnoreCase('Activo')}">
                                          <option value="activo" selected>Activo</option>
                                          <option value="inactivo">Inactivo</option>
                                        </c:when>
                                        <c:when test="${tempObjeto.estado.equalsIgnoreCase('Inactivo')}">
                                          <option value="activo">Activo</option>
                                          <option value="inactivo" selected>Inactivo</option>
                                        </c:when>
                                      </c:choose>
                                      <!--<option value="eliminado">Eliminado</option>-->
                                    </select>
                                  </div>
                                </div>
                                <div class="col-md-12">
                                  <div class="form-group form-group-default">
                                    <label>Producto</label>
                                    <select class="form-control" name="editServicioId">
                                      <c:forEach var="temp" items="${servicios}">
                                        <c:choose>
                                          <c:when test="${tempObjeto.servicioId.id == temp.id}">
                                            <option value="${temp.id}" selected>S/${temp.precio} - ${temp.minutos}min - ${temp.descripcion}</option>
                                          </c:when>
                                          <c:otherwise>
                                            <option value="${temp.id}">S/${temp.precio} - ${temp.minutos}min - ${temp.descripcion}</option>
                                          </c:otherwise>
                                        </c:choose>
                                      </c:forEach>
                                    </select>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </c:when>
                <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('cliente')}">
                  <c:if test="${tempObjeto.clienteId.email.equalsIgnoreCase(miPersonaObtenida.email)}">
                    <tr>
                      <td>${tempObjeto.fechaHora}</td>
                      <td>${tempObjeto.fechaHoraEnd}</td>
                      <td>${tempObjeto.clienteId.apellidos}, ${tempObjeto.clienteId.nombres}</td>
                      <td>${tempObjeto.tecnicoId.apellidos}, ${tempObjeto.tecnicoId.nombres}</td>
                      <td>
                        ${tempObjeto.servicioId.descripcion}<br>${tempObjeto.servicioId.minutos}min<br>S/${tempObjeto.servicioId.precio}
                      </td>
                      <td>${tempObjeto.estado}</td>
                      <td>
                        <fmt:formatDate value="${tempObjeto.createdAt }" type="both" dateStyle="medium" timeStyle="short" />
                      </td>
                      <td>
                        <fmt:formatDate value="${tempObjeto.updatedAt }" type="both" dateStyle="medium" timeStyle="short" />
                      </td>
                      <td>
                        <div class="form-button-action">
                          <button type="button" data-toggle="modal" class="btn btn-link btn-primary" data-target="#${tempObjeto.uniqueId}">
                            <i class="fa fa-edit"></i>
                          </button>
                          <button type="button" data-toggle="modal" class="btn btn-link btn-danger" data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                            <i class="fa fa-times"></i>
                          </button>
                        </div>
                      </td>
                    </tr>

                    <!--Modal detalles-->
                    <div class="modal" id="${tempObjeto.id}${tempObjeto.uniqueId}${tempObjeto.id}" role="dialog">
                      <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title">
                              <span class="fw-mediumbold">Detalles de la </span>
                              <span class="fw-light">Venta</span>
                            </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <form>
                            <div class="modal-body">
                              <div class="container-fluid">
                                <div class="row">
                                  <div class="col-sm-6">
                                    <div class="form-group form-group-default">
                                      <label>Id</label>
                                      <input name="showId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                    </div>
                                  </div>
                                </div>
                                <div class="row">
                                  <div class="col-md-6">
                                    <div class="form-group form-group-default">
                                      <label>Fecha Inicio</label>
                                      <input name="showFecha" type="date" class="form-control" value="${tempObjeto.fechaHora.substring(0,10)}" readonly>
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Fecha Fin</label>
                                      <input name="showFechaEnd" type="date" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(0,10)}" readonly>
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Cliente</label>
                                      <input name="showClienteId" type="text" class="form-control" value="${tempObjeto.clienteId.apellidos}, ${tempObjeto.clienteId.nombres}" readonly>
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Inventario</label>
                                      <input name="showSalaId" type="text" class="form-control" value="${tempObjeto.salaId.nroSala}" readonly>
                                    </div>
                                  </div>
                                  <div class="col-md-6">
                                    <div class="form-group form-group-default">
                                      <label>Hora Inicio</label>
                                      <input name="showHora" type="time" class="form-control" value="${tempObjeto.fechaHora.substring(11,16)}" readonly>
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Hora Fin</label>
                                      <input name="showHoraEnd" type="time" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(11,16)}" readonly>
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Técnico</label>
                                      <input name="showTecnicoId" type="text" class="form-control" value="${tempObjeto.tecnicoId.apellidos}, ${tempObjeto.tecnicoId.nombres}" readonly>
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Estado</label>
                                      <input name="showEstado" type="text" class="form-control" value="${tempObjeto.estado}" readonly>
                                    </div>
                                  </div>
                                  <div class="col-md-12">
                                    <div class="form-group form-group-default">
                                      <label>Producto</label>
                                      <input name="showServicio" type="text" class="form-control" value="S/${tempObjeto.servicioId.precio} - ${tempObjeto.servicioId.minutos}minutos - ${tempObjeto.servicioId.descripcion}" readonly>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="modal-footer">
                              <button type="button" data-toggle="modal" class="btn btn-primary" data-target="#${tempObjeto.uniqueId}">Editar</button>
                              <button type="button" class="btn btn-danger" onclick="miFuncionEliminar(${tempObjeto.id})">Eliminar Cita</button>
                              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                    <!--Fin Modal detalles-->

                    <!-- Modal Eliminar -->
                    <div class="modal fade" id="${tempObjeto.id}${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header no-bd">
                            <h5 class="modal-title">
                              <span class="fw-light">¿Está relamente seguro de querer</span>
                              <span class="fw-mediumbold"> eliminar </span>
                              <span class="fw-light">esta Venta?</span>
                            </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <div class="modal-body">
                            <form action="CitaDestroyServlet" method="post">
                              <div class="row">
                                <div class="col-sm-12">
                                  <div class="form-group form-group-default">
                                    <label>Id</label>
                                    <input name="destroyId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Inicio</label>
                                    <input type="text" class="form-control" value="${tempObjeto.fechaHora}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Fin</label>
                                    <input type="text" class="form-control" value="${tempObjeto.fechaHoraEnd }" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Cliente</label>
                                    <input type="text" class="form-control" readonly
                                           value="${tempObjeto.clienteId.nombres} - ${tempObjeto.clienteId.apellidos}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Técnico</label>
                                    <input type="text" class="form-control" readonly
                                           value="${tempObjeto.tecnicoId.nombres} - ${tempObjeto.tecnicoId.apellidos}">
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <button type="submit" class="btn btn-danger">Borrar</button>
                                </div>
                              </div>
                            </form>
                          </div>
                        </div>
                      </div>
                    </div>

                    <!-- Modal Editar -->
                    <div class="modal" id="${tempObjeto.uniqueId}" role="dialog" aria-hidden="true">
                      <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title">
                              <span class="fw-mediumbold">Editar</span>
                              <span class="fw-light">Venta</span>
                            </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <form action="CitaEditServlet" method="post">
                            <div class="modal-body">
                              <div class="container-fluid">
                                <div class="row">
                                  <div class="col-sm-6">
                                    <div class="form-group form-group-default">
                                      <label>Id</label>
                                      <input name="editId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                    </div>
                                  </div>
                                </div>
                                <div class="row">
                                  <div class="col-sm-6">
                                    <div class="form-group form-group-default">
                                      <label>Fecha Inicio</label>
                                      <input required name="editFecha" type="date" class="form-control" value="${tempObjeto.fechaHora.substring(0,10)}">
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Fecha Fin</label>
                                      <input required name="editFechaEnd" type="date" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(0,10)}">
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Cliente</label>
                                      <select class="form-control js-example-basic-single" name="editClienteId" style="width: 100%">
                                        <c:forEach var="temp" items="${mi_lista_de_personas}">
                                          <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Cliente')}">
                                            <c:choose>
                                              <c:when test="${tempObjeto.clienteId.id == temp.id}">
                                                <option value="${temp.id}" selected>${temp.nombres} - ${temp.apellidos}</option>
                                              </c:when>
                                              <c:otherwise>
                                                <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                              </c:otherwise>
                                            </c:choose>
                                          </c:if>
                                        </c:forEach>
                                      </select>
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Inventario</label>
                                      <select class="form-control" name="editSalaId">
                                        <c:forEach var="temp" items="${salas}">
                                          <c:choose>
                                            <c:when test="${tempObjeto.salaId.id == temp.id}">
                                              <option value="${temp.id}" selected>${temp.nroSala}</option>
                                            </c:when>
                                            <c:otherwise>
                                              <option value="${temp.id}">${temp.nroSala}</option>
                                            </c:otherwise>
                                          </c:choose>
                                        </c:forEach>
                                      </select>
                                    </div>
                                  </div>
                                  <div class="col-md-6">
                                    <div class="form-group form-group-default">
                                      <label>Hora Inicio</label>
                                      <input required name="editHora" type="time" class="form-control" value="${tempObjeto.fechaHora.substring(11,16)}">
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Hora Fin</label>
                                      <input required name="editHoraEnd" type="time" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(11,16)}">
                                    </div>
                                    <div class="form-group form-group-default">
                                      <label>Técnico</label>
                                      <select class="form-control" name="editTecnicoId">
                                        <c:forEach var="temp" items="${mi_lista_de_personas}">
                                          <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Técnico')}">
                                            <c:choose>
                                              <c:when test="${tempObjeto.tecnicoId.id == temp.id}">
                                                <option value="${temp.id}" selected>${temp.nombres} - ${temp.apellidos}</option>
                                              </c:when>
                                              <c:otherwise>
                                                <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                              </c:otherwise>
                                            </c:choose>
                                          </c:if>
                                        </c:forEach>
                                      </select>
                                    </div>

                                    <div class="form-group form-group-default">
                                      <label>Estado</label>
                                      <select class="form-control" name="editEstado">
                                        <c:choose>
                                          <c:when test="${tempObjeto.estado.equalsIgnoreCase('Activo')}">
                                            <option value="activo" selected>Activo</option>
                                            <option value="inactivo">Inactivo</option>
                                          </c:when>
                                          <c:when test="${tempObjeto.estado.equalsIgnoreCase('Inactivo')}">
                                            <option value="activo">Activo</option>
                                            <option value="inactivo" selected>Inactivo</option>
                                          </c:when>
                                        </c:choose>
                                        <!--<option value="eliminado">Eliminado</option>-->
                                      </select>
                                    </div>
                                  </div>
                                  <div class="col-md-12">
                                    <div class="form-group form-group-default">
                                      <label>Producto</label>
                                      <select class="form-control" name="editServicioId">
                                        <c:forEach var="temp" items="${servicios}">
                                          <c:choose>
                                            <c:when test="${tempObjeto.servicioId.id == temp.id}">
                                              <option value="${temp.id}" selected>S/${temp.precio} - ${temp.minutos}min - ${temp.descripcion}</option>
                                            </c:when>
                                            <c:otherwise>
                                              <option value="${temp.id}">S/${temp.precio} - ${temp.minutos}min - ${temp.descripcion}</option>
                                            </c:otherwise>
                                          </c:choose>
                                        </c:forEach>
                                      </select>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="modal-footer">
                              <button type="submit" class="btn btn-primary">Guardar</button>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>

                  </c:if>
                </c:when>

                  <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('técnico')}">
                    <tr>
                      <td>${tempObjeto.fechaHora}</td>
                      <td>${tempObjeto.fechaHoraEnd}</td>
                      <td>${tempObjeto.clienteId.apellidos}, ${tempObjeto.clienteId.nombres}</td>
                      <td>${tempObjeto.tecnicoId.apellidos}, ${tempObjeto.tecnicoId.nombres}</td>
                      <td>
                        ${tempObjeto.servicioId.descripcion}<br>${tempObjeto.servicioId.minutos}min<br>S/${tempObjeto.servicioId.precio}
                      </td>
                      <td>${tempObjeto.estado}</td>
                      <td>
                        <fmt:formatDate value="${tempObjeto.createdAt }" type="both" dateStyle="medium" timeStyle="short" />
                      </td>
                      <td>
                        <fmt:formatDate value="${tempObjeto.updatedAt }" type="both" dateStyle="medium" timeStyle="short" />
                      </td>
                      <td>
                        <div class="form-button-action">
                          <button type="button" data-toggle="modal" class="btn btn-link btn-primary" data-target="#${tempObjeto.uniqueId}">
                            <i class="fa fa-edit"></i>
                          </button>
                          <button type="button" data-toggle="modal" class="btn btn-link btn-danger" data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                            <i class="fa fa-times"></i>
                          </button>
                        </div>
                      </td>
                    </tr>

                    <!--Modal detalles-->
                  <div class="modal" id="${tempObjeto.id}${tempObjeto.uniqueId}${tempObjeto.id}" role="dialog">
                    <div class="modal-dialog modal-lg" role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">
                            <span class="fw-mediumbold">Detalles de la </span>
                            <span class="fw-light">Venta</span>
                          </h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <form>
                          <div class="modal-body">
                            <div class="container-fluid">
                              <div class="row">
                                <div class="col-sm-6">
                                  <div class="form-group form-group-default">
                                    <label>Id</label>
                                    <input name="showId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                  </div>
                                </div>
                              </div>
                              <div class="row">
                                <div class="col-md-6">
                                  <div class="form-group form-group-default">
                                    <label>Fecha Inicio</label>
                                    <input name="showFecha" type="date" class="form-control" value="${tempObjeto.fechaHora.substring(0,10)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Fecha Fin</label>
                                    <input name="showFechaEnd" type="date" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(0,10)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Cliente</label>
                                    <input name="showClienteId" type="text" class="form-control" value="${tempObjeto.clienteId.apellidos}, ${tempObjeto.clienteId.nombres}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Inventario</label>
                                    <input name="showSalaId" type="text" class="form-control" value="${tempObjeto.salaId.nroSala}" readonly>
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <div class="form-group form-group-default">
                                    <label>Hora Inicio</label>
                                    <input name="showHora" type="time" class="form-control" value="${tempObjeto.fechaHora.substring(11,16)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Hora Fin</label>
                                    <input name="showHoraEnd" type="time" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(11,16)}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Técnico</label>
                                    <input name="showTecnicoId" type="text" class="form-control" value="${tempObjeto.tecnicoId.apellidos}, ${tempObjeto.tecnicoId.nombres}" readonly>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Estado</label>
                                    <input name="showEstado" type="text" class="form-control" value="${tempObjeto.estado}" readonly>
                                  </div>
                                </div>
                                <div class="col-md-12">
                                  <div class="form-group form-group-default">
                                    <label>Producto</label>
                                    <input name="showServicio" type="text" class="form-control" value="S/${tempObjeto.servicioId.precio} - ${tempObjeto.servicioId.minutos}minutos - ${tempObjeto.servicioId.descripcion}" readonly>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button type="button" data-toggle="modal" class="btn btn-primary" data-target="#${tempObjeto.uniqueId}">Editar</button>
                            <button type="button" class="btn btn-danger" onclick="miFuncionEliminar(${tempObjeto.id})">Eliminar Venta</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                  <!--Fin Modal detalles-->


                  <!-- Modal Eliminar -->
                  <div class="modal fade" id="${tempObjeto.id}${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                      <div class="modal-content">
                        <div class="modal-header no-bd">
                          <h5 class="modal-title">
                            <span class="fw-light">¿Está relamente seguro de querer</span>
                            <span class="fw-mediumbold"> eliminar </span>
                            <span class="fw-light">esta Venta?</span>
                          </h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <form action="CitaDestroyServlet" method="post">
                            <div class="row">
                              <div class="col-sm-12">
                                <div class="form-group form-group-default">
                                  <label>Id</label>
                                  <input name="destroyId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Inicio</label>
                                  <input type="text" class="form-control" value="${tempObjeto.fechaHora}" readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Fin</label>
                                  <input type="text" class="form-control" value="${tempObjeto.fechaHoraEnd }" readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Cliente</label>
                                  <input type="text" class="form-control"
                                         value="${tempObjeto.clienteId.nombres} - ${tempObjeto.clienteId.apellidos}"
                                         readonly>
                                </div>
                                <div class="form-group form-group-default">
                                  <label>Técnico</label>
                                  <input type="text" class="form-control"
                                         value="${tempObjeto.tecnicoId.nombres} - ${tempObjeto.tecnicoId.apellidos}"
                                         readonly>
                                </div>

                              </div>
                              <div class="col-md-6">
                                <button type="submit" class="btn btn-danger">Borrar</button>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Modal Editar -->
                  <!--<div class="modal fade" id="${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">-->
                  <div class="modal" id="${tempObjeto.uniqueId}" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">
                            <span class="fw-mediumbold">Editar</span>
                            <span class="fw-light">Venta</span>
                          </h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <form action="CitaEditServlet" method="post">
                          <div class="modal-body">
                            <div class="container-fluid">
                              <div class="row">
                                <div class="col-sm-6">
                                  <div class="form-group form-group-default">
                                    <label>Id</label>
                                    <input name="editId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                  </div>
                                </div>
                              </div>
                              <div class="row">
                                <div class="col-sm-6">
                                  <div class="form-group form-group-default">
                                    <label>Fecha Inicio</label>
                                    <input required name="editFecha" type="date" class="form-control" value="${tempObjeto.fechaHora.substring(0,10)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Fecha Fin</label>
                                    <input required name="editFechaEnd" type="date" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(0,10)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Cliente</label>
                                    <select class="form-control js-example-basic-single" name="editClienteId" style="width: 100%">
                                      <c:forEach var="temp" items="${mi_lista_de_personas}">
                                        <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Cliente')}">
                                          <c:choose>
                                            <c:when test="${tempObjeto.clienteId.id == temp.id}">
                                              <option value="${temp.id}" selected>${temp.nombres} - ${temp.apellidos}</option>
                                            </c:when>
                                            <c:otherwise>
                                              <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                            </c:otherwise>
                                          </c:choose>
                                        </c:if>
                                      </c:forEach>
                                    </select>
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Inventario</label>
                                    <select class="form-control" name="editSalaId">
                                      <c:forEach var="temp" items="${salas}">
                                        <c:choose>
                                          <c:when test="${tempObjeto.salaId.id == temp.id}">
                                            <option value="${temp.id}" selected>${temp.nroSala}</option>
                                          </c:when>
                                          <c:otherwise>
                                            <option value="${temp.id}">${temp.nroSala}</option>
                                          </c:otherwise>
                                        </c:choose>
                                      </c:forEach>
                                    </select>
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <div class="form-group form-group-default">
                                    <label>Hora Inicio</label>
                                    <input required name="editHora" type="time" class="form-control" value="${tempObjeto.fechaHora.substring(11,16)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Hora Fin</label>
                                    <input required name="editHoraEnd" type="time" class="form-control" value="${tempObjeto.fechaHoraEnd.substring(11,16)}">
                                  </div>
                                  <div class="form-group form-group-default">
                                    <label>Técnico</label>
                                    <select class="form-control" name="editTecnicoId">
                                      <c:forEach var="temp" items="${mi_lista_de_personas}">
                                        <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('Técnico')}">
                                          <c:choose>
                                            <c:when test="${tempObjeto.tecnicoId.id == temp.id}">
                                              <option value="${temp.id}" selected>${temp.nombres} - ${temp.apellidos}</option>
                                            </c:when>
                                            <c:otherwise>
                                              <option value="${temp.id}">${temp.nombres} - ${temp.apellidos}</option>
                                            </c:otherwise>
                                          </c:choose>
                                        </c:if>
                                      </c:forEach>
                                    </select>
                                  </div>

                                  <div class="form-group form-group-default">
                                    <label>Estado</label>
                                    <select class="form-control" name="editEstado">
                                      <c:choose>
                                        <c:when test="${tempObjeto.estado.equalsIgnoreCase('Activo')}">
                                          <option value="activo" selected>Activo</option>
                                          <option value="inactivo">Inactivo</option>
                                        </c:when>
                                        <c:when test="${tempObjeto.estado.equalsIgnoreCase('Inactivo')}">
                                          <option value="activo">Activo</option>
                                          <option value="inactivo" selected>Inactivo</option>
                                        </c:when>
                                      </c:choose>
                                      <!--<option value="eliminado">Eliminado</option>-->
                                    </select>
                                  </div>
                                </div>
                                <div class="col-md-12">
                                  <div class="form-group form-group-default">
                                    <label>Producto</label>
                                    <select class="form-control" name="editServicioId">
                                      <c:forEach var="temp" items="${servicios}">
                                        <c:choose>
                                          <c:when test="${tempObjeto.servicioId.id == temp.id}">
                                            <option value="${temp.id}" selected>S/${temp.precio} - ${temp.minutos}min - ${temp.descripcion}</option>
                                          </c:when>
                                          <c:otherwise>
                                            <option value="${temp.id}">S/${temp.precio} - ${temp.minutos}min - ${temp.descripcion}</option>
                                          </c:otherwise>
                                        </c:choose>
                                      </c:forEach>
                                    </select>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
            </c:when>
          </c:choose>

        </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>


</jsp:attribute>
  <jsp:attribute name="script_area">

    <!--   Core JS Files   -->
    <!--<script src="assets/js/core/jquery.3.2.1.min.js"></script>-->
    <script>
      $(document).ready(function () {
        $('.js-example-basic-single').select2();
      });
    </script>

    <!--Datatables--> 
    <!--<script src="assets/js/plugin/datatables/datatables.min.js"></script>-->
    <script>
      // Add Row
      $('#add-row').DataTable({
        "pageLength": 5
      });
    </script>

    <script>
      function miFuncionEliminar(id) {
        console.log("Test: " + id);
        swal({
          title: '¿Estás seguro?',
          text: "No serás capaz de revertirlo!",
          type: 'warning',
          buttons: {
            confirm: {
              text: 'Sí, eliminar!',
              className: 'btn btn-danger'
            },
            cancel: {
              visible: true,
              text: 'Cancelar',
              className: 'btn btn-danger'
            }
          }
        }).then((Delete) => {
          if (Delete) {

            // Llamada AJAX
            $.ajax({
              url: "CitaDestroyServlet",
              data: {
                destroyId: id // parámetro a enviar
              },
              success: function (response) {
                location.reload();
              }
            });

            swal({
              title: 'Eliminado!',
              text: 'Su venta ha sido eliminada.',
              type: 'success',
              buttons: {
                confirm: {
                  className: 'btn btn-success'
                }
              }
            });
          } else {
            swal.close();
          }
        });
      }
    </script>
  </jsp:attribute>
</t:template>

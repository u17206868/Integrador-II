<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:template title="Inventario">
    <jsp:attribute name="head_area">
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
                        $('#detalle_' + arg.event.extendedProps.uuid).modal();
                    },
                    // editable: true,
                    dayMaxEvents: true,
                    events: []
                });

                for (let item of ${produccionesJson}) {
                    calendar.addEvent({
                        id: item.id,
                        title: 'Produccion ' + item.id,
                        start: item.fechaHora,
                        end: item.fechaHoraEnd,
                        extendedProps: {
                            department: 'BioChemistry',
                            uuid: item.uniqueId
                        }
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
                        <h4 class="card-title">Listado de ordenes de produccion</h4>
                        <div class="float-right ml-auto">
                            <button class="btn btn-primary btn-round ml-auto" data-toggle="modal" data-target="#addRowModal">
                                <i class="fa fa-plus"></i>
                                Añadir Nueva Produccion
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table id="add-row" class="display table table-striped table-hover" >
                            <thead>
                                <tr>
                                    <th>Descripcion</th>
                                    <th>Hora de Inicio</th>
                                    <th>Hora Fin </th>
                                    <th>Medida</th>
                                    <th>Color</th>
                                    <th>Estado</th>
                                    <th>Creado</th>
                                    <th>Modificado</th>
                                    <th>Tecnico</th>
                                    <th>Producto</th>
                                    <th>Sala</th>


                                    <th style="width: 10%">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${mi_lista_de_producciones }">
                                    <tr>
                                        <td>${item.descripcion}</td>
                                        <td>${item.fechaHora}</td>
                                        <td>${item.fechaHoraEnd}</td>
                                        <td>${item.medida}</td>
                                        <td>${item.color}</td>
                                        <td>${item.estado}</td>
                                        <td>
                                            <fmt:formatDate value="${item.createdAt }" type="both" dateStyle="medium" timeStyle="short" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${item.updatedAt }" type="both" dateStyle="medium" timeStyle="short" />
                                        </td>

                                        <td>${item.tecnicoId.apellidos}, ${item.tecnicoId.nombres}</td>
                                        <td>
                                            ${item.servicioId.descripcion}
                                        </td>
                                        <td>
                                            ${item.salaId.nroSala}
                                        </td>
                                        <td>
                                            <div class="form-button-action">
                                                <button type="button" data-toggle="modal" class="btn btn-link btn-primary" data-target="#${item.uniqueId}">
                                                    <i class="fa fa-edit"></i>
                                                </button>
                                                <button type="button" data-toggle="modal" class="btn btn-link btn-danger" data-target="#${item.id}${item.uniqueId}">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>


                                    <!-- Modal Eliminar -->
                                <div class="modal fade" id="${item.id}${item.uniqueId}" data-backdrop="static" data-keyboard="false" 
                                     tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-light">¿Confirmación de eliminación?</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="ProduccionDestroyServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>¿Está seguro que desea eliminar la producción con el id ${item.id}?</label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <input type="hidden" name="id" value="${item.id}">
                                                            <button type="submit" class="btn btn-danger">Borrar</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Modal Editar -->
                                <div class="modal fade" id="${item.uniqueId}"  data-backdrop="static" data-keyboard="false" tabindex="-1"  role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-mediumbold">Editar</span> 
                                                    <span class="fw-light">Produccion</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="ProduccionEditServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Descripcion</label>
                                                                <input value="${item.descripcion}" required name="descripcion" type="text" class="form-control" placeholder="Ingrese la descripcion">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Medida</label>
                                                                <input value="${item.medida}" step="any" required name="medida" type="number" class="form-control" placeholder="Ingrese la medida">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Color</label>
                                                                <input value="${item.color}" required name="color" type="text" class="form-control" placeholder="Ingrese el color">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <div class="row">
                                                                    <div class="col-sm-6">
                                                                        <label>Fecha de Inicio</label>
                                                                        <input value="${item.fechaHora.substring(0,10)}"
                                                                               required name="fechaInicio" type="date" class="form-control" placeholder="Ingrese la hora de inicio">
                                                                    </div>
                                                                    <div class="col-sm-6">
                                                                        <label>Hora de Inicio</label>
                                                                        <input value="${item.fechaHora.substring(11,16)}"
                                                                               required name="fechaHora" type="time" class="form-control" placeholder="Ingrese la hora de inicio">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <div class="row">
                                                                    <div class="col-sm-6">
                                                                        <label>Fecha Fin</label>
                                                                        <input value="${item.fechaHoraEnd.substring(0,10)}"
                                                                               required name="fechaEnd" type="date" class="form-control" placeholder="Ingrese la hora de fin">

                                                                    </div>
                                                                    <div class="col-sm-6">
                                                                        <label>Hora de Fin</label>
                                                                        <input value="${item.fechaHoraEnd.substring(11,16)}"
                                                                               required name="fechaHoraEnd" type="time" class="form-control" placeholder="Ingrese la hora de fin">

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Estado</label>
                                                                <select class="form-control" name="estado" required>
                                                                    <option value="">::: Seleccione :::</option>
                                                                    <option value="Activo" ${item.estado == "activo" ? "selected" : ""}>Activo</option>
                                                                    <option value="Inactivo" ${item.estado == "inactivo" ? "selected" : ""}>Inactivo</option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Sala</label>
                                                                <select class="form-control" name="salas" required>
                                                                    <option value="">::: Seleccione :::</option>
                                                                    <c:forEach var="sala" items="${salas}">
                                                                        <option value="${sala.id}"
                                                                                ${item.salaId.id == sala.id ? "selected" : ""}
                                                                                >${sala.nroSala}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Servicio</label>
                                                                <select class="form-control" name="servicioId" required>
                                                                    <option value="">::: Seleccione :::</option>
                                                                    <c:forEach var="servicio" items="${servicios}">
                                                                        <option value="${servicio.id}"
                                                                                ${item.servicioId.id == servicio.id ? "selected" : ""}
                                                                                >${servicio.descripcion}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Tecnico</label>
                                                                <select class="form-control" name="tecnicoId" required>
                                                                    <option value="">::: Seleccione :::</option>
                                                                    <c:forEach var="tecnico" items="${tecnicos}">
                                                                        <option value="${tecnico.id}"
                                                                                ${item.tecnicoId.id == tecnico.id ? "selected" : ""}
                                                                                >${tecnico.nombres} ${tecnico.apellidos}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <input type="hidden" name="id" value="${item.id}">
                                                            <button type="submit" class="btn btn-primary">Guardar</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Modal Detalle -->      
                                <div class="modal fade" id="detalle_${item.uniqueId}"  data-backdrop="static" data-keyboard="false" tabindex="-1"  role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-mediumbold">Detalle</span> 
                                                    <span class="fw-light">Produccion</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="ProduccionEditServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Descripcion</label>
                                                                <input value="${item.descripcion}" type="text" disabled="" class="form-control">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Medida</label>
                                                                <input value="${item.medida}" type="text" disabled="" class="form-control">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Color</label>
                                                                <input value="${item.color}" type="text" disabled="" class="form-control">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <div class="row">
                                                                    <div class="col-sm-6">
                                                                        <label>Fecha de Inicio</label>
                                                                        <input value="${item.fechaHora.substring(0,10)}" type="text" disabled="" class="form-control">
                                                                    </div>
                                                                    <div class="col-sm-6">
                                                                        <label>Hora de Inicio</label>
                                                                        <input value="${item.fechaHora.substring(11,16)}" type="text" disabled="" class="form-control">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <div class="row">
                                                                    <div class="col-sm-6">
                                                                        <label>Fecha Fin</label>
                                                                        <input value="${item.fechaHoraEnd.substring(0,10)}" type="text" disabled="" class="form-control">

                                                                    </div>
                                                                    <div class="col-sm-6">
                                                                        <label>Hora de Fin</label>
                                                                        <input value="${item.fechaHoraEnd.substring(11,16)}" type="text" disabled="" class="form-control">

                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Estado</label>
                                                                <input value="${item.estado}" type="text" disabled="" class="form-control">

                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Sala</label>
                                                                <input value="${item.salaId.nroSala}" type="text" disabled="" class="form-control">

                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Servicio</label>
                                                                <input value="${item.servicioId.descripcion}" type="text" disabled="" class="form-control">

                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Tecnico</label>
                                                                <input  value="${item.tecnicoId.nombres} ${item.tecnicoId.apellidos}" type="text" disabled="" class="form-control">

                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addRowModal" data-backdrop="static" data-keyboard="false" tabindex="-1"  role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header no-bd">
                        <h5 class="modal-title">
                            <span class="fw-mediumbold">Nuevo</span> 
                            <span class="fw-light">Produccion</span>
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="ProduccionCreateServlet" method="post">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group form-group-default">
                                        <label>Descripcion</label>
                                        <input required name="descripcion" type="text" class="form-control" placeholder="Ingrese la descripcion">
                                    </div>
                                    <div class="form-group form-group-default">
                                        <label>Medida</label>
                                        <input step="any" required name="medida" type="number" class="form-control" placeholder="Ingrese la medida">
                                    </div>
                                    <div class="form-group form-group-default">
                                        <label>Color</label>
                                        <input required name="color" type="text" class="form-control" placeholder="Ingrese el color">
                                    </div>
                                    <div class="form-group form-group-default">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <label>Fecha de Inicio</label>
                                                <input 
                                                    required name="fechaInicio" type="date" class="form-control" placeholder="Ingrese la hora de inicio">
                                            </div>
                                            <div class="col-sm-6">
                                                <label>Hora de Inicio</label>
                                                <input 
                                                    required name="fechaHora" type="time" class="form-control" placeholder="Ingrese la hora de inicio">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-default">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <label>Fecha Fin</label>
                                                <input 
                                                    required name="fechaEnd" type="date" class="form-control" placeholder="Ingrese la hora de fin">

                                            </div>
                                            <div class="col-sm-6">
                                                <label>Hora de Fin</label>
                                                <input 
                                                    required name="fechaHoraEnd" type="time" class="form-control" placeholder="Ingrese la hora de fin">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-default">
                                        <label>Estado</label>
                                        <select class="form-control" name="estado" required>
                                            <option value="">::: Seleccione :::</option>
                                            <option value="Activo">Activo</option>
                                            <option value="Inactivo">Inactivo</option>
                                        </select>
                                    </div>
                                    <div class="form-group form-group-default">
                                        <label>Sala</label>
                                        <select class="form-control" name="salas" required>
                                            <option value="">::: Seleccione :::</option>
                                            <c:forEach var="sala" items="${salas}">
                                                <option value="${sala.id}">${sala.nroSala}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group form-group-default">
                                        <label>Servicio</label>
                                        <select class="form-control" name="servicioId" required>
                                            <option value="">::: Seleccione :::</option>
                                            <c:forEach var="servicio" items="${servicios}">
                                                <option value="${servicio.id}">${servicio.descripcion}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group form-group-default">
                                        <label>Tecnico</label>
                                        <select class="form-control" name="tecnicoId" required>
                                            <option value="">::: Seleccione :::</option>
                                            <c:forEach var="tecnico" items="${tecnicos}">
                                                <option value="${tecnico.id}">${tecnico.nombres} ${tecnico.apellidos}</option>
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

        <script src="assets/js/core/jquery.3.2.1.min.js"></script>
        <script src="assets/js/plugin/datatables/datatables.min.js"></script>
        <script>
            $('#add-row').DataTable({
                "pageLength": 5,
            });
        </script>

    </jsp:attribute>
</t:template>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:template title="Listar Facturas">
    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">


        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center">
                        <h4 class="card-title">Comprobantes</h4>
                        <button class="btn btn-primary btn-round ml-auto" data-toggle="modal" data-target="#addRowModal">
                            <i class="fa fa-plus"></i>
                            Añadir Comprobante
                        </button>
                    </div>
                </div>
                <div class="card-body">

                    <!-- Modal Crear -->
                    <div class="modal fade" id="addRowModal" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header no-bd">
                                    <h5 class="modal-title">
                                        <span class="fw-mediumbold">
                                            Nuevo</span> 
                                        <span class="fw-light">
                                            Comprobante
                                        </span>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form  action="ComprobanteCreateServlet" method="post">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group form-group-default">
                                                    <label>Número</label>
                                                    <input required name="addNumero" type="text" class="form-control"">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Serie</label>
                                                    <input required name="addSerie" type="text" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Tipo</label>
                                                    <select class="form-control" name="addTipo">
                                                        <option value="boleta">Boleta</option>
                                                        <option value="factura">Factura</option>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Fecha</label>
                                                    <input required name="addFecha" type="date" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Hora</label>
                                                    <input required name="addHora" type="time" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Persona</label>
                                                    <select class="form-control" name="addPersonaId">
                                                        <c:forEach var="tempObjetoCreate" items="${mi_lista_de_personas }">
                                                            <option value="${tempObjetoCreate.id }">${tempObjetoCreate.nombres }</option>
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

                    <div class="table-responsive">
                        <table id="add-row" class="display table table-striped table-hover" >
                            <thead>
                                <tr>
                                    <th>Número</th>
                                    <th>Serie</th>
                                    <th>Tipo</th>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Persona</th>
                                    <th>Estado</th>
                                    <th>Creado</th>
                                    <th>Modificado</th>
                                    <th style="width: 10%">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tempObjeto" items="${mi_lista_de_comprobantes }">
                                    <tr>
                                        <td>${tempObjeto.numero }</td>
                                        <td>${tempObjeto.serie}</td>
                                        <td>${tempObjeto.tipo}</td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.fecha }" type="date" dateStyle="medium"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.hora }" type="time" timeStyle="short"/>
                                        </td>
                                        <td>${tempObjeto.personaId.nombres}</td>
                                        <td>${tempObjeto.estado}</td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.createdAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.updatedAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                        </td>
                                        <td>
                                            <div class="form-button-action">
                                                <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                                        data-target="#${tempObjeto.uniqueId}">
                                                    <i class="fa fa-edit"></i>
                                                </button>
                                                <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                                        data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>



                                    <!-- Modal Eliminar -->
                                <div class="modal fade" id="${tempObjeto.id}${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-light">¿Está relamente seguro de querer</span>
                                                    <span class="fw-mediumbold"> eliminar </span>
                                                    <span class="fw-light">este comprobante?</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="ComprobanteDestroyServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="destroyId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Número</label>
                                                                <input type="text" class="form-control" value="${tempObjeto.numero }" readonly>
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
                                <div class="modal fade" id="${tempObjeto.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-mediumbold">
                                                        Editar</span> 
                                                    <span class="fw-light">
                                                        Factura
                                                    </span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="ComprobanteEditServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="editId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Número</label>
                                                                <input required name="editNumero" type="text" class="form-control" value="${tempObjeto.numero }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Serie</label>
                                                                <input required name="editSerie" type="text" class="form-control" value="${tempObjeto.serie }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Tipo</label><select class="form-control" name="editTipo">
                                                                    <option value="boleta">Boleta</option>
                                                                    <option value="factura">Factura</option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Fecha</label>
                                                                <input required name="editFecha" type="date" class="form-control" value="${tempObjeto.fecha }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Hora</label>
                                                                <input required name="editHora" type="time" class="form-control" value="${tempObjeto.hora }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Persona</label>
                                                                <select class="form-control" name="editPersonaId">
                                                                    <c:forEach var="tempObjetoEdit" items="${mi_lista_de_personas }">
                                                                        <option value="${tempObjetoEdit.id }">${tempObjetoEdit.nombres }</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Estado</label>
                                                                <select class="form-control" name="editEstado">
                                                                    <option value="activo">Activo</option>
                                                                    <option value="inactivo">Inactivo</option>
                                                                    <!--<option value="eliminado">Eliminado</option>-->
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
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!--   Core JS Files   -->
        <script src="assets/js/core/jquery.3.2.1.min.js"></script>
        <!-- Datatables -->
        <script src="assets/js/plugin/datatables/datatables.min.js"></script>
        <script>
            // Add Row
            $('#add-row').DataTable({
                "pageLength": 5,
            });
        </script>

    </jsp:attribute>
</t:template>
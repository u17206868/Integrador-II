<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:template title="Listar Clientes">
    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center">
                        <h4 class="card-title">Clientes</h4>

                        <c:choose>
                            <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                                <button class="btn btn-primary btn-round ml-auto" data-toggle="modal" data-target="#addRowModal">
                                    <i class="fa fa-plus"></i>
                                    Añadir Cliente
                                </button>
                            </c:when>
                        </c:choose>

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
                                            Cliente
                                        </span>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form  action="PersonaCreateServlet" method="post">
                                        <div class="row">
                                            <div class="col-sm-6">

                                                <div class="form-group form-group-default">
                                                    <label>Nombres</label>
                                                    <input required name="addNombres" type="text" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Apellidos</label>
                                                    <input required name="addApellidos" type="text" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Dirección</label>
                                                    <input required name="addDireccion" type="text" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Referencia</label>
                                                    <input required name="addReferencia" type="text" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Distrito</label>
                                                    <select class="form-control" name="addDistritoId">
                                                        <c:forEach var="tempD" items="${miListaDeDistritos}">
                                                            <option value="${tempD.id}">${tempD.descripcion}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-sm-6">
                                                <div class="form-group form-group-default">
                                                    <label>DNI</label>
                                                    <input required name="addDni" type="text" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Teléfono</label>
                                                    <input required name="addTelefono" type="text" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Email</label>
                                                    <input required name="addEmail" type="email" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Password</label>
                                                    <input required name="addPassword" type="password" class="form-control">
                                                </div>
                                                <!--                                                <div class="form-group form-group-default">
                                                                                                    <label>Turno</label>
                                                                                                    <select class="form-control" name="addTurno">
                                                                                                        <option value="1">Mañana</option>
                                                                                                        <option value="2">Tarde</option>
                                                                                                        <option value="3">Noche</option>
                                                                                                    </select>
                                                                                                </div>
                                                -->
                                                <div class="form-group form-group-default">
                                                    <label>Tipo de Cliente</label>
                                                    <select class="form-control" name="addTdPersonaId">
                                                        <c:forEach var="temp" items="${mi_lista_de_TdP }">
                                                            <c:choose>
                                                               
                                                                <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('trabajador')}">
                                                                    <c:if test="${temp.descripcion.equalsIgnoreCase('cliente')}">
                                                                        <option value="${temp.id }">${temp.descripcion }</option>
                                                                    </c:if>
                                                                </c:when>
                                                            </c:choose>
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
                        <table id="add-row" class="display table table-striped table-hover" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th style="width: 10%">Acción</th>
                                    <th>Apellidos y Nombres</th>
                                    <th>DNI</th>
                                    <th>Teléfono</th>
                                    <th>Dirección</th>
                                    <th>Referencia</th>
                                    <th>Distrito</th>
                                    <th>Email</th>
                                    <th>Tipo de Cliente</th>
                                    <th>Estado</th>
                                    <th>Creado</th>
                                    <th>Modificado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="temp" items="${mi_lista_de_personas }">
                                    <c:choose>
                                        <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                                            <tr>
                                                <td>
                                                    <div class="form-button-action">
                                                        <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-xs"
                                                                data-target="#${temp.uniqueId}">
                                                            <i class="fa fa-edit"></i>
                                                        </button>
                                                        <button type="button" data-toggle="modal" class="btn btn-link btn-danger btn-xs"
                                                                data-target="#${temp.id}${temp.uniqueId}">
                                                            <i class="fa fa-times"></i>
                                                        </button>
                                                    </div>
                                                </td>
                                                <td>${temp.apellidos}, ${temp.nombres}</td>
                                                <td>${temp.dni}</td>
                                                <td>${temp.telefono}</td>
                                                <td>${temp.direccion}</td>
                                                <td>${temp.referencia}</td>
                                                <td>${temp.distritoId.descripcion}</td>
                                                <td>${temp.email}</td>
                                                <td>${temp.tipoPersonaId.descripcion}</td>
                                                <td>${temp.estado}</td>
                                                <td>
                                                    <fmt:formatDate value="${temp.createdAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${temp.updatedAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('trabajador')}">
                                            <c:if test="${temp.tipoPersonaId.descripcion.equalsIgnoreCase('cliente')}">
                                                <tr>
                                                    <td>
                                                        <div class="form-button-action">
                                                            <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                                                    data-target="#${temp.uniqueId}">
                                                                <i class="fa fa-edit"></i>
                                                            </button>
                                                            <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                                                    data-target="#${temp.id}${temp.uniqueId}">
                                                                <i class="fa fa-times"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                    <td>${temp.apellidos}, ${temp.nombres }</td>
                                                    <td>${temp.dni}</td>
                                                    <td>${temp.telefono}</td>
                                                    <td>${temp.direccion}</td>
                                                    <td>${temp.referencia}</td>
                                                    <td>${temp.distritoId.descripcion}</td>
                                                    <td>${temp.email}</td>
                                                    <td>${temp.tipoPersonaId.descripcion}</td>
                                                    <td>${temp.estado}</td>
                                                    <td>
                                                        <fmt:formatDate value="${temp.createdAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${temp.updatedAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('cliente')}">
                                            <c:if test="${temp.email.equalsIgnoreCase(miPersonaObtenida.email)}">
                                                <tr>
                                                    <td>
                                                        <div class="form-button-action">
                                                            <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                                                    data-target="#${temp.uniqueId}">
                                                                <i class="fa fa-edit"></i>
                                                            </button>
                                                            <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                                                    data-target="#${temp.id}${temp.uniqueId}">
                                                                <i class="fa fa-times"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                    <td>${temp.apellidos}, ${temp.nombres }</td>
                                                    <td>${temp.dni}</td>
                                                    <td>${temp.telefono}</td>
                                                    <td>${temp.direccion}</td>
                                                    <td>${temp.referencia}</td>
                                                    <td>${temp.distritoId.descripcion}</td>
                                                    <td>${temp.email}</td>
                                                    <td>${temp.tipoPersonaId.descripcion}</td>
                                                    <td>${temp.estado}</td>
                                                    <td>
                                                        <fmt:formatDate value="${temp.createdAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${temp.updatedAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:when>
                                    </c:choose>



                                    <!-- Modal Eliminar -->
                                <div class="modal fade" id="${temp.id}${temp.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-light">¿Está relamente seguro de querer</span>
                                                    <span class="fw-mediumbold"> eliminar </span>
                                                    <span class="fw-light">este cliente?</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="PersonaDestroyServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="destroyId" type="text" class="form-control" value="${temp.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Nombres</label>
                                                                <input type="text" class="form-control" value="${temp.nombres }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Apellidos</label>
                                                                <input type="text" class="form-control" value="${temp.apellidos }" readonly>
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
                                <div class="modal fade" id="${temp.uniqueId}" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header no-bd">
                                                <h5 class="modal-title">
                                                    <span class="fw-mediumbold">Editar</span>
                                                    <span class="fw-light">Cliente</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="PersonaEditServlet" method="post">
                                                    <div class="row ">
                                                        <div class="col-sm-6">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="editId" type="text" class="form-control" value="${temp.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Nombres</label>
                                                                <input required name="editNombres" type="text" class="form-control" value="${temp.nombres }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Apellidos</label>
                                                                <input required name="editApellidos" type="text" class="form-control" value="${temp.apellidos }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Dirección</label>
                                                                <input required name="editDireccion" type="text" class="form-control" value="${temp.direccion}">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Referencia</label>
                                                                <input required name="editReferencia" type="text" class="form-control" value="${temp.referencia}">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Distrito</label>
                                                                <select class="form-control" name="editDistritoId">
                                                                    <c:forEach var="tempD" items="${miListaDeDistritos}">
                                                                        <option value="${tempD.id}">${tempD.descripcion}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <div class="form-group form-group-default">
                                                                <label>DNI</label>
                                                                <input required name="editDni" type="text" class="form-control" value="${temp.dni }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Teléfono</label>
                                                                <input required name="editTelefono" type="text" class="form-control" value="${temp.telefono }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Email</label>
                                                                <input required name="editEmail" type="text" class="form-control" value="${temp.email }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Password</label>
                                                                <input name="editPassword" type="password" class="form-control">
                                                            </div>

                                                            <!--                                                            <div class="form-group form-group-default">
                                                                                                                            <label>Turno</label>
                                                                                                                            <select class="form-control" name="editTurno">
                                                                                                                                <option value="1">Mañana</option>
                                                                                                                                <option value="2">Tarde</option>
                                                                                                                                <option value="3">Noche</option>
                                                                                                                            </select>
                                                                                                                        </div>
                                                            -->
                                                            <div class="form-group form-group-default">
                                                                <label>Tipo de Cliente</label>
                                                                <c:choose>
                                                                    <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                                                                        <select class="form-control" name="editTdPersonaId">
                                                                            <c:forEach var="tempEdit" items="${mi_lista_de_TdP }">
                                                                                <option value="${tempEdit.id }">${tempEdit.descripcion }</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('trabajador')}">
                                                                        <select class="form-control" name="editTdPersonaId">
                                                                            <c:forEach var="tempEdit" items="${mi_lista_de_TdP }">
                                                                                <c:if test="${tempEdit.descripcion.equalsIgnoreCase('cliente')}">
                                                                                    <option value="${tempEdit.id }">${tempEdit.descripcion }</option>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </c:when>
                                                                    <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('cliente')}">
                                                                        <input name="editTdPersonaId" type="text" class="form-control" value="${temp.tipoPersonaId.id}" readonly>
                                                                    </c:when>
                                                                </c:choose>
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
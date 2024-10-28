<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:template title="Listar Ventas">
    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">


        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center">
                        <h4 class="card-title">Ventas</h4>
                        <div class="float-right ml-auto">
                            <a class="btn btn-primary btn-border btn-round" href="ReporteDistritosServlet">
                                <i class="far fa-file-pdf"></i>
                                Reporte de Ventas
                            </a>

                            <button class="btn btn-primary btn-round" data-toggle="modal" data-target="#addRowModal">
                                <i class="fa fa-plus"></i>
                                Nueva Venta
                            </button>
                        </div>

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
                                            Nueva</span> 
                                        <span class="fw-light">
                                            Venta
                                        </span>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form  action="DistritoCreateServlet" method="post">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group form-group-default">
                                                    <label>Nombre</label>
                                                    <input required name="addDescripcion" id="addName" type="text" class="form-control" placeholder="Llene el distrito">
                                                </div>

                                                <!--                        <div class="form-group form-group-default">
                                                                          <label>Select</label>
                                                                          <select class="form-control" name="addDepartamentoId" id="addDepartamentoId">
                                                <c:forEach var="tempObjetoDepa" items="${mi_lista_de_departamentos }">
                                                  <option value="${tempObjetoDepa.id }">${tempObjetoDepa.descripcion }</option>
                                                </c:forEach>
                    
                                              </select>
                                            </div>-->
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
                                    <th>Nombre</th>
                                    <th>Estado</th>
                                    <th>Creado</th>
                                    <th>Modificado</th>
                                    <th style="width: 10%">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tempObjeto" items="${mi_lista_de_distritos }">
                                    <tr>
                                        <td>${tempObjeto.descripcion }</td>
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
                                                    <span class="fw-light">esta venta?</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="DistritoDestroyServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="destroyId" id="destroyId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Nombre</label>
                                                                <input name="destroy_distrito_departamento" id="destroy_distrito_departamento" type="text" class="form-control" value="${tempObjeto.descripcion }" readonly>
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
                                                        Venta
                                                    </span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="DistritoEditServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="editId" id="editId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Nombre</label>
                                                                <input required name="editDescripcion" id="editDescripcion" type="text" class="form-control" value="${tempObjeto.descripcion }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Select</label>

                                                                <select class="form-control" name="editEstado" id="editEstado">
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
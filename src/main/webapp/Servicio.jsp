<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<t:template title="Inventario">
    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">


        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center">
                        <h4 class="card-title">Inventario</h4>
                        <div class="float-right ml-auto">
                            <a class="btn btn-primary btn-border btn-round" href="ServicioXClienteServlet">
                                <i class="far fa-file-pdf"></i>
                                Inventario de productos
                            </a>
                            <a class="btn btn-primary btn-border btn-round" href="ServicioXTipoServlet">
                                <i class="far fa-file-pdf"></i>
                                Inventario
                            </a>
                            <button class="btn btn-primary btn-round ml-auto" data-toggle="modal" data-target="#addRowModal">
                                <i class="fa fa-plus"></i>
                                Añadir Producto
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
                                            Nuevo</span> 
                                        <span class="fw-light">
                                            Producto
                                        </span>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form  action="ServicioCreateServlet" method="post">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group form-group-default">
                                                    <label>Nombre</label>
                                                    <input required name="addDescripcion" type="text" class="form-control" placeholder="Llene el nombre">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Detalles</label>
                                                    <input required name="addDetalles" type="text" class="form-control" placeholder="Llene el detalle">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Cantidad</label>
                                                    <input required name="addMinutos" type="text" class="form-control" placeholder="Llene la cantidad">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Precio</label>
                                                    <input required name="addPrecio" type="text" class="form-control" placeholder="Llene el precio">
                                                </div>

                                                <div class="form-group form-group-default">
                                                    <label>Categoría</label>
                                                    <select class="form-control" name="addCategoriaId">
                                                        <c:forEach var="temp" items="${mi_lista_de_categorias }">
                                                            <option value="${temp.id }">${temp.descripcion }</option>
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
                                    <th>Nombre</th>
                                    <th>Detalles</th>
                                    <th>Cantidad</th>
                                    <th>Precio</th>
                                    <th>Categoría</th>
                                    <th>Estado</th>
                                    <th>Creado</th>
                                    <th>Modificado</th>
                                    <th style="width: 10%">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tempObjeto" items="${mi_lista_de_servicios }">
                                    <tr>
                                        <td>${tempObjeto.descripcion }</td>
                                        <!--                     <td>
                                        ${fn:split('xxx yyy zzz', ' ')}
                                      </td>-->
                                        <td>${tempObjeto.detalles}</td> 
                                        <td>${tempObjeto.minutos}</td>
                                        <td>${tempObjeto.precio}</td>
                                        <td>${tempObjeto.categoriaId.descripcion}</td>
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
                                                    <span class="fw-light">este producto?</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="ServicioDestroyServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="destroyId" id="destroyId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Nombre</label>
                                                                <input name="destroyObject" id="destroyObject" type="text" class="form-control" value="${tempObjeto.descripcion }" readonly>
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
                                                        Producto
                                                    </span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="ServicioEditServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="editId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Nombre</label>
                                                                <input required name="editDescripcion" type="text" class="form-control" value="${tempObjeto.descripcion }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Detalles</label>
                                                                <input required name="editDetalles" type="text" class="form-control" value="${tempObjeto.detalles }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Precio</label>
                                                                <input required name="editPrecio" type="number" class="form-control" value="${tempObjeto.precio }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Categoría</label>
                                                                <select class="form-control" name="editCategoriaId">
                                                                    <c:forEach var="tempObjetoEdit" items="${mi_lista_de_categorias }">
                                                                        <option value="${tempObjetoEdit.id }">${tempObjetoEdit.descripcion }</option>
                                                                    </c:forEach>
                                                                </select>
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
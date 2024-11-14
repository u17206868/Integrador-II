<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:template title="Listar DetalleComprobante">
    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">


        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center">
                        <h4 class="card-title">Detalle de Comprobante</h4>
                        <button class="btn btn-primary btn-round ml-auto" data-toggle="modal" data-target="#addRowModal">
                            <i class="fa fa-plus"></i>
                            Añadir Detalle de Comprobante
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
                                            Detalle de Comprobante
                                        </span>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form  action="DetalleComprobanteCreateServlet" method="post">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="form-group form-group-default">
                                                    <label>Cantidad</label>
                                                    <input required name="addCantidad" id="addCAntidad" type="number" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Precio</label>
                                                    <input required name="addPrecio" id="addPrecio" type="number" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Subtotal</label>
                                                    <c:out value="${35+42}"/>  
                                                    <input required name="addSubtotal" id="addSubtotal" type="number" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>IGV</label>
                                                    <input required name="addIgv" type="number" class="form-control">
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Total</label>
                                                    <input required name="addTotal" type="number" class="form-control">
                                                </div>

                                                <div class="form-group form-group-default">
                                                    <label>Comprobante</label>
                                                    <select class="form-control" name="addComprobanteId">
                                                        <c:forEach var="tempObjetoCreate" items="${mi_lista_de_comprobantes }">
                                                            <option value="${tempObjetoCreate.id }">${tempObjetoCreate.numero } - ${tempObjetoCreate.serie}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group-default">
                                                    <label>Producto</label>
                                                    <select class="form-control" name="addServicioId">
                                                        <c:forEach var="tempObjetoCreate2" items="${mi_lista_de_servicios }">
                                                            <option value="${tempObjetoCreate2.id }">${tempObjetoCreate2.descripcion} - ${tempObjetoCreate2.precio}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <%--
                                                                        <div class="form-group form-group-default">
                                                                          <label>Prenda</label>
                                                                          <select class="form-control" name="addPrendaId">
                                                                            <c:forEach var="tempObjetoCreate3" items="${mi_lista_de_prendas }">
                                                                              <option value="${tempObjetoCreate3.id }">N°: ${tempObjetoCreate3.cantidad } - Peso: ${tempObjetoCreate3.peso} - ${tempObjetoCreate3.tipoDePrendaId.descripcion }</option>
                                                                            </c:forEach>
                                                                          </select>
                                                                        </div>
                                                --%>
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
                                    <th>Cantidad</th>
                                    <th>Precio</th>
                                    <th>Subtotal</th>
                                    <th>IGV</th>
                                    <th>Total</th>
                                    <th>Comprobante</th>
                                    <th>Producto</th>
                                    <!--<th>Prenda</th>-->
                                    <th>Estado</th>
                                    <th>Creado</th>
                                    <th>Modificado</th>
                                    <th style="width: 10%">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tempObjeto" items="${mi_lista_de_dcs }">
                                    <tr>
                                        <td>${tempObjeto.cantidad }</td>
                                        <td>${tempObjeto.precio}</td>
                                        <td>${tempObjeto.subtotal}</td>
                                        <td>${tempObjeto.igv}</td>
                                        <td>${tempObjeto.total}</td>
                                        <td>${tempObjeto.comprobanteId.numero}</td>
                                        <td>${tempObjeto.servicioId.descripcion}</td>
                                        <%--<td>${tempObjeto.prendaId.marca}</td>--%>
                                        <td>${tempObjeto.estado}</td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.createdAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${tempObjeto.updatedAt }" type="both" dateStyle="medium" timeStyle="short"/>
                                        </td>
                                        <td>
                                            <div class="form-button-action">
                                                <c:choose>
                                                    <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('administrador')}">
                                                        <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                                                data-target="#${tempObjeto.uniqueId}">
                                                            <i class="fa fa-edit"></i>
                                                        </button>
                                                        <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                                                data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                                                            <i class="fa fa-times"></i>
                                                        </button>
                                                    </c:when>
                                                    <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('cliente')}">
<!--                                                        <button disabled="" type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                                                data-target="#${tempObjeto.uniqueId}">
                                                            <i class="fa fa-edit"></i>
                                                        </button>
                                                            <button disabled="" type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                                                data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                                                            <i class="fa fa-times"></i>
                                                        </button>-->
                                                    </c:when>
                                                    <c:when test="${miPersonaObtenida.tipoPersonaId.descripcion.equalsIgnoreCase('trabajador')}">
                                                        <button type="button" data-toggle="modal" class="btn btn-link btn-primary btn-lg"
                                                                data-target="#${tempObjeto.uniqueId}">
                                                            <i class="fa fa-edit"></i>
                                                        </button>
                                                        <button type="button" data-toggle="modal" class="btn btn-link btn-danger"
                                                                data-target="#${tempObjeto.id}${tempObjeto.uniqueId}">
                                                            <i class="fa fa-times"></i>
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        
                                                    </c:otherwise>
                                                </c:choose>
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
                                                    <span class="fw-light">este Detalle de Comprobante?</span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="DetalleComprobanteDestroyServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="destroyId" id="destroyId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Total</label>
                                                                <input type="text" class="form-control" value="${tempObjeto.total }" readonly>
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
                                                        Detalle de Comprobante
                                                    </span>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form  action="DetalleComprobanteEditServlet" method="post">
                                                    <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-group-default">
                                                                <label>Id</label>
                                                                <input name="editId" type="text" class="form-control" value="${tempObjeto.id }" readonly>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Cantidad</label>
                                                                <input required name="editCantidad" type="number" class="form-control" value="${tempObjeto.cantidad }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Precio</label>
                                                                <input required name="editPrecio" type="number" class="form-control" value="${tempObjeto.precio }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Subtotal</label>
                                                                <input required name="editSubtotal" type="number" class="form-control" value="${tempObjeto.subtotal }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>IGV</label>
                                                                <input required name="editIgv" type="number" class="form-control" value="${tempObjeto.igv }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Total</label>
                                                                <input required name="editTotal" type="number" class="form-control" value="${tempObjeto.total }">
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Comprobante</label>
                                                                <select class="form-control" name="editComprobanteId">
                                                                    <c:forEach var="tempObjetoEdit" items="${mi_lista_de_comprobantes}">
                                                                        <option value="${tempObjetoEdit.id }">${tempObjetoEdit.numero}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group form-group-default">
                                                                <label>Producto</label>
                                                                <select class="form-control" name="editServicioId">
                                                                    <c:forEach var="tempObjetoEdit2" items="${mi_lista_de_servicios}">
                                                                        <option value="${tempObjetoEdit2.id }">${tempObjetoEdit2.descripcion}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <%--                             
                                                            <div class="form-group form-group-default">
                                                              <label>Prenda</label>
                                                              <select class="form-control" name="editPrendaId">
                                                                <c:forEach var="tempObjetoEdit3" items="${mi_lista_de_prendas }">
                                                                  <option value="${tempObjetoEdit3.id }">${tempObjetoEdit3.marca }</option>
                                                                </c:forEach>
                                                              </select>
                                                            </div>
                                                            --%>
                                                            <div class="form-group form-group-default">
                                                                <label>Estado</label>
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Plasticos Continental | Página de registro</title>

        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="../assets/auth/plugins/fontawesome-free/css/all.min.css">
        <!-- icheck bootstrap -->
        <link rel="stylesheet" href="../assets/auth/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="../assets/auth/dist/css/adminlte.min.css">
    </head>
    <body class="hold-transition register-page">
        <div class="register-box">
            <!--      <div class="register-logo">
                    <a href="#">Lavandería<b>UTP</b></a>
                  </div>-->

            <div class="card">
                <div class="card-header text-center" style="background-color: whitesmoke;">
                    <a href="../index.jsp" class="logo">
                        <img src="../assets/img/Logo2.png" width="100%" height="100%" alt="navbar brand" class="navbar-brand">
                    </a>
                </div>
                <div class="card-body register-card-body">
                    <p class="login-box-msg">Registrar un nuevo miembro</p>

                    <form action="../PersonaCreateServlet" method="post">
                        <div class="input-group mb-3">
                            <input required name="addNombres" type="text" class="form-control" placeholder="Nombres">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input required name="addApellidos" type="text" class="form-control" placeholder="Apellidos">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input required name="addDni" type="text" class="form-control" placeholder="DNI">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-user-check"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input required name="addTelefono" type="text" class="form-control" placeholder="Teléfono">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-phone"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input required name="addDireccion" type="text" class="form-control" placeholder="Dirección">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-map-marker-alt"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input required name="addReferencia" type="text" class="form-control" placeholder="Referencia">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-map-marker-alt"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <select class="form-control" name="addDistritoId">
                                <option value="2">Alto Selva Alegre</option>
                                <option value="1">Arequipa</option>
                                <option value="3">Cayma</option>
                                <option value="4">Cerro Colorado</option>
                                <option value="5">Characato</option>
                                <option value="6">Chiguata</option>
                                <option value="7">Jacobo Hunter</option>
                                <option value="8">Jose Luis Bustamante y Rivero</option>
                                <option value="9">La Joya</option>
                                <option value="10">Mariano Melgar</option>
                                <option value="11">Miraflores</option>
                                <option value="12">Mollebaya</option>
                                <option value="13">Paucarpata</option>
                                <option value="14">Pocsi</option>
                                <option value="15">Polobaya</option>
                                <option value="16">Quequeña</option>
                                <option value="17">Sabandia</option>
                                <option value="18">Sachaca</option>
                                <option value="19">San Juan de Siguas</option>
                                <option value="20">San Juan de Tarucani</option>
                                <option value="21">Santa Isabel de Siguas</option>
                                <option value="22">Santa Rita de Siguas</option>
                                <option value="23">Socabaya</option>
                                <option value="24">Tiabaya</option>
                                <option value="25">Uchumayo</option>
                                <option value="26">Vitor</option>
                                <option value="27">Yanahuara</option>
                                <option value="28">Yarabamba</option>
                                <option value="29">Yura</option>
                            </select>
                        </div>
                        <div class="input-group mb-3">
                            <input required name="addEmail" type="email" class="form-control" placeholder="Email">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input required name="addPassword" type="password" class="form-control" placeholder="Contraseña">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input required name = "addPassword2" type="password" class="form-control" placeholder="Repita la contraseña">
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <div class="icheck-primary">
                                    <input type="checkbox" id="agreeTerms" name="terms" value="agree">
                                    <label for="agreeTerms">
                                        Acepto los <a href="#">términos y condiciones</a>
                                    </label>
                                </div>
                            </div>
                            <!-- /.col -->
                            <div class="col-4">
                                <button type="submit" class="btn btn-primary btn-block">Registrar</button>
                            </div>
                            <!-- /.col -->
                        </div>
                    </form>


                    <a href="../auth/login.jsp" class="text-center">Ya tengo una cuenta</a>
                </div>
                <!-- /.form-box -->
            </div><!-- /.card -->
        </div>
        <!-- /.register-box -->

        <!-- jQuery -->
        <script src="../assets/auth/plugins/jquery/jquery.min.js"></script>
        <!-- Bootstrap 4 -->
        <script src="../assets/auth/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- AdminLTE App -->
        <script src="../assets/auth/dist/js/adminlte.min.js"></script>
    </body>
</html>

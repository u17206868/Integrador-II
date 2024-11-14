<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>LavanderíaUTP | Olvidé mi contraseña</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../assets/auth/plugins/fontawesome-free/css/all.min.css">
    <!-- icheck bootstrap -->
    <link rel="stylesheet" href="../assets/auth/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../assets/auth/dist/css/adminlte.min.css">
  </head>
  <body class="hold-transition login-page">
    <div class="login-box">
      <!--            <div class="login-logo">
                      <a href="#">Lavandería<b>UTP</b></a>
                  </div>-->
      <!-- /.login-logo -->
      <div class="card">
        <div class="card-header text-center" style="background-color: whitesmoke;">
          <a href="../index.jsp" class="logo">
            <img src="../assets/img/HLavanderia.png" width="100%" height="100%" alt="navbar brand" class="navbar-brand">
          </a>
        </div>
        <div class="card-body login-card-body">
          <p class="login-box-msg">¿Olvidó su contraseña?</p>

          <form action="recover-password.html" method="post">
            <div class="input-group mb-3">
              <input type="email" class="form-control" placeholder="Email">
              <div class="input-group-append">
                <div class="input-group-text">
                  <span class="fas fa-envelope"></span>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-12">
                <button type="submit" class="btn btn-primary btn-block">Solicitar nueva contraseña</button>
              </div>
              <!-- /.col -->
            </div>
          </form>

          <p class="mt-3 mb-1">
            <a href="login.jsp">Iniciar sesión</a>
          </p>
          <p class="mb-0">
            <a href="register.jsp" class="text-center">Registrarse</a>
          </p>
        </div>
        <!-- /.login-card-body -->
      </div>
    </div>
    <!-- /.login-box -->

    <!-- jQuery -->
    <script src="../assets/auth/plugins/jquery/jquery.min.js"></script>
    <!-- Bootstrap 4 -->
    <script src="../assets/auth/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- AdminLTE App -->
    <script src="../assets/auth/dist/js/adminlte.min.js"></script>
  </body>
</html>

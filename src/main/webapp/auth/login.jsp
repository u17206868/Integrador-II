<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Log In</title>

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
<!--      <div class="login-logo">

        <a href="index.jsp" class="logo">
          <img src="../assets/img/Lavanderia.svg" alt="navbar brand" class="navbar-brand">
        </a>
        <a href="#">Lavandería<b>UTP</b></a>
      </div>-->
      <!-- /.login-logo -->
      <div class="card">
        <div class="card-header text-center" style="background-color: whitesmoke;">
          <a href="../index.jsp" class="logo">
            <img src="../assets/img/LogoReportes.jpg" width="100%" height="100%" alt="navbar brand" class="navbar-brand">
          </a>
        </div>
        <div class="card-body login-card-body">
          <p class="login-box-msg">Loguéate para ingresar</p>

          <form action="../PersonaLoginServlet" method="post">
            <div class="input-group mb-3">
              <input required name="loginEmail" type="email" class="form-control" placeholder="Email">
              <div class="input-group-append">
                <div class="input-group-text">
                  <span class="fas fa-envelope"></span>
                </div>
              </div>
            </div>
            <div class="input-group mb-3">
              <input required name="loginPassword" type="password" class="form-control" placeholder="Contraseña">
              <div class="input-group-append">
                <div class="input-group-text">
                  <span class="fas fa-lock"></span>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-8">
                <div class="icheck-primary">
                  <input type="checkbox" id="remember">
                  <label for="remember">
                    Recordarme
                  </label>
                </div>
              </div>
              <!-- /.col -->
              <div class="col-4">
                <button type="submit" class="btn btn-primary btn-block">Entrar</button>
              </div>
              <!-- /.col -->
            </div>
          </form>

          <p class="mb-0">
            <!--<a href="../AuthRegisterServlet" class="text-center">Registrarse</a>-->
            <a href="register.jsp" class="text-center">Registrarse</a>
          </p>
          <p class="mb-1">
            <a href="forgot-password.jsp">Olvidé mi contraseña</a>
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
















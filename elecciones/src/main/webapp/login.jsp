<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Municipalidad de Tres de Febrero - Elecciones Paso 2021</title>

        <!-- CSS -->
		
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
		<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />
		<link href="<c:url value="/resources/font-awesome/css/font-awesome.min.css" />" rel="stylesheet"  type="text/css" />
		<link href="<c:url value="/resources/css/form-elements.css" />" rel="stylesheet"  type="text/css" />
		<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet"  type="text/css" />

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->



    </head>

    <body>

        <!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">

                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>Acceder al Sistema</h3>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-key"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
								<form id="login_elecciones_2017" class="login-form" action="/12354854985/login" method="POST">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">Usuario</label>
			                        	<input type="text" name="username" placeholder="Ingrese usuario..." class="form-username form-control" id="username">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Clave</label>
			                        	<input type="password" name="password" placeholder="Ingrese clave..." class="form-password form-control" id="password">
			                        </div>
			                        <button type="submit" name="submit" class="btn">Acceder</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>



       <!-- <form method="POST" action="/asistenteHabilitaciones/login" class="form-signin">
             <h2 class="form-heading">Acceder a Gesti&oacute;n de Datos</h2>

             <div class="form-group ">
                 <span></span>
                 <input name="username" type="text" class="form-control" placeholder="Usuario"
                   autofocus="true"/>
                 <input name="password" type="password" class="form-control" placeholder="Clave"/>
                  <span></span>

                 <button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
             </div>

       </form>-->

        <!-- Javascript -->
		
		<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery.backstretch.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/scripts.js" />"></script>
		

        
        <!--[if lt IE 10]>
            <script src="<c:url value="/resources/js/placeholder.js" />"></script>
        <![endif]-->

    </body>

</html>
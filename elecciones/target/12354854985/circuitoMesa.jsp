<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Municipalidad de Tres de Febrero - Elecciones Paso 2017</title>
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
                <div class="container">
                    <div class="row">
		                <p>
							<c:if test="${not empty circuito}">
								Circuito ${circuito}
							</c:if>
				
							<c:if test="${not empty mesa}">
								Mesa ${mesa}
							</c:if>
					    </p>
                   </div>
       			 </div>

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
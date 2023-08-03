<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import = "java.net.URL" %>

<sec:authorize access="checkAccess('ADMIN')"> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Municipalidad de Tres de Febrero - Elecciones Paso 2021</title>
<style type="text/css">
html,body
{  
   padding:0;
   margin:0;
   width: 100%;
   height: 100%;
   box-sizing: border-box;
}


</style>

</head>
<body>
<iframe id="dashboardframe" width="100%" height="100%" src="resources/static/dashboard/index.html" style="position: absolute; height: 100%; border: none; width: 100%;"></iframe>
</body>
</html>
</sec:authorize>


<sec:authorize access="checkNoAccess('ADMIN')"> 
<%
            URL url = new URL(request.getRequestURL().toString());

            String url_redirect = "https://" + url.getHost() ;
            
            if(url.getPort() != 80)
            	url_redirect = url_redirect + ":" + url.getPort();
            
			url_redirect = url_redirect + ((String)request.getAttribute("javax.servlet.forward.request_uri"));

			response.sendRedirect(url_redirect.replace("dashboard", ""));
%>
</sec:authorize>

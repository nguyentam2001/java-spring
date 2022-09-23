<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My custom login page</title>
<link rel="stylesheet" type="text/css" href="css/demo.css">
</head>
<body>
	<form:form action="${pageContext.request.contextPath}/authenticateTheUser"
		method="POST">
		<!-- check for login error -->
		<c:if test="${param.error!=null }">
			<i class="failed">Sorry! Invalid username or password</i>
		</c:if>
		<p>
		User name: <input type="text" name="username"/>
		</p>
		<p>
		Password:<input type="password" name="password"/>
		</p>
		
		<input type="submit" value="login"/>
	</form:form>
</body>
</html>
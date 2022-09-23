<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>Luv2code Company Home Page</title>
</head>
<body>
	<h2>luv2code company home page-Yoohoo!</h2>
	<hr>
		Welcome to the luv2code Company home page
	<hr>
	<!-- Display user name and role -->
	<p>
		User:
		<security:authentication property="principal.username" />
		<br> Role(s)
		<security:authentication property="principal.authorities" />
	</p>
	<hr>
	
	<!-- display link of role-->
	<security:authorize access="hasRole('MANAGER')">
	<!-- Add a link to point to/ leader -->
	<p>
		<a href="${pageContext.request.contextPath}/leaders">LeaderShip
			meeting</a> (Only for Manager peeps)
	</p>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
	<p>
		<a href="${pageContext.request.contextPath}/systems">IT System
			Meeting</a> (Only for Admin peeps)
	</p>
	</security:authorize>
	<hr>

	<!-- Adding logout button -->
	<form:form action="${pageContext.request.contextPath }/logout"
		method="POST">
		<input type="submit" value="Logout" />
	</form:form>
</body>
</html>
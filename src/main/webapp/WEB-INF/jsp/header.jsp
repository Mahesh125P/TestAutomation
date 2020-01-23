<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>VDS Lite</title>
<!-- <meta name="viewport" content="width=device-width,initial-scale=1" /> -->
<!-- <meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> -->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0" />
<!--  disable zooming capabilities on mobile devices -->
<link href="webjars/bootstrap/4.2.1/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/custom.css"
	rel="stylesheet">
</head>
<body>
	<nav
		class=" container-fluid navbar navbar-expand-md navbar-dark bg-info vds-theme ">
		<!-- <nav class="navbar navbar-light bg-faded logo"> -->
		<%-- <div class="container-fluid nav navbar-right form-group navbar-expand-sm">
			<c:if test="${not empty userName}">
				<label class="pull-right">Welcome ${userName}</label>
				
			</c:if>
		</div>
		<div
			class="container-fluid nav navbar-right form-group navbar-expand-sm">
			<img src="<c:url value='/images/vds_logo.png'/>"
				alt="VDS" id="pic" height="25" width="55" />
				 <a href="<c:url value="/returnToHome" />">
					<img class="img-fluid" border="0" alt="VDS" src="<c:url value='${pageContext.request.contextPath}/images/Logo.png'/>" height="25" width="55">
				</a> 
			<h2 align="right" class="form-heading">Automated Testing Tool</h2>
		</div>			
		<div class="container-fluid nav navbar-right form-group navbar-expand-sm">
			<a href="<c:url value="/logout" />" class="btn btn-info btn-sm">
					<span class="glyphicon glyphicon-off"></span> Log out
			</a>
				
		</div> --%>
		<!-- </nav> -->

		<div style="width: 200px">
			<%-- <img src="<c:url value='/images/vds_logo.png'/>"
				alt="VDS" id="pic" height="25" width="55" /> --%>
				<a>
					<img class="img-fluid" border="0" alt="VDS" src="<c:url value='/images/Logo.png'/>" height="25" width="55">
				</a> 
		</div>
		<div  style="width: 800px">		
			<h2 align="center" >Automated Testing Tool</h2>
		</div>
		<div  style="width: 200px">
			<c:if test="${not empty userName}">
				<label class="pull-right">Welcome ${userName}</label>
				<a href="<c:url value="/logout" />" class="btn btn-info btn-sm">
					<span class="glyphicon glyphicon-off"></span> Log out
				</a>
			</c:if>
		</div>

	</nav>




	<div class="container-fluid table-responsive "
		style="padding: 15px; margin-bottom: 5px;"></div>
	<script src="webjars/jquery/3.3.1-1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.2.1/js/bootstrap.min.js"></script>
</body>
</html>
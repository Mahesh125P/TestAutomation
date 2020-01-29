<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0" />
      <title>Log in with your account</title>
     <link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
      <link href="${pageContext.request.contextPath}/bootstrap.min.js" rel="stylesheet"> 
      <link href="${pageContext.request.contextPath}/jquery-3.1.js" rel="stylesheet">
      <link href="${pageContext.request.contextPath}/custom.css"rel="stylesheet">
  </head>
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
  <body>

    <div class="container-fluid">
      <s:form method="POST" action="loginSubmit1" class="form-signin" modelAttribute="login">
        <h2 class="form-heading">User Login</h2>

        <div class="form-group">            
            <table align="left" style="margin-left:450px; margin-top:15px;">
            <tr><td>
            <span>${errorMessage}</span>
            </td></tr>
            <tr align="center"><td>
            <label style="align-content: center;">User Login</label>
            </td></tr>
            <tr><td>
            <s:input name="username" type="text" placeholder="Username" path="userName"
                   autofocus="true"/>
            </td></tr>     
            <tr><td>    
            <s:input name="password" type="password" placeholder="Password" path="password"/>
            </td></tr>
            <%-- <span>${error}</span> --%>
            <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
            <tr><td>&nbsp;
            </td></tr>
			<tr align="center"><td>
            <button style="size:200px; align-content: center;" type="submit">Log In</button>
            <%-- <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4> --%>
           </td></tr> 
            </table>
        </div>
      </s:form>
    </div>    
  </body>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html lang="en">
  <head>      
      <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0" />
      <title>Log in with your account</title>
     <link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
      <link href="${pageContext.request.contextPath}/bootstrap.min.js" rel="stylesheet"> 
      <link href="${pageContext.request.contextPath}/jquery-3.1.js" rel="stylesheet">
      <link href="${pageContext.request.contextPath}/custom.css"rel="stylesheet">
  </head>
<script type="text/javascript">
function handleSelect(appName) {
	//alert (appName.value);
	var url = '${pageContext.request.contextPath}/appToTest1?selectedApplicationName='+appName.value;
	window.location = url;
}

</script>
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
  <body>

    <div class="container-fluid">
      <s:form method="GET" action="startTest" class="form-signin"  modelAttribute="login">
<!--         <h2 class="form-heading">Test Automation</h2> -->

        <div class="form-group">
            <%-- <span>${errorMessage}</span> --%>
            <table style="width: 100%">
            <tr><td>
            <label>Application Name</label></td>
            <td>
            <s:select name="appsList" path="selectedApplicationName" class="form-control dropdown-primary w-auto bootstrap-select" onchange="javascript:handleSelect(this)">
            	<s:options items="${applicationList}" />
            </s:select>
            </td>
            <%-- <s:select id="selectedApplicationName" path="applicationList" multiple="single" onkeydown="javascript:handleSelect(this)"
                   autofocus="true"> --%>
                   <!-- <option value="VDS">Vehicle Distribution System</option>
                   <option value="ITRS">ITRS</option> -->
                   
                   <%-- <s:options  items="${applicationList}"/>  --%>
                   <%-- <c:forEach items="${sizeList}" var="s" varStatus="status">
			        <c:choose>
			            <c:when test="${s eq ""}">
			                <option value="${s}" selected="true">${s}</option>
			            </c:when>
			            <c:otherwise>
			                <option value="${s}">${s}</option>
			            </c:otherwise>
			        </c:choose> 
			    </c:forEach> --%>
			    
			    
			    <%-- <td>
				<s:select id="ApplicationName" cssClass="short" path="" onchange="javascript:handleSelect(this)">
				<c:if test="${selectedApplicationName == 'VDS'}">				
				<s:option value = 'VDS'/>
				<s:option value = 'ITRS'/>
				</c:if>
				<c:if test="${selectedApplicationName == 'ITRS'}">								
				<s:option value = 'VDS'/>
				<s:option value = 'ITRS'/>
				</c:if>
				</s:select> 
             </td> --%>
             <td>  
             <label>Screen Name</label>
             </td>
             <td>
             <%-- <if<%=  %>> --%>
             <s:select multiple="true" path="selectedScreenName"  cssClass="long" >
                   <s:options  items="${screenNameList}"/>
             </s:select>
             </td>  
             <%-- <td>  
             <label>Test Case Name</label>
             </td>
             <td>
             <if<%=  %>>
             <s:select multiple="true" path="testCaseList" cssClass="short" >
                   <s:options  items="${testCaseList}"/>
             </s:select>
             </td>   --%>
             </table>
           <%-- <s:input name="applicationName" type="text" class="form-control" placeholder="applicationName" path=""
                   autofocus="true"/> --%>
            <%-- <span>${error}</span> --%>
            <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
		</div>
		<div>
		<table style="margin-left:500px;align-content: center;height: 300px;"><tr><td>
            <button style="align-content: center;" type="submit">Start Testing</button>
            <%-- <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4> --%>
        </td></tr></table>    
        </div>
      </s:form>
    </div>
  </body>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</html>
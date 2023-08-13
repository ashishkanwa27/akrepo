<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page isELIgnored="false" %>
    <%@page import="java.util.Arrays"%>
    <%@page import="java.util.List"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>This is help page</title>
</head>
<body>

<%-- <% 
 String name=(String)  request.getAttribute("name");
%> --%>

<h1>Hello my name is ${name } </h1> 
<h1>This is help page</h1>

<c:forEach var="item" items="${marks}">
			<c:out value="${item }"></c:out>
</c:forEach>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Homepage</title>
</head>
<body>
<h1>This is home page</h1>
<h1>Called by home controller</h1>
<h1>url /home</h1>

<% 
String name=(String)request.getAttribute("name");
%>
<h1> Name is <%=name %> </h1>

</body>
</html>
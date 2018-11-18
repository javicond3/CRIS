<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Researchers List</title>
</head>
<body>
<%@ include file = "Header.jsp" %>
	<table border="1">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Last name</th>
			<th>Publications</th>
			<th>Email</th>
		</tr>
		<c:forEach items="${researcherList}" var="r_i">
			<tr>
				<td><a href="ResearcherServlet?id=${r_i.id}">${r_i.id}</a></td>
				<td>${r_i.name}</td>
				<td>${r_i.lastName}</td>
				<td>${fn:length(r_i.publications)}</td>
				<td>${r_i.email}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
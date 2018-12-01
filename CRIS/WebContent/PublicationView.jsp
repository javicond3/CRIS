<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Researcher view</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<p style="color: red;">${message}</p>
	<h2>Publication info</h2>
	<p>ID: ${publication.id }</p>
	<p>Name: ${publication.publicationName }</p>
	<p>Title: ${publication.title }</p>
	<p>Date: ${publication.publicationDate }</p>
	<p>Eid: ${publication.eid }</p>
	<p>Cites: ${publication.citeCount }</p>
	<p>First author: ${firstAuthor.name} ${firstAuthor.lastName}  </p>
	<h2>Researchers</h2>
	<table border="1">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Last Name</th>
		</tr>
		<c:forEach items="${authors}" var="r_i">
			<tr>
				<td><a href="ResearcherServlet?id=${r_i.id}">${r_i.id}</a></td>
				<td>${r_i.name}</td>
				<td>${r_i.lastName}</td>
			</tr>
		</c:forEach>
	</table>
	<!-- en los jsp se compara asi no con el equals -->
	<c:if test="${firstAuthor.id==user.id || userAdmin}">
You have permission to modify this page
<h3>Update info</h3>
		<form action="UpdatePublicationServlet" method="post">
			<input type="hidden" name="id" value="${publication.id}" />
			<p>
				Name: <input type="text" name="name" value="${publication.publicationName}" />
			</p>
			<p>
				Title: <input type="text" name="title"
					value="${publication.title}" />
			</p>
			<p>
				Date: <input type="text" name="date" value="${publication.publicationDate}" />
			</p>
			<p>
				Eid: <input type="text" name="eid" value="${publication.eid}" />
			</p>
			<button type="submit">Update</button>
		</form>
		<form action="UpdateCitationsApiServlet" method="get">
			<input type="hidden" name="id" value="${publication.id}" />
			<button type="submit">Update citations</button>
		</form>
	</c:if>
</body>
</html>
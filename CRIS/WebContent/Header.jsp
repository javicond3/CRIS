<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<% UserService userService = UserServiceFactory.getUserService(); %>

<c:if test="${pageContext.request.userPrincipal == null}">
    <p><a href='<%=userService.createLoginURL(request.getRequestURI())%>'> Login
 with Google</a>
    </p>
</c:if>

<c:if test="${pageContext.request.userPrincipal != null}">
    <p><a href='<%=userService.createLogoutURL(request.getRequestURI())%>'> Logout from Google</a>
    </p>
</c:if>
<p style="color: red;">${message}</p>
<h3>
	<a href="ResearcherListServlet">Researchers list</a>
</h3>
<hr>
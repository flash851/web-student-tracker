<%-- <%@ page import="java.util.*,com.flash.web.jdbc.*" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Student Tracker App</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<%-- <%
	//get the students from the request object( sent by the servlet)
	List<Student> students=(List<Student>)request.getAttribute("STUDENTS_LIST");
%> --%>
<body>
	<div id="wrapper">
		<div id="header">
		<h2>FooBar University</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
		
		<!--  put new button: add student  -->
		<input type="button" value="Add Student"
		onclick="window.location.href='add-student-form.jsp'; return false;"
		class="add-student-button"
		/>
		<table>
			<tr>
				<th>First Name</th>
				<th>First Name</th>
				<th>First Name</th>
			</tr>
			<%-- <%for(Student tempStudent:students){%> --%>
			<c:forEach var="tempStudent" items="${STUDENTS_LIST}">
			<tr>
				<%-- <td><%=tempStudent.getFirstName()%></td>
				<td><%=tempStudent.getLastName() %></td>
				<td><%=tempStudent.getEmail()%></td> --%>
				<td>${tempStudent.firstName}</td>
				<td>${tempStudent.lastName}</td>
				<td>${tempStudent.email}</td>
			</tr>
			</c:forEach>	
			<%-- <%}%> --%>
		</table>
		</div>
	</div>
	<%-- <%=students %> --%>
</body>
</html>
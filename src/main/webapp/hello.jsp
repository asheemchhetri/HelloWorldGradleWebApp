<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
<head>
	<title>HelloWorld</title>
	<style>
		th, tr, td, table {
			border: 1px solid red;
		}
	</style>
</head>
<body>
	<jsp:useBean id="greeting" class="HelloWorldGradleWebApp.Greeting"/>
	<h3>${greeting.hello}</h3>
	<hr/>
	<p>Using getProperty with jsp tag (Commit 1)</p>
	<h4><jsp:getProperty property="hello" name="greeting"/></h4>
	<p>Commit #2 to try out CI trigger ... </p>
	<hr/>
	<p>Commit #3 to try out CI trigger ... </p>
	<hr/>
	<p>Commit #4 to try out CI trigger ... </p>
	<hr/>
	<p>Commit #5 to try out CI trigger ... </p>
	<hr/>
	<p>Commit #6 to try out CI trigger on any commit and JDK 11 for gradlew ... </p>
	<hr/>
	<p>Commit #7 to try out CI trigger on any commit and JDK 11 for gradlew ... </p>
	<hr/>
	<p>Commit #8 to try out CI trigger and "STAGING" slots </p>
	<hr/>
	<p>Commit #9 to try out CI trigger and "STAGING" slots </p>
	<hr />
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
		</tr>

		<c:forEach items="${key_list}" var="usr" varStatus="idx">
			<tr>
				<td>${usr.first_name}</td>
				<td>${usr.last_name}</td>
				<td>${usr.email}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
	<title>HelloWorld</title>
</head>
<body>
	<jsp:useBean id="greeting" class="HelloWorldGradleWebApp.Greeting"/>
	<h3>${greeting.hello}</h3>
</body>
</html>
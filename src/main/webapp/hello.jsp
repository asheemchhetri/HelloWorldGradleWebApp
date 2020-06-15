<!DOCTYPE html>
<html>
<head>
	<title>HelloWorld</title>
</head>
<body>
	<jsp:useBean id="greeting" class="HelloWorldGradleWebApp.Greeting"/>
	<h3>${greeting.hello}</h3>
	<hr/>
	<p>Using getProperty with jsp tag (Commit 1)</p>
	<h4><jsp:getProperty property="hello" name="greeting"/></h4>
</body>
</html>
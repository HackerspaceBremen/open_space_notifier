<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%
		if(request.getParameter("title")!=null){
	%>
    <title><%=request.getParameter("title")%></title>
    <%
		}else{
    %>
    <title>Administration</title>
    <%
		}
    %>
    <%
		if(request.getParameter("child")!=null && request.getParameter("child").equals("true")){
	%>
	<link rel="stylesheet" href="../stylesheets/foundation.min.css">
    <script src="../javascripts/vendor/custom.modernizr.js"></script>
	<%				
		}else{
	%>
    <link rel="stylesheet" href="stylesheets/foundation.min.css">
    <script src="javascripts/vendor/custom.modernizr.js"></script>
    <%				
		}
	%>
</head>
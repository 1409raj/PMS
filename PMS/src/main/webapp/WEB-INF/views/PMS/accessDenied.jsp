<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code='heading.access.denied' /></title>
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
</head>
<body>
	<spring:message code='text.access.denied' />
	<!-- Select sum(remaining) as remaining,sum(total) as total
from (Select  case when label_name = name then 1 else 0 end as remaining,1 as total
 from employee_dynamic_form_details
Where NAME like 'Field%') gg; -->
	<!-- a href="<c:url value="/logout" />">Logout</a> -->
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
      <c:when test="${result=='SUCCESS'}">
		<div data-alert class="alert-box success">
		  Speichern war erfolgreich!
		  <a href="#" class="close">&times;</a>
		</div>
      </c:when>
      <c:when test="${result=='ERROR'}">
      	<div data-alert class="alert-box alert">
		  Die Eingaben sind fehlerhaft. Die Eingaben wurden zurückgesetzt
		  <a href="#" class="close">&times;</a>
		</div>
      </c:when>
	  <c:otherwise></c:otherwise>
</c:choose>
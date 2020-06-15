<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> -->
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="ListComputers"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                      <h1>Add Computer</h1> 
                    <!--<h1><spring:message code="label.addComputer" /></h1>-->
					<div class="form-group ">
                     </div>
                    <form action="AddComputer" method="POST" name="AddComputer">
                        <fieldset>
                           <!--  <input type="text" hidden id="computerId" name="computerId" value="${computerToUpdate.id }"/>  TODO: Change this value with the computer id -->
                        
                            <div class="form-group">
                               <label for="computerName">Computer name</label>
                                <!--<label for="computerName"><spring:message code="label.computerName" /></label>
                            -->    <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name">
                          		
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="text" class="form-control" id="introduced" name="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="text" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date">
                            </div>
                              <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
	                                <c:forEach items="${companies}" var="company">
	                                	<c:if test="${ company.id==1}">
	                                		 <option value="${company.id}" selected><c:out value="${company.name}"></c:out></option>
	                                	</c:if>
	                                	<c:if test="${company.id!=1}">
  	                                   		 <option value="${company.id}"><c:out value="${company.name}"></c:out></option>
										</c:if>  	                                   		 
	                                </c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        
                        <div class="actions pull-right">
                            <a href="ListComputers"><input type="submit" value="Add" class="btn btn-primary"></a>
                            or
                            <a href="ServletListComputers" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="js/Validation.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>

<%@ page isELIgnored="false"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC  "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="ListComputers"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
        	
        	<c:choose>
				<c:when test="${ searchForm != null }">
					
					<h1 id="homeTitle">
					${ nbComputer } Computers found for "${ searchForm }"
					</h1>
				</c:when>
				<c:otherwise>
					<h1 id="homeTitle">
						${ nbComputer } computers found
					</h1>
				</c:otherwise>
			</c:choose>
        	
        	
        	 <div align="right">
                    <a class="btn btn-default" id="editComputer" href="?lang=en">en</a> 
                    <a class="btn btn-default" id="editComputer" href="?lang=fr">fr</a>
                	<br><br>
                </div>
        	
            
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="searchForm" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="AddComputer"><spring:message code="label.addComputer"></spring:message></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="label.edit"></spring:message></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="ListComputers/deleteComputer" method="POST">
            <input type="hidden" name="selection" id="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="ListComputers" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:choose>
				<c:when test="${searchForm != null}">
                 	<c:forEach items="${computerList}" var="computer">
                    	<tr>
                        	<td class="editMode">
                            	<input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        	</td>
                        	<td>
                            	<a href="EditComputer?id=${computer.id}" onclick=""><c:out value="${computer.name}"></c:out></a>
                        	</td>
                        	<td><c:out value="${computer.introduced}"></c:out></td>
                        	<td><c:out value="${computer.discontinued}"></c:out></td>
                        	<td><c:out value="${computer.company.name}"></c:out></td>
                    	</tr>  
                    	</c:forEach>
               </c:when>
				<c:otherwise>
					 <c:forEach items="${computerList}" var="computer">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="EditComputer?id=${computer.id}" onclick=""><c:out value="${computer.name}"></c:out></a>
                        </td>
                        <td><c:out value="${computer.introduced}"></c:out></td>
                        <td><c:out value="${computer.discontinued}"></c:out></td>
                        <td><c:out value="${computer.company.name}"></c:out></td>
                    </tr>  
                    </c:forEach>
				</c:otherwise>
				</c:choose>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">

        <c:if test="${pageIterator!=null}">
            <ul class="pagination">
                <li>
                  <c:if test="${pageIterator>0}">
                    <a href="ListComputers?pageIterator=${pageIterator-1}" aria-label="Previous">

                      <span aria-hidden="true">&laquo;</span>
                  	</a>
                  </c:if>
              </li>
              <c:forEach  var = "i" begin = "1" end = "5">
              	<li><a href="ListComputers?pageIterator=${pageIterator+i}"><c:out value="${pageIterator+i}"></c:out></a></li>
              </c:forEach>
              
              <li>
              <c:if test="${pageIterator<nbPages}">
                <a href="ListComputers?pageIterator=${pageIterator+1}" aria-label="Next">

                    <span aria-hidden="true">&raquo;</span>
                </a>
                </c:if>
            </li>
        </ul>
        </c:if>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default"><a href="ListComputers?taillePage=10">10</a></button>
            <button type="button" class="btn btn-default"><a href="ListComputers?taillePage=50">50</a></button>
            <button type="button" class="btn btn-default"><a href="ListComputers?taillePage=100">100</a></button>

        </div>

    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>
</body>	
</html>
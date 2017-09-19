<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:url var="css" value="/resources/css" />
<spring:url var="js" value="/resources/js" />
<spring:url var="images" value="/resources/images" />


<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<meta name="_csrf" content="${_csrf.token }">
<meta name="_csrf_header" content="${_csrf.headerName }" >

<title>CharanKart - ${title}</title>

<script type="text/javascript">
	window.menu = '${title}';
	window.contextRoot = '${contextRoot}';
</script>

<!-- Bootstrap Core CSS -->
<link href="${css}/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Readable theme CSS download from bootswatch.com-->
<link href="${css}/bootstrap-readable-theme.css" rel="stylesheet">

<!-- Bootstrap DataTable theme for jQuery Datatables-->
<link href="${css}/dataTables.bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${css}/myapp.css" rel="stylesheet">


</head>

<body>

	<div class="wrapper">

		<!-- Navigation -->
		<%@include file="./shared/navbar.jsp"%>

		<!-- Page Content -->

		<div class="content">
			<c:if test="${userClickHome == true }">
				<!-- If user clicks home page then load this page in content section -->
				<%@include file="home.jsp"%>
			</c:if>

			<c:if test="${userClickAbout == true }">
				<!-- If user clicks About ,then load this page in content section -->
				<%@include file="about.jsp"%>
			</c:if>

			<c:if test="${userClickContact == true }">
				<!-- If user clicks Contact , then load this page in content section -->
				<%@include file="contact.jsp"%>
			</c:if>
			
			<!-- If user clicks Products , then load All Products or depending on Category -->
			<c:if test="${userClickAllProducts == true or userClickCategoryProducts == true}">
				
				<%@include file="listProducts.jsp"%>
			</c:if>
			
			<!-- If user clicks Specific Product , then load Corresponding Product  -->
			<c:if test="${userClickShowProduct == true  }">
				
				<%@include file="singleProduct.jsp"%>
			</c:if>
			
			<!-- If user clicks Manage Product , then load this page  -->
			<c:if test="${userClickManageProducts == true  }">
				
				<%@include file="manageProducts.jsp"%>
			</c:if>
			
			

		</div>


		<!-- /.container -->

		<!--  Footer line -->
		<%@include file="./shared/footer.jsp"%>

		
		<script src="${js}/jquery.js"></script>
		
		<script src="${js}/jquery.validate.js"></script>
		
		
		
		<script src="${js}/popper.min.js"></script>
		
		<!-- Bootstrap core JavaScript -->
		<script src="${js}/bootstrap.min.js"></script>
		
		<!-- Datatable plugin -->
		<script src="${js}/jquery.dataTables.js"></script>
		
		<!-- Datatable Bootstrap Script -->
		<script src="${js}/dataTables.bootstrap.js"></script>
		
		<!-- Bootbox JS -->
		<script src="${js}/bootbox.min.js"></script>
		
		<script src="${js}/myapp.js"></script>

	</div>
</body>

</html>

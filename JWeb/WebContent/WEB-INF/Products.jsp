<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>EpiBaby | Products</title>
<meta name="description"
	content="Documentation and reference library for ZURB Foundation. JavaScript, CSS, components, grid and more." />
<meta name="author"
	content="ZURB, inc. ZURB network also includes zurb.com" />
<meta name="copyright" content="ZURB, inc. Copyright (c) 2014" />
<link rel="shortcut icon" href="/JWeb/images/favicon.ico" />
<link rel="stylesheet" href="/JWeb/css/foundation.css" />
<script src="/JWeb/js/vendor/modernizr.js"></script>
<style>
img#productimage {
	max-width: 200px	;
	max-height: 200px;
}
h1 {
	margin-top: 20px;
}
</style>
</head>
<body>
	<jsp:include page="MenuTop.jsp" />
	<div class="row">
		<div class="large-12 columns">
			<jsp:include page="AddProduct.jsp" />
			<div class="row">
				<div class="large-10 columns">
					<h1>Nos produits</h1>
				</div>
			</div>
	    	<c:forEach var="item" items="${products}" >
				<div class="row">
					<div class="large-12 columns">
					<div class="panel clearfix">
						<div class="row">
								<div class="large-3 columns">
									<img id="productimage" class="productimage" src="<c:out value=" ${item.imagePath}"/>" >
								</div>
								<div class="large-9 columns">
										<div class="row">
											<h2 class="subheader"> 										
												<c:out value="${item.name}" />
											</h2>
										</div>
									<div class="row">
									<div class="large-3 columns">
										<div class="row">
											<h3 class="subheader">$
												<c:out value="${item.price}" />
											</h3>
											</div>
											<div class="row">
										<c:choose>
											<c:when test="${item.isInStock == true}">
												<p style="color:#43AC6A"><em>En stock</em></p>
											</c:when>
											<c:otherwise>
												<p style="color:#f04124"><em>Stocks épuisés</em></p>
											</c:otherwise>
										</c:choose>
										</div>
										<c:if test="${!empty sessionScope}">
										<c:if test="${sessionScope.usersession.isAdmin == true}">
										<div class="row">
											<form method="post" action="ChangeProductStock">
												<input type="hidden" name="callfrom" value="Products"/>
												<button type="submit" name="productId" value="${item.id}" class="button tiny success">Changer le stock</button>
											</form>
										</div>
										</c:if>
										</c:if>
									</div>
									<div class="large-6 columns">
									<div class="row">
									<p>
										<c:out value="${item.description}" />
									</p>
									</div>
									<div class="row large-4 columns">
									<c:if test="${!empty sessionScope}">
									<c:if test="${sessionScope.usersession.isAdmin == true}">
										<div class="right">
										<form method="post" action="DeleteProduct">
	 		 								<button type="submit" name="delete" value="${item.id}" class="button alert">Supprimer</button>
		 	 							</form>	
		 	 							</div>
									</c:if>
									</c:if>
									<div class="right">
										<a role="button" class="button" href="<c:out value="ProductView?product=${item.id}"/>">Voir détails</a>
									</div>
									</div>									
								</div>
									</div>
									</div>
			 	 				</div>
							</div>
						</div>
					</div>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="Footer.jsp"/>
</body>
</html>
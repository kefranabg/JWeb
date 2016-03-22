<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>EpiBaby | Accueil</title>
<meta name="description"
	content="Documentation and reference library for ZURB Foundation. JavaScript, CSS, components, grid and more." />
<meta name="author"
	content="ZURB, inc. ZURB network also includes zurb.com" />
<meta name="copyright" content="ZURB, inc. Copyright (c) 2014" />
<link rel="shortcut icon" href="/JWeb/images/favicon.ico" />
<link rel="stylesheet" href="/JWeb/css/foundation.css" />
<script src="/JWeb/js/vendor/modernizr.js"></script>
<style>
h1 {
	margin-top: 20px;	
}
</style>
</head>
<body>
	<jsp:include page="MenuTop.jsp" />
		<div class="row">
		<div class="large-12 columns">
			<jsp:include page="AddNews.jsp" />
			<div class="row">
				<div class="large-10 columns">
					<h1>Les news</h1>
				</div>
			</div>
	    	<c:forEach var="item" items="${news}" >
				<div class="row">
					<div class="large-12 columns">
					<div class="panel clearfix">
						<div class="row">
								<div class="large-3 columns">
									<img id="newsimage" class="newsimage" src="<c:out value=" ${item.imagePath}"/>" >
								</div>
								<div class="large-9 columns">
									<div class="row">
										<div class="large-6 columns">
											<h2 class="subheader"> 										
												<c:out value="${item.title}" />
											</h2>
										</div>
										<div class="large-3 columns">
											<p>${ item.dateHumanReadable }</p>
										</div>										
									</div>
									<div class="row">
									<div class="large-6 columns">
									<p>
										<c:out value="${item.description}" />
									</p>
									</div>
									<div class="row large-4 columns">
									<c:if test="${!empty sessionScope}">
									<c:if test="${sessionScope.usersession.isAdmin == true}">
										<div class="right">
										<form method="post" action="DeleteNews">
	 		 								<button type="submit" name="delete" value="${item.id}" class="button alert">Supprimer</button>
		 	 							</form>	
		 	 							</div>
									</c:if>
									</c:if>
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
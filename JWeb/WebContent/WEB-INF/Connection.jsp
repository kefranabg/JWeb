<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>EpiBaby | Connexion</title>
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
	margin-top:20px;
}
</style>
</head>
<body>
   	<jsp:include page="MenuTop.jsp"/>
	<div class="row">
		<div class="large-12 columns">
			<form method="post" action="Connection" data-abide>
				<div class="row">
					<div class="large-12 columns">
						<h1>Connexion</h1>
					</div>
				</div>
   	         	<div class="row">
   	         		<div class="large-12 columns">
						<label>Adresse e-mail
							<input type="email" id="userEmail" name="userEmail" required pattern="email"/>
						</label>
						<small class="error">Veuillez entrer une adresse e-mail valide.</small>
   	         		</div>
   	         	</div>
				<div class="row">
					<div class="large-12 columns">
						<label>Mot de passe
							<input type="password" id="userPassword" name="userPassword" required/>
						</label>
						<small class="error">Veuillez entrer votre mot de passe.</small>
					</div>
				</div>               
				<div class="row">
					<c:choose>
						<c:when test="${!empty sessionScope.usersession}">
							<div class="large-5 columns">
								<p>Vous êtes connecté(e) avec l'adresse : ${sessionScope.usersession.email}</p>
							</div>
						</c:when>
						<c:otherwise>
							<div class="large-5 columns">
								<a href="Inscription">Pas encore de compte? Inscris-toi !</a>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="large-5 columns">
						<button type="submit" class="right">Se connecter</button>
					</div>
				</div>				
        	</form>
				<c:if test="${ !empty error }">
					<div class="row">
						<div class="large-12 columns">
							<span class="error"><c:out value="${ error }"/></span>
						</div>
					</div>
				</c:if>
		</div>
	</div>
	<jsp:include page="Footer.jsp"/>
</body>
</html>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>EpiBaby | Inscription</title>
<meta name="description"
	content="Documentation and reference library for ZURB Foundation. JavaScript, CSS, components, grid and more." />
<meta name="author"
	content="ZURB, inc. ZURB network also includes zurb.com" />
<meta name="copyright" content="ZURB, inc. Copyright (c) 2014" />
<link rel="shortcut icon" href="/JWeb/images/favicon.ico" />
<link rel="stylesheet" href="/JWeb/css/foundation.css" />
<script src="/JWeb/js/vendor/modernizr.js"></script>
<style>
img.newsimage#newsimage {
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
			<form method="post" action="Inscription" data-abide>	
				<div class="row">
					<div class="large-12 columns">
						<h1>Inscription</h1>
					</div>
				</div>

				<div class="row">
					<div class="large-6 columns">
						<label for="userName">Nom
							<input	type="text" id="userName" name="userName" required/>
						</label>
						<small class="error">Vous devez entrer un nom d'utilisateur.</small>
					</div>
					<div class="large-6 columns">
						<label for="userEmail">Adresse e-mail
							<input type="email" id="userEmail" name="userEmail" required pattern="email"/>
						</label>
						<small class="error">Vous devez entrer une adresse e-mail valide.</small>
					</div>
				</div>
				
				<div class="row">
					<div class="large-12 columns">
						<label for="userPassword">Mot de passe
							<input	type="password" id="userPassword" name="userPassword" required/>
						</label>
						<small class="error">Vous devez entrer un mot de passe.</small>
					</div>
				</div>
				
				<div class="row">
					<div class="large-12 columns">
						<label for="userPasswordConfirmation">Confirmation du mot de passe
							<input type="password" id="userPasswordConfirmation" name="userPasswordConfirmation" required data-equalto="userPassword"/>
						</label>
						<small class="error">Les mots de passe ne sont pas les mêmes.</small>						
					</div>
				</div>
				<div class="row">
					<div class="large-12 columns">
						<button type="submit" class="right">Valider l'inscription</button>
					</div>
				</div>				
			</form>
			<c:if test="${ !empty userForm.errors }">
				<div class="row">
					<div class="large-12 columns">
						<span class="error">${ userForm.errors }</span>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:include page="Footer.jsp"/>
</body>
</html>
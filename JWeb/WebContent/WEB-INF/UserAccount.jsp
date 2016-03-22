<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>EpiBaby | Mon compte</title>
<meta name="description"
	content="Documentation and reference library for ZURB Foundation. JavaScript, CSS, components, grid and more." />
<meta name="author"
	content="ZURB, inc. ZURB network also includes zurb.com" />
<meta name="copyright" content="ZURB, inc. Copyright (c) 2014" />
<link rel="shortcut icon" href="/JWeb/images/favicon.ico" />
<link rel="stylesheet" href="/JWeb/css/foundation.css" />
<script src="/JWeb/js/vendor/modernizr.js"></script>
<style>
img.userimage#userimage {
	max-width: 400px;
	max-height: 400px;
}

h1 {
	margin-top: 20px;
}
</style>
</head>
<body>
	<jsp:include page="MenuTop.jsp" />
	<section role="main" class="scroll-container">
	<div class="row">
		<div class="large-12 columns">
			<form method="post" action="UserAccount"
				enctype="multipart/form-data" data-abide>
				<div class="row">
					<div class="large-12 columns">
						<h1>Modifier vos information</h1>
					</div>
				</div>
				<div class="row">
					<div class="large-10 columns">
						<div class="row">
							<div class="small-4 columns">
								<label for="userName">Nom <input type="text"
									id="userName" name="userName"
									value="<c:out value="${sessionScope.usersession.name}" />"
									required />
								</label> <small class="error">Vous devez entrer un nom
									d'utilisateur.</small>
							</div>
							<div class="small-4 columns end">
								<label for="userEmail">Adresse e-mail <input
									type="email" id="userEmail" name="userEmail"
									value="<c:out value="${sessionScope.usersession.email}" />"
									required pattern="email" />
								</label> <small class="error">Vous devez entrer une adresse
									e-mail valide.</small>
							</div>
						</div>
						<div class="row">
							<div class="large-8 columns">
								<label for="userPassword">Mot de passe <input
									type="password" id="userPassword" name="userPassword"
									value="<c:out value="${sessionScope.usersession.password}" />"
									required />
								</label> <small class="error">Vous devez entrer un mot de passe.</small>
							</div>
						</div>
						<div class="row">
							<div class="large-8 columns">
								<label for="userPasswordConfirmation">Confirmation du
									mot de passe <input type="password"
									id="userPasswordConfirmation" name="userPasswordConfirmation"
									value="<c:out value="${sessionScope.usersession.password}" />"
									required data-equalto="userPassword" />
								</label> <small class="error">Les mots de passe ne sont pas les
									mêmes.</small>
							</div>
						</div>
					</div>
					<div class="large-2 column">
						<img id="userimage"
							src="<c:out value="${sessionScope.usersession.imagePath}" />">
					</div>
				</div>
				<div class="row">
					<div class="large-1 columns">
						<c:choose>
							<c:when test="${sessionScope.usersession.newsletter == true}">
								<div class="switch tiny">
									<input type="checkbox" id="userNewsletter"
										name="userNewsletter" checked="checked" /> <label
										for="userNewsletter"></label>
								</div>
							</c:when>
							<c:otherwise>
								<div class="switch tiny">
									<input type="checkbox" id="userNewsletter"
										name="userNewsletter" /> <label for="userNewsletter"></label>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div>
						<label>S'inscrire à la newsletter</label>
					</div>
				</div>
				<div class="row">
					<div class="large-4 columns">
						<label for="userImage">Changer la photo de profil <input
							type="file" id="userImage" name="userImage" accept="image/*" />
						</label>
					</div>
					<div class="large-3 columns">
						<button type="submit">Enregistrer les modifications</button>
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
			<hr>
		</div>
	</div>
	<c:if test="${sessionScope.usersession.isAdmin == true}">
		<div class="row">
			<div class="large-12 columns">
				<div class="row">
					<div class="large-12 columns">
						<h1>Gérer les utilisateurs</h1>
					</div>
				</div>
				<div class="row">
					<div class="large-12 columns">
						<table>
							<tr>
								<th width="200">Id</th>
								<th width="200">Nom</th>
								<th width="200">E-mail</th>
								<th width="200">Newsletter</th>
								<th width="200">Date d'inscription</th>
								<th width="200">Action</th>
							</tr>
							<c:forEach var="item" items="${users}">
								<c:if test="${item.isAdmin == false}">
									<tr>
										<td><c:out value="${item.id}" /></td>
										<td><c:out value="${item.name}" /></td>
										<td><c:out value="${item.email}" /></td>
										<td class="switch">
											<c:choose>
												<c:when test="${item.newsletter == true}">
													<input type="checkbox" id="itemNewsletter" name="itemNewsletter" disabled checked="checked" />
													<label for="itemNewsletter"></label>
												</c:when>
												<c:otherwise>
													<input type="checkbox" id="itemNewsletter" name="itemNewsletter" disabled/>
													<label for="itemNewsletter"></label>
												</c:otherwise>
											</c:choose>
										</td>
										<td><c:out value="${ item.dateHumanReadable }"/></td>
										<td>
											<form method="post" action="DeleteUser">
												<button type="submit" name="delete" value="${item.id}"
													class="button alert">Supprimer</button>
											</form>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if> <jsp:include page="Footer.jsp" /> </section>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!empty sessionScope}">
	<form method="post" action="AddComment" data-abide>
		<h1>Ajouter un commentaire</h1>
		<label>Titre du commentaire
			<input type="text" name="title" required pattern="^[a-zA-Z,.!?éàçè]+$"/>
		</label>
		<small class="error">Vous devez entrer un titre valide pour votre commentaire.</small>
		<label>Description du commentaire
			<textarea id="comment" name="comment" required/></textarea>
		</label>
		<small class="error">Vous devez entrer un commentaire.</small>
		<input type="hidden" name="id_user" value="<c:out value="${sessionScope.usersession.id}"/>"> 
		<input type="hidden" name="id_product" value="<c:out value="${product.id}" />"> 
		<input type="hidden" name="product" value="<c:out value="${product.id}"/>">
		<button type="submit" class="button">Poster le commentaire</button>
	</form>
</c:if>
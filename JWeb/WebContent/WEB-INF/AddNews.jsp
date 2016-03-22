<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="/JWeb/js/vendor/jquery.js"></script>
<script>
$(document).ready(function(e){
  $("#addNewsHideButton").click(function(e){
    $("#addNewsDiv").slideToggle(500);
    if ($("#addNewsHideButton").val() == "Cacher l'administration des news") {
    	$("#addNewstHideButton").val("Afficher l'administration des news");
    }
	else {
		$("#addNewsHideButton").val("Cacher l'administration des news");
	}	
  });
});
</script>
<c:if test="${!empty sessionScope}">
<c:if test="${sessionScope.usersession.isAdmin == true}">
	<input class="button" type="button" id="addNewsHideButton" value="Cacher l'administration des news" />
		<div class="row">
			<div class="large-12 columns" id="addNewsDiv">
				<form method="post" action="AddNews" enctype="multipart/form-data" data-abide>
					<div class="row">
					<div class="large-10 columns">
						<h1>Ajouter une news</h1>
					</div>
					</div>
					<div class="row" >
						<div class="large-12 columns">
						<label>Titre de la news
							<input type="text" id="title" name="title" placeholder="Ex: Nouvel arrivage de bébés" required pattern="^[a-zA-Z éàçè'!?.,-]+$"/>
						</label>
						<small class="error">Vous devez entrer un titre valide pour la news.</small>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<label>Description de la news
								<textarea id="description" name="description" required/></textarea>
							</label>
							<small class="error">Vous devez entrer une description pour la news.</small>
						</div>
					</div>
					<div class="row">
						<div class="large-4 columns">
							<label>Ajouter une photo à
								<input type="file" id="image" name="image" accept=".jpg, .png, .gif" />
							</label>
							<small class="error">Vous devez choisir une image.</small>
						</div>
						<div class="large-4 columns">
							<button type="submit" class="right">Poster la news</button>
						</div>
					</div>
				</form>
				<c:if test="${ !empty newsForm.errors }">
					<div class="row">
						<div class="large-12 columns">
							<span class="error">${ newsForm.errors }</span>
						</div>
					</div>
				</c:if>
			<hr>
			</div>
		</div>
	</c:if>
</c:if>
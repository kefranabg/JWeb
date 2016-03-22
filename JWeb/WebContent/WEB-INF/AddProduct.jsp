<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="/JWeb/js/vendor/jquery.js"></script>
<script>
$(document).ready(function(e){
  $("#addProductHideButton").click(function(e){
    $("#addProductDiv").slideToggle(500);
    if ($("#addProductHideButton").val() == "Cacher l'administration des produits") {
    	$("#addProductHideButton").val("Afficher l'administration des produits");
    }
	else {
		$("#addProductHideButton").val("Cacher l'administration des produits");
	}	
  });
});
</script>
<c:if test="${!empty sessionScope}">
<c:if test="${sessionScope.usersession.isAdmin == true}">
	<input class="button" type="button" id="addProductHideButton" value="Cacher l'administration des produits" />
		<div class="row">
			<div class="large-12 columns" id="addProductDiv">
				<form method="post" action="AddProduct" enctype="multipart/form-data" data-abide>
					<div class="row">
					<div class="large-10 columns">
						<h1>Ajouter un produit</h1>
					</div>
					</div>
					<div class="row" >
						<div class="large-8 columns">
						<label>Nom du produit
							<input type="text" id="name" name="name" placeholder="Ex: Bébé Siamois" required pattern="^[a-zA-Z éàçè'!.,?-]+$"/>
						</label>
						<small class="error">Vous devez entrer un nom de produit valide.</small>
						</div>
					<div class="large-4 columns">
					    <div class="row collapse">
							<label>Prix
			      			    <input type="number" id="price" name="price" maxlength="12" required pattern="^[0-9,.]+$" />
							</label>
							<small class="error">Vous devez entrer un prix valide.</small>
				        </div>
   					</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<label>Description du produit
								<textarea id="description" name="description" required/></textarea>
							</label>
							<small class="error">Vous devez entrer une description pour le produit.</small>
						</div>
					</div>
					<div class="row">
						<div class="large-4 columns">
							<label>Ajouter une photo au produit
								<input type="file" id="image" name="image" accept=".jpg, .png, .gif" />
							</label>
							<small class="error">Vous devez choisir une image.</small>
						</div>
						<div class="large-4 columns">
							<button type="submit" class="right">Ajouter le produit</button>
						</div>
					</div>
				</form>
				<c:if test="${ !empty productForm.errors }">
					<div class="row">
						<div class="large-12 columns">
							<span class="error">${ productForm.errors }</span>
						</div>
					</div>
				</c:if>
			<hr>
			</div>
		</div>
	</c:if>
</c:if>
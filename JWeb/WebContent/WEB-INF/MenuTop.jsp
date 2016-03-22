<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="contain-to-grid sticky">
	<nav class="top-bar docs-bar" data-topbar role="navigation" data-options="sticky_on: large">
		<section class="top-bar-section">
				<ul class="title-area">
					<li>
						<a href="/JWeb/Acceuil">
						<img src="/JWeb/images/epibaby.jpg" width="40" height="40"/>
						</a>
					</li>
					<li class="name">
						<h1>
							<a href="/JWeb/Acceuil">
							EpiBaby
							</a>
						</h1>
					</li>
				</ul>
			<ul class="right">
				<li class="divider"></li>
				<li><a href="/JWeb/Acceuil">Accueil</a></li>
				<li class="divider"></li>
				<li><a href="/JWeb/Products">Produits</a></li>
				<li class="divider"></li>
				<c:choose>
					<c:when test="${!empty sessionScope.usersession}">
						<li><a href="/JWeb/UserAccount">Mon compte</a></li>
				<li class="divider"></li>
						<li><a href="/JWeb/Disconnection">Deconnexion</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/JWeb/Connection">Connexion</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</section>
	</nav>
</div>

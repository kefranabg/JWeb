<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<div class="large-12 columns">
		<div class="row">
			<div class="large-5 columns">
	    		<img id="productviewimage" src="<c:out value="${product.imagePath}"/>">				
			</div>
			<div class="large-7 columns">
	  			<h1><c:out value="${product.name}" /></h1>				
				<c:choose>
					<c:when test="${product.isInStock == true}">
						<p style="color: #43AC6A">
							<em>En stock</em>
						</p>
					</c:when>
					<c:otherwise>
						<p style="color: #f04124">
							<em>Stocks épuisés</em>
						</p>
					</c:otherwise>
				</c:choose>
				<h2 class="subheader">
					<c:out value="${product.price}" /> $
				</h2>
	   			<p><c:out value="${product.description}" /></p>	
				<c:if test="${!empty sessionScope}">
					<c:if test="${sessionScope.usersession.isAdmin == true}">
						<div class="right">
							<form method="post" action="DeleteProduct">
	 		 					<button type="submit" name="delete" value="${product.id}" class="button alert">Supprimer</button>
		 	 				</form>	
		 	 			</div>
						<div class="right">
							<form method="post" action="ChangeProductStock">
								<input type="hidden" name="callfrom" value="<c:out value="ProductView?product=${product.id}"/>"/>
								<button type="submit" name="productId" value="${product.id}" class="button success">Changer le stock</button>
							</form>	
						</div>
					</c:if>
				</c:if>
			</div>
	  	</div>
	</div>
</div>
<div class="row">
	<div class="large-12 columns">
		<hr>
		<jsp:include page="AddComment.jsp" />
		<h1>Commentaires</h1>
		<c:forEach varStatus="loop" items="${comments}">
				<div class="row">
					<div class="large-12 columns">
					<div class="panel clearfix">
						<div class="row">
							<div class="large-3 columns">
								<figure>
									<img id="usercommentimage" class="usercommentimage" src="<c:out value=" ${users[loop.index].imagePath}"/>" >
									<figcaption><c:out value="${users[loop.index].name}" /></figcaption>
								</figure>
							</div>
							<div class="large-9 columns">
								<div class="row">
									<div class="large-6 columns">
										<h3 class="subheader"> 								
											<c:out value="${comments[loop.index].title}" />
										</h3>
									</div>
									<div class="large-3 columns">
										<p><c:out value="${comments[loop.index].dateHumanReadable}" /></p>
									</div>										
								</div>
								<div class="row">
									<div class="large-6 columns">
										<p>
											<c:out value="${comments[loop.index].comment}" />
										</p>
									</div>
									<div class="row large-4 columns">
										<c:if test="${sessionScope.usersession.isAdmin == true}">
											<div class="right">
												<form method="post" action="DeleteComment">
													<input type="hidden" name="product"
														value="<c:out value="${product.id}"/>">
													<button type="submit" name="delete" value="${comments[loop.index].id}" class="button alert">Supprimer</button>
												</form>
											</div>
										</c:if>
										<c:if test="${sessionScope.usersession.name == users[loop.index].name}">
											<c:if test="${sessionScope.usersession.isAdmin == false}">
												<div class="right">
													<form method="post" action="DeleteComment">
														<input type="hidden" name="product"
															value="<c:out value="${product.id}"/>">
														<button type="submit" name="delete"	value="${comments[loop.index].id}" class="button alert">Supprimer</button>
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
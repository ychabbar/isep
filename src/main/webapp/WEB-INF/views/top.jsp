<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<nav class="navigation-bar dark shadow opacity">
<div class="navigation-bar-content dark opacity shadow">
	<a class="element brand image-button image-left" href="/"><strong>isepFM</strong>
		<img src="/resources/images/isepfm_logo.png" /></a> <span
		class="element-divider"></span> <a class="element brand" href="/">
		<span class="icon-music"> Musique</span>
	</a> <a class="element brand" href="#"> <span class="icon-user">
			Artiste</span>
	</a> <a class="element brand" href="#"> <span class="icon-playlist">
			Album</span>
	</a> <span class="element-divider"></span>

	<div class="element input-element">
		<form action="search" method="get">
			<div class="input-control text span3">
				<input type="text" id="recherche" name="recherche"
					placeholder="Recherche de musique">
				<button class="btn-search" type="submit" value="Recherche"></button>
			</div>
		</form>
	</div>


	<c:if test="${empty sessionScope.sessionUtilisateur}">
		<a class="element brand" href="/connexion"><span
			class="icon-user-3" id="createConnectionModal">Connexion</span></a>
		<span class="element-divider place-right"></span>


	</c:if>
	<c:if test="${!empty sessionScope.sessionUtilisateur}">
		<a class="element brand" href="/deconnexion"><span
			class="icon-user-3" id="createConnectionModal">Deconnexion</span></a>
		<span class="element-divider place-right"></span>


	</c:if>

	<button class="element place-right"
		onclick='document.location.href="inscription";'>Rejoignez-nous</button>
</div>
</nav>
</head>
<body>

</body>
</html>
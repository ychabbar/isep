<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/metro-bootstrap.css" rel="stylesheet">
<link href="resources/css/metro-bootstrap-responsive.css"
	rel="stylesheet">

<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/jquery.widget.min.js"></script>
<script src="resources/js/metro-loader.js"></script>

<style>
.indent {
	height: 40px;
}
</style>

<title>isepFM - Accueil</title>
</head>
<body class="metro">
	<div class="">
		<div class="container bg-white border">
			<jsp:include page="top.jsp" />
			<div class="indent"></div>
			<div class="grid">
				<!-- START of grid -->
				<div class="row">
					<!-- START welcome & share top -->
					<div class="span8">
						<img src="resources/images/isepfm_logo.png" class="span3">
						<div class="">
							<h1>LA référence</h1>
							<h3 class="text-justify">isepFM est un service de recherche
								de musique simple et rapide, recherchez votre bonheur par titre,
								genre ou album en quelques clics.</h3>
						</div>
					</div>

					<div class="row span3">
						<button class="command-button inverse">
							<i class="icon-info-2 on-left"></i> En savoir plus isepFM
						</button>
					</div>
					<div class="row span3">
						<a href="https://www.facebook.com/isepFM2013"
							class="command-button primary"> <i
							class="icon-facebook on-left"></i> Visitez notre page<small>sur
								facebook</small>
						</a>
					</div>
				</div>
				<!--  END welcome & share top -->

				<!-- START Albums of the week tiles -->
				<c:forEach items="${artistList}" var="artist" varStatus="rowCounter">
					<c:choose>
						<c:when
							test="${(rowCounter.count) == 1 ||(rowCounter.count) == 4 }">
							<div class="row">
						</c:when>
					</c:choose>

					<a href="/artist/${artist.name}"
						class="tile double double-vertical span3">
						<div class="tile-content image">
							<img src="resources/images/${artist.name}/mega.png">
						</div>
						<div class="brand bg-dark opacity border">
							<h2 class="fg-white">
								<strong> ${artist.name}</strong>
							</h2>
							<h3 class="fg-white"> ${nbNotes[rowCounter.index]} votes</h3>
						</div>
					</a>
					<c:choose>
						<c:when test="${rowCounter.count == 3 || rowCounter.count == 6}">
			</div>
			<div class="row">
				</c:when>
				</c:choose>
				</c:forEach>

				<!-- END Albums of the week tiles -->


				<div class="row">
					<!-- START of Top artist of the week -->
					<div class="span4 shadow">
						<h2>
							<strong>Albums <span class="fg-red">à la une</span>
							</strong>
							<div class="row">
						</h2>
						<div class="listview">

							<c:forEach items="${album}" var="albumUne" varStatus="statusUne">
								<c:choose>
						<c:when	test="${(statusUne.count) == 1 ||(statusUne.count) == 2 ||(statusUne.count) == 3 }">
						
								<a href="album/${albumUne.name}" class="list">
									<div class="list-content">
										<img
											src="resources/images/${albumArtist[statusUne.index]}/${albumFolder[statusUne.index]}/large.png"
											class="icon cycle">
										<div class="data">
											<span class="list-title">${albumUne.name}</span> <span
												class="list-remark">Sortie le ${albumUne.releaseDate}</span>
										</div>
									</div>
								</a>
								</c:when>
					</c:choose>
							</c:forEach>
						</div>
					</div>
					<!-- START of Top artist of the week -->
					<!-- START of Top Track of the week -->
					<div class="span4 shadow">
						<h2>
							<strong>Top <span class="fg-red"> Albums</span> (TOP like)
							</strong>
						</h2>
						<c:forEach items="${topLikeAlbum}" var="albumTopLike" varStatus="statusTopLike">
						<c:choose>
						<c:when
							test="${(statusTopLike.count) == 1 ||(statusTopLike.count) == 2 ||(statusTopLike.count) == 3 }">
							<div class="row">
					
						<div class="listview">
							<a href="album/${albumTopLike}" class="list">
								<div class="list-content">
									<img src="resources/images/${artistList2[statusTopLike.index]}/${albumFolder2[statusTopLike.index]}/large.png"
										class="icon cycle">
									<div class="data">
										<span class="list-title">${albumTopLike}</span> <span
											class="list-remark">${topNbLike[statusTopLike.index]} likes</span>
									</div>
								</div>
								
							<!--  </a> <a href="#" class="list">
								<div class="list-content">
									<img src="resources/images/excel2013icon.png"
										class="icon cycle">
									<div class="data">
										<span class="list-title">${album}</span> <span
											class="list-remark">3 114 votes</span>
									</div>
								</div>
							</a><a href="#" class="list">
								<div class="list-content">
									<img src="resources/images/excel2013icon.png"
										class="icon cycle">
									<div class="data">
										<span class="list-title">Daft Punk</span> <span
											class="list-remark">3 114 votes</span>
									</div>
								</div> -->
							</a>
						</div>
							</c:when>
					</c:choose>
						</c:forEach>
					</div>
					<!-- START of Top Track of the week -->
				</div>
			</div>
			<!-- END of grid -->
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#createConnectionModal_")
					.on(
							'click',
							function() {
								$
										.Dialog({
											overlay : true,
											shadow : true,
											flat : true,
											icon : '<span class="icon-user"></span>',
											title : 'Connexion',
											content : '<div style="width: 540px;"><form class="margin20"><fieldset><label>Identifiant</label><div class="input-control text" data-role="input-control"><input type="text" placeholder=""><button class="btn-clear" tabindex="-1" type="button"></button></div><label>Mot de passe</label><div class="input-control password" data-role="input-control"><input type="password" placeholder="" autofocus=""><button class="btn-reveal" tabindex="-1" type="button"></button></div> <div></div><input type="submit" value="Valider"></fieldset></form></div>',
											onShow : function(_dialog) {
												var html = [ '' ].join("");

												$.Dialog.content(html);
											}
										});
							});
		})
	</script>


</body>
</html>
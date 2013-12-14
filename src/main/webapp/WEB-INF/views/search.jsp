<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/resources/css/metro-bootstrap.css" rel="stylesheet">
<link href="/resources/css/metro-bootstrap-responsive.css"
	rel="stylesheet">

<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/jquery.widget.min.js"></script>
<script src="/resources/js/metro-loader.js"></script>

<style>
.indent {
	height: 40px;
}
</style>

<title>isepFM - Recherche</title>
</head>
<body class="metro">
	<div class="container bg-white border">

		<jsp:include page="top.jsp" />

		<div class="indent"></div>
		<div class="form-search grid show-grid">
			<fieldset>
				<legend>Recherche de musique sur isepFM </legend>
			</fieldset>
			
			<form action="search" method="get">
				<div class="input-control text span6">
					<input type="text" placeholder="Recherche de musique" value= "${recherche}"  id="recherche2" name="recherche" > 
					<button class="btn-search"></button>
					
			</form>
					<div class="row">
						<a onClick="window.location.href=(/albumsA/+$('#recherche2').val())" >							
							<h5 class="image-button bg-gray fg-white image-left">
								Artistes <i class="icon-playlist bg-gray fg-white"></i>
							</h5>	
						</a>
						
						<a onClick="window.location.href=(/albumsG/+$('#recherche2').val())" >
							<h5 class="image-button bg-gray fg-white image-left" >
								Genres <i class="icon-playlist bg-gray fg-white"></i>
							</h5>
						</a>
						<a onClick="window.location.href=(/albumsT/+$('#recherche2').val())"" >
							<h5 class="image-button bg-gray fg-white image-left">
								Titres <i class="icon-playlist bg-gray fg-white"></i>
							</h5>	
						</a>					
					</div>

				</div>
		
			
		</div>

		<div class="indent"></div>
		<nav class="breadcrumbs">
			<ul>
				<li><a href="#"><i class="icon-search"></i> Résultat de
						votre recherche</a></li>

				<li class="active"><a href="#">${recherche}</a></li>
			</ul>
		</nav>
		<div>
			<!-- START Albums of the week tiles -->

		</div>
		
		<div class="album-content-genre">
			<div class="span12">
				<div class="window flat shadow grid">
					<div class="caption">
						<span class="icon icon-playlist"></span>
						<div class="title">Album dont le genre correspond à ${recherche}: </div>

					</div>
					
					<div class="content">

						<div class="row">
							<c:forEach items="${albumGenre}" var="albumGenre" varStatus="status">
								
								<div class="tile bg-transparent">
								<a href="album/${albumGenre.name}">
							
								<img
										src="/resources/images/${artistListGenre[status.index]}/${albumFolderGenre[status.index]}/large.png"
										alt="120x120" style="width: 120px; height: 120px;"
										class="polaroid shadow">
										
								</a>
								</div>
								
								<div class="tile double rounded">
								<a href="album/${albumGenre.name}">
									<h4>${albumGenre.name}</h4>
									<h5>${artistListGenre[status.index]}</h5>
									<h6>${nbTrackByAlbumGenre[status.index]} titres (sorti le : ${dateSortieAlbumGenre[status.index]})</h6>
								</a>
								</div>							
								
						
							</c:forEach>
						</div>

						
						<div class="row">
							<a href="albumsG/${recherche}" >
								<h5 class="text-right" style="margin-right: 15px">
									Voir plus d'albums <i class="icon-arrow-right on-left on-right"
										style="background: darkblue; color: white; padding: 5px; border-radius: 50%"></i>
								</h5>
							</a>


						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="album-content-artist">
			<div class="span12">
				<div class="window flat shadow grid">
					<div class="caption">
						<span class="icon icon-playlist"></span>
						<div class="title">Album dont l'artiste correspond à ${recherche}: </div>

					</div>
					<div class="content">

						<div class="row">
							<c:forEach items="${albumArtist}" var="albumArtist" varStatus="status">

								<div class="tile bg-transparent">
								<a href="album/${albumArtist.name}">
									<img
										src="/resources/images/${artistListArtist[status.index]}/${albumFolderArtist[status.index]}/large.png"
										alt="120x120" style="width: 120px; height: 120px;"
										class="polaroid shadow">
								</a>
								</div>
								<div class="tile double rounded">
								<a href="album/${albumArtist.name}">
									<h4>${albumArtist.name}</h4>
									<h5>${artistListArtist[status.index]}</h5>
									<h6>${nbTrackByAlbumArtist[status.index]} titres (Sorti : ${dateSortieAlbumArtist[status.index]})</h6>
								</a>
								</div>
								

							</c:forEach>
						</div>

						
						<div class="row">
							<a href="albumsA/${recherche}" >
								<h5 class="text-right" style="margin-right: 15px">
									Voir plus d'albums <i class="icon-arrow-right on-left on-right"
										style="background: darkblue; color: white; padding: 5px; border-radius: 50%"></i>
								</h5>
							</a>


						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="album-content">
			<div class="span12">
				<div class="window flat shadow grid">
					<div class="caption">
						<span class="icon icon-playlist"></span>
						<div class="title">Album par titre correspond à ${recherche}: </div>

					</div>
					<div class="content">

						<div class="row">
							<c:forEach items="${albumTrack}" var="albumTrack" varStatus="status">

								<div class="tile bg-transparent">
								<a href="album/${albumTrack.name}">
									<img
										src="/resources/images/${artistListTrack[status.index]}/${albumFolderTrack[status.index]}/large.png"
										alt="120x120" style="width: 120px; height: 120px;"
										class="polaroid shadow">
								</a>
								</div>
								<div class="tile double rounded">
								<a href="album/${albumTrack.name}">
									<h4>${albumTrack.name}</h4>
									<h5>${artistListTrack[status.index]}</h5>
									<h6>${nbTrackByAlbumTrack[status.index]} titres (sorti le ${dateSortieAlbumTrack[status.index]})</h6>
								</a>
								</div>

							</c:forEach>
						</div>

						
						<div class="row">
							<a href="albumsT/${recherche}" >
								<h5 class="text-right" style="margin-right: 15px">
									Voir plus d'albums <i class="icon-arrow-right on-left on-right"
										style="background: darkblue; color: white; padding: 5px; border-radius: 50%"></i>
								</h5>
							</a>


						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="indent"></div>
	</div>
	<span class="element-divider place-right"></span>
	


	<script type="text/javascript">
		$(function() {
			$("#createFlatWindow")
					.on(
							'click',
							function() {
								$
										.Dialog({
											overlay : true,
											shadow : true,
											flat : true,
											icon : '<img src="/resources/images/excel2013icon.png">',
											title : 'Flat window',
											content : '',
											onShow : function(_dialog) {
												var html = [ '<iframe width="640" height="480" src="//www.youtube.com/embed/_24bgSxAD9Q" frameborder="0"></iframe>' ]
														.join("");

												$.Dialog.content(html);
											}
										});
							});
		})
	</script>
</body>
</html>
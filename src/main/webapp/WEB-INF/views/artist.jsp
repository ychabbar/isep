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

<title>isepFM - Artiste</title>
</head>
<body class="metro">
	<div class="container bg-white">

		<jsp:include page="top.jsp" />
		
		<div class="grid">
			<div class="row">
				<!-- START of Artist photo tiles -->
				<div class="span6 ">
					<div class="artist-photo-tiles">
						<div class="row no-margin">
							<div class="caption">
								<span class="icon icon-user"> ${artist.name}</span>
							</div>
						</div>
						<div class="row">
							<div class="span4 tile double-vertical">
								<img src="/resources/images/${artist.name}/mega.png">
							</div>
							<div class="">
								<div class="span2 tile">
									<img src="/resources/images/${artist.name}/extralarge.png">
								</div>
							</div>
							<div class="">
								<div class="span2 tile">
									<img src="/resources/images/${artist.name}/extralarge.png">
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<iframe style="margin-bottom: 0px" width="440" height="280"
						src="//www.youtube.com/embed?listType=search&list=${artist.name}"
						frameborder="0"> </iframe>
				</div>
			</div>
		</div>
		<div class="span12">
			<div class="artist-description">
				<div class="row bg-grayLighter border">

					<blockquote>
						<p class="border text-justify">${artist.description}</p>
					</blockquote>
				</div>

			</div>
		</div>
		<div class="grid">
			<div class="row">
				<div class="track-content span8">
					<div class="window flat shadow">
						<div class="caption">
							<span class="icon icon-music"></span>
							<div class="title">Titres</div>
						</div>

						<div class="content">
							<table class="table striped">
								<thead>
									<tr>
										<th class="text-left">Titre</th>
										<th class="text-left">Durée</th>

									</tr>
								</thead>
								<tbody>

									<c:forEach items="${trackList}" var="track" varStatus="status">
										<tr>
											<td><a href="#"> ${track.trackName}</a></td>
											<td class="right">${trackDuration[status.index]}</td>

										</tr>
									</c:forEach>
								</tbody>

								<tfoot></tfoot>
							</table>
							<div class="row">
								<a href="#">
									<h5 class="text-right" style="margin-right: 15px">
										Voir plus de titres <i
											class="icon-arrow-right on-left on-right"
											style="background: darkblue; color: white; padding: 5px; border-radius: 50%"></i>
									</h5>
								</a>
							</div>
						</div>
					</div>
				</div>
				<div class="span4">

					<div class="grid fluid">
						<div class="row" style="margin-top: 0px">
							<h3>Top 3 albums</h3>
							<div class="listview">

								<c:forEach items="${albumList}" var="albumList"
									varStatus="status">
									<a href="#" class="list bg-white fg-dark">
										<div class="list-content">
											<img
												src="/resources/images/${artist.name}/${albumPath[status.index]}/large.png"
												class="icon">
											<div class="data">
												<span class="list-title">${albumList.name}</span> <span
													class="list-remark">${albumList.releaseDate}</span>
											</div>
										</div>
									</a>
								</c:forEach>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="indent"></div>


	</div>

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
											icon : '<img src="resources/images/excel2013icon.png">',
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
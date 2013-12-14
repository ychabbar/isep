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
<script src="/resources/js/metro-scroll.js"></script>
<style>
.indent {
	height: 40px;
}
</style>

<title>isepFM - Album</title>
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
								<span class="icon icon-playlist"> ${albumName}</span>
							</div>
							<div class="caption">
								<span class="icon icon-user"> <a
									href="../artist/${artist}">${artist}</a></span>
							</div>
						</div>
						<div class="row">
							<div class="span4 tile double-vertical">
								<img
									src="/resources/images/${artist}/${albumFolder}/extralarge.png">
							</div>


							<div class="">
								<div class="span2 tile">
									<c:set var="note" value="${isNote}" />
									<c:choose>
										<c:when test="${empty sessionScope.sessionUtilisateur}">
											<img
												src="/resources/images/${artist}/${albumFolder}/large.png">
										</c:when>
										<c:when test="${note == true}">
											<p>Il y a eu ${nbVote} votes</p>
											<p>La moyenne est ${moyVote}</p>
										</c:when>
										<c:when test="${!empty sessionScope.sessionUtilisateur}">
											<div>
												<form:form method="post" action="${albumName}/addNote"
													commandName="rating">
								             1<input type="radio" name="rating" value="1">  &nbsp;&nbsp;<input
														type="submit" value="Je vote!" />
													<BR>								           
								             2<input type="radio" name="rating" value="2">
													<BR>
								             3<input type="radio" name="rating" value="3"> &nbsp;&nbsp; ${nbVote} votes<BR>
								             4<input type="radio" name="rating" value="4">
													<BR> 
								             5<input type="radio" name="rating" value="5"> &nbsp;&nbsp; Moy: ${moyVote}<BR>
												</form:form>
											</div>
										</c:when>
									</c:choose>
								</div>

							</div>
							<div class="">
								<div class="span2 tile">
									<c:set var="like" value="${isLike}" />
									<c:choose>
										<c:when test="${empty sessionScope.sessionUtilisateur}">
											<img
												src="/resources/images/${artist}/${albumFolder}/large.png">
										</c:when>
										<c:when test="${like == true}">
											<img src="/resources/images/jaime.jpg">
										</c:when>
										<c:when test="${!empty sessionScope.sessionUtilisateur}">
											<a href="/album/${albumName}/like"> <img
												src="/resources/images/like.jpg">
											</a>
											<!-- onmouseover="click()" -->
										</c:when>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<iframe style="margin-bottom: 0px" width="440" height="280"
						src="//www.youtube.com/embed?listType=search&list=${albumName}"
						frameborder="0"> </iframe>
				</div>
			</div>
		</div>
		<div class="span12">
			<div class="artist-description">
				<div class="row bg-grayLighter border">
					<div data-role="scrollbox" data-scroll="vertical">
						<blockquote>
							<p class="border text-justify">${description}</p>
						</blockquote>
					</div>
				</div>

			</div>
		</div>
		<div class="grid">
			<div class="row">
				<div class="track-content span8">
					<div class="window flat shadow">
						<div class="caption">
							<span class="icon icon-music"></span>
							<div class="title">Titres de ${albumName}</div>
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

						</div>
					</div>
				</div>
				<div class="span4">
					<div class="grid fluid">
						<div class="row" style="margin-top: 0px">
							<h3>Autres albums de ${artist}</h3>
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

		<div class="grid">
			<div class="comments">
				<div class="row">
					<h1>
						Commentaires
						<button class="button inverse" id="createComment">Ajouter</button>
					</h1>
				</div>
				<c:forEach items="${commentsList}" var="commentsList"
					varStatus="status">

					<div class="row">
						<blockquote>
							<p>${commentsList.content}</p>
							<small>${userNameList[status.index]} <cite
								title="Source Title"> ${commentsList.date}</cite></small>
						</blockquote>
					</div>
				</c:forEach>

			</div>
		</div>
	</div>

	<div style="display: none">
		<div class="commentForm">
			<form:form id="form" method="post" modelAttribute="Comments"
				action="addComment" commandName="Comments">
				<fieldset>
					<legend>Commentaire</legend>
					<div class="input-control textarea" data-role="input-control">
						<form:textarea path="content" maxlength="150" />
						<form:input path="id_album" value="${albumFromDB.id}" />
						<input type="hidden"
							value="${sessionScope.sessionUtilisateurName }" name="userName" />
					</div>
					<input type="submit" onClick="this.form.submit()" value="rr" />
				</fieldset>
			</form:form>
		</div>
	</div>

	<script type="text/javascript">
		function click() {
			alert("Clique sur l'image si tu aimes l'album !");
		}

		$("#createComment").on('click', function() {
			$.Dialog({
				shadow : true,
				overlay : false,
				icon : '<span class="icon-comments"></span>',
				title : 'Donnez votre avis',
				width : 600,
				padding : 10,
				content : $("div.commentForm").html()
			});
		});
	</script>
</body>
</html>
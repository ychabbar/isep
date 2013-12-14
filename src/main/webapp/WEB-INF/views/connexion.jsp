<%@ page pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="form.css" />

<link href="resources/css/metro-bootstrap.css" rel="stylesheet">
<link href="resources/css/metro-bootstrap-responsive.css"
	rel="stylesheet">


<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/jquery.widget.min.js"></script>
<script src="resources/js/metro-loader.js"></script>

</head>
<body class="metro">
	<div class="container bg-white">
		<jsp:include page="top.jsp" />
		<form method="post" action="connexion">
			<fieldset>
				<legend>Connexion</legend>
				<label for="nom">Nom d'utilisateur</label>
				<div class="input-control text" data-role="input-control">
					<input placeholder="" type="text" id="nom" name="nom"
						size="20"
						maxlength="20" />
					<button type="button" class="btn-clear" tabindex="-1"></button>
				</div>
				<label for="motdepasse">Mot de passe*</label>
				<div class="input-control password" data-role="input-control">
					<input placeholder="" type="password" id="motdepasse"
						name="motdepasse" value="" size="20" maxlength="20" />
					<button type="button" class="btn-reveal" tabindex="-1"></button>
				</div>
				<input value="Connexion" type="submit">
			</fieldset>
		</form>

		<span class="erreur">${form.erreurs['motdepasse']}</span>
		<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
		
	</div>
</body>
</html>
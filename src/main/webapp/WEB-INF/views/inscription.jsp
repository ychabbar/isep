<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Devenir membre d'isepFM</title>

<link href="resources/css/metro-bootstrap.css" rel="stylesheet">
<link href="resources/css/metro-bootstrap-responsive.css"
	rel="stylesheet">

<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/jquery.widget.min.js"></script>
<script src="resources/js/metro-loader.js"></script>

<title>isepFM - Inscription</title>
</head>
<body class="metro">
	<div class="container">
		<jsp:include page="top.jsp" />
		<form method="post" action="inscription">
			<fieldset>
				<legend>Inscription</legend>

				<label for="email">Adresse email <span class="requis">*</span></label>
				<div class="input-control text" data-role="input-control">
					<input type="email" id="mail" name="mail"
						value="<c:out value="${users.mail}"/>" size="20" maxlength="60" />
					<button type="button" class="btn-clear" tabindex="-1"></button>
				</div>

				<span class="erreur">${form.erreurs['mail']}</span> <br /> <label
					for="motdepasse">Mot de passe <span class="requis">*</span></label>

				<div class="input-control password" data-role="input-control">
					<input type="password" id="password" name="password" value=""
						size="20" maxlength="20" />
					<button type="button" class="btn-reveal" tabindex="-1"></button>
				</div>


				<label for="confirmation">Confirmation du mot de passe <span
					class="requis">*</span>
				</label>

				<div class="input-control password" data-role="input-control">
					<input type="password" id="confirmation" name="confirmation"
						value="" size="20" maxlength="20" />
					<button type="button" class="btn-reveal" tabindex="-1"></button>
				</div>


				<span class="erreur">${form.erreurs['password']}</span> <span
					class="erreur">${form.erreurs['confirmation']}</span> <label
					for="nom">Nom d'utilisateur</label>

				<div class="input-control text" data-role="input-control">

					<input type="text" id="name" name="name"
						value="<c:out value="${users.name}"/>" size="20" maxlength="20" />
					<button type="button" class="btn-clear" tabindex="-1"></button>
				</div>
				<span class="erreur">${form.erreurs['name']}</span> <br /> <input
					type="submit" value="Inscription" class="sansLabel" /> <br />

				<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
			</fieldset>
		</form>
	</div>
</body>
</html>
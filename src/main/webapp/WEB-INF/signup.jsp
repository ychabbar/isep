<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Devenir membre d'isepFM</title>

<link type="text/css" href="/resources/css/custom.css" rel="stylesheet" />
<link type="text/css" href="/resources/css/bootstrap.min.css"
	rel="stylesheet" />

</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="container">
				<div class="row clearfix">
					<div class="col-md-1 column"></div>
					<div class="well">
						<form class="form-horizontal">
							<fieldset>
								<!-- Form Name -->
								<legend>Devenir membre d'isepFM</legend>

								<div class="form-group">
									<label for="userName" class="col-sm-2 control-label">Nom
										d'utilisateur</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" id="userName"
											placeholder="" required>
									</div>
								</div>
								<div class="form-group">
									<label for="email" class="col-sm-2 control-label">Email</label>
									<div class="col-sm-5">
										<input type="email" class="form-control" id="inputEmail3"
											placeholder="" required>
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="col-sm-2 control-label">Mot
										de passe</label>
									<div class="col-sm-5">
										<input type="password" class="form-control" id="password"
											placeholder="" required>
									</div>
								</div>
								<div class="form-group">
									<label for="" confirmPassword"" class="col-sm-2 control-label">Confirmez
										le mot de passe</label>
									<div class="col-sm-5">
										<input type="password" class="form-control"
											id="confirmPassword" placeholder="" required>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-default">Validez
											l'inscription</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
					<div class="col-md-1 column"></div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
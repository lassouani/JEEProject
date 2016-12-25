<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
<title>Inscription</title>
</head>
<body>

	<form method="post" action="inscription">
	    <fieldset>
                <legend>Inscription</legend>
                <p>Vous pouvez vous inscrire via ce formulaire.</p>

                <label for="email">Adresse email <span class="requis">*</span></label>
                <input type="email" id="email" name="email" size="20" maxlength="60"  value="<c:out value="${param.email}"/>"   />
                <span class="erreur">${erreurs['email']}</span>
                <br />

                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${erreurs['motdepasse']}</span>
                <br />

                <label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>
                <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
                <span class="erreur">${erreurs['confirmation']}</span>
                <br />

                <label for="nom">Nom d'utilisateur</label>
                <input type="text" id="nom" name="nom" value="<c:out value="${param.nom}" />" size="20" maxlength="20" />
                <span class="erreur">${erreurs['nom']}</span>
                <br />

				 <p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>

                <input type="submit" value="Inscription" class="sansLabel" />
                <br />
            </fieldset>
        </form>

</body>
</html>
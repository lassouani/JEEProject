<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
<title>Listes Des Clients</title>
</head>
    <body>
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
        <c:choose>
            <%-- Si aucun client n'existe en session, affichage d'un message par d�faut. --%>
            <c:when test="${ empty sessionScope.clients }">
                <p class="erreur">Aucun client enregistr�.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Nom</th>
                    <th>Pr�nom</th>
                    <th>Adresse</th>
                    <th>T�l�phone</th>
                    <th>Email</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des clients en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.clients }" var="mapClients" varStatus="boucle">
                <%-- Simple test de parit� sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propri�t�s du bean Client, qui est stock� en tant que valeur de l'entr�e courante de la map --%>
                    <td><c:out value="${ mapClients.value.nom }"/></td>
                    <td><c:out value="${ mapClients.value.prenom }"/></td>
                    <td><c:out value="${ mapClients.value.adresse }"/></td>
                    <td><c:out value="${ mapClients.value.telephone }"/></td>
                    <td><c:out value="${ mapClients.value.email }"/></td>
                    <%-- Lien vers la servlet de suppression, avec passage du nom du client - c'est-�-dire la cl� de la Map - en param�tre gr�ce � la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/suppressionClient"><c:param name="nomClient" value="${ mapClients.key }" /></c:url>">
                            <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
    </body>
</html>
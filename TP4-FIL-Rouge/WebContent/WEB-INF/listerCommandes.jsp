<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
<title>Listes des Commandes</title>
</head>
<body>
        <c:import url="/inc/menu.jsp" />
        <div id="corps">
        <c:choose>
            <%-- Si aucune commande n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty sessionScope.commandes }">
                <p class="erreur">Aucune commande enregistrée.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Client</th>
                    <th>Date</th>
                    <th>Montant</th>
                    <th>Mode de paiement</th>
                    <th>Statut de paiement</th>
                    <th>Mode de livraison</th>
                    <th>Statut de livraison</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des commandes en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.commandes }" var="mapCommandes" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Commande, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${ mapCommandes.value.client.prenom } ${ mapCommandes.value.client.nom }"/></td>
                    <td><c:out value="${ mapCommandes.value.date }"/></td>
                    <td><c:out value="${ mapCommandes.value.montant }"/></td>
                    <td><c:out value="${ mapCommandes.value.modePaiement }"/></td>
                    <td><c:out value="${ mapCommandes.value.statutPaiement }"/></td>
                    <td><c:out value="${ mapCommandes.value.modeLivraison }"/></td>
                    <td><c:out value="${ mapCommandes.value.statutLivraison }"/></td>
                    <%-- Lien vers la servlet de suppression, avec passage de la date de la commande - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
                    <td class="action">
                        <a href="<c:url value="/suppressionCommande"><c:param name="dateCommande" value="${ mapCommandes.key }" /></c:url>">
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
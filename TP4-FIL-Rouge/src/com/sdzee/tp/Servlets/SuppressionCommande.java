package com.sdzee.tp.Servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.Beans.Commande;

public class SuppressionCommande extends HttpServlet {

    public static final String PARAM_DATE_COMMANDE = "dateCommande";
    public static final String SESSION_COMMANDES   = "commandes";

    public static final String VUE                 = "/listeCommandes";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* R�cup�ration du param�tre */
        String dateCommande = getValeurParametre( request, PARAM_DATE_COMMANDE );

        /* R�cup�ration de la Map des commandes enregistr�es en session */
        HttpSession session = request.getSession();
        Map<String, Commande> commandes = (HashMap<String, Commande>) session.getAttribute( SESSION_COMMANDES );

        /* Si la date de la commande et la Map des commandes ne sont pas vides */
        if ( dateCommande != null && commandes != null ) {
            /* Alors suppression de la commande de la Map */
            commandes.remove( dateCommande );
            /* Et remplacement de l'ancienne Map en session par la nouvelle */
            session.setAttribute( SESSION_COMMANDES, commandes );
        }

        /* Redirection vers la fiche r�capitulative */
        response.sendRedirect( request.getContextPath() + VUE );
    }

    /*
     * M�thode utilitaire qui retourne null si un param�tre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
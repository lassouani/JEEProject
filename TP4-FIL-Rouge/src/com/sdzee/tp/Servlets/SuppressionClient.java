package com.sdzee.tp.Servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.Beans.Client;

public class SuppressionClient extends HttpServlet {

    public static final String PARAM_NOM_CLIENT = "nomClient";
    public static final String SESSION_CLIENTS  = "clients";

    public static final String VUE              = "/listeClients";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* R�cup�ration du param�tre */
        String nomClient = getValeurParametre( request, PARAM_NOM_CLIENT );

        /* R�cup�ration de la Map des clients enregistr�s en session */
        HttpSession session = request.getSession();
        Map<String, Client> clients = (HashMap<String, Client>) session.getAttribute( SESSION_CLIENTS );

        /* Si le nom du client et la Map des clients ne sont pas vides */
        if ( nomClient != null && clients != null ) {
            /* Alors suppression du client de la Map */
            clients.remove( nomClient );
            /* Et remplacement de l'ancienne Map en session par la nouvelle */
            session.setAttribute( SESSION_CLIENTS, clients );
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
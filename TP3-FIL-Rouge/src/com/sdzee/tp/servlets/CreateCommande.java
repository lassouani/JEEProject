package com.sdzee.tp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;

import com.sdzee.tp.form.*;
/**
 * Servlet implementation class Commande
 */
@WebServlet("/Commande")
public class CreateCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/* Constantes */
    
	public static final String ATT_COMMANDE = "commande";
    public static final String ATT_FORM     = "form";

    public static final String VUE_SUCCES   = "/WEB-INF/afficheCommande.jsp";
    public static final String VUE_FORM     = "/WEB-INF/creerCommande.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCommande() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/* � la r�ception d'une requ�te GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/* Pr�paration de l'objet formulaire */
        CreationCommandeForm form = new CreationCommandeForm();

        /* Traitement de la requ�te et r�cup�ration du bean en r�sultant */
        Commande commande = form.creerCommande( request );

        /* Ajout du bean et de l'objet m�tier � l'objet requ�te */
        request.setAttribute( ATT_COMMANDE, commande );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErreurs().isEmpty() ) {
            /* Si aucune erreur, alors affichage de la fiche r�capitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, r�-affichage du formulaire de cr�ation avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
	
	
	}



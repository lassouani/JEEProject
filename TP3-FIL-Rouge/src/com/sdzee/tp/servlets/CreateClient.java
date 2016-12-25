package com.sdzee.tp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.form.CreationClientForm;

/**
 * Servlet implementation class Client
 */
@WebServlet("/Client")
public class CreateClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	    public static final String ATT_CLIENT = "client";
	    public static final String ATT_FORM   = "form";

	    public static final String VUE_SUCCES = "/WEB-INF/afficheClient.jsp";
	    public static final String VUE_FORM   = "/WEB-INF/creerClient.jsp";
    
    
    
    /**
     * Default constructor. 
     */
    public CreateClient() {
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	     /* À la réception d'une requête GET, simple affichage du formulaire */
	        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        /* Préparation de l'objet formulaire */
	        CreationClientForm form = new CreationClientForm();

	        /* Traitement de la requête et récupération du bean en résultant */
	        Client client = form.creerClient( request );

	        /* Ajout du bean et de l'objet métier à l'objet requête */
	        request.setAttribute( ATT_CLIENT, client );
	        request.setAttribute( ATT_FORM, form );

	        if ( form.getErreurs().isEmpty() ) {
	            /* Si aucune erreur, alors affichage de la fiche récapitulative */
	            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
	        } else {
	            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
	            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	        }
	    }
	

}

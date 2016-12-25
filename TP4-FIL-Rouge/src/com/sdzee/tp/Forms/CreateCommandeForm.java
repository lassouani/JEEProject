package com.sdzee.tp.Forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sdzee.tp.Beans.Client;
import com.sdzee.tp.Beans.Commande;

public final class CreateCommandeForm {
    private static final String CHAMP_CHOIX_CLIENT     = "choixNouveauClient";
    private static final String CHAMP_LISTE_CLIENTS    = "listeClients";
    private static final String CHAMP_DATE             = "dateCommande";
    private static final String CHAMP_MONTANT          = "montantCommande";
    private static final String CHAMP_MODE_PAIEMENT    = "modePaiementCommande";
    private static final String CHAMP_STATUT_PAIEMENT  = "statutPaiementCommande";
    private static final String CHAMP_MODE_LIVRAISON   = "modeLivraisonCommande";
    private static final String CHAMP_STATUT_LIVRAISON = "statutLivraisonCommande";

    private static final String ANCIEN_CLIENT          = "ancienClient";
    private static final String SESSION_CLIENTS        = "clients";
    private static final String FORMAT_DATE            = "dd/MM/yyyy HH:mm:ss";

    private String              resultat;
    private Map<String, String> erreurs                = new HashMap<String, String>();

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Commande creerCommande( HttpServletRequest request ) {
        Client client;
        /*
         * Si l'utilisateur choisit un client d�j� existant, pas de validation �
         * effectuer
         */
        String choixNouveauClient = getValeurChamp( request, CHAMP_CHOIX_CLIENT );
        if ( ANCIEN_CLIENT.equals( choixNouveauClient ) ) {
            /* R�cup�ration du nom du client choisi */
            String nomAncienClient = getValeurChamp( request, CHAMP_LISTE_CLIENTS );
            /* R�cup�ration de l'objet client correspondant dans la session */
            HttpSession session = request.getSession();
            client = ( (Map<String, Client>) session.getAttribute( SESSION_CLIENTS ) ).get( nomAncienClient );
        } else {
            /*
             * Sinon on garde l'ancien mode, pour la validation des champs.
             * 
             * L'objet m�tier pour valider la cr�ation d'un client existe d�j�,
             * il est donc d�conseill� de dupliquer ici son contenu ! � la
             * place, il suffit de passer la requ�te courante � l'objet m�tier
             * existant et de r�cup�rer l'objet Client cr��.
             */
            CreateClientForm clientForm = new CreateClientForm();
            client = clientForm.creerClient( request );

            /*
             * Et tr�s important, il ne faut pas oublier de r�cup�rer le contenu
             * de la map d'erreur cr��e par l'objet m�tier CreationClientForm
             * dans la map d'erreurs courante, actuellement vide.
             */
            erreurs = clientForm.getErreurs();
        }

        /*
         * Ensuite, il suffit de proc�der normalement avec le reste des champs
         * sp�cifiques � une commande.
         */

        /*
         * R�cup�ration et conversion de la date en String selon le format
         * choisi.
         */
        DateTime dt = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
        String date = dt.toString( formatter );

        String montant = getValeurChamp( request, CHAMP_MONTANT );
        String modePaiement = getValeurChamp( request, CHAMP_MODE_PAIEMENT );
        String statutPaiement = getValeurChamp( request, CHAMP_STATUT_PAIEMENT );
        String modeLivraison = getValeurChamp( request, CHAMP_MODE_LIVRAISON );
        String statutLivraison = getValeurChamp( request, CHAMP_STATUT_LIVRAISON );

        Commande commande = new Commande();

        commande.setClient( client );

        double valeurMontant = -1;
        try {
            valeurMontant = validationMontant( montant );
        } catch ( Exception e ) {
            setErreur( CHAMP_MONTANT, e.getMessage() );
        }
        commande.setMontant( valeurMontant );

        commande.setDate( date );

        try {
            validationModePaiement( modePaiement );
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_PAIEMENT, e.getMessage() );
        }
        commande.setModePaiement( modePaiement );

        try {
            validationStatutPaiement( statutPaiement );
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_PAIEMENT, e.getMessage() );
        }
        commande.setStatutPaiement( statutPaiement );

        try {
            validationModeLivraison( modeLivraison );
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_LIVRAISON, e.getMessage() );
        }
        commande.setModeLivraison( modeLivraison );

        try {
            validationStatutLivraison( statutLivraison );
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_LIVRAISON, e.getMessage() );
        }
        commande.setStatutLivraison( statutLivraison );

        if ( erreurs.isEmpty() ) {
            resultat = "Succ�s de la cr�ation de la commande.";
        } else {
            resultat = "�chec de la cr�ation de la commande.";
        }
        return commande;
    }

    private double validationMontant( String montant ) throws Exception {
        double temp;
        if ( montant != null ) {
            try {
                temp = Double.parseDouble( montant );
                if ( temp < 0 ) {
                    throw new Exception( "Le montant doit �tre un nombre positif." );
                }
            } catch ( NumberFormatException e ) {
                temp = -1;
                throw new Exception( "Le montant doit �tre un nombre." );
            }
        } else {
            temp = -1;
            throw new Exception( "Merci d'entrer un montant." );
        }
        return temp;
    }

    private void validationModePaiement( String modePaiement ) throws Exception {
        if ( modePaiement != null ) {
            if ( modePaiement.length() < 2 ) {
                throw new Exception( "Le mode de paiement doit contenir au moins 2 caract�res." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de paiement." );
        }
    }

    private void validationStatutPaiement( String statutPaiement ) throws Exception {
        if ( statutPaiement != null && statutPaiement.length() < 2 ) {
            throw new Exception( "Le statut de paiement doit contenir au moins 2 caract�res." );
        }
    }

    private void validationModeLivraison( String modeLivraison ) throws Exception {
        if ( modeLivraison != null ) {
            if ( modeLivraison.length() < 2 ) {
                throw new Exception( "Le mode de livraison doit contenir au moins 2 caract�res." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de livraison." );
        }
    }

    private void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( statutLivraison != null && statutLivraison.length() < 2 ) {
            throw new Exception( "Le statut de livraison doit contenir au moins 2 caract�res." );
        }
    }

    /*
     * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * M�thode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
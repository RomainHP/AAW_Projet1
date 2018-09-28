/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;

/**
 *
 * @author rcharpen
 */
public class Utilisateur implements Serializable {
    
    private String identifiant;
    private String motDePasse;
    
    public Utilisateur(String id, String mdp){
        this.identifiant=id;
        this.motDePasse=mdp;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
}

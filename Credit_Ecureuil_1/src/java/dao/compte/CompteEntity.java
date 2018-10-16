package dao.compte;

import dao.utilisateur.UtilisateurEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rcharpen
 */
@Entity
@Table(name="Compte")
public class CompteEntity implements Serializable {
    private static Long cptIdCompte = 1l;
    
    @Id
    private Long id;
    
    private String nom;
    
    @ManyToOne
    private UtilisateurEntity proprietaire;
    
    private Double solde;
        
    public CompteEntity(){
	
    }
    
    public CompteEntity(UtilisateurEntity prop){
        this.id=cptIdCompte++;
        this.nom="default";
	this.proprietaire = prop;
	this.solde = 100d;
    }
    
    public CompteEntity(String nom, UtilisateurEntity prop){
        this.id = cptIdCompte++;
        this.nom=nom;
	this.solde = 100d;
	this.proprietaire = prop;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public UtilisateurEntity getProprietaire(){
	return this.proprietaire;
    }
    
    public void setProprietaire(UtilisateurEntity ue){
	this.proprietaire = ue;
    }
    
    public Double getSolde(){
	return this.solde;
    }
    
    public void setSolde(Double val){
	this.solde = val;
    }
}

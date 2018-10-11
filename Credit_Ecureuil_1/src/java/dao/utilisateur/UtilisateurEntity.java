package dao.utilisateur;

import dao.compte.CompteEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rcharpen
 */
@Entity
@Table(name="Utilisateur")
public class UtilisateurEntity implements Serializable {
    
    @Id
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;

    @OneToMany
    private List<CompteEntity> comptes = new ArrayList<CompteEntity>();

    public UtilisateurEntity(){
        this.email="";
        this.motDePasse="";
        this.nom="";
        this.prenom="";
    }
    
    public UtilisateurEntity(String id, String mdp){
        this.email=id;
        this.motDePasse=mdp;
        this.nom="";
        this.prenom="";
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    
    public List<CompteEntity> getComptes(){
	return this.comptes;
    }
    
    public void setEmail(String id){
	this.email = id;
    }
    
    public void setMotDePasse(String mdp){
	this.motDePasse = mdp;
    }
    
    public void setComptes(List<CompteEntity> comptes){
	this.comptes = comptes;
    }
    
}

package dao.entreprise;

import dao.utilisateur.UtilisateurEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author romain
 */
@Entity
@Table(name="Entreprise")
public class EntrepriseEntity implements Serializable {
    @Id
    private Long siret;
    
    @Column
    private String nom;
    
    @OneToOne
    private UtilisateurEntity proprietaire = null;
    
    public EntrepriseEntity(){
        this.siret=0l;
        this.nom="";
    }
    
    public EntrepriseEntity(Long siret, String nom, UtilisateurEntity proprietaire){
        this.siret = siret;
        this.nom = nom;
        this.proprietaire = proprietaire;
    }

    public Long getSiret() {
        return siret;
    }

    public String getNom() {
        return nom;
    }

    public void setSiret(Long siret) {
        this.siret = siret;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public UtilisateurEntity getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(UtilisateurEntity proprietaire) {
        this.proprietaire = proprietaire;
    }
    
    @Override
    public String toString(){
        if (nom.isEmpty()){
            return String.valueOf(siret);
        } else {
            return siret + " (" + nom + ")";
        }
    }
}

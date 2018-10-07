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
@Table(name="Accounts")
public class CompteEntity implements Serializable {

    @Id
    private Long id;
    
    private String nom;
    
    @ManyToOne
    @JoinColumn(name="account_FK")
    private UtilisateurEntity compte;
        
    public CompteEntity(Long id, String nom){
        this.id = id;
        this.nom=nom;
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
    
    public UtilisateurEntity getUtilisateur(){
	return this.compte;
    }
    
    public void setUtilisateur(UtilisateurEntity ue){
	this.compte = ue;
    }
}

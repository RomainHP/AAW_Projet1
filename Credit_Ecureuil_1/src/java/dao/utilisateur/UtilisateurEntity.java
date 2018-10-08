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
    private String identifiant;
    private String motDePasse;

//    @OneToMany(mappedBy="compte")
//    private List<CompteEntity> comptes = new ArrayList<CompteEntity>();

    public UtilisateurEntity(String id, String mdp){
        this.identifiant=id;
        this.motDePasse=mdp;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    
//    public List<CompteEntity> getComptes(){
//	return this.comptes;
//    }
    
    public void setIdentifiant(String id){
	this.identifiant = id;
    }
    
    public void setMotDePasse(String mdp){
	this.motDePasse = mdp;
    }
    
//    public void setComptes(List<CompteEntity> comptes){
//	this.comptes = comptes;
//    }
    
}

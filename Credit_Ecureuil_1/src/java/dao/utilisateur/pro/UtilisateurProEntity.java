package dao.utilisateur.pro;

import dao.entreprise.EntrepriseEntity;
import dao.utilisateur.UtilisateurEntity;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author romain
 */
@Entity
@Table(name="UtilisateurPro")
public class UtilisateurProEntity extends UtilisateurEntity {
    
    @OneToOne(orphanRemoval = true)
    private EntrepriseEntity entreprise = null;
    
    public UtilisateurProEntity(){
        super();
    }
    
    public UtilisateurProEntity(String id, String mdp){
        super(id,mdp);
    }
    
    public EntrepriseEntity getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseEntity entreprise) {
        this.entreprise = entreprise;
    }
}

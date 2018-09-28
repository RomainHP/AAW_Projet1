package dao.utilisateur;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author rcharpen
 */
@Entity
public class UtilisateurEntity implements Serializable {
    
    @Id
    private String identifiant;
    private String motDePasse;

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
}

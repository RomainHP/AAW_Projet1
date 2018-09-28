package dao.compte;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author rcharpen
 */
@Entity
public class CompteEntity implements Serializable {

    @Id
    private Long id;
    
    private String nom;
    
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
    
}

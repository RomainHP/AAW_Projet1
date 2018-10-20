package dao.compte;

import dao.transaction.TransactionEntity;
import dao.utilisateur.UtilisateurEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Compte")
public class CompteEntity implements Serializable {  
    public static Long cptCompte = 1l;
    
    @Id
    private Long id;
    
    private String nom;
    
    @ManyToOne
    private UtilisateurEntity proprietaire;
    
    @OneToMany
    private List<TransactionEntity> transactions = new ArrayList<TransactionEntity>();
    
    private Double solde;
        
    public CompteEntity(){
	
    }
    
    public CompteEntity(UtilisateurEntity prop){
	this.id = cptCompte++;
        this.nom="default";
	this.proprietaire = prop;
	this.solde = 100d;
    }
    
    public CompteEntity(String nom, UtilisateurEntity prop){
	this.id = cptCompte++;
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

    public List<TransactionEntity> getTransactions() {
	return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
	this.transactions = transactions;
    }
}

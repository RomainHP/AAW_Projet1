package dao.compte;

import dao.transaction.TransactionEntity;
import dao.utilisateur.UtilisateurEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Compte")
@Inheritance(
    strategy = InheritanceType.JOINED
)
public class CompteEntity implements Serializable {  
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private UtilisateurEntity proprietaire;
    
    @OneToMany
    private List<TransactionEntity> transactions;
    
    private Double solde;
        
    public CompteEntity(){
	this(null);
    }
    
    public CompteEntity(UtilisateurEntity prop){
	this.proprietaire = prop;
	this.solde = 0d;
    }
    
    public CompteEntity(UtilisateurEntity prop, double solde){
	this.solde = solde;
	this.proprietaire = prop;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    @Override
    public String toString(){
        return "Compte courant";
    }
}

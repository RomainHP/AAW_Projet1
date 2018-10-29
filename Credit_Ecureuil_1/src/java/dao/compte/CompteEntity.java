package dao.compte;

import dao.transaction.TransactionEntity;
import dao.utilisateur.UtilisateurEntity;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @OneToMany(mappedBy = "cptDest")
    private List<TransactionEntity> transactions_in;
    
    @OneToMany(mappedBy = "cptSource")
    private List<TransactionEntity> transactions_out;
    
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

    public List<TransactionEntity> getTransactions_in() {
        return transactions_in;
    }

    public List<TransactionEntity> getTransactions_out() {
        return transactions_out;
    }

    public void setTransactions_in(List<TransactionEntity> transactions_in) {
        this.transactions_in = transactions_in;
    }

    public void setTransactions_out(List<TransactionEntity> transactions_out) {
        this.transactions_out = transactions_out;
    }
    
    public List<TransactionEntity> getAllTransactions(){
        List<TransactionEntity> result = new ArrayList<>(this.transactions_in);
        result.addAll(this.transactions_out);
        // tri en fonction de la date (en decroissant)
        result.sort(new Comparator<TransactionEntity>(){
            @Override
            public int compare(TransactionEntity o1, TransactionEntity o2) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    return format.parse(o2.getDate()).compareTo(format.parse(o1.getDate()));
                } catch (ParseException ex) {
                    Logger.getLogger(CompteEntity.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        });
        return result;
    }
    
    @Override
    public String toString(){
        return "Compte courant";
    }
}

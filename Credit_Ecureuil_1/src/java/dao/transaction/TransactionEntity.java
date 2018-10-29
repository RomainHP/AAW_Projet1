package dao.transaction;

import dao.compte.CompteEntity;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author etienne
 */
@Entity
@Table(name="Transactions")
public class TransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private CompteEntity cptSource;
    
    @ManyToOne
    private CompteEntity cptDest;
    
    private String date;
    
    private Double montant;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public TransactionEntity(){
	
    }
    
    public TransactionEntity(CompteEntity cesrc, CompteEntity cedst, Double val){
	this.cptSource = cesrc;
	this.cptDest = cedst;
	this.montant = val;
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date today = Calendar.getInstance().getTime();
	this.date = df.format(today);
    }

    public CompteEntity getCptSource() {
	return cptSource;
    }

    public void setCptSource(CompteEntity cptSource) {
	this.cptSource = cptSource;
    }

    public CompteEntity getCptDest() {
	return cptDest;
    }

    public void setCptDest(CompteEntity cptDest) {
	this.cptDest = cptDest;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public Double getMontant() {
	return montant;
    }

    public void setMontant(Double montant) {
	this.montant = montant;
    }
    
    
}

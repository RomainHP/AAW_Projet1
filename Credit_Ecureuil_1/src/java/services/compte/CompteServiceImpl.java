package services.compte;

import dao.compte.CompteDao;
import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
import dao.transaction.TransactionDaoImpl;
import dao.transaction.TransactionEntity;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CompteServiceImpl implements CompteService{
   
    @Autowired
    private CompteDaoImpl dao;
    
    @Autowired
    private UtilisateurDaoImpl udi;
    
    @Autowired TransactionDaoImpl tdi;

    public CompteServiceImpl(){
	this.dao = new CompteDaoImpl();
	this.udi = new UtilisateurDaoImpl();
	this.tdi = new TransactionDaoImpl();
    }

    public CompteDao getDao() {
        return dao;
    }
    
    @Override
    public boolean virement(Long src, Long dest, Double montant) {
	CompteEntity cesrc = dao.find(src);
	CompteEntity cedst = dao.find(dest);
	if(cesrc!=null && cedst!=null){
	    if(cesrc.getSolde()>=montant && montant>0f){
		cedst.setSolde(cedst.getSolde() + montant);
		cesrc.setSolde(cesrc.getSolde() - montant);
		dao.save(cedst);
		dao.save(cesrc);		
		TransactionEntity te = new TransactionEntity(cesrc, cedst, montant);
		cesrc.getTransactions().add(te);
		cedst.getTransactions().add(te);
		this.tdi.save(te);
		dao.update(cedst);
		dao.update(cesrc);
		return true;
	    }
	}
	
	return false;
    }

    @Override
    public List<CompteEntity> consultation(String username) {
        UtilisateurEntity ant = userDao.find(username);
        if (ant!=null){
            return ant.getComptes();
        }
        return new ArrayList<>();
    }
    
    @Override
    public boolean creeCompte(String nomCompte, String nomUtilisateur){
	UtilisateurEntity ue = this.udi.find(nomUtilisateur);
	if(ue != null){
	    CompteEntity ce = new CompteEntity(nomCompte, ue);
	    dao.save(ce);
	    ue.addSingleAccount(ce);
	    CompteEntity.cptCompte++;
	    this.udi.save(ue);
	    return true;
	}
	return false;
    }

    @Override
    public boolean supprCompte(Long id) {
	CompteEntity ce = this.dao.find(id);
	CompteEntity ceToDel = null;
	if(ce != null){
	    if(ce.getSolde()==0d){
		this.dao.remove(ce);
		for(CompteEntity ceTmp : ce.getProprietaire().getComptes()){
		    if(ceTmp == ce){
			ceToDel = ceTmp;
		    }
		}
		ce.getProprietaire().getComptes().remove(ceToDel);
		CompteEntity.cptCompte--;
		return true;
	    }
	}
	return false;
    }

    @Override
    public CompteEntity getAcc(Long id) {
	return this.dao.find(id);
    }
}

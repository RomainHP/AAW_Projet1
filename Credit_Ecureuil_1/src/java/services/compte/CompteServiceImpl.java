package services.compte;

import dao.compte.CompteDao;
import dao.compte.CompteEntity;
import dao.transaction.TransactionDao;
import dao.transaction.TransactionEntity;
import dao.utilisateur.UtilisateurDao;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CompteServiceImpl implements CompteService{
   
    @Resource
    CompteDao dao;
    
    @Resource
    UtilisateurDao userDao;
    
    @Resource 
    TransactionDao transactionDao;

    public void setDao(CompteDao dao) {
        this.dao = dao;
    }

    public void setUserDao(UtilisateurDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public CompteDao getDao() {
        return dao;
    }

    public UtilisateurDao getUserDao() {
        return userDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
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
		this.transactionDao.save(te);
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
	UtilisateurEntity ue = userDao.find(nomUtilisateur);
	if(ue != null){
	    CompteEntity ce = new CompteEntity(nomCompte, ue);
	    dao.save(ce);
	    ue.addAccount(ce);
	    userDao.save(ue);
	    return true;
	}
	return false;
    }

    @Override
    public boolean supprCompte(Long id) {
	CompteEntity ce = dao.find(id);
	if(ce != null){
	    if(ce.getSolde()==0d){
		ce.getProprietaire().removeAccount(ce);
                userDao.save(ce.getProprietaire());
		dao.remove(ce);
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

package services.compte;

import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import services.utilisateur.UtilisateurService;
import services.utilisateur.UtilisateurServiceImpl;


public class CompteServiceImpl implements CompteService{
   
    @Autowired
    private CompteDaoImpl dao;

    public CompteServiceImpl(){
	this.dao = new CompteDaoImpl();
    }
    
    @Override
    public boolean virement(Long src, Long dest, Double montant) {
	List<CompteEntity> allAcc = dao.retrieveAllAccounts();
	
	for (CompteEntity acc : allAcc) {
	    if(acc.getId() == dest){
		CompteEntity srcAccount = dao.find(src);
		if(srcAccount.getSolde()>=montant){
		    System.out.println("Compte trouve debut transaction");
		    dao.getEm().getTransaction().begin();
		    srcAccount.setSolde(srcAccount.getSolde() - montant);
		    dao.getEm().getTransaction().commit();
		    System.out.println("Compte trouve fin transaction");
		    return true;
		}
	    }
	}
	
	return false;
    }

    @Override
    public List<CompteEntity> consultation(String username) {
	UtilisateurService service = new UtilisateurServiceImpl();
        UtilisateurEntity ant = service.getUtilisateur(username);
        if (ant!=null){
            return ant.getComptes();
        }
        return new ArrayList<>();
    }

    @Override
    public List<CompteEntity> retrieveAccounts() {
	return dao.retrieveAllAccounts();
    }
    
}

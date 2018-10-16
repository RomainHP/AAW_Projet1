package services.compte;

import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
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
	CompteEntity cesrc = dao.find(src);
	CompteEntity cedst = dao.find(dest);
	if(cesrc!=null && cedst!=null){
	    if(cesrc.getSolde()>=montant){
		cedst.setSolde(cedst.getSolde() + montant);
		cesrc.setSolde(cesrc.getSolde() - montant);
		dao.createNewAccount(cedst);
		dao.createNewAccount(cesrc);
		return true;
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
    
}

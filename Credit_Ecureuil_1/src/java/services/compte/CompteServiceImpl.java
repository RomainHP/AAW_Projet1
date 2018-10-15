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
    public void virement() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CompteEntity> consultation(String username) {
	UtilisateurService service = new UtilisateurServiceImpl();
        UtilisateurEntity ant = service.getUtilisateur(username);
        if (ant!=null){
            System.err.println("ok");
            return ant.getComptes();
        }
        System.err.println("pas ok");
        return new ArrayList<>();
    }

    
    
}

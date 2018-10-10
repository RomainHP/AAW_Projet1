package services.compte;

import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


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
	return dao.getAccounts(username);
    }

    
    
}

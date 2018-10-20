package dao.transaction;

public interface TransactionDao{
    public void save(TransactionEntity ue);
    
    public TransactionEntity find(String identifiant);
    
    public void update(TransactionEntity ue);
    
    public void remove(TransactionEntity ue);
}

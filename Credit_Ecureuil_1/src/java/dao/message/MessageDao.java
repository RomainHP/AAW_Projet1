package dao.message;

/**
 *
 * @author Romain
 */
public interface MessageDao {
    
    public void save(MessageEntity me);
    
    public MessageEntity find(String id);
    
    public void update(MessageEntity me);
    
    public void remove(MessageEntity me);
}

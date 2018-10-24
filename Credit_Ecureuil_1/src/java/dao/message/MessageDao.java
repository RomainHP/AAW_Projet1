package dao.message;

/**
 *
 * @author Romain
 */
public interface MessageDao {
    
    public void save(MessageEntity me);
    
    public MessageEntity find(Long id);
    
    public void update(MessageEntity me);
    
    public void remove(MessageEntity me);
}

import java.util.LinkedList;
class QueryFormatException extends Exception{
    public QueryFormatException(String msg){
        super(msg);
    }
}
class Bypass{
    public static enum BypassOrder {
        VERTEX_LEFT_RIGHT,
        LEFT_VERTEX_RIGHT,
        LEFT_RIGHT_VERTEX
    }
    private BypassOrder order;
    public BypassOrder getBypassOrder(){
        return order;
    }
    public Bypass(String inputOrder) throws IllegalArgumentException{
        order = BypassOrder.valueOf(inputOrder);
    }
}
class Query{
    private String content;
    public static enum QueryType  {
        ADD,
        DELETE,
        FIND,
        Bypass   
    }
    private QueryType type;
    public QueryType getQueryType(){
        return type;
    }
    private void initQueryType(String inputContent) throws QueryFormatException, IllegalArgumentException{
        int left = 0;
        int right = inputContent.indexOf(' ');
        if (right == -1) throw new QueryFormatException("No space between type and content!");
        type = queryContent.valueOf(inputContent.substring(left, right)); 
    }
    private void initQueryContent(String inputContent) throws QueryFormatException{
        int left = inputContent.indexOf(' ');
        if (left == -1) throw new QueryFormatException("Cant't find content in query");
        content = inputContent.substring(left, inputContent.length());
    }
    public Query(String inputContent) throws QueryFormatException, IllegalArgumentException{
        initQueryType(inputContent);
        initQueryContent(inputContent);
    }

}    
public class Lab{
    public static Query readNextQuery(){
        return null; // заглушка
    }
    public static void processQueries(){
    
    }
    public static void executeLab(){
    
    }
    public static void main(String[] args){    
    }
}

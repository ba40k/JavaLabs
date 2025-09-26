import java.text.ParseException;
import java.util.function.Consumer;
import java.util.ArrayList;
class QueryFormatException extends Exception{
    public QueryFormatException(String msg){
        super(msg);
    }
}
interface Parsable{
    void fromString(String source) throws ParseException;
}
class BinarySearchTree<T extends  Parsable & Comparable<T>>{
    // пока что класс пустой просто для примера
    class Node {
        private T key;
        private Node leftChild, rightChild;
        private Node previous;
        private boolean isLeftChild;
        public Node(T inputKey){
            key = inputKey;
            leftChild = null;
            rightChild = null;
        }
        public T getKey(){
            return key;
        }
        public Node getLeftChild(){
            return leftChild;
        }
        public Node getRightChild(){
            return rightChild;
        }
        public void setLeftChild(Node child){
            leftChild = child;
        }
        public void setRightChild(Node child){
            rightChild = child;
        }
        public boolean isItLeftChild(){
            return isLeftChild;
        }
        public void setIsLeftChild(boolean value){
             isLeftChild = value;
        }
    }
    private Node root;

    public BinarySearchTree(){
        root = null;
    }
    public void executeInsertQuery(){
    
    }
    public void executeSearchQuery(){
    
    }
    public void executeDeleteQuery(){
    
    }
    public ArrayList<Node> executeTraversalQuery(TraversalStrategy<T> strategy){
        ArrayList<Node> res = new ArrayList<>();
        strategy.traverse(root, node -> res.add(node));
        return res;
    }
    
}
interface TraversalStrategy<T extends Parsable & Comparable<T>>{
    void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action);
}
class LeftRightCurrentTraversal<T extends Parsable & Comparable<T>> implements TraversalStrategy<T>{
    public void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action){
        if (vertex == null) return;
        traverse(vertex.getLeftChild(), action);
        traverse(vertex.getRightChild(), action);
        action.accept(vertex);
    }
}
class LeftCurrentRightTraversal<T extends Parsable & Comparable<T>> implements TraversalStrategy<T>{
    public void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action){
        if (vertex == null) return;
        traverse(vertex.getLeftChild(), action);
        action.accept(vertex);
        traverse(vertex.getRightChild(), action);
    }
}

class CurrentLeftRightTraversal<T extends  Parsable & Comparable<T>> implements TraversalStrategy<T>{
    public void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action){
        if (vertex == null) return;
        action.accept(vertex); 
        traverse(vertex.getLeftChild(), action);
        traverse(vertex.getRightChild(), action);
    }
}


class QueryFactory <T extends  Parsable & Comparable<T>>{
    public Query<T> constructQuery(String inputContent) throws QueryFormatException{
        Query<T> abstractQuery = new Query<>(inputContent);
        switch (abstractQuery.getType()){
            case "insert":
                return new InsertQuery<>(inputContent);
            case "delete":
                return new DeleteQuery<>(inputContent);
            case "search":
                return new SearchQuery<>(inputContent);
            case "bypass":
                return new TraversalQuery<>(inputContent);
            default:
                throw new QueryFormatException("Wrong query type!");
        }
    }
}
class TraversalFactory<T extends Parsable & Comparable<T>>{
    public TraversalStrategy<T> constructTraversal(String inputContent) throws QueryFormatException{
        switch (inputContent){
            case "left-right-vertex":
                return new LeftRightCurrentTraversal<>();
            default:
                throw new QueryFormatException("Worng traversal type!");
        }
    }
}
interface BinarySearchTreeCommand<T extends Parsable & Comparable<T>> {
    void execute(BinarySearchTree<T> tree);
}
class Query<T extends Parsable & Comparable<T>> implements BinarySearchTreeCommand<T>{
    private String content;
    private String type;
    private void initQueryType(String inputContent) throws QueryFormatException{
        int left = 0;
        int right = inputContent.indexOf(' ');
        if (right == -1) throw new QueryFormatException("No space between type and content!");
        type = inputContent.substring(left, right); 
    }
    private void initQueryContent(String inputContent) throws QueryFormatException{
        int left = inputContent.indexOf(' ') + 1;
        if (left == 0) throw new QueryFormatException("Cant't find content in query");
        content = inputContent.substring(left);
    }
    public String getType(){
        return type;
    }
    public String getContent(){
        return content;
    }
    public Query(String inputContent) throws QueryFormatException{
        initQueryType(inputContent);
        initQueryContent(inputContent);
    }
    public void execute(BinarySearchTree<T> tree){
        
    }
}
class InsertQuery<T extends Parsable & Comparable<T>> extends Query<T>{
    public InsertQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }   
    @Override
    public void execute(BinarySearchTree<T> tree){
        tree.executeInsertQuery();
    }
}
class DeleteQuery<T extends Parsable & Comparable<T>> extends Query<T>{
    public DeleteQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }
    @Override
    public void execute(BinarySearchTree<T> tree){
        tree.executeDeleteQuery();
    }    
}
class SearchQuery<T extends  Parsable & Comparable<T>> extends Query<T>{
    public SearchQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }
    @Override
    public void execute(BinarySearchTree<T> tree){
        tree.executeSearchQuery();
    }    
}    
class TraversalQuery<T extends  Parsable & Comparable<T>> extends Query<T>{
    public TraversalQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }
    @Override
    public void execute(BinarySearchTree<T> tree){
        //smthng
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



import java.text.ParseException;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
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
    public void executeInsertQuery(T value){
        Node cur = root;
        Node prev = null;
        boolean direction = false;
        while (cur != null){
            prev = cur;
            int diff = cur.getKey().compareTo(value);
            if (diff == 0) return;
            if (diff>0){
                cur = cur.getLeftChild();
                direction = false;
            } else {
                cur = cur.getRightChild();
                direction = true;
            }
            
        }
        if (direction == false){
            prev.setLeftChild(new Node(value));
            prev.getLeftChild().setIsLeftChild(true);
        } else {
            prev.setRightChild(new Node(value));
            prev.getRightChild().setIsLeftChild(false);
        }
        
    }
    public boolean executeSearchQuery(T value){
        Node cur = root;
        while (cur != null){
            int diff = cur.getKey().compareTo(value); 
            if (diff == 0){
                return true;
            }
            if (diff < 0){
                cur = cur.getRightChild();
            } else{
                cur = cur.getLeftChild();
            }
        }
        return false;
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
            case "traversal":
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
            case "left-vertex-right":
                return new LeftCurrentRightTraversal<>();
            case "vertex-left-right":
                return new CurrentLeftRightTraversal<>();
            default:
                throw new QueryFormatException("Worng traversal type!");
        }
    }
}
interface BinarySearchTreeCommand<T extends Parsable & Comparable<T>> {
    void execute(BinarySearchTree<T> tree) throws QueryFormatException ;
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
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException {
        
    }
}
class InsertQuery<T extends Parsable & Comparable<T>> extends Query<T>{
    public InsertQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }   
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException {
        //T val;
        //val.fromString(getContent());
        //tree.executeInsertQuery(val);
    }
}
class DeleteQuery<T extends Parsable & Comparable<T>> extends Query<T>{
    public DeleteQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException {
        tree.executeDeleteQuery();
    }    
}
class SearchQuery<T extends  Parsable & Comparable<T>> extends Query<T>{
    public SearchQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException {
      //  System.out.println(tree.executeSearchQuery(val));
    }    
}    
class TraversalQuery<T extends  Parsable & Comparable<T>> extends Query<T>{
    public TraversalQuery(String inputContent) throws QueryFormatException{
        super(inputContent);
    }
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException{
        TraversalFactory<T> factory = new TraversalFactory<>();
        ArrayList<BinarySearchTree<T>.Node> res =  tree.executeTraversalQuery(factory.constructTraversal(getContent())); 
        System.out.println(res);
    }    
}

class Demonstrator<T extends Parsable & Comparable<T>>{
     private final String INPUT_FILE_NAME; 
     private QueryFactory<T> queryFactory;
     private Query<T> readQuery(Scanner in) throws QueryFormatException {
         return queryFactory.constructQuery(in.nextLine());
     }
     private void processQueries() throws QueryFormatException, FileNotFoundException {
        BinarySearchTree<T> tree = new BinarySearchTree<>();
        Scanner in = new Scanner(new File(INPUT_FILE_NAME)); // here can be File not Found
        while(in.hasNext()){
           readQuery(in).execute(tree); // here can be WrongQueryFormat
        }
    }
    public Demonstrator(String args[]) throws IOException {
        queryFactory = new QueryFactory<>();
        switch (args.length){
            case 0:
                INPUT_FILE_NAME = "input.txt";
                break;
            case 1:
                INPUT_FILE_NAME = args[0];
                break;
            default:
                throw new IOException("Wrong console parameter!");
        }
    }
    public void demonstrateLab() throws QueryFormatException, FileNotFoundException {
         processQueries();
    }
}
public class Lab{
    private static void executeLab(String args[]){
       // Demostrator demostrator = new Demonstrator();
    }
    public static void main(String[] args){    
        
    }
}







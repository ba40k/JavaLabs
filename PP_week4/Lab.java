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
interface FromStringParser<T>{
    T fromString(String source) throws ParseException;
}
class ParametrizedInstanseFactory<T extends Comparable<T>>{
    private final FromStringParser<T> parser;
    public ParametrizedInstanseFactory(FromStringParser<T> parser){
        this.parser = parser;
    }
    public T createNewInstanse(String source) throws ParseException{
        return parser.fromString(source);
    }
}
class BinarySearchTree<T extends  Comparable<T>>{
    class Node {
        private T key;
        private Node leftChild, rightChild;
        private Boolean deleted;
        public Node(T inputKey){
            key = inputKey;
            leftChild = null;
            rightChild = null;
            deleted = false;
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
        public boolean isDeleted(){
           return deleted;
        }
        public void setDeleted(){
           deleted = true;
        }
        public void  executeLazyDelete(){
           if (getLeftChild()!=null && getLeftChild().isDeleted() == true)     setLeftChild(null);
           if (getRightChild()!=null && getRightChild().isDeleted() == true)     setRightChild(null);
 
        }
    }
    private Node root;

    public BinarySearchTree(){
        root = null;
    }
    public void executeInsertQuery(T value){
        if (root == null  || root.isDeleted()){
            root = new Node(value);
        }
        Node cur = root;
        Node prev = null;
        while (cur.getLeftChild() != null || cur.getRightChild() != null){
            cur.executeLazyDelete();
            prev = cur;
            int diff = cur.getKey().compareTo(value);
            if (diff == 0) return;
            if (diff>0){
                cur = cur.getLeftChild();
            } else {
                cur = cur.getRightChild();
            }
        }
        int diff = cur.getKey().compareTo(value);
        if (diff == 0) return;
            if (diff>0){
                cur.setLeftChild(new Node(value));
            } else {
                cur.setRightChild(new Node(value));
         }       
    }
    public Node executeSearchQuery(T value){
        Node cur = root;
        while (cur != null){
            cur.executeLazyDelete();
            int diff = cur.getKey().compareTo(value); 
            if (diff == 0){
                return cur;
            }
            if (diff < 0){
                cur = cur.getRightChild();
            } else{
                cur = cur.getLeftChild();
            }
        }
        return null;
    }
    private void  swapKeys(Node a, Node b){
        T tempKey = a.key;
        a.key = b.key;
        b.key = tempKey;
    }
    private Node findReplacement(Node node){
        node.executeLazyDelete();
        if (node.getRightChild() == null) return node;
        node = node.getRightChild();
        node.executeLazyDelete();
        while (node.getLeftChild()!=null){
            node = node.getLeftChild();
            node.executeLazyDelete();
        }
        return node;
    }
    public void executeDeleteQuery(T value){
        Node toDelete = executeSearchQuery(value);
        if (toDelete == null) return;
        Node replacement = findReplacement(toDelete);
        swapKeys(toDelete, replacement);
        replacement.setDeleted();
    }
    public ArrayList<Node> executeTraversalQuery(TraversalStrategy<T> strategy){
        ArrayList<Node> res = new ArrayList<>();
        strategy.traverse(root, node -> res.add(node));
        return res;
    }
    
}
interface TraversalStrategy<T extends  Comparable<T>>{
    void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action);
}
class LeftRightCurrentTraversal<T extends  Comparable<T>> implements TraversalStrategy<T>{
    public void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action){
        if (vertex == null || vertex.isDeleted()) return;
        vertex.executeLazyDelete();
        traverse(vertex.getLeftChild(), action);
        traverse(vertex.getRightChild(), action);
        action.accept(vertex);
    }
}
class LeftCurrentRightTraversal<T extends Comparable<T>> implements TraversalStrategy<T>{
    public void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action){
        if (vertex == null  || vertex.isDeleted()) return;
        vertex.executeLazyDelete();
        traverse(vertex.getLeftChild(), action);
        action.accept(vertex);
        traverse(vertex.getRightChild(), action);
    }
}

class CurrentLeftRightTraversal<T extends   Comparable<T>> implements TraversalStrategy<T>{
    public void traverse(BinarySearchTree<T>.Node vertex, Consumer<BinarySearchTree<T>.Node> action){
        if (vertex == null || vertex.isDeleted()) return;
        vertex.executeLazyDelete();
        action.accept(vertex); 
        traverse(vertex.getLeftChild(), action);
        traverse(vertex.getRightChild(), action);
    }
}


class QueryFactory <T extends   Comparable<T>>{
    public Query<T> constructQuery(String inputContent, ParametrizedInstanseFactory<T> contentFactory) throws QueryFormatException{
        Query<T> abstractQuery = new Query<>(inputContent, contentFactory);
        switch (abstractQuery.getType()){
            case "insert":
                return new InsertQuery<>(inputContent, contentFactory);
            case "delete":
                return new DeleteQuery<>(inputContent, contentFactory);
            case "search":
                return new SearchQuery<>(inputContent, contentFactory);
            case "traversal":
                return new TraversalQuery<>(inputContent, contentFactory);
            default:
                throw new QueryFormatException("Wrong query type!");
        }
    }
}
class TraversalFactory<T extends Comparable<T>>{
    public TraversalStrategy<T> constructTraversal(String inputContent) throws QueryFormatException{
        switch (inputContent){
            case "left-right-vertex":
                return new LeftRightCurrentTraversal<>();
            case "left-vertex-right":
                return new LeftCurrentRightTraversal<>();
            case "vertex-left-right":
                return new CurrentLeftRightTraversal<>();
            default:
                throw new QueryFormatException("Wrong traversal type!");
        }
    }
}
class Query<T extends  Comparable<T>>{
    private String content;
    private String type;
    private ParametrizedInstanseFactory<T> contentFactory;
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
    public T getParsedContent() throws ParseException{
        return contentFactory.createNewInstanse(getContent());
    }
    public Query(String inputContent, ParametrizedInstanseFactory<T> contentFactory) throws QueryFormatException{
        initQueryType(inputContent);
        initQueryContent(inputContent);
        this.contentFactory = contentFactory;
    }
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException, ParseException {
        
    }
}
class InsertQuery<T extends  Comparable<T>> extends Query<T>{
    public InsertQuery(String inputContent, ParametrizedInstanseFactory<T> contentFactory ) throws QueryFormatException{
        super(inputContent, contentFactory);
    }   
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException, ParseException {
        //we need to create T var from content in query and ask Tree to perform operation
        tree.executeInsertQuery(getParsedContent());
        System.out.println(getContent() + " was inserted");
        
    }
}
class DeleteQuery<T extends  Comparable<T>> extends Query<T>{
    public DeleteQuery(String inputContent, ParametrizedInstanseFactory<T> contentFactory) throws QueryFormatException{
        super(inputContent, contentFactory);
    }
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException, ParseException {
        tree.executeDeleteQuery(getParsedContent());
        System.out.println(getContent() + " was deleted");
    }    
}
class SearchQuery<T extends  Comparable<T>> extends Query<T>{
    public SearchQuery(String inputContent, ParametrizedInstanseFactory<T> contentFactory) throws QueryFormatException{
        super(inputContent, contentFactory);
    }
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException, ParseException {
        System.out.print("Searching for: " + getContent() + "\nresult: ");
        if (tree.executeSearchQuery(getParsedContent())!=null){
            System.out.println("Element found");
        } else {
            System.out.println("Element not found");
        }
    }    
}    
class TraversalQuery<T extends Comparable<T>> extends Query<T>{
    public TraversalQuery(String inputContent, ParametrizedInstanseFactory<T> contentFactory) throws QueryFormatException{
        super(inputContent,  contentFactory);
    }
    @Override
    public void execute(BinarySearchTree<T> tree) throws QueryFormatException, ParseException{
        TraversalFactory<T> factory = new TraversalFactory<>();
        ArrayList<BinarySearchTree<T>.Node> res =  tree.executeTraversalQuery(factory.constructTraversal(getContent())); 
        System.out.println(res);
    }    
}

class Demonstrator<T extends  Comparable<T>, E extends FromStringParser<T>>{
     private final String INPUT_FILE_NAME; 
     private QueryFactory<T> queryFactory;
     private ParametrizedInstanseFactory<T> contentFactory;
     private Query<T> readQuery(Scanner in) throws QueryFormatException {
         return queryFactory.constructQuery(in.nextLine(),contentFactory);
     }
     private E parser;
     private void processQueries() throws QueryFormatException, FileNotFoundException, ParseException {
        BinarySearchTree<T> tree = new BinarySearchTree<>();
        Scanner in = new Scanner(new File(INPUT_FILE_NAME)); // here can be File not Found
        while(in.hasNext()){
           readQuery(in).execute(tree); // here can be WrongQueryFormat
        }
    }
    public Demonstrator(String args[], E parser) throws IOException {
        this.parser = parser;
        contentFactory = new ParametrizedInstanseFactory<>(parser);
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
    public void demonstrateLab() throws QueryFormatException, FileNotFoundException, ParseException {
         processQueries();
    }
}
class IntegerParser implements FromStringParser<Integer>{
    public Integer fromString(String source) throws ParseException{
        return Integer.parseInt(source);
    }
}
public class Lab{
    
    private static void executeLab(String args[]) throws ParseException, QueryFormatException, FileNotFoundException, IOException{
        Demonstrator<Integer, IntegerParser> demonstrator = new Demonstrator<>(args, new IntegerParser());
        demonstrator.demonstrateLab();
    }
    public static void main(String[] args){    
        try{
            executeLab(args);      
        } catch(ParseException e){
           System.out.println(e.getMessage());
        } catch (QueryFormatException e){
           System.out.println(e.getMessage());
        } catch (FileNotFoundException e){
           System.out.println(e.getMessage());
        } catch (IOException e){
           System.out.println(e.getMessage());
        }
    }
}







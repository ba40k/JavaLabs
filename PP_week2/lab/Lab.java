import java.lang.StringBuilder;
import java.util.ArrayList;
class LabException extends Exception{
    public LabException(String message){
        super(message);
    }
}
public class Lab{
    public static StringBuilder transformString(String s) {
        StringBuilder res = new StringBuilder();
        int digitCount =0 ;
        boolean dotFinded = false;
        for (int i = 0;i<s.length();i++){
            if (Character.isDigit(s.charAt(i)) && dotFinded){
                digitCount++;
            } else{
                digitCount =0 ;
            }
            if (s.charAt(i)=='.'){
                dotFinded = true;
                digitCount = 0;
            } else{
                if (!Character.isDigit(s.charAt(i))) dotFinded = false;
            }
            
            if (digitCount<=2 || !dotFinded){
                res.append(s.charAt(i));
            }
        }
        return res;
    }
    public static void executeLab(String[] args) throws LabException{
        if (args.length == 0) throw new LabException("Empty input");
        for (String s : args){
            System.out.println(transformString(s));
        }
    }
    public static boolean isWrongInput(String s){
       for (int i =0;i<s.length();i++){
           if (s.charAt(i)!='.' && !Character.isDigit(s.charAt(i))) return true;
       }
       return false;
        
    }
    public static void main(String[] args){
        try{
            executeLab(args);
        } catch (LabException e){
            System.out.println(e.getMessage());
        }
    }
}

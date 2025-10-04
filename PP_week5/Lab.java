import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.lang.Math;
class SeriesException extends Exception{
    public SeriesException(String msg){
        super(msg);
    }
}
abstract class Series{
    private double first;
    private double step;
    private String endOfSeriesSign;
    private String delimiter;
    abstract double getElementWithNumber(int number) throws SeriesException;
    abstract double calculateSum();
    protected  abstract double getNext(double x);
    public void setEndOfSeriesSign(String s){
        endOfSeriesSign = s;
    }
    public void setDelimiter(String s){
        delimiter = s;
    }
    public double getFirst(){
        return first;
    }
    public double getStep(){
        return step;
    }
    public Series(double first, double step){
        this.first = first;
        this.step = step;
        setEndOfSeriesSign(".");
	setDelimiter(", "); 
    }
    protected abstract boolean isEndOfString(int number);
    public String toString(){
        StringBuilder res = new StringBuilder();
        double cur = first;
        int number = 1;
        while (!isEndOfString(number)){
            res.append(cur + delimiter);
            cur = getNext(cur);
            number++;
        }
        res.append(cur + endOfSeriesSign);
        return res.toString();
    }
    public void writeToFile(File file) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(toString() + '\n' + calculateSum() + '\n');
        fileWriter.close();
    }
}
class Liner extends Series{
    int length;
    public Liner(double first, double step, int length) throws SeriesException{
        super(first, step);
        if (length <= 0) throw new SeriesException("Liner series must have at least 1 element!");
        this.length = length;
    }
    protected double getNext(double x){
        return x + getStep();
    }
    public double calculateSum(){
        return (2.0 * getFirst() + getStep() * (length - 1)) * length / 2;
    }
    public double getElementWithNumber(int n) throws SeriesException{
        if(n<=0 || n > length){
            throw new SeriesException("You requested elemnt out of series"); 
        }
        return getFirst()  + (length - 1) * getStep();
    } 
    protected boolean isEndOfString(int number){
        return number>=length;
    }
}
class Exponential extends Series{
    int length;
    public Exponential(double first, double step, int length) throws SeriesException{
        super(first, step);
        if (length <= 0) throw new SeriesException("Liner series must have at least 1 element!");
        if (step == 1) throw new SeriesException("Step can't be 1 in exponential series!");
        this.length = length;
    }
    protected double getNext(double x){
        return x * getStep();
    }
    public double calculateSum(){
        return getFirst() * (Math.pow(getStep(), length) - 1) / (getStep() - 1);
    }
    public double getElementWithNumber(int n) throws SeriesException{
        if(n<0 || n > length){
            throw new SeriesException("You requested elemnt out of series"); 
        }
        return getFirst()  * Math.pow(getStep(), n - 1);
    } 
    protected boolean isEndOfString(int number){
        return number>=length;
    }
}

public class Lab{
    public static void main(String[] args){
        try{
            Liner liner = new Liner(1, 2, 10);
            System.out.println(liner);
            System.out.println(liner.getElementWithNumber(9));
            System.out.println(liner.calculateSum());
            liner.writeToFile(new File("output.txt"));
            Exponential exp = new Exponential (1, 2, 10);
            System.out.println(exp);
            System.out.println(exp.getElementWithNumber(9));
            System.out.println(exp.calculateSum());
            exp.writeToFile(new File("output.txt"));
       
        } catch (SeriesException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

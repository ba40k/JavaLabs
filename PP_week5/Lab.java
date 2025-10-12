import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class SeriesException extends Exception{
    public SeriesException(String msg){
        super(msg);
    }
}
abstract class Series{
    private double first;
    private double step;
    private int length;
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
    public int  getLength(){
        return length;
    }
    public Series(double first, double step, int length){
        this.first = first;
        this.step = step;
        this.length = length;
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
    public String getToWriteString(){
        return ("length: " +length+ "\nfirst: "+ first + "\nstep: "+step +'\n' +  toString() + '\n' + calculateSum() + '\n'); 
    }
    public void writeToFile(File file) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(getToWriteString());
        fileWriter.close();
    }
}
class Liner extends Series{
    public Liner(double first, double step, int length) throws SeriesException{
        super(first, step, length);
        if (length <= 0) throw new SeriesException("Liner series must have at least 1 element!");
    }
    protected double getNext(double x){
        return x + getStep();
    }
    public double calculateSum(){
        return (2.0 * getFirst() + getStep() * (getLength() - 1)) * getLength() / 2;
    }
    public double getElementWithNumber(int n) throws SeriesException{
        if(n<=0 || n > getLength()){
            throw new SeriesException("You requested elemnt out of series"); 
        }
        return getFirst()  + (getLength() - 1) * getStep();
    } 
    protected boolean isEndOfString(int number){
        return number>=getLength();
    }
}
class Exponential extends Series{
    public Exponential(double first, double step, int length) throws SeriesException{
        super(first, step, length);
        if (length <= 0) throw new SeriesException("Liner series must have at least 1 element!");
        if (step == 1) throw new SeriesException("Step can't be 1 in exponential series!");
    }
    protected double getNext(double x){
        return x * getStep();
    }
    public double calculateSum(){
        return getFirst() * (Math.pow(getStep(), getLength()) - 1) / (getStep() - 1);
    }
    public double getElementWithNumber(int n) throws SeriesException{
        if(n<0 || n > getLength()){
            throw new SeriesException("You requested elemnt out of series"); 
        }
        return getFirst()  * Math.pow(getStep(), n - 1);
    } 
    protected boolean isEndOfString(int number){
        return number>=getLength();
    }
}
class LabApp extends JFrame{
    private Container container;
    private JColorChooser chooser;
    private JComboBox<String> progressionTypeBox;
    private JButton saveToFileButton;
    private String[] comboBoxOptions;
    private JTextField fileNameField;
    private JTextArea outputArea;
    private JButton showSeriesButton;
    private JButton showSumButton;
    private JButton showNthElementButton;
    private class fileNameFieldFocusListener implements FocusListener{
       public void focusGained(FocusEvent e){
           if (fileNameField.getForeground().equals(Color.GRAY)){
               fileNameField.setText("");
               fileNameField.setForeground(Color.BLACK);
           }
       }
       public void focusLost(FocusEvent e){}
    }
    private class saveButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            File file = new File(fileNameField.getText());
            Series s = null;
            Color color = chooser.getColor();
            int first = color.getRed();
            int step = color.getBlue();
            int number = color.getGreen();
            try{
                if (progressionTypeBox.getItemAt(progressionTypeBox.getSelectedIndex()) == "Liner") s = new Liner(first,step,number);
                else s = new Exponential(first,step,number);
            } catch (SeriesException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return ;
            }
            try {
                s.writeToFile(file);
            } catch (IOException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    private class showButtonActionListener implements ActionListener{
       public void actionPerformed(ActionEvent e){
            Series s = null;
            Color color = chooser.getColor();
            int first = color.getRed();
            int step = color.getBlue();
            int number = color.getGreen();
            try{
                if (progressionTypeBox.getItemAt(progressionTypeBox.getSelectedIndex()) == "Liner") s = new Liner(first,step,number);
                else s = new Exponential(first,step,number);
            } catch (SeriesException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return ;
            }
            String str = null;
            switch (e.getActionCommand()){
                case "series":
                    str = s.getToWriteString();
                    break;
                case "sum":
                    str = Double.toString(s.calculateSum());
                    break;
                case "element":
                    try{
                        str = Double.toString(s.getElementWithNumber(number));
                    } catch (SeriesException ex){
                         JOptionPane.showMessageDialog(null, ex.getMessage());
                         return ;
                    }
            }
            if (str.length() > 200){
                JOptionPane.showMessageDialog(null, "Too much symbols, try to save to file!");
                return ;
            }
            outputArea.setText(str);
           
       }
    }
    public LabApp(){
        container = getContentPane();
        container.setLayout(new FlowLayout());
        comboBoxOptions =new String[] {"Liner", "Exponential"};

        chooser = new JColorChooser();
        progressionTypeBox = new JComboBox<>(comboBoxOptions);
        saveToFileButton = new JButton("Save to file!");
        fileNameField = new JTextField("example.txt");
        fileNameField.setForeground(Color.GRAY);
        outputArea = new JTextArea("here will be shown your series");
        outputArea.setEditable(false);
        showSeriesButton = new JButton("show series");        
        showSumButton = new JButton("show sum");
        showNthElementButton = new JButton("show n-th element");

        saveToFileButton.addActionListener(new saveButtonActionListener());
        fileNameField.addFocusListener(new fileNameFieldFocusListener());
        showSeriesButton.setActionCommand("series");
 	showSeriesButton.addActionListener(new showButtonActionListener());
        showSumButton.setActionCommand("sum");
 	showSumButton.addActionListener(new showButtonActionListener());
        showNthElementButton.setActionCommand("element");
 	showNthElementButton.addActionListener(new showButtonActionListener());

 
        container.add(fileNameField);
	container.add(saveToFileButton);
        container.add(progressionTypeBox);
        container.add(chooser);
        container.add(showSeriesButton);
        container.add(showSumButton);
        container.add(showNthElementButton);
        container.add(outputArea);

        setTitle("Lab");
        setPreferredSize(new Dimension(2000, 2000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setVisible(true);
    }
}
public class Lab{
    public static void main(String[] args){
        new LabApp();
    }
}


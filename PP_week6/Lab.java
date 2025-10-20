import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.util.TreeSet;
import java.awt.event.*;
import java.io.FileNotFoundException;
class LabApp extends JFrame{
    private JButton readFileButton;
    private JButton processDataButton;
    private Container container;
    private JTextArea inputPreview;
    private JTextArea outputPreview;
    JFileChooser fileChooser;
    private boolean isCorrectFormat(String s){
        String[] text = s.split(", ");
        if (text.length != 4) return false;
        boolean containsNonDigits = text[0].matches(".*\\D.*") | text[3].matches(".*\\D.*");
        if (containsNonDigits) return false;
        if (text[3].length() > 2 || Integer.parseInt(text[3])<0 || Integer.parseInt(text[3])>10) return false;
        return true;
   }
    private class readFileButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int returnVal = fileChooser.showOpenDialog(LabApp.this);
                File file = fileChooser.getSelectedFile();

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               Scanner in;
               try{
                   in = new Scanner(file);
               } catch (FileNotFoundException ex){
                   JOptionPane.showMessageDialog(null, "What");
                   return ;
               }

                StringBuilder text = new StringBuilder();
                int number = 1;
                while (in.hasNextLine()){
                    String curLine = in.nextLine();
                    if (isCorrectFormat(curLine)) {
                        text.append(curLine);
                        text.append("\n");
                    } else{
                        JOptionPane.showMessageDialog(null, "Wrong input format at line #" + number);
                        return ;
                    }
                    number++;
                }
                inputPreview.setText(text.toString());
            } 
        }
    }
    private class processDataButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (inputPreview.getText().equals( "input preview")){
                JOptionPane.showMessageDialog(null, "No input");            
                return;
            }
            TreeSet<String> st =new TreeSet<>(); 
            String[] text = inputPreview.getText().split("\n");
            for (String s : text){
                st.add(s.split(", ")[2]);
            }
            outputPreview.setText(st.toString());
        }
    }
    public LabApp(){
        container = getContentPane();
        container.setLayout(new GridLayout(0, 1));
        fileChooser = new JFileChooser();
        readFileButton = new JButton("Read file");
        readFileButton.setFont(new Font("Arial", Font.BOLD, 40));
        readFileButton.setPreferredSize(new Dimension(600,200));
  
        inputPreview = new JTextArea("input preview");
//        inputPreview.setPreferredSize(new Dimension(600,600));
        inputPreview.setEditable(false);  
        inputPreview.setFont(new Font("Arial", Font.BOLD, 14));
    //    inputPreview.setLineWrap(true);
      //  inputPreview.setWrapStyleWord(true);

        JScrollPane inputScrollPane = new JScrollPane(inputPreview);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        inputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

	outputPreview = new JTextArea("out preview");
  //      outputPreview.setPreferredSize(new Dimension(600,600));
        outputPreview.setEditable(false);  
        outputPreview.setFont(new Font("Arial", Font.BOLD, 14));
//        outputPreview.setLineWrap(true);
  //      outputPreview.setWrapStyleWord(true);

	JScrollPane outputScrollPane = new JScrollPane(outputPreview);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        processDataButton = new JButton("Pocess data!");
        processDataButton.setFont(new Font("Arial", Font.BOLD, 40));
        processDataButton.setPreferredSize(new Dimension(600,200));

        container.add(readFileButton);
        container.add(inputScrollPane); 
        container.add(outputScrollPane);
        container.add(processDataButton);

        readFileButton.addActionListener(new readFileButtonActionListener());
        processDataButton.addActionListener(new processDataButtonActionListener());

        setTitle("Lab");
        setPreferredSize(new Dimension(2000, 2000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setVisible(true);

    }
}
public class Lab{
    public static void main(String[] args){
        LabApp app = new LabApp();
    }
}

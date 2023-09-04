/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chuck.a
 */
public class Files {
    //Delete Files
    public boolean deleteFile(String archive){
        return new File(archive).delete();
    }
    //Count Lines File
    public int countLines(String archive){
        int count = 0;
        for (File file: new File(archive).listFiles()) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }
    //Create File
    public boolean createFile(String archive){
        try {
            return new File (archive).createNewFile();
        } catch (IOException ioe) {
           return false;
        }
    }
    //Read file
    public void readFile(String archive) throws Exception{
        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(archive)),
                StandardCharsets.UTF_8.name());
        try (BufferedReader br = new BufferedReader(isr)) {
            String line;
            while((line = br.readLine()) != null){
                //process the line
                System.out.println(line);
            }
        }
    }
    //Write File
    public void writeFile(String archive, String[] lines){
       File fichero = new File(archive);
       if (fichero.exists()) {

           try {
               BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
               PrintWriter wr = new PrintWriter(bw);
               if(lines.length>0){
                   for (String line : lines) {
                       wr.write(line.concat("\n"));
                   }
               } 
               bw.close();
           } catch (IOException ex) {
               Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    //Open File text editor
    public void openFileEditor(String archive) throws IOException{
        File file = new File(archive);
        if (file.exists()) {
            //first check if Desktop is supported by Platform or not
            if(!Desktop.isDesktopSupported()){
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()){
                desktop.open(file);
            } 
        }
    }
    
    //Open File
    public void openFile(String archive) throws IOException{
        File file = new File(archive);
        if (file.exists()) {
            try (Scanner openfile = new Scanner(file)) {
                while (openfile.hasNextLine()) {
                    String filedata = openfile.nextLine();
                    System.out.println(filedata);
                }
            }
        }
    }
}

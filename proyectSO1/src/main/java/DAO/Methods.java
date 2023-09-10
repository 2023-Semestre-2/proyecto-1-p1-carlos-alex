/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.TableBSP;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Caili
 */
public class Methods {
    
    //Contador de lineas del programa obtiene 1 KB en memoria
    public int countProgram(String path){
        return new Files().countLines(path);
    }   
    
    //Valida si se puede cargar archivo de formato ASM
    public boolean loadFileASM(String path){
        return new Files().isASM(path);
    } 
    
    public int getWeight(String line){
        int weight = 0;
        if(line.contains("MOV") || line.contains("INC")
            || line.contains("DEC") || line.contains("SWAP")
            || line.contains("POP") || line.contains("PUSH")){
            weight=weight+1;
        }
        else if(line.contains("LOAD") || line.contains("STORE")
            || line.contains("INT 20H") || line.contains("INT 10H")
            || line.contains("JMP") || line.contains("CMP")
            || line.contains("JE") || line.contains("JNE")){
            weight=weight+2;
        }
        else if(line.contains("ADD") || line.contains("SUB")
            || line.contains("PARAM")){
            weight=weight+3;
        }
        else if(line.contains("INT 21H")){
            weight=weight+5;
        }
        return weight;
    }
    
    public List<TableBSP> readFileToTable(String archive) throws Exception{
        List<TableBSP> result = new ArrayList<>();
        
        File file = new File(archive);
        if (file.exists()) {
            try (Scanner openfile = new Scanner(file)) {
                int count=0;
                while (openfile.hasNextLine()) {
                    String line = openfile.nextLine();
                    TableBSP bsp = new TableBSP();
                    bsp.setInstruction(line);
                    bsp.setNumberLine(count);
                    bsp.setWeight(getWeight(line));
                    result.add(bsp);
                    count++;
                }
            }
        }
        return result;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Cell;
import DTO.Document;
import DTO.Memory;
import DTO.WeightTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Caili - alex
 */
public class Methods {
    
    /**
     * 
     * @param path
     * @return 
     */
    //Contador de de archivos 
    public int countProgram(String path){
        return new Files().countProgram(path);
    }   
    
     /**
     * 
     * @param path
     * @return 
     */
    //Contador de lineas de un archivo 
    public int countLines(String path){
        return new Files().countLines(path);
    } 
    
    
     /**
     * 
     * @param memory
     * @return 
     */
    //Obtener memoria principal
    public int principalMemory(Integer memory){
        if(memory==null){
            return 256;
        }
        return memory;
    } 
    
    /**
     * 
     * @param memory
     * @return 
     */
    //Obtener memoria principal
    public int virtualMemory(Integer memory){
        if(memory==null){
            return 64;
        }
        return memory;
    } 
    
    
    /**
     * 
     * @param memory
     * @return 
     */
    //Obtener memoria configurable
    public int configMemory(Integer memory){
        if(memory==null){
            return 512;
        }
        return memory;
    } 
    /**
     * 
     * @param path
     * @return 
     */
    //Valida si se puede cargar archivo de formato ASM
    public boolean loadFileASM(String path){
        return new Files().isASM(path);
    } 
    
    /**
     * 
     * @param line
     * @return 
     */
    //Obtener peso
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
    
    
    /**
     * 
     * @param archive
     * @return
     * @throws Exception 
     */
    public List<WeightTable> readFileToTable(String archive) throws Exception{
        List<WeightTable> result = new ArrayList<>();
        
        File file = new File(archive);
        if (file.exists()) {
            try (Scanner openfile = new Scanner(file)) {
                int count=0;
                while (openfile.hasNextLine()) {
                    String line = openfile.nextLine();
                    WeightTable bsp = new WeightTable();
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
    
    
    /**
     * 
     * @param routesFile
     * @param localFiles
     * @return
     * @throws Exception 
     */
    public List<Document> loadFile(List<String> routesFile, List<Document> localFiles) throws Exception{
        if(routesFile.isEmpty()){
            return localFiles;
        }
        int cont=0;
        for(;cont<routesFile.size();cont++){
            Document document = new Document();
            document.setIndex(cont);
            document.setLocation(routesFile.get(cont));
            document.setNumberLines(countLines(routesFile.get(cont)));
            String[] locationPart = routesFile.get(cont).split("\\\\");
            document.setName(locationPart[locationPart.length-1]);
            localFiles.add(document);
        }
        return localFiles;
    }
    
    public Memory loadMemory(Integer reservedMemSize, Integer memorySize, List<Document> document){
        int memorySizeTotal = configMemory(memorySize);
        int reservedMemSizeTotal = virtualMemory(reservedMemSize);
        
        Memory memory = new Memory();
        //validar q la memoria total es mayor a la memoria reservada
        if(memorySizeTotal>reservedMemSizeTotal){
            memory.setMemorySize(memorySizeTotal);
            memory.setReservedMemSize(reservedMemSizeTotal);
            memory.setUserMemSize(memorySizeTotal-reservedMemSizeTotal);
            
            int count = 0;
            
            //cantidad de memoria
            //asignar position de inicio y fin para los archivos
            int startMem = reservedMemSizeTotal;
            int userMemSize = memorySizeTotal-reservedMemSizeTotal;
            
            
            List<Cell> cells = new ArrayList<>();
            for(;count<memorySizeTotal;count++){
                 //llenar memoria reservada
                if(count<reservedMemSizeTotal){
                    //llenamos en dado caso que haya documentos
                    if(!document.isEmpty()){
                        Cell cell = new Cell();
                        cell.setIndex(count);
                        //llenar el nombre en la memoria reservada
                        if(count<document.size()){
                            cell.setName(document.get(count).getName());
                        }
                        int countProgram = document.get(count).getNumberLines();
                        //
                        if(userMemSize>countProgram){
                           //
                           startMem = startMem +2;//para dar un espacio de 2 de programa a programa
                           
                           cell.setStartingAddress(startMem);
                           cell.setEndindAddress(startMem+countProgram);
                           
                           startMem = startMem+countProgram;
                           
                        }
                        cell.setIsReserved(true);
                        cells.add(cell);
                    }
                    memory.setCellsReserved(cells);
                }
                
                memory.setCells(cells);
            }
        }
        return memory;
    }
}

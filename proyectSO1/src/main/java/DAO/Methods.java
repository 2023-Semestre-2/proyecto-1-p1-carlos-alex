/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.BCP;
import DTO.CPU;
import DTO.Cell;
import DTO.Document;
import DTO.Memory;
import DTO.Registers;
import DTO.WeightTable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    
    private Memory getMemory(Integer reservedMemSize, Integer memorySize){
        int memorySizeTotal = configMemory(memorySize);
        int reservedMemSizeTotal = virtualMemory(reservedMemSize);
        
        Memory memory = new Memory();
        memory.setMemorySize(memorySizeTotal);//512
        memory.setReservedMemSize(reservedMemSizeTotal);//64
        memory.setUserMemSize(memorySizeTotal-reservedMemSizeTotal);//448
        
        return memory;
    }
    
  
    
    // Memoria reservada de almacenamiento
    public Memory loadMemoryReserved(Integer reservedMemSize, Integer memorySize, List<Document> document) throws Exception{ //mejor que reciba un objeto memory

        Memory memory = getMemory(reservedMemSize, memorySize); 
        //validar si cantidad de archivos es mayor a la memoria reservada
        if(!document.isEmpty()  && document.size()<memory.getReservedMemSize()){
            List<Cell> cells = new ArrayList<>();
            //Si pasa estas condiciones agregamos a la memoria reservada
            int i=0;
            int memoryTemp = memory.getUserMemSize();
            int startUsed = memory.getReservedMemSize();
            for(; i<document.size(); i++){
                Cell cell = new Cell();
                int countProgram = document.get(i).getNumberLines();
                //validacion para saber si hay memoria de usuario disponible
                if(countProgram<memoryTemp){
                    cell.setIndex(i);
                    cell.setName(document.get(i).getName());
                    cell.setLocation(document.get(i).getLocation());
                    cell.setIsReserved(true);
                    cell.setStartingAddress(startUsed);
                    cell.setEndindAddress(startUsed+countProgram);
                    memoryTemp = memoryTemp - (startUsed+countProgram);
                    startUsed = (startUsed+countProgram)+1;
                }
                if(cell.getIndex()!=null){
                    cells.add(cell);
                }
                cell.setInstructions(readFileToTable(document.get(i).getLocation())); //genera un objeto weightTable  con informacion de las instrucciones del programa
            }
            //Este for se ejecuta x veces, siendo x la cantidad de programas, cada programa setea todas las posiciones y se obtienen sus instrucciones
            memory.setCellsReserved(cells);
            //memory.setCellsAll(cells);
        }
        //System.out.println(memory.getCellsAll());
        //System.out.println(memory.getCellsReserved());
        return memory;
    }
    
    public Memory loadMemoryPrincipal(Integer reservedMemSize, Integer memorySize, Memory ssdMemory) throws Exception{
        Memory newMemory = new Memory();
        List<Cell> cells = new ArrayList<>();
        
        int reservedMemory = reservedMemSize;
        int totalMemory = memorySize;
        int spaceMemory = totalMemory-reservedMemory;
        newMemory.setMemorySize(memorySize);
        newMemory.setReservedMemSize(reservedMemSize);
        newMemory.setUserMemSize(spaceMemory);
        int i=0;
        
        if(spaceMemory>0){
            for(; i<ssdMemory.getCellsReserved().size(); i++){
                Cell cell = new Cell();
                int countProgram =  countNumber(ssdMemory.getCellsReserved().get(i).getStartingAddress(), ssdMemory.getCellsReserved().get(i).getEndindAddress());
                //validacion para saber si hay memoria de usuario disponible
                if(countProgram<spaceMemory){
                    cell.setIndex(i);
                    cell.setName(ssdMemory.getCellsReserved().get(i).getName());
                    cell.setIsReserved(false);
                    cell.setStartingAddress(reservedMemory);
                    cell.setEndindAddress(reservedMemory+countProgram);
                    spaceMemory = spaceMemory - (reservedMemory+countProgram);
                    reservedMemory = (reservedMemory+countProgram)+1;
                }
                if(cell.getIndex()!=null){
                    cells.add(cell);
                }
                cell.setInstructions(readFileToTable(ssdMemory.getCellsReserved().get(i).getLocation()));
            }
            newMemory.setCellsNoReserved(cells);
        }
        return newMemory;
    }
    
    private int countNumber(int start, int finish){
        int count = 0;
        for(; start<finish; start++){
            count= count+1;
        }
        return count; 
    }
    
    public String[][] getSSDTable(Memory memory) {
        String[] cols = {"INDEX", "VALUES"};
        String[][] data = new String[memory.getMemorySize()][cols.length];
        List<Cell> cells = memory.getCellsReserved();
        int cont = cells.size();
        //System.out.println(cont);
        int reservedIndex = 0;
        int userIndex = 0;
        for(int i = 0; i < memory.getMemorySize(); i++){
            if (reservedIndex<cont) {
                if (cells.get(reservedIndex).getIndex() == i) {
                    Cell reservedCell = cells.get(reservedIndex);
                    data[i][1] = reservedCell.getName()+","+reservedCell.getStartingAddress()+","+reservedCell.getEndindAddress();
                    reservedIndex++;
                }
            }
            
            if (userIndex<cont) {
                Cell userCell = cells.get(userIndex);
                if (i>=userCell.getStartingAddress() & i<userCell.getEndindAddress()) {
                    List<WeightTable> instructions = userCell.getInstructions();
                    data[i][1] = instructions.get(i-userCell.getStartingAddress()).getInstruction();
                    
                }
                if (i>userCell.getEndindAddress()) {
                    userIndex++;
                }
            }
            
            data[i][0] = Integer.toString(i);
 
        }
        return data;
    }
    
    public String[][] getRAMTable(Memory memory) {
        String[] cols = {"INDEX", "VALUES"};
        String[][] data = new String[memory.getMemorySize()][cols.length];
        List<Cell> cells = memory.getCellsReserved();
        List<Cell> cellsNT = memory.getCellsNoReserved();
        int contNT = cellsNT.size();
        int rI = 0;
        int reservedSize = memory.getReservedMemSize();
        int sizeReserved = memory.getCellsReserved().size();
        int contProgram = 0;
        int bcpIndex = 0;
        for (int i=0;i<memory.getMemorySize();i++) {
            
            if (i<reservedSize) {
                if (bcpIndex<sizeReserved) {
                    BCP actual = cells.get(bcpIndex).getBcp();
                    int bcpSize = actual.getSize();
                    if (rI == 0) {
                        data[i][1] = "BCP => "+actual.getProgramName();
                    }
                    if (rI == 1) {
                        data[i][1] = "STATE => "+actual.getState();
                    }
                    if(rI == 2) {
                        data[i][1] = "PRIORIDAD => "+actual.getPriority();
                    }
                    if(rI == 3) {
                        data[i][1] = "ALCANCE => "+Integer.toString(actual.getProgramSize());
                    }
                    if(rI == 4) {
                        data[i][1] = "INICIO => "+actual.getRamAddress();
                    }
                    if(rI == 5) {
                        data[i][1] = "SIGUIENTE BCP => "+actual.getNextBCPAdress();
                    }
                    if (rI>5) {
                        data[i][1] = "BCP => "+actual.getProgramName()+" PRIVATE SPACE";
                    }
                    if (rI == bcpSize-1) {
                        bcpIndex++;
                        rI = 0;
                        System.out.println("si");
                    }
                    rI++;
                }
            }
            else {
                
                if (contProgram<contNT) {
                    int starting = cellsNT.get(contProgram).getStartingAddress();
                    int ending = cellsNT.get(contProgram).getEndindAddress();
                    if (i >=starting & i<ending) {
                        data[i][1] = cellsNT.get(contProgram).getInstructions().get(i-starting).getInstruction();
                    }
                    else {
                        contProgram++;
                    }
                }
                
            }
            data[i][0] = Integer.toString(i);
            
        }
        return data;
    }
    
    public String[][] getCPUTable(CPU cpu) {
        int cols = 100;
        String[][] data = new String[cpu.getLoadedBCPS().size()][cols];
        for (int i=0;i<cpu.getLoadedBCPS().size();i++) {
            data[i][0] = cpu.getLoadedBCPS().get(i).getBcp().getProgramName();
        }
        System.out.println(data[0][0]);
        return data;
    }
    
    public boolean isInt(String str) {
        try {
            int numero = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
    //metodo que resiva la memoria SSD, obtenga los 5 programas cargados, los cargue en la memoria de usuario, llame a otro metodo para generar el BCP de cada uno
    public List<Map> getProcessTable(Memory SSD) { 
        List<Cell> cells = SSD.getCellsReserved();
        Map<String, String> program = new HashMap<>();
        List<Map> processTable = new ArrayList<>();
        int size = cells.size();
        
        for (int i=0;i<size;i++) {
            program.put("Name", cells.get(i).getName());
            program.put("State", "Nuevo");
            processTable.add(program);
        }
        
        return processTable;
    }
    
    public BCP createBCP(Cell cell, Integer CPU, Integer NextBCP) {
        BCP bcp = new BCP();
        bcp.setState("Listo");
        Registers registers = new Registers();
        Stack stack = new Stack();
        int programSize = cell.getEndindAddress()-cell.getStartingAddress();
        
        bcp.setProgramSize(programSize);
        bcp.setStack(stack);
        bcp.setProgramRegisters(registers);
        bcp.setPC(Integer.toString(CPU));
        bcp.setNextBCPAdress(Integer.toString(NextBCP));
        bcp.setRamAddress(Integer.toString(cell.getStartingAddress()));
        bcp.setEndingAdress(Integer.toString(cell.getEndindAddress()));
        bcp.setPriority("Normal");
        bcp.setSize(18);
        bcp.setProgramName(cell.getName());
        return bcp;  
    }
    
    public void execute(CPU cpu) {
        
    }
    


    
    
    
}

package DAO;


import DTO.ErrorFail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Caili
 */
public class AnalyticSintax {
    
    public List<ErrorFail> getErrors(String location) throws FileNotFoundException{
        List<ErrorFail> errors = new ArrayList<>();
        int countLines = 0;
        File file = new File(location);
        if (file.exists()) {
            try (Scanner openfile = new Scanner(file)) {
                while (openfile.hasNextLine()) {
                    String line = openfile.nextLine();
                    if(line.contains("MOV") || line.contains("mov")){
                        movError(line,countLines,errors);
                    }
                    else if(line.contains("LOAD") || line.contains("load")){
                        loadError(line,countLines, errors);
                    }
                    else if(line.contains("STORE") || line.contains("store")){
                        storeError(line,countLines, errors);
                    }
                    else if(line.contains("ADD") || line.contains("add")){
                        addError(line,countLines, errors);
                    }
                    else if(line.contains("SUB") || line.contains("sub")){
                        subError(line,countLines, errors);
                    }
                    else if(line.contains("INC") || line.contains("inc")){
                        incError(line,countLines, errors);
                    }
                    else if(line.contains("DEC") || line.contains("dec")){
                        incError(line,countLines, errors);
                    }
                    else if(line.contains("POP") || line.contains("pop")){
                        popError(line,countLines, errors);
                    }
                    else if(line.contains("PUSH") || line.contains("push")){
                        pushError(line,countLines, errors);
                    }
                    else if(line.contains("SWAP") || line.contains("swap")){
                        swapError(line,countLines, errors);
                    }
                    else if(line.contains("JE") || line.contains("je")
                            || line.contains("JNE")|| line.contains("jne")
                            || line.contains("JMP")|| line.contains("jmp")){
                        displacementError(line,countLines, errors);
                    }
                    else if(line.contains("CMP") || line.contains("cmp")){
                        cmpError(line,countLines, errors);
                    }
                    else if(line.contains("PARAM") || line.contains("param")){
                        paramError(line,countLines, errors);
                    }
                    else if(line.contains("INT") || line.contains("int")){
                        intError(line,countLines, errors);
                    }else{
                        ErrorFail e= getGetError(line, "Error de interrupcion. Interrupcion invalida", countLines);
                        errors.add(e);
                    }
                    countLines ++;
                }
            }
        }
        return errors;
    }
    
    
    public void intError(String line, int countLines, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        if(!instr[1].equalsIgnoreCase("20H")
        && !instr[1].equalsIgnoreCase("10H")
        && !instr[1].equalsIgnoreCase("09H")
        && !instr[1].equalsIgnoreCase("21H")){
            ErrorFail e= getGetError(line, "Error de interrupcion. Interrupcion no invalido", countLines);
            errors.add(e);
        }
    }
    
    public void paramError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(",")){
            String[] instr = line.split(" ");
            if(!isNumeric(instr[1])){
                ErrorFail e= getGetError(line, "Error de parametrizacion de pila. Registro invalido", countLines);
                errors.add(e);
            }
        }else{
            String[] instr = line.split(" ");          
            String [] reg = instr[1].split(",");
            int countNotNumber = 0;
            for (String reg1 : reg) {
                if (!isNumeric(reg1)) {
                        countNotNumber++;
                }
            }
            if(countNotNumber>0){
                ErrorFail e= getGetError(line, "Error de parametrizacion de pila. Registro invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    public void cmpError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(",")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            String [] reg = instr[1].split(",");
            if(!isNumeric(reg[1])){
                if(!reg[0].equalsIgnoreCase("AX")||
                !reg[0].equalsIgnoreCase("BX")||
                !reg[0].equalsIgnoreCase("CX")||
                !reg[0].equalsIgnoreCase("DX")||
                !reg[1].equalsIgnoreCase("AX")||
                !reg[1].equalsIgnoreCase("BX")||
                !reg[1].equalsIgnoreCase("CX")||
                !reg[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
                }
            }
        } 
    }
    
    public void displacementError(String line, int countLines, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            ErrorFail e= getGetError(line, "Error de desplazamiento. Registro invalido", countLines);
            errors.add(e);
        }
    }
    
    public void swapError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(",")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            String [] reg = instr[1].split(",");
            if(!isNumeric(reg[1])){
                if(!reg[0].equalsIgnoreCase("AX")||
                !reg[0].equalsIgnoreCase("BX")||
                !reg[0].equalsIgnoreCase("CX")||
                !reg[0].equalsIgnoreCase("DX")||
                !reg[1].equalsIgnoreCase("AX")||
                !reg[1].equalsIgnoreCase("BX")||
                !reg[1].equalsIgnoreCase("CX")||
                !reg[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
                }
            }
        }
    }
    
    
    public void pushError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(" ")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            if(!instr[1].equalsIgnoreCase("AX")||
                !instr[1].equalsIgnoreCase("BX")||
                !instr[1].equalsIgnoreCase("CX")||
                !instr[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    public void popError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(" ")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            if(!instr[1].equalsIgnoreCase("AX")||
                !instr[1].equalsIgnoreCase("BX")||
                !instr[1].equalsIgnoreCase("CX")||
                !instr[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    public void decError(String line, int countLines, List<ErrorFail> errors){
        if(line.contains(" ")){
           String[] instr = line.split(" ");
           if(!instr[1].equalsIgnoreCase("AX")||
                !instr[1].equalsIgnoreCase("BX")||
                !instr[1].equalsIgnoreCase("CX")||
                !instr[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    public void incError(String line, int countLines, List<ErrorFail> errors){
        if(line.contains(" ")){
           String[] instr = line.split(" ");
           if(!instr[1].equalsIgnoreCase("AX")||
                !instr[1].equalsIgnoreCase("BX")||
                !instr[1].equalsIgnoreCase("CX")||
                !instr[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
            }
        }
    }
            
    public void subError(String line, int countLines, List<ErrorFail> errors){
        if(line.contains(",")){
            ErrorFail e= getGetError(line, "Instruction invalid", countLines);
            errors.add(e);
        }else{
            if(!line.contains(" ")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            if(!instr[1].equalsIgnoreCase("AX")||
                    !instr[1].equalsIgnoreCase("BX")||
                    !instr[1].equalsIgnoreCase("CX")||
                    !instr[1].equalsIgnoreCase("DX")){
                    ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                    errors.add(e);
                }
            }
        }  
    }
    
    
    public void addError(String line, int countLines, List<ErrorFail> errors){
        if(line.contains(",")){
            ErrorFail e= getGetError(line, "Instruction invalid", countLines);
            errors.add(e);
        }else{
            if(!line.contains(" ")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            if(!instr[1].equalsIgnoreCase("AX")||
                    !instr[1].equalsIgnoreCase("BX")||
                    !instr[1].equalsIgnoreCase("CX")||
                    !instr[1].equalsIgnoreCase("DX")){
                    ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                    errors.add(e);
                }
            }
        }
    }
    
   
    public void storeError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(" ")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            if(!instr[1].equalsIgnoreCase("AX")||
                !instr[1].equalsIgnoreCase("BX")||
                !instr[1].equalsIgnoreCase("CX")||
                !instr[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    
    public void loadError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(" ")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            if(!instr[1].equalsIgnoreCase("AX")||
                !instr[1].equalsIgnoreCase("BX")||
                !instr[1].equalsIgnoreCase("CX")||
                !instr[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    public void movError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(",")){
            ErrorFail e= getGetError(line, "Error no contain registers", countLines);
            errors.add(e);
        }else{
            String[] instr = line.split(" ");
            String [] reg = instr[1].split(",");
            if(!isNumeric(reg[1])){
                if(!reg[0].equalsIgnoreCase("AX")||
                !reg[0].equalsIgnoreCase("BX")||
                !reg[0].equalsIgnoreCase("CX")||
                !reg[0].equalsIgnoreCase("DX")||
                !reg[1].equalsIgnoreCase("AX")||
                !reg[1].equalsIgnoreCase("BX")||
                !reg[1].equalsIgnoreCase("CX")||
                !reg[1].equalsIgnoreCase("DX")){
                ErrorFail e= getGetError(line, "Error registro invalido. Valor invalido", countLines);
                errors.add(e);
                }
            }
        }
    }
    
    
    private ErrorFail getGetError(String line, String message, int countLines){
        ErrorFail error = new ErrorFail();
        error.setIsError(true);
        error.setMessage(message);
        error.setNumLine(countLines);
        error.setInstruction(line);
        return error;
    }
    
    /**
     * Metodo para validar si es numerico
     * Utilizado en el funcion de reconocer codigo
     * @param register
     * @return 
     */
    private boolean isNumeric(String register){
	try {
            Integer.valueOf(register);
            return true;
	} catch (NumberFormatException nfe){
            return false;
	}
    }
}

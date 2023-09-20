package DAO;


import DTO.ErrorFail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public AnalyticSintax() {
    }
    
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
                        storeLoadError(line,countLines, errors);
                    }
                    else if(line.contains("STORE") || line.contains("store")){
                        storeLoadError(line,countLines, errors);
                    }
                    else if(line.contains("ADD") || line.contains("add")){
                        addSubError(line,countLines, errors);
                    }
                    else if(line.contains("SUB") || line.contains("sub")){
                        addSubError(line,countLines, errors);
                    }
                    else if(line.contains("INC") || line.contains("inc")){
                        incDecError(line,countLines, errors);
                    }
                    else if(line.contains("DEC") || line.contains("dec")){
                        incDecError(line,countLines, errors);
                    }
                    else if(line.contains("POP") || line.contains("pop")){
                        pushPopError(line,countLines, errors);
                    }
                    else if(line.contains("PUSH") || line.contains("push")){
                        pushPopError(line,countLines, errors);
                    }
                    else if(line.contains("SWAP") || line.contains("swap")){
                        swapCmpError(line,countLines, errors);
                    }
                    else if(line.contains("JE") || line.contains("je")
                            || line.contains("JNE")|| line.contains("jne")
                            || line.contains("JMP")|| line.contains("jmp")){
                        displacementError(line,countLines, errors);
                    }
                    else if(line.contains("CMP") || line.contains("cmp")){
                        swapCmpError(line,countLines, errors);
                    }
                    else if(line.contains("PARAM") || line.contains("param")){
                        paramError(line,countLines, errors);
                    }
                    else if(line.contains("INT") || line.contains("int")){
                        intError(line,countLines, errors);
                    }else{
                        ErrorFail e= getGetError(line, "[ERROR] => [INSTRUCCION INVALIDA]", countLines);
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
            sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
        }
    }
    
    public void paramError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(",")){
            String[] instr = line.split(" ");
            if(!isNumeric(instr[1])){
                sendMessageError(line, countLines, errors, "[ERROR] => [REGISTRO INVALIDO EN PARAMETRIZACION DE PILA]");
            }
        }else{
            String[] instr = line.split(" ");          
            String [] reg = instr[1].split(",");
            int countNotNumber = 0;
            int countNumber = 0;
            for (String reg1 : reg) {
                if (!isNumeric(reg1)) {
                    countNotNumber++;
                }
                countNumber++;
            }
            if(countNotNumber>0){
                sendMessageError(line, countLines, errors, "[ERROR] => [REGISTRO INVALIDO EN PARAMETRIZACION DE PILA]");
            }
            if(countNumber>3){
                sendMessageError(line, countLines, errors, "[ERROR] => [VIOLACION DE LIMITE DE PARAMETROS]");
            }
        }
    }
    
    public void displacementError(String line, int countLines, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            sendMessageError(line, countLines, errors, "[ERROR] => [DESPLAZAMIENTO]");
        }
    }
    
    public void swapCmpError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(",")){
            sendMessageError(line, countLines, errors, "[ERROR] => [FALTA DE REGISTROS VALIDOS]");
        }else{
            String[] instr = line.split(" ");
            String [] reg = instr[1].split(",");
            String[] values = {"AX", "BX", "CX", "DX", "ax", "bx", "cx", "dx"};

            if(isNumeric(reg[0])){
                sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
            }
            if(!isNumeric(reg[0])){
                boolean found = Arrays.asList(values).contains(reg[0]);
                if(found==false){
                    sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
                }
            }
            if(isNumeric(reg[1])){
                sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
            }
            if(!isNumeric(reg[1])){
                boolean found = Arrays.asList(values).contains(reg[1]);
                if(found==false){
                    sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
                }
            }
        }
    }
    
    
    public void pushPopError(String line, int countLines, List<ErrorFail> errors){
        generic(line, countLines,errors); 
    }
        
    public void incDecError(String line, int countLines, List<ErrorFail> errors){
        if(line.contains(" ")){
           String[] instr = line.split(" ");
           String[] values = {"AX", "BX", "CX", "DX", "ax", "bx", "cx", "dx"};
            if(!isNumeric(instr[1])){
                boolean found = Arrays.asList(values).contains(instr[1]);
                if(found==false){
                    sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
                }
            }else{
                sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA, ONLY STRING NO PERMIT NUMBER]");
            }
        }
    }
            
    public void addSubError(String line, int countLines, List<ErrorFail> errors){
        generic(line, countLines,errors); 
    }
    
    
    public void storeLoadError(String line, int countLines, List<ErrorFail> errors){
        generic(line, countLines,errors);
    }
    
    public void movError(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(",")){
            sendMessageError(line, countLines, errors, "[ERROR] => [NO CONTAIN REGISTERS]");
        }else{
            String[] instr = line.split(" ");
            String [] reg = instr[1].split(",");
            String[] values = {"AX", "BX", "CX", "DX", "ax", "bx", "cx", "dx"};
            
            if(isNumeric(reg[0])){
                sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
            }
            if(!isNumeric(reg[0])){
                boolean found = Arrays.asList(values).contains(reg[0]);
                if(found==false){
                    sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
                }
            }
            if(!isNumeric(reg[1])){
                boolean found = Arrays.asList(values).contains(reg[1]);
                if(found==false){
                    sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
                }
            }
        }
    }
    
    private void generic(String line, int countLines, List<ErrorFail> errors){
        if(!line.contains(" ")){ 
            sendMessageError(line, countLines, errors, "[ERROR] => [NO CONTAIN REGISTERS]");
        }
        else if(line.contains(",")){
            sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
        }
        else{
            String[] instr = line.split(" ");
            if(!isNumeric(instr[1])){
                String[] values = {"AX", "BX", "CX", "DX", "ax", "bx", "cx", "dx"};
                boolean found = Arrays.asList(values).contains(instr[1]);
                if(found==false){
                    sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
                }
            }else{
                sendMessageError(line, countLines, errors, "[ERROR] => [INTERRUPCION INVALIDA]");
            }
        } 
    }
    
    private void sendMessageError(String line, int countLines, List<ErrorFail> errors, String message){
        ErrorFail e= getGetError(line, message, countLines);
        errors.add(e);
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

package DAO;


import DTO.ErrorFail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    
    public List<ErrorFail> getErrors(Map<String, String> register, String location) throws FileNotFoundException{
        List<ErrorFail> errors = new ArrayList<>();
        int countLines = 0;
        File file = new File(location);
        if (file.exists()) {
            try (Scanner openfile = new Scanner(file)) {
                while (openfile.hasNextLine()) {
                    String line = openfile.nextLine();
                    if(line.contains("MOV") || line.contains("mov")){
                        movError(line,countLines,register,errors);
                    }
                    if(line.contains("LOAD") || line.contains("load")){
                        loadError(line,countLines,register, errors);
                    }
                    if(line.contains("STORE") || line.contains("store")){
                        storeError(line,countLines,register, errors);
                    }
                    if(line.contains("ADD") || line.contains("add")){
                        addError(line,countLines,register, errors);
                    }
                    if(line.contains("SUB") || line.contains("sub")){
                        subError(line,countLines,register, errors);
                    }
                    if(line.contains("INC") || line.contains("inc")){
                        incError(line,countLines,register, errors);
                    }
                    if(line.contains("DEC") || line.contains("dec")){
                        incError(line,countLines,register, errors);
                    }
                    if(line.contains("POP") || line.contains("pop")){
                        popError(line,countLines,register, errors);
                    }
                    if(line.contains("PUSH") || line.contains("push")){
                        pushError(line,countLines,register, errors);
                    }
                    if(line.contains("SWAP") || line.contains("swap")){
                        swapError(line,countLines,register, errors);
                    }
                    if(line.contains("JE") || line.contains("je")
                            || line.contains("JNE")|| line.contains("jne")
                            || line.contains("JMP")|| line.contains("jmp")){
                        displacementError(line,countLines,register, errors);
                    }
                    if(line.contains("CMP") || line.contains("cmp")){
                        cmpError(line,countLines,register, errors);
                    }
                    if(line.contains("PARAM") || line.contains("param")){
                        paramError(line,countLines,register, errors);
                    }
                    if(line.contains("INT") || line.contains("int")){
                        intError(line,countLines,register, errors);
                    }
                    countLines ++;
                }
            }
        }
        return errors;
    }
    
    
    public void intError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        if(!instr[1].equalsIgnoreCase("20H")
        && !instr[1].equalsIgnoreCase("10H")
        && !instr[1].equalsIgnoreCase("09H")
        && !instr[1].equalsIgnoreCase("21H")){
            ErrorFail e= getGetError(line, "Error de interrupcion. Interrupcion no invalido", countLines);
            errors.add(e);
        }
    }
    
    public void paramError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        int countComma = countComma(instr[1]);
        if(countComma==0){
            boolean isValue0 = isNumeric(instr[1]);
            if(!isValue0){
                ErrorFail e= getGetError(line, "Error de parametrizacion de pila. Registro invalido", countLines);
                errors.add(e);
            }
        }else{
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
    
    public void cmpError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        String [] reg = instr[1].split(",");
        //validar si es registro de origen o contiene valor
        String value1 = register.get(reg[0]);
        String value2 = register.get(reg[1]);
        boolean isValue1 = isNumeric(value1);
        boolean isValue2 = isNumeric(value2);
        if(isValue1 == false || isValue2==false){
            ErrorFail e= getGetError(line, "Error comparativo de contenido. Registro invalido", countLines);
            errors.add(e);
        }
        
    }
    
    public void displacementError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            ErrorFail e= getGetError(line, "Error de desplazamiento. Registro invalido", countLines);
            errors.add(e);
        }
    }
    
    public void swapError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        String [] reg = instr[1].split(",");
        //validar si es registro de origen o contiene valor
        
        boolean isValue1 = isNumeric(reg[0]);
        boolean isValue2 = isNumeric(reg[1]);
        if(!isValue1 &&  isValue2){
            String value1 = register.get(reg[0]);
            boolean isNumber1 = isNumeric(value1);
            if(!isNumber1){
                ErrorFail e= getGetError(line, "Error de movimiento de valores", countLines);
                errors.add(e);
            }
        }
        if(isValue1 && !isValue2){
            String value2 = register.get(reg[1]);
            boolean isNumber2 = isNumeric(value2);
            if(!isNumber2){
                ErrorFail e= getGetError(line, "Error de movimiento de valores", countLines);
                errors.add(e);
            }
        }
        if(!isValue1 && !isValue2){
            String value1 = register.get(reg[0]);
            String value2 = register.get(reg[1]);
            boolean isNumber1 = isNumeric(value1);
            boolean isNumber2 = isNumeric(value2);
            if(!isNumber1 ||  !isNumber2){
                ErrorFail e= getGetError(line, "Error de movimiento de valores", countLines);
                errors.add(e);
            }
        }
    }
    
    
    public void pushError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            String value = register.get(instr[1]);
            boolean isNumber = isNumeric(value);
            if(!isNumber){
                ErrorFail e= getGetError(line, "Error de operacion en pila. Registro invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    public void popError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            String value = register.get(instr[1]);
            boolean isNumber = isNumeric(value);
            if(!isNumber){
                ErrorFail e= getGetError(line, "Error de operacion en pila. Registro invalido", countLines);
                errors.add(e);
            }
        }
    }
    
    public void decError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        if(validateSpaceText(line)>0){
            String[] instr = line.split(" ");
            boolean isValue = isNumeric(instr[1]);
            if(!isValue){
                String value = register.get(instr[1]);
                boolean isNumber = isNumeric(value);
                if(!isNumber){
                    ErrorFail e= getGetError(line, "Error al decrementarle 1 a el registro al acumulador. Inserte valor numerico", countLines);
                    errors.add(e);
                }
            }
        }
    }
    
    public void incError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        if(validateSpaceText(line)>0){
            String[] instr = line.split(" ");
            boolean isValue = isNumeric(instr[1]);
            if(!isValue){
                String value = register.get(instr[1]);
                boolean isNumber = isNumeric(value);
                if(!isNumber){
                    ErrorFail e= getGetError(line, "Error al incrementarle 1 a el registro al acumulador. Inserte valor numerico", countLines);
                    errors.add(e);
                }
            }
        }
    }
    
    
    private int countComma(String instruction){
        // El contador de espacios
        int countSpaces = 0;
        // Recorremos la cadena:
        for (int i = 0; i < instruction.length(); i++) {
            // Si el carácter en [i] es un espacio (' ') aumentamos el contador 
            if (instruction.charAt(i) == ','){ 
                countSpaces++;
            }
        }
        
        return countSpaces;
    }
    
    private int validateSpaceText(String instruction){
        // El contador de espacios
        int countSpaces = 0;
        // Recorremos la cadena:
        for (int i = 0; i < instruction.length(); i++) {
            // Si el carácter en [i] es un espacio (' ') aumentamos el contador 
            if (instruction.charAt(i) == ' '){ 
                countSpaces++;
            }
        }
        
        return countSpaces;
    }
    
    public void subError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            String value = register.get(instr[1]);
            boolean isNumber = isNumeric(value);
            if(!isNumber){
                ErrorFail e= getGetError(line, "Error al restarle el registro al acumulador. Inserte valor numerico", countLines);
                errors.add(e);
            }
        }
    }
    
    public void addError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            String value = register.get(instr[1]);
            boolean isNumber = isNumeric(value);
            if(!isNumber){
                ErrorFail e= getGetError(line, "Error al sumarle el registro al acumulador. Inserte valor numerico", countLines);
                errors.add(e);
            }
        }
    }
    
    public void storeError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            String value = register.get(instr[1]);
            boolean isNumber = isNumeric(value);
            if(!isNumber){
                ErrorFail e= getGetError(line, "Error almacenamiento en el acumulador. Inserte valor numerico", countLines);
                errors.add(e);
            }
        }
    }
    
    
    public void loadError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        boolean isValue = isNumeric(instr[1]);
        if(!isValue){
            String value = register.get(instr[1]);
            boolean isNumber = isNumeric(value);
            if(!isNumber){
                ErrorFail e= getGetError(line, "Error de carga de registro en el acumulador. Inserte valor numerico", countLines);
                errors.add(e);
            }
        }
    }
    
    public void movError(String line, int countLines, Map<String, String> register, List<ErrorFail> errors){
        String[] instr = line.split(" ");
        String [] reg = instr[1].split(",");
        //validar si es registro de origen o contiene valor
        boolean isValue = isNumeric(reg[1]);
        if(!isValue){
            String value = register.get(reg[1]);
            boolean isNumber = isNumeric(value);
            if(!isNumber){
                ErrorFail e= getGetError(line, "Error de movimiento de valores", countLines);
                errors.add(e);
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

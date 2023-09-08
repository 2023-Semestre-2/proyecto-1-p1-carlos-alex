/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Caili
 */
public class Utils {
    
    /**
     * Metodo para rellenar el numero binario con ceros para mantener su representacion en 8 bits
     * @param number
     * @return 
     */
    private String fillZeros(int number){
        String binary = Integer.toBinaryString(number);
        
        if (binary.length() >= 8) {
            return binary;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 8 - binary.length()) {
            sb.append('0');
        }
        sb.append(binary);

        return sb.toString();
    }
    
    /**
     * Metodo para convertir un numero entero a binario
     * @param number
     * @return 
     */
    public String convertDecimalToBinary(int number){
        if(number<0){
            return Integer.toBinaryString(number).substring(24, 32);
        }
        return fillZeros(number); 
    }
    
    /**
     * Metodo para obtener la representacion binaria de la instruccion asm
     * @param line
     * @return 
     */
    public String getBinaryCode(String line){
        MemoryDAO p = new MemoryDAO();
        
        String[] code=line.split("\\s+");
        String operator = code[0];
        String register = code[1].contains(",")==true?code[1].substring(0, code[1].length()-1):code[1];
        String number = code[1].contains(",")==true? code[2] : "";
        int sendNumber = number.equals("") ? 0 : Integer.parseInt(number);
        return p.getValueOperationByKey(operator)+" "+p.getValueRegisterByKey(register)
                +" "+convertDecimalToBinary(sendNumber);
    }
    
    
    /**
     * Metodo para operar y almacenar resultados de las instrucciones asm
     * @param line
     * @param registers 
     */
    public void getRegistersData(String line, HashMap<String,Integer> registers) {
        String[] code=line.split("\\s+");
        String operator = code[0];
        String register = code[1];

        switch(operator) {
            case "MOV":
                register = code[1].contains(",")==true?code[1].substring(0, code[1].length()-1):code[1];
                int value = Integer.parseInt(code[2]);
                registers.put(register, value);
                break;
            case "LOAD":
                registers.put("AC", registers.get(register));
                break;
            case "STORE":
                registers.put(register, registers.get("AC"));
                break;
            case "ADD":
                registers.put("AC", registers.get("AC")+registers.get(register));
                break;
            case "SUB":
                registers.put("AC", registers.get("AC")-registers.get(register));
                break;
            default:
                break;
        }
        
    }
    
    /**
     * Metodo para obtener un numero random del 0 al 99
     * Donde los primeros 10 espacios estan ocupados y donde tambien
     * valida si hay espacios sufientes para acomodar el programa sino
     * sigue en el metodo hasta cumplir con el objetivo
     * @param location
     * @return 
     */
    public int getRandomInitial(String location){
        int countLines = new Files().countLines(location);

        int min = 0;
        int max = 99;

        int number = new Random().nextInt((max - min) + 1) + min;
        if(number<10){
            return getRandomInitial(location);
        }

        int sub = max - number;
        if(sub<countLines){
            return getRandomInitial(location);
        }
        return number;
    }
    
    /**
     * Metodo para reconocer si el codigo es valido
     * Si resultado es mayor a 0 es pq contiene errores
     * @param location
     * @return 
     */
    public int isCodeASM(String location){
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(location))) {
        String line;
        while ((line = br.readLine()) != null) {
            String criteria= " ";
            String [] strarr = line.split(criteria);
    
            switch (strarr.length) {
                case 3:  
                    int countOp = countNotOperator(strarr[0]);
                    int countReg = countNotRegisterWithComma(strarr[1]);
                    int num = countNotNumeric(strarr[2]);
                    
                    count = count + countOp +  countReg + num;
                    
                    break;

                case 2:
                    int countOp1 = countNotOperator(strarr[0]);
                    int countReg1 = countNotRegister(strarr[1]);
                    
                    count =  count +  countOp1 +  countReg1;
                    break;
                default:
                    count++;
                }
            }
        }catch (IOException e) {
           count++;
        }
        return count;
    }
    
    
    /**
     * Metodo para contar si no es operador
     * Utilizado en el funcion de reconocer codigo
     * @param location
     * @return 
     */
    private int countNotOperator(String operator){
        int count = 0;
        if(!operator.equalsIgnoreCase("LOAD") && !operator.equalsIgnoreCase("STORE")
        && !operator.equalsIgnoreCase("MOV") && !operator.equalsIgnoreCase("SUB")
        && !operator.equalsIgnoreCase("ADD")){
            count = count +1;
        }     
        return count;
    }
    
    /**
     * Metodo para contar si no es un numero
     * Utilizado en el funcion de reconocer codigo
     * @param location
     * @return 
     */
    private int countNotNumeric(String numeric){
        int count = 0;
        if(!isNumeric(numeric)){
            count = count +1;
        }    
        return count;
    }
    
    
    /**
     * Metodo para contar si no es un registro y si trae coma
     * Utilizado en el funcion de reconocer codigo
     * @param location
     * @return 
     */
    private int countNotRegisterWithComma(String register){
        int count = 0;
        if(!register.contains(",")){
            count = count +1;
        }else{
            String [] strarr = register.split(",");
            if(!strarr[0].equalsIgnoreCase("AX") && !strarr[0].equalsIgnoreCase("BX")
            && !strarr[0].equalsIgnoreCase("CX") && !strarr[0].equalsIgnoreCase("DX")){
                count = count +1;
            }
        }    
        return count;
    }
 
    
    /**
     * Metodo para contar si no es un registro y si no trae coma
     * Utilizado en el funcion de reconocer codigo
     * @param location
     * @return 
     */
    private int countNotRegister(String register){  
        int count = 0;
        if(!register.equalsIgnoreCase("AX") && !register.equalsIgnoreCase("BX")
        && !register.equalsIgnoreCase("CX") && !register.equalsIgnoreCase("DX")){
            count = count +1;
        }     
        return count;
    }
 
    /**
     * Metodo para validar si es numerico
     * Utilizado en el funcion de reconocer codigo
     * @param location
     * @return 
     */
    private boolean isNumeric(String cadena){
	try {
            Integer.valueOf(cadena);
            return true;
	} catch (NumberFormatException nfe){
            return false;
	}
    }
    
    
    /**
     * Metodo que procesa cada linea del archivo asm
     * @param location
     * @return 
     */
    public List<Map> result(String location){
        List<Map> result = new ArrayList();
        HashMap<String, Integer> registers = new HashMap<>();
        registers.put("AC",0 );
        registers.put("AX",0 );
        registers.put("BX",0 );
        registers.put("CX",0 );
        registers.put("DX",0 );
        registers.put("PC",0);
        registers.put("IR",0);
        int locMemory = getRandomInitial(location);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(location))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Map<String, String> data = new HashMap<>();
                    getRegistersData(line,registers);
                    data.put("MEMORY",String.valueOf(locMemory));
                    data.put("CODE_ASM", line);
                    data.put("CODE_BINARY", getBinaryCode(line));
                    
                    data.put("AC",Integer.toString(registers.get("AC")));
                    data.put("AX",Integer.toString(registers.get("AX")));
                    data.put("BX",Integer.toString(registers.get("BX")));
                    data.put("CX",Integer.toString(registers.get("CX")));
                    data.put("DX",Integer.toString(registers.get("DX")));
                    data.put("PC", "0");
                    data.put("IR", data.get("CODE_ASM"));
                    result.add(data);
                    //System.out.println(result);
                    locMemory++;
                }
            }
        } catch (IOException e) {
        }
        return result;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.WeightTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

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
    
    
    //Carga el valor al AC proveniente de un registro
    public Map<String, String> load(Map<String, String> register, String instruction){
        String[] instr = instruction.split(" ");
        
        if(instr[0].equalsIgnoreCase("LOAD")){
            String value = register.get(instr[1]);
            register.replace("AC", value);
        }
        
        return register;
    }
    
    
    //Almacena el valor del AC a un registro destino 
    public Map<String, String> store(Map<String, String> register, String instruction){
        String[] instr = instruction.split(" ");
        if(instr[0].equalsIgnoreCase("STORE")){
            String value = register.get("AC");
            register.replace(instr[1], value);
        }
        return register;
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
    
    //Movimiento de los valores a un destino. 
    // 1- MOV reg_destino, reg_origen
    // 2- MOV reg_destino, valor
    public Map<String, String> mov(Map<String, String> register, String instruction){
        String[] instr = instruction.split(" ");
        if(instr[0].equalsIgnoreCase("MOV")){
            String [] reg = instr[1].split(",");
            
            //validar si es registro de origen o contiene valor
            boolean isValue = isNumeric(reg[1]);
            if(isValue){
                register.replace(reg[0], reg[1]);
            }
            if(!isValue){
                String value = register.get(reg[1]);
                register.replace(reg[0], value);
            }
        }
        return register;
    }
    
    //Suma al AC el valor del registro
    public Map<String, String> add(Map<String, String> register, String instruction){
        String[] instr = instruction.split(" ");
        if(instr[0].equalsIgnoreCase("ADD")){
            int ac = Integer.parseInt(register.get("AC"))
                    +Integer.parseInt(register.get(instr[1]));
            register.replace("AC", String.valueOf(ac));
        }
        return register;
    }
    
    
    //Resta al AC el valor del registro
    public Map<String, String> sub(Map<String, String> register, String instruction){
        String[] instr = instruction.split(" ");
        if(instr[0].equalsIgnoreCase("SUB")){
            int ac = Integer.parseInt(register.get("AC"))
                    -Integer.parseInt(register.get(instr[1]));
            register.replace("AC", String.valueOf(ac));
        }
        return register;
    }
    
    //Incrementa
    //1-Incrementa en 1 el valor del AC
    //2-Incrementa en 1 el valor ubicado en el registro 
    public Map<String, String> inc(Map<String, String> register, String instruction){
        if(!instruction.contains(" ") && instruction.equalsIgnoreCase("INC")){
            int ac = Integer.parseInt(register.get("AC"))+1;
            register.replace("AC", String.valueOf(ac));
        }
        if(instruction.contains(" ")){
            String[] instr = instruction.split(" ");
            int regV = Integer.parseInt(register.get(instr[1]))+1;
            register.replace(instr[1], String.valueOf(regV));
        }
        return register;
    }
    
    //Incrementa
    //1-Decrementa en 1 el valor del AC
    //2-Decrementa en 1 el valor ubicado en el registro 
    public Map<String, String> dec(Map<String, String> register, String instruction){
        if(!instruction.contains(" ") && instruction.equalsIgnoreCase("DEC")){
            int ac = Integer.parseInt(register.get("AC"))-1;
            register.replace("AC", String.valueOf(ac));
        }
        if(instruction.contains(" ")){
            String[] instr = instruction.split(" ");
            int regV = Integer.parseInt(register.get(instr[1]))-1;
            register.replace(instr[1], String.valueOf(regV));
        }
        return register;
    }
    
    
    //Intercambian los valores entre los registros
    public Map<String, String> swap(Map<String, String> register, String instruction){
        String[] instr = instruction.split(" ");
        if(instr[0].equalsIgnoreCase("SWAP")){
            String[] regV = instr[1].split(",");
            String valueRegister1 = register.get(regV[0]);            String valueRegister2 = register.get(regV[1]);
            register.replace(regV[0], valueRegister2);
            register.replace(regV[1], valueRegister1);
        }
        return register;
    }
    
    //Finaliza el programa 
    public boolean exit(String instruction){
        boolean result = false;
        if(instruction.equalsIgnoreCase("INT 20H")){
            result=true;
        }
        return result;
    }
    
    //Finaliza el programa se imprime el valor del DX
    public void print(Map<String, String> register, String instruction){
        if(instruction.equalsIgnoreCase("INT 10H")){
            System.out.println("");
            System.out.println(register.get("DX"));
        }
    }
    
    //Entrada del teclado (solo numérico 0-255), el valor se guarda en el DX, finaliza con un ENTER
    public Map<String, String> read(Map<String, String> register, String instruction){
        Scanner reader = new Scanner(System.in);
        if(instruction.equalsIgnoreCase("INT 09H")){
            System.out.println("Escriba un valor numerico entre 0 a 255: ");
            int numero = reader.nextInt();
            if(numero>255 || numero<0){
                read(register, instruction);
            }
            Scanner input= new Scanner(System.in);
            System.out.println("Presiona la tecla Enter para finalizar");
            String readString = input.nextLine();
            while(readString!=null) {
                System.out.println(readString);
                if (readString.equals("")){
                    System.out.println("Tecla Enter presionada");
                    register.replace("DX", String.valueOf(numero));
                    System.out.println("Se guardado el registro en el DX");
                    return register;
                }     
                if (input.hasNextLine()){
                    readString = input.nextLine();
                }                    
                else{
                    readString = null;
                }                    
            }            
        }
        return register;
    }
    
    
    public Stack push(Stack stack, Map<String, String> register, String instruction){
        String[] instr = instruction.split(" ");
        if(instr[0].equalsIgnoreCase("PUSH")){
            String value = register.get(instr[1]);
            stack.push(value);
        }
        
        return stack;
    }
    
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
}

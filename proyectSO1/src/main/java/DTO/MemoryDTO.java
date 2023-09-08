/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Caili
 */
public class MemoryDTO {
    private final Map<String, String> registers = new HashMap<>();
    private final Map<String, String> operations = new HashMap<>();

    public MemoryDTO() {

    }

    public Map<String, String> getRegisters() {
        registers.put("AX","0001");
        registers.put("BX","0010");
        registers.put("CX","0011");
        registers.put("DX","0100");
        return registers;
    }

    public Map<String, String> getOperations() {
        operations.put("LOAD","0001");
        operations.put("STORE","0010");
        operations.put("MOV","0011");
        operations.put("SUB","0100");
        operations.put("ADD","0101");
        return operations;
    }
}

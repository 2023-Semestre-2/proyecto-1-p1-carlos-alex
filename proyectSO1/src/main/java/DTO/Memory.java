/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author asmal
 */
public class Memory {
    private Map<String, String> cells = new HashMap<>();
    private int reservedMemSize = 0; //se calcula dependiendo del tamano total
    private int memorySize = 0; //se configura al iniciar el programa
    private int userMemSize = 0; //memorySize-reservedSize
    public Memory() {
        cells.put("name", "");
        cells.put("startingAddress", "");
        cells.put("endindAddress", "");
        cells.put("index", "");
    }
    
    /**
     * 
     * @return 
     */
    public Map<String, String> getCells() {
        return cells;
    }
    
    /**
     * 
     * @return 
     */
    public int getReservedMemSize() {
        return reservedMemSize;
    }
    
    /**
     * 
     * @return 
     */
    public int getMemorySize() {
        return memorySize;
    }
    
    /**
     * 
     * @return 
     */
    public int getUserMemSize() {
        return userMemSize;
    }

    public void setCells(Map<String, String> cells) {
        this.cells = cells;
    }

    public void setReservedMemSize(int reservedMemSize) {
        this.reservedMemSize = reservedMemSize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public void setUserMemSize(int userMemSize) {
        this.userMemSize = userMemSize;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asmal
 */
public class Memory {
    private List<Cell> cellsAll = new ArrayList<>();
    private List<Cell> cellsReserved = new ArrayList<>();
    private Integer reservedMemSize; //se calcula dependiendo del tamano total
    private Integer memorySize; //se configura al iniciar el programa
    private Integer userMemSize; //memorySize-reservedSize
    
    
    public Memory() {
     
    }

    public List<Cell> getCellsAll() {
        return cellsAll;
    }

    public void setCellsAll(List<Cell> cellsAll) {
        this.cellsAll = cellsAll;
    }

    public List<Cell> getCellsReserved() {
        return cellsReserved;
    }

    public void setCellsReserved(List<Cell> cellsReserved) {
        this.cellsReserved = cellsReserved;
    }
    
    

    public Integer getReservedMemSize() {
        return reservedMemSize;
    }

    public void setReservedMemSize(Integer reservedMemSize) {
        this.reservedMemSize = reservedMemSize;
    }

    public Integer getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    public Integer getUserMemSize() {
        return userMemSize;
    }

    public void setUserMemSize(Integer userMemSize) {
        this.userMemSize = userMemSize;
    }  

    @Override
    public String toString() {
        return "Memory{" + "cellsAll=" + cellsAll + ", cellsReserved=" + cellsReserved + ", reservedMemSize=" + reservedMemSize + ", memorySize=" + memorySize + ", userMemSize=" + userMemSize + '}';
    }
}

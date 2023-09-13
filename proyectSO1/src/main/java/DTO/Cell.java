/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Caili
 */
public class Cell {
    
    private String name;
    private Integer startingAddress;
    private Integer endindAddress;
    private Integer index;
    private String instruction;
    private boolean isReserved;

    public Cell() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(Integer startingAddress) {
        this.startingAddress = startingAddress;
    }

    public Integer getEndindAddress() {
        return endindAddress;
    }

    public void setEndindAddress(Integer endindAddress) {
        this.endindAddress = endindAddress;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isIsReserved() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
     
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.Map;

import DTO.MemoryDTO;

/**
 *
 * @author Caili
 */
public class MemoryDAO {
    
    /**
     * 
     * @param key
     * @return 
     */
    public String getValueRegisterByKey(String key){
        String result = null;
        for (Map.Entry<String, String> entry : new MemoryDTO().getRegisters().entrySet()) {
            if(entry.getKey().equalsIgnoreCase(key)){
                result = entry.getValue();
            }
        }
        return result;
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    public String getValueOperationByKey(String key){
        String result = null;
        for (Map.Entry<String, String> entry : new MemoryDTO().getOperations().entrySet()) {
            if(entry.getKey().equalsIgnoreCase(key)){
                result = entry.getValue();
            }
        }
        return result;
    }
}

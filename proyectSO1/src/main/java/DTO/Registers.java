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
public class Registers {
    Map<String, Integer> register = new HashMap<>();

    public Registers() {
        register.put("AX", 0);
        register.put("BX", 0);
        register.put("CX", 0);
        register.put("DX", 0);
        register.put("AC", 0);
        register.put("PC", 0);
        register.put("IR", 0);
    }

    public Map<String, Integer> getRegister() {
        return register;
    }

    public void setRegister(Map<String, Integer> register) {
        this.register = register;
    }
}

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
    Map<String, String> register = new HashMap<>();

    public Registers() {
        register.put("AX", "");
        register.put("BX", "");
        register.put("CX", "");
        register.put("DX", "");
        register.put("AC", "");
        register.put("PC", "");
        register.put("IR", "");
        register.put("FLAG", "false");
    }

    public Map<String, String> getRegister() {
        return register;
    }

    public void setRegister(Map<String, String> register) {
        this.register = register;
    }
}

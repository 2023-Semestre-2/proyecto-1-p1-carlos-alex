/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Stack;

/**
 *
 * @author asmal
 */
public class BCP {
    String state;
    String PC;
    Registers programRegisters;
    Stack stack;
    String addressCPU;
    String e_s_State;
    String startupTime;
    String spentTime;
    String nextBCPAdress;
    String ramAddress;
    String endingAdress;
    String priority;
    
    public BCP() {}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public Registers getProgramRegisters() {
        return programRegisters;
    }

    public void setProgramRegisters(Registers programRegisters) {
        this.programRegisters = programRegisters;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public String getAddressCPU() {
        return addressCPU;
    }

    public void setAddressCPU(String addressCPU) {
        this.addressCPU = addressCPU;
    }

    public String getE_s_State() {
        return e_s_State;
    }

    public void setE_s_State(String e_s_State) {
        this.e_s_State = e_s_State;
    }

    public String getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(String startupTime) {
        this.startupTime = startupTime;
    }

    public String getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public String getNextBCPAdress() {
        return nextBCPAdress;
    }

    public void setNextBCPAdress(String nextBCPAdress) {
        this.nextBCPAdress = nextBCPAdress;
    }

    public String getRamAddress() {
        return ramAddress;
    }

    public void setRamAddress(String ramAddress) {
        this.ramAddress = ramAddress;
    }

    public String getEndingAdress() {
        return endingAdress;
    }

    public void setEndingAdress(String endingAdress) {
        this.endingAdress = endingAdress;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.List;

/**
 *
 * @author asmal
 */
public class CPU {
    List<Cell> loadedBCPS;
    BCP actual;
    int cantBPC = 0;
    int operaciones = 4;
    int indexInstr = 0;

    public int getIndexInstr() {
        return indexInstr;
    }

    public void setIndexInstr(int indexInstr) {
        this.indexInstr = indexInstr;
    }

    @Override
    public String toString() {
        return "CPU{" + "loadedBCPS=" + loadedBCPS + ", actual=" + actual + '}';
    }

    public CPU() {
    }

    public List<Cell> getLoadedBCPS() {
        return loadedBCPS;
    }

    public void setLoadedBCPS(List<Cell> loadedBCPS) {
        this.loadedBCPS = loadedBCPS;
        setCantBPC(loadedBCPS.size());
    }

    public int getCantBPC() {
        return cantBPC;
    }

    public void setCantBPC(int cantBPC) {
        this.cantBPC = cantBPC;
    }

    public int getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(int operaciones) {
        this.operaciones = operaciones;
    }


    public BCP getActual() {
        return actual;
    }

    public void setActual(BCP actual) {
        this.actual = actual;
    }
    
    public void setFirstBCP() {
        this.actual = loadedBCPS.get(0).getBcp();
        
    }
    
}

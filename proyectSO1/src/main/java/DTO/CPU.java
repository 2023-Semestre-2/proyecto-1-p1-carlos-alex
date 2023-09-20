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
    }

   

    public BCP getActual() {
        return actual;
    }

    public void setActual(BCP actual) {
        this.actual = actual;
    }
    
}

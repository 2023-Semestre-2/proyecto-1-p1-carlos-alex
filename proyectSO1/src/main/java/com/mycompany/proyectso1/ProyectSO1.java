/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectso1;

import DAO.Methods;
import DTO.Registers;
import java.util.Stack;

/**
 *
 * @author chuck.a
 */
public class ProyectSO1 {

    public static void main(String[] args) {
        Methods m = new Methods();
        Stack pila = new Stack();
        Registers r = new Registers();
        r.getRegister().replace("AX", "6");
        r.getRegister().replace("BX", "5");
        System.out.println(r.getRegister());
        m.cmp(r.getRegister(), "CMP AX,BX");
        System.out.println(r.getRegister());

    }
}

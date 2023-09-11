/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectso1;

import DAO.Methods;
import DTO.Document;
import DTO.Registers;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author chuck.a
 */
public class ProyectSO1 {

    public static void main(String[] args) throws Exception {
        Methods m = new Methods();
        /*Stack pila = new Stack();
        Registers r = new Registers();
        r.getRegister().replace("AX", "6");
        r.getRegister().replace("BX", "5");
        System.out.println(r.getRegister());
        m.cmp(r.getRegister(), "CMP AX,BX");
        System.out.println(r.getRegister());*/
        
        
        List<Document> document = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routes.add("C:\\Users\\chuck.a\\Desktop\\SO1\\proyecto-1-p1-carlos-alex\\proyectSO1\\src\\main\\java\\Examples\\example1.asm");
        routes.add("C:\\Users\\chuck.a\\Desktop\\SO1\\proyecto-1-p1-carlos-alex\\proyectSO1\\src\\main\\java\\Examples\\example2.asm");
        routes.add("C:\\Users\\chuck.a\\Desktop\\SO1\\proyecto-1-p1-carlos-alex\\proyectSO1\\src\\main\\java\\Examples\\example3.asm");
        routes.add("C:\\Users\\chuck.a\\Desktop\\SO1\\proyecto-1-p1-carlos-alex\\proyectSO1\\src\\main\\java\\Examples\\example4.asm");
        m.loadFile(routes, document);
        System.out.println(document);
    }
}

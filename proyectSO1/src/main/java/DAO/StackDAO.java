/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

/**
 *
 * @author Caili
 */
public class StackDAO {
    
    public Stack writeStack(String archive) throws Exception{
        Stack stack = new Stack();
        
        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(archive)),
                StandardCharsets.UTF_8.name());
        try (BufferedReader br = new BufferedReader(isr)) {
            String line;
            while((line = br.readLine()) != null){
                //process the line
                stack.push(line);
            }
        }
        
        return stack;
    }
    
}

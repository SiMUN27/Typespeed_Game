/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reflection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;

import model.Word;
import typespeed_game.UpdateScreen;

/**
 *
 * @author Simun
 */
public class Typespeed_Reflection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
 
        Word word = new Word("Word");
        
        StringBuffer bafer = new StringBuffer();
        bafer.append("<!DOCTYPE html>\n");
        bafer.append("<html>\n");
        bafer.append("<head>\n");
        bafer.append("<title>Moja prva dokumentacija</title>\n");
        bafer.append("</head>\n");
        bafer.append("<body>\n");
        bafer.append("<h1>Popis klasa</h1>\n");
        
        try {
            FileWriter pisac = new FileWriter(new File("C:\\Users\\Simun\\Desktop\\Typespeed\\Typespeed_Game\\dokumentacija.html"), false);
            
            Class klasa = word.getClass();
            bafer.append("<h2>" + klasa.getName() +  "</h2>\n");

            bafer.append("<h3>Popis konstruktora</h3>\n");
            bafer.append("<table border='1'>\n");
            bafer.append("<th>Naziv konstruktora</th>"
                    + "<th>Parametri konstruktora</th>");
            
            for(Constructor c : klasa.getConstructors()) {
                bafer.append("<tr><td>\n");
                bafer.append(c.getName());
                bafer.append("</td><td>\n");
                if(c.getParameterCount() > 0) {
                    for(Parameter parameter : c.getParameters()) {
                        bafer.append(parameter.getType().getName() + " "
                                + parameter.getName() + "\n");
                                
                    }
                }
                bafer.append("</td></tr>\n");
            }
            
            bafer.append("</table>\n");
            
            bafer.append("<h3>Popis metoda</h3>\n");
            bafer.append("<table border='1'>\n");
            bafer.append("<th>Naziv metode</th>"
                    + "<th>Ulazni parametri metode</th>"
                    + "<th>Izlazni parametar metode</th>");
            
            for(Method metoda : klasa.getMethods()) {
                bafer.append("<tr><td>\n");
                bafer.append(metoda.getName());
                bafer.append("</td><td>\n");
                if(metoda.getParameterCount() > 0) {
                    for(Parameter parameter : metoda.getParameters()) {
                        bafer.append(parameter.getType().getName() + " "
                                + parameter.getName() + "\n");
                                
                    }
                }
                bafer.append("</td><td>\n");
                bafer.append(metoda.getReturnType().getName() + "</td>");
            }
            
            bafer.append("</table>\n");
            
            for(Field polje : klasa.getDeclaredFields()) {
                String nazivVarijable = polje.getName();
                System.out.println("Naziv varijable je:" + nazivVarijable);
                System.out.println("Access modifier je:" + polje.getModifiers());
            }
            
            bafer.append("</body>\n");
            bafer.append("</html>\n");
            
            pisac.append(bafer.toString());
            
            pisac.flush();
            pisac.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Word.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

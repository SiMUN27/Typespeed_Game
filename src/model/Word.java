/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static model.Word.words;

/**
 *
 * @author Simun
 */
public class Word implements Serializable {

    public double xCor;
    public double yCor;
    public double dXCor;
    public double dYCor;
    public double targetWidth;
    public String word;
    public static Map<String, Word> words = new HashMap<String, Word>();
    public static Map<String, Word> loadedWords = new HashMap<String, Word>();

    public static boolean loadWords;

    public static List<String> wordBank = new ArrayList<String>();
    public static String fileName = "./words.txt";
    public static String FILE_LOAD_PATH = "C:\\Users\\Simun\\Desktop\\Typespeed\\Typespeed_Game\\words.ser";

    public Word(String word) {

        this.word = word;
//        this.xCor = Math.random()*100;//random position on screen
        this.xCor = 3;
        this.yCor = Math.random() * 100;//random position on screen
        this.targetWidth = 1;
        double theta = Math.random() * Math.PI;
        this.dXCor = Math.cos(theta);
        this.dYCor = Math.sin(theta);
    }

    public void update() {
        this.xCor += this.dXCor / 1.5;
        //this.yCor += this.dYCor;

        if (xCor < 0) {
            dXCor = -dXCor;
            xCor = 1;
        }

        if (xCor + targetWidth > 100) {
            dXCor = -dXCor;
            xCor = 99 - targetWidth;
        }

        if (yCor < 0) {
            dYCor = -dYCor;
            yCor = 1;
        }

        if (yCor > 100) {
            dYCor = -dYCor;
            yCor = 99;
        }
    }

    public static void setup() {

        if (loadWords) {
            try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(FILE_LOAD_PATH))) {

               Object object = inStream.readObject();

                    //Map<String,Word> loadedWords = (Map<String,Word>) object;
//                    if (object instanceof Map) {
//                   loadedWords  = (Map<String,Word>) object;
//
//                    return loadedWords;
//                    }
                  words  = (Map<String,Word>) object;

                    //return loadedWords;
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Word.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
             try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();

            String line = br.readLine();

            while (line != null) {
                wordBank.add(br.readLine());

                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            //String everything = sb.toString();
            //System.out.println(everything);
            br.close();
        } catch (IOException e) {
            wordBank.add("file");
            wordBank.add("not");
            wordBank.add("found");
        }
        }
       

    }

    public static void setupWords(int numTargets) {

        int numTargetsAdded = 0;
        numTargets = Math.min(numTargets, wordBank.size());
        while (numTargetsAdded < numTargets) {
            Word newWord = Word.getRandomWord();

            if (words.get(newWord.word) == null) {
                words.put(newWord.word, newWord);
                numTargetsAdded++;
            }
        }
    }

    public static Word getRandomWord() {
        int randomIndex = (int) (Math.floor(Math.random() * wordBank.size()));
        String randomWord = wordBank.get(randomIndex);
        return new Word(randomWord);
    }

    public static Map<String, Word> getWords() {

        return words;
        
    }
}

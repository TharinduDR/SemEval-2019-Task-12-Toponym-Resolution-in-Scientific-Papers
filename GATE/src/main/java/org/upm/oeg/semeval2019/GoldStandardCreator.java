/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.semeval2019;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Pablo
 */
public class GoldStandardCreator {
    
    
    
    String URL="http://alt.qcri.org/semeval2019/index.php?id=tasks";
    
    
     public static void main(String[] args) throws Exception {

        
        String path="C:\\Users\\Pablo\\Documents\\NetBeansProjects\\SemEval2019\\resources\\devdata\\detectionTrimmed";
        File dir=new File(path);
         
         for(File f: dir.listFiles()){
   
             if(f.getName().endsWith("ann")){
                 readGoldFile(f);
             
             }
         
         
         }

        
        
    }
     
     
     public static void readGoldFile(File f) throws Exception{
         
         String name = f.getName().replace("ann", "txt");
         
         BufferedReader Buffer = new BufferedReader(new InputStreamReader(
                new FileInputStream(f), "UTF8"));
        
        String line="";
        
        
        
        while( (line=Buffer.readLine()) != null ){
            
            
            String [] linesplitted= line.split("\t");
            if(linesplitted[1].contains("Location")){
            
                String [] position= linesplitted[1].split(" ");
                Integer init= Integer.valueOf(position[1]);
                String endString= position[2];
                
                if(position[2].contains(";")){
                    endString = endString.split(";")[1];
                }
                Integer end=Integer.valueOf(endString);
                
                String newline= name+"\t"+(init-1)+"\t"+(end-1)+"\t"+linesplitted[2]+"\t"+position[0]; 
                System.out.println(newline);
            
            }
            
            
        }
         
     
     
     
     }
    
    
}

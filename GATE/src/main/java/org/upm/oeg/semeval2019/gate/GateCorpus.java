/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.semeval2019.gate;

import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.creole.ResourceInstantiationException;
import gate.util.InvalidOffsetException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Pablo
 */
public class GateCorpus {
    
    
     public String CorpusName;
    private Corpus Corpus;
    public List <GateDocument> Documents;

    static Logger logger = Logger.getLogger(GateCorpus.class);
    
    
    public Corpus createCorpusfromFolder(String folder, String name, String Encoding) throws ResourceInstantiationException, MalformedURLException {

        Corpus = Factory.newCorpus(name);
        Documents= new ArrayList();
        
        File dir = new File(folder);
        if (!dir.isDirectory()) {
            
            throw new ResourceInstantiationException("Bad directory :"+folder +"  "+dir.getAbsolutePath());
        }

        File[] listFiles = dir.listFiles();

        for (File f : listFiles) {

            if(!f.getName().endsWith("zip")){
               GateDocument doc = new GateDocument(f,Encoding);
               Documents.add(doc);
               Corpus.add(doc.getDocumentIntance());
            }
        }
        return Corpus;

    }
    
    
    public void loadGoldStandardFromFile(File GoldFile, String annotationType, String annotationSet) {

        logger.info("Reading Gold standard: " + GoldFile.getName()+" in Corpus "+this.Corpus.getName());
        for (Document doc : this.Corpus) {

            try {

                AnnotationSet annotSetGen = doc.getAnnotations(annotationSet);

                // Read the gold standard file and get the list of NamedEntities 
                List<String> listGold = null;

                listGold = readGoldStandardCompleteFile(doc.getName(), GoldFile);

                if(listGold.isEmpty()){
                logger.info("There is no annotations for "+ doc.getName() + " in "+GoldFile.getName());
                }
                for (String GoldString : listGold) {

                    createAnnotationFromGoldStandard(GoldString, annotSetGen, doc);

                }

            } catch (IOException | InvalidOffsetException ex) {
                logger.error(ex);
            }

        }

    }
    
    
    private List<String> readGoldStandardCompleteFile(String docName, File GoldFile) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        //logger.info("Complete GoldFile found");
        List<String> List = new ArrayList();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(GoldFile), "UTF8"));

        String str;

        while ((str = in.readLine()) != null) {
            if (str.length() > 1) {

                String docColumn = str.split("\t")[0];
                if (docColumn.equals(docName)) {
                    List.add(str);
                }
            }
        }

        in.close();
        return List;
    }
    
    
    
     private void createAnnotationFromGoldStandard(String GoldStringLine, AnnotationSet annotSetGen, Document document) throws InvalidOffsetException {

        String[] GoldEntity = GoldStringLine.split("\t");   // id[0] - start[1] - end[2] - text[3] - type[4]
        if (GoldEntity.length < 5) {
            logger.error("Bad format line: Length=" + GoldEntity.length + "in gold file " + GoldEntity[0] + " ");
            return;
        }

        FeatureMap fm = Factory.newFeatureMap();
        fm.put("DocId", GoldEntity[0]);
        Long start = Long.parseLong(GoldEntity[1]);
        Long end = Long.parseLong(GoldEntity[2]);
        fm.put("string", GoldEntity[3]);
        fm.put("type", GoldEntity[4]);

        Integer id = annotSetGen.add(
                start, end,
                GoldEntity[4], // MODIFICATION
                fm);

        String TextInDocument = gate.Utils.stringFor(document, start, end).replaceAll("\n", " ");

        if (!TextInDocument.equals(GoldEntity[3])) {

            logger.error("The entity string for '" +TextInDocument+"' doesn't match in document for gold '" + GoldEntity[3]+ "' in " + document.getName()+" "+start+" "+end);
            //logger.error(GoldStringLine);
            System.out.println(GoldEntity[0]+"\t"+(start-1)+"\t"+(end-1)+"\t"+GoldEntity[3]+"\t"+GoldEntity[4]+"\t NOT MATCH");
        }

    }
     
    
}

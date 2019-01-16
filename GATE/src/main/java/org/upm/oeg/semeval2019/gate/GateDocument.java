/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.semeval2019.gate;

import gate.Document;
import gate.Factory;
import gate.creole.ResourceInstantiationException;
import java.io.File;
import java.net.MalformedURLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Pablo
 */
public class GateDocument {
    
     private String DocumentName;
    private Document DocumentIntance=null;
    
    private File OriginalFile;
    private String Content;
    
    private String Enconding;
    
    //public GateAnnotationSet EvaluationSet;
    
    final static Logger logger = Logger.getLogger(GateDocument.class);
    
    
    public GateDocument(File File, String Encoding) throws ResourceInstantiationException, MalformedURLException {
    
       OriginalFile=File;
       DocumentName=File.getName();
       Enconding=Encoding;
       DocumentIntance = Factory.newDocument(OriginalFile.toURI().toURL(),Encoding);
       DocumentIntance.setName(DocumentName);
       
    }
     public Document getDocumentIntance() {
        return DocumentIntance;
    }
    
    public String getDocumentName() {
        return DocumentName;
    }
}

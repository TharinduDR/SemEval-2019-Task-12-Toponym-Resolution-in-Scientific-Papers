/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.upm.oeg.semeval2019.gate;

import gate.Corpus;
import gate.Gate;
import gate.gui.MainFrame;
import gate.util.GateException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 *
 * @author Pablo
 */
public class GateHandler {
    
    
    
    final static Logger logger = Logger.getLogger(GateHandler.class);
    
    
   public static void initGate(String ResourcesDir,boolean ShowGui,String ... Plugins) throws InterruptedException, InvocationTargetException, GateException, MalformedURLException{
        
        logger.info("Initializing GATE in folder "+ResourcesDir);
     
        if(Gate.isInitialised())return;
        
        init(ResourcesDir);
        
        if(ShowGui){showGateGUI();}
        
        for(String Plugin: Plugins){
            logger.info("Adding Plugin "+Plugin);
            registrerHomePlugin(Plugin);
        }
    
    }
    

    public static void init(String ResourcesDir){
    
        try {
            logger.info("Initializing GATE");
            Gate.setGateHome(new File(ResourcesDir + File.separator + "GateHome"));
            Gate.setPluginsHome(new File(Gate.getGateHome()+File.separator+"Plugins"));
            Gate.setSiteConfigFile(new File(Gate.getGateHome() + File.separator + "gate.xml"));
            Gate.setUserConfigFile(new File(Gate.getGateHome()+ File.separator + "gate.xml"));
            Gate.setUserSessionFile(new File(Gate.getGateHome() + File.separator + "gate.session"));
            
            
            Gate.init();
            
            
            logger.info("GATE initialised");
        } catch (GateException ex) {
           logger.error(ex); 
        }

    }
    
    
    
    public static void registrerHomePlugin(String PluginName) throws GateException, MalformedURLException{
    
        Gate.getCreoleRegister().registerDirectories(new File( Gate.getPluginsHome().getAbsolutePath() +File.separator+PluginName).toURI().toURL());
    
    }
    
    
    public static void showGateGUI() throws InterruptedException, InvocationTargetException{
    
    
        SwingUtilities.invokeAndWait (new Runnable() {
            public void run() {
            MainFrame.getInstance().setVisible(true) ;
                    }
            }) ;
    }
    
}

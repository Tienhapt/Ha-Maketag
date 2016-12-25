/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagxml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ha
 */
public class DocGhi {
    public FileReader docFile(String fname){
        FileReader f = null;
        try {
            f=new FileReader(fname);
        } catch (Exception e) {System.out.println(e);}
        return f;
    }
    public FileWriter ghiFile(String fwname){
        FileWriter fw = null;
        try {    
            fw=new FileWriter(fwname);
        } catch (IOException ex) {System.out.println(ex);}
        return fw;
    }
}

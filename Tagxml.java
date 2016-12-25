package tagxml;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import vn.hus.nlp.sd.SentenceDetector;
import vn.hus.nlp.sd.SentenceDetectorFactory;
/**
 * @author Ha
 */
public class Tagxml {
    public static void main(String[] args) throws IOException {
        DocGhi doc=new DocGhi();
        DelString del=new DelString();
        FileReader ftext,ftext1;
        FileWriter fxml,fxml1;
        ftext=doc.docFile(args[0]);
        fxml=doc.ghiFile("D://locdoan.xml");
        String linef,linep,linef2;
        BufferedReader docf=new BufferedReader(ftext);
        BufferedWriter ghif = new BufferedWriter(fxml);
        ghif.write(args[1]+"\n");
        int idb=0,iddiv=0,idp=0,ids=0;
        while((linef=docf.readLine())!=null){
            //Đánh nhãn body
            int posstart=linef.indexOf("<pkg:package");
            if(posstart==0){
                idb++;
                ghif.write("<body id="+"\"b"+idb+"\">"+"\n");
                while(linef!=null){
                    //Đánh nhãn div
                    int possp=linef.indexOf("</w:p>");
                    linep=linef.substring(0, possp+6);
                    int possfoot=linep.indexOf("\"Footer\"");
                    int i= 0;
                    int count=0;
                    while(i<linep.length())
                    {
                        if(linep.indexOf("</w:t>",i)>=0)
                        {
                            count++;
                            i=linep.indexOf("</w:t>",i)+6;
                           
                        }else{  break;}
                    }
                    int i1= 0;
                    int count1=0;
                    while(i1<linep.length())
                    {
                        if(linep.indexOf("<w:b/>",i1)>=0)
                        {
                            count1++;
                            i1=linep.indexOf("<w:b/>",i1)+6;
                           
                        }else{  break;}
                    }
                    linef=linef.substring(possp+7);
                    int possdiv=linep.indexOf("<w:b/>");
                    int possdiv1=linep.indexOf("<w:i/>");
                    if((possdiv>0)&&(possdiv1<0)&&(count>0)&&(count<=count1)){
                        if(iddiv>0){ghif.write("</div>"+"\n");idp=0;}
                        iddiv++;
                        ghif.write("<div id="+"\"d"+iddiv+"\">"+"\n");
                    }
                    
                    //Đánh nhãn p
                    if(possp>0){
                        int posbegin=linep.indexOf("<w:t");
                        int posend=linep.indexOf("</w:t>");
                        String concat=null;
                        if((posbegin>=0)&&(posend>6)){
                            concat=concat+linep.substring(posbegin, posend);
                            linep=linep.substring(posend+6);
                            int posbegin1,posend1;
                            do{
                                posbegin1=linep.indexOf("<w:t");
                                posend1=linep.indexOf("</w:t>");
                                if((posbegin1>=0)&&(posend1>posbegin1)){
                                    String concat1=linep.substring(posbegin1, posend1);
                                    int postext1=concat1.indexOf(">");
                                    concat1=concat1.substring(postext1+1);
                                    concat=concat+concat1;
                                    linep=linep.substring(posend1+6);
                                }
                            }while((posbegin1>=0)&&(posend1>posbegin1));
                            while(concat.indexOf("<w:t")>=0){
                                concat=concat.substring(concat.indexOf("<w:t")+5);
                            }
                            int postext=concat.indexOf(">");
                            if (postext>0) concat=concat.substring(postext+1);
                            while(concat.indexOf("<w:t>")>=0){
                                concat=concat.substring(concat.indexOf("<w:t>")+5);
                            }
                            concat = concat.replace('\u00A0',' ').trim();
                            concat = concat.replace('\u2007',' ').trim();
                            concat = concat.replace('\u202F',' ').trim();
                            concat = concat.replaceAll("\\s+"," ");
                            if((concat!=null)&&(possfoot<0)&&(concat.length()!=0)){
                                idp++;
                                ghif.write("<p id="+"\"d"+iddiv+"p"+idp+"\">"+"\n");
                                ghif.write(concat);
                                ghif.write("\n");
                                ghif.write("</p>"+"\n");
                            }
                        }
                    }else {linef=null;}
                    
                }
            }
        }
        ghif.write("</div>"+"\n");
        ghif.write("</body>");
        ghif.close();
        docf.close();
        fxml.close();
        ftext.close();
        //tach cau trong mot doan
        SentenceDetector sDetector = SentenceDetectorFactory.create("vietnamese");
	String inputFile = "D://locdoan.xml";
	String outputFile = "D://tachcau.xml";
	sDetector.detectSentences(inputFile, outputFile);
        ftext1=doc.docFile(outputFile);
        fxml1=doc.ghiFile(args[1]);
        BufferedReader docf1=new BufferedReader(ftext1);
        BufferedWriter ghif1 = new BufferedWriter(fxml1);
        docf1.readLine();
        String tagp=null;
        while((linef2=docf1.readLine())!=null){
            int posb1=linef2.indexOf("<body id=");
            int posd1=linef2.indexOf("<div id=");
            int posp1=linef2.indexOf("<p id=");
            int endp=linef2.indexOf("</p>");
            int enddiv=linef2.indexOf("</div>");
            int endb=linef2.indexOf("</body>");
            if (posp1==0){
                int vt2=linef2.indexOf(">");
                tagp=linef2.substring(6, vt2-1);
            }
            if((posb1<0)&&(posd1<0)&&(posp1<0)&&(endp<0)&&(enddiv<0)&&(endb<0)){
                ids++;
                String tags="<s id="+tagp+"s"+ids+"\">";
                tags=tags+linef2+"</s>";
                ghif1.write(tags+"\n");
            }else{
                ghif1.write(linef2+"\n");}
            int endp1=linef2.indexOf("</p>");
            if(endp1==0){ids=0;}
        }
        ghif1.close();
        docf1.close();
        fxml1.close();
        ftext1.close();
	System.out.println("Done");
    }
}

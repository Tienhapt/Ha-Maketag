
package tagxml;

/**
 * @author Ha
 */
public class DelString {
    public String Delstring(String s,int i, int j){
        String s1,s2,s3=null;
        if((i==0)&&(i<j)){
            s2=s.substring(j+1);
            s3=s2;
        }
        if((i>0)&&(j>i)){
            s1=s.substring(0,i);
            s2=s.substring(j+1);
            s3=s1+s2;
        }
        return s3;
    }
}

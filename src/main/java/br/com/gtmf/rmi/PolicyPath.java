package br.com.gtmf.rmi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;


/**
 * Localiza o arquivo de seguranca "POLICY"
 * 
 * 
 * @author Gabriel Tavares
 * 
 */
public class PolicyPath {
	
	public File getPolicyFile() {
        try {
            File policy = File.createTempFile("jcac", ".policy");
            InputStream is = getClass().getResourceAsStream(ConstantsRmi.POLICY);
            
            FileWriter fw = new FileWriter(policy);            
			BufferedWriter bw = new BufferedWriter(fw);
            
            int read = 0;
            while((read = is.read()) != -1) {
                bw.write(read);
            }
            
            bw.close();
            is.close();
            policy.deleteOnExit();
            
            return policy;
            
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}

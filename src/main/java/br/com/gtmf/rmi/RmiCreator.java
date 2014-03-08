package br.com.gtmf.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Classe comum a inicilizacao do Cliente e do Servidor
 * para estabelecer a comunicacao.
 * 
 * 
 * @author Gabriel Tavares
 * 
 */
public abstract class RmiCreator {
	
	private static final Logger LOG = LoggerFactory.getLogger(RmiCreator.class);
	
	public RmiCreator(Class<?> addCodebaseServer) {
		
		LOG.debug("[Begin] " + addCodebaseServer.getSimpleName());
		
		// Especifica o local para baixar os stubs e suas classes associadas aos clientes
		System.setProperty("java.rmi.server.codebase", addCodebaseServer
				.getProtectionDomain().getCodeSource().getLocation().toString());
		
		// Carrega o arquivo Policy que contem as permissoes
		System.setProperty("java.security.policy", new PolicyPath()
			.getPolicyFile().getAbsolutePath());

		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
	}

    public abstract boolean customRmi();
	
}


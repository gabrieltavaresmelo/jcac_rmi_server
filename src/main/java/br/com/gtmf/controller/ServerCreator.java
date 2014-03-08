package br.com.gtmf.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.gtmf.rmi.ConstantsRmi;
import br.com.gtmf.rmi.IServerController;
import br.com.gtmf.rmi.RmiCreator;

/**
 * Inicializa o Servidor RMI com base
 * no nome do servidor e na porta
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public class ServerCreator extends RmiCreator {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServerCreator.class);

	private boolean connected = false;

	private ServerController controller = null;	
	private static Registry registry = null;

	public ServerCreator() {
		super(IServerController.class);		
		connected = customRmi();
	}

	@Override
	public boolean customRmi() {
		try {		
			// Registra o objeto num servico de nomes	
			controller  = new ServerController();
			
			// Mapeia o objeto em um nome
			getRegistry().rebind(ConstantsRmi.SERVICE_NAME, controller);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		
		return true;
	}
	
	public void close() {
		try {
			if(controller != null){
				controller.close();
			}
			
			getRegistry().unbind(ConstantsRmi.SERVICE_NAME);			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public static Registry getRegistry() throws RemoteException {
		if(registry == null){
			// Cria o registrador de nomes na porta definida
			registry = LocateRegistry.createRegistry(ConstantsRmi.SERVICE_PORT);
		}
		return registry;
	}
}

package br.com.gtmf.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * [INTERFACE RMI]
 * 
 * Interface remota contendo os metodos que o 
 * cliente recebe do servidor.
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public interface Notify extends Remote {

	public void onOpen(String name) throws RemoteException;
	
    public void onMessage(String message) throws RemoteException;
    
    public void onClose(String name) throws RemoteException;

	public String getUsername() throws RemoteException;
    
}
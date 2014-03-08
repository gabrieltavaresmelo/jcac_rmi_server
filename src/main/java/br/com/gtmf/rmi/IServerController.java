package br.com.gtmf.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * [INTERFACE RMI]
 * 
 * Interface remota contendo os metodos que os 
 * clientes podem invocar.
 * 
 * 
 * ----------------------------------
 * Caracteristicas:
 * 
 * * Permite a comunicação entre objetos em diferentes 
 *   hosts;
 * * Um Objeto Remoto está localizado no servidor;
 * * Clientes invocam metodos de objetos 
 *   remotos exatamente como se fossem locais;
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public interface IServerController extends Remote {    

	public void onOpen(Notify n, String name) throws RemoteException;
    
    public void onMessage(Notify n, String message) throws RemoteException;
    
    public void onClose(Notify n, String name) throws RemoteException;
    
}
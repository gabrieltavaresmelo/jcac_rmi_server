package br.com.gtmf.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.gtmf.model.Bundle;
import br.com.gtmf.model.User;
import br.com.gtmf.rmi.IServerController;
import br.com.gtmf.rmi.Notify;
import br.com.gtmf.utils.CollectionUtils;

/**
 * Classe que implementa a interface remota IServerController.
 * 
 * Todas as mensagens sao interceptadas pelo Servidor e 
 * redirecionadas para os clientes.
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public class ServerController extends UnicastRemoteObject implements IServerController {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ServerController.class);


	private NotifyersConnected serverRmi = new NotifyersConnected();	
	private Collection<User> usersOn = new ArrayList<User>();
	private String firstLogged = "";

	
	public ServerController() throws RemoteException {
		super();
	}

	/**
	 * Requisicao de login de cliente
	 */
	@Override
	public void onOpen(Notify n, String name) throws RemoteException {
		
		serverRmi.add(n);
		serverRmi.incCounter();
		
		n.onOpen(name);
		
		serverRmi.decCounter();
	}

	/**
	 * Requisicao de logout de cliente
	 */
	public synchronized void onClose(Notify n, String name)	throws RemoteException {
		
		serverRmi.remove(n);
		serverRmi.incCounter();
		
//		for (Iterator i = serverRmi.getCollection().iterator(); i.hasNext();) {
//			Notify client = (Notify) i.next();
//			client.onClose(name);
//		}
		n.onClose(name);
		
		serverRmi.decCounter();		
	}

	/**
	 * Nova mensagem de cliente
	 */
	@Override
	public void onMessage(Notify conn, String message) {
		try {
//			LOG.debug(message);
			Bundle bundle = new Bundle(message);
			
			switch (bundle.getHead()) {
			case Bundle.USER_IN:
				
				boolean isEmpty = usersOn.size() == 0 ? true : false;
								
				String username = bundle.getUser();
				
				if(CollectionUtils.search(usersOn, bundle.getUser()) != null){ // nome ja existe
					username = username + "1";
				}
				
				// Adiciona o usuario na lista
				User newUser = new User();
				newUser.setName(username);
				
				usersOn.add(newUser);

				// Envia a confirmacao do username para o usuario recem logado
				bundle = new Bundle(Bundle.USER_IN, username);
				this.sendTo(bundle.toJson(), conn);
				
				// Envia new bundle com a lista de todos os usuarios
				bundle = new Bundle(Bundle.LIST_USERS_ON, CollectionUtils.toMap(usersOn));				
				this.sendToAll(bundle.toJson());
				
				if(isEmpty){
					firstLogged  = username;
				}

				bundle = new Bundle(Bundle.USER_FIRST, firstLogged);
				this.sendToAll(bundle.toJson());
				
				break;
				
			case Bundle.USER_OUT:
				User user = CollectionUtils.search(usersOn, conn);
				
				if(user != null){
					usersOn.remove(user);
					
					// Envia new bundle com a informacao de encerrar o jogo
					bundle = new Bundle(Bundle.USER_OUT, user.getName());				
					this.sendToAllWithRestrict(bundle.toJson(), conn);
					
					// Envia new bundle com a lista de todos os usuarios
					bundle = new Bundle(Bundle.LIST_USERS_ON, CollectionUtils.toMap(usersOn));				
					this.sendToAll(bundle.toJson());
				}
				
				break;
				
			case Bundle.QUESTION_SEND:
				bundle.setHead(Bundle.QUESTION_RECEIVE);

				// Envia new bundle com a pergunta para o destinatario
				this.sendToAllWithRestrict(bundle.toJson(), conn);				
				break;
				
			case Bundle.ANSWER_SEND:
				bundle.setHead(Bundle.ANSWER_RECEIVE);

				// Envia new bundle com a resposta para o destinatario
				this.sendToAllWithRestrict(bundle.toJson(), conn);				
				break;
				
			case Bundle.CHAT_MSG_SEND:
				bundle.setHead(Bundle.CHAT_MSG_RECEIVE);

				// Envia new bundle com a mensagem do chat para o destinatario
				this.sendToAll(bundle.toJson());				
				break;
				
			case Bundle.NEW_GAME_SEND:
				
				String split [] = bundle.getUser().split(";");
				username = split[0];
				String persona = split[1];
//				System.out.println(persona);
				
				// Diz que o usuario esta pronto para comecar o jogo
				for (User tmp : usersOn) {
					if(tmp.getName().equals(username)){
						tmp.setRequestNewGame(true);
						tmp.setPersona(persona);
					}
				}
				
				// Verifica se todos os usuarios estao prontos para comecar
				boolean canStartGame = true;
				
				for (User tmp : usersOn) {
					if(!tmp.isRequestNewGame()){
						canStartGame = false;
					}
				}
				
				// Se todos estiverem prontos, sera dado o inicio
				if(canStartGame && usersOn.size() > 1){
					bundle.setHead(Bundle.NEW_GAME_RECEIVE);
	
					// Envia new bundle mandando iniciar o jogo
					this.sendToAll(bundle.toJson());
				}
				
				break;
				
			case Bundle.RIDDLE_PERSONA_SEND:
				
				persona = bundle.getPersona();				
				User userMe = CollectionUtils.search(usersOn, conn);
				
				boolean isWin = false;
				
				for (User tmp : usersOn) {
					if (!userMe.getName().equals(tmp.getName())
							&& persona.equalsIgnoreCase(tmp.getPersona())) {
						isWin = true;
					}
				}
				
				bundle.setHead(Bundle.RIDDLE_PERSONA_RECEIVE);
				bundle.setPersona(userMe.getName() + "=" +isWin);

				// Envia new bundle com a mensagem do chat para o destinatario
				this.sendToAll(bundle.toJson());
				
				break;
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Envia para todos os clientes conectados
	 * 
	 * @param message
	 * @throws RemoteException 
	 */
	private void sendToAll(String message) throws RemoteException {
		
		serverRmi.incCounter();
		
//		for (Iterator<Notify> i = serverRmi.getCollection().iterator(); i.hasNext();) {
//			Notify client = (Notify) i.next();
//			client.onMessage(message);
//		}
		
		Collection<Notify> con = serverRmi.getCollection();
		
		synchronized (con) {
			for (Notify c : con) {
				c.onMessage(message);
			}
		}
		
		serverRmi.decCounter();
	}

	/**
	 * Envia para todos os clientes conectados, exceto para conRestriciton
	 * 
	 * @param message
	 * @param restriciton
	 * @throws RemoteException 
	 */
	public void sendToAllWithRestrict(String message, Notify conRestriciton) throws RemoteException {
		
		serverRmi.incCounter();
		
//		for (Iterator<Notify> i = serverRmi.getCollection().iterator(); i.hasNext();) {
//			Notify client = (Notify) i.next();
//			
//			if(!client.equals(conRestriciton)){
//				client.onMessage(message);
//			}
//		}
		Collection<Notify> con = serverRmi.getCollection();
		
		synchronized (con) {
			for (Notify c : con) {
				if(!c.equals(conRestriciton)){
					c.onMessage(message);
				}
			}
		}
		
		serverRmi.decCounter();
	}

	/**
	 * Envia para um cliente especifico
	 * 
	 * @param message
	 * @param con
	 * @throws RemoteException 
	 */
	private void sendTo(String text, Notify con) throws RemoteException {
		
		serverRmi.incCounter();
		
		synchronized (con) {
			con.onMessage(text);
		}
		
		serverRmi.decCounter();
	}

	public void close() {
		try {
			Bundle bundle = new Bundle(Bundle.SERVER_OUT, "");
			String message = bundle.toString();
			sendToAll(message);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}		
	}
}

package br.com.gtmf.controller;

import java.util.ArrayList;
import java.util.Collection;

import br.com.gtmf.rmi.Notify;

/**
 * Classe responsavel por manter a referencia
 * aos clientes conectados de forma thread-safe.
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public class NotifyersConnected {

	private Collection<Notify> notifyers = new ArrayList<Notify>();
	private int counter = 0;

	/**
	 * Adiciona um cliente na lista [thread-safe]
	 * 
	 * @param item
	 */
	public synchronized void add(Notify item) {
		try {
			while (counter > 0) {
				wait();
			}
			notifyers.add(item);
		} catch (InterruptedException e) {
			System.out.println("Addition interrupted.");
		} finally {
			notifyAll();
		}
	}

	/**
	 * Remove um cliente da lista [thread-safe]
	 * 
	 * @param item
	 */
	public synchronized void remove(Notify item) {
		try {
			while (counter > 0) {
				wait();
			}
			notifyers.remove(item);
		} catch (InterruptedException e) {
			System.out.println("Removal interrupted.");
		} finally {
			notifyAll();
		}
	}

	public synchronized void incCounter() {
		counter++;
		notifyAll();
	}

	public synchronized void decCounter() {
		counter--;
		notifyAll();
	}

	public Collection<Notify> getCollection() {
		return notifyers;
	}

}

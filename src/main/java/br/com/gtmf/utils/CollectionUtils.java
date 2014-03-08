package br.com.gtmf.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.gtmf.model.User;
import br.com.gtmf.rmi.Notify;

/**
 * 
 * Classe que contem metodos utilitarios
 * de Colecoes e Tabelas de espalhamento
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public class CollectionUtils {


	/**
	 * Busca por um determinada string numa colecao
	 * 
	 * @param values
	 * @param newString
	 * @return
	 */	
	public static User search(Collection<User> values, Notify value) {
		User foundedString = null;

		for (Iterator<User> iterator = values.iterator(); iterator.hasNext();) {
			User tmp = (User) iterator.next();
			
			try {
				if(tmp.getName().equals(value.getUsername())){
					foundedString = tmp;
					break;
				}
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}

		return foundedString;
	}

	/**
	 * Busca por um determinada string numa colecao
	 * 
	 * @param values
	 * @param newString
	 * @return
	 */	
	public static User search(Collection<User> values, String value) {
		User foundedString = null;

		for (Iterator<User> iterator = values.iterator(); iterator.hasNext();) {
			User tmp = (User) iterator.next();
			
			if(tmp.getName().equals(value)){
				foundedString = tmp;
				break;
			}
		}

		return foundedString;
	}

	/**
	 * Busca por um determinado string numa tabela de espalhamento
	 * 
	 * @param map
	 * @param newString
	 * @return
	 */
	public static List<String> search(Map<String, List<String>> map, String newString) {
		List<String> foundedString = null;
		
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			List<String> value = entry.getValue();

		    if(key.equals(newString)){
				foundedString = value;
				break;
			}
		}
		
		return foundedString;
	}

	/**
	 * Busca por um determinado string numa tabela de espalhamento
	 * 
	 * @param map
	 * @param newString
	 * @return
	 */
	public static String searchStrStr(Map<String, String> map, String newString) {
		String foundedString = null;
		
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

		    if(key.equals(newString)){
				foundedString = value;
				break;
			}
		}
		
		return foundedString;
	}

	/** 
	 * Remove um string da colecao caso seja igual ao parametro 'newString'
	 * 
	 * @param values
	 */
	public static void removeOutStrings(Collection<String> values, String newString) {		
		for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
			String tmp = (String) iterator.next();
			
			if(tmp.equals(newString)){
				iterator.remove();
			}
		}
		
//		System.out.println("Lista: " + vehicles.size());
	}

	/**
	 * Remove um string da tabela de espalhamento caso seja igual ao parametro 'newString'
	 * 
	 * @param vehiclesMap
	 */
	public static void removeOutStrings(Map<String, List<String>> map, String newString) {			
		for (Iterator<Entry<String, List<String>>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) iterator.next();
			String key = entry.getKey();
//			List<String> value = entry.getValue();
			
			if(key.equals(newString)){
				iterator.remove();
			}
		}
		
//		System.out.println("Lista: " + vehicles.size());
	}

	/**
	 * Remove um string da tabela de espalhamento caso seja igual ao parametro 'newString'
	 * 
	 * @param vehiclesMap
	 */
	public static void removeOutStringsStrings(Map<String, String> map, String newString) {			
		for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			String key = entry.getKey();
//			List<String> value = entry.getValue();
			
			if(key.equals(newString)){
				iterator.remove();
			}
		}
		
//		System.out.println("Lista: " + vehicles.size());
	}

	/**
	 *  
	 * @param vehiclesMap
	 */
	public static void print(Map<String, List<String>> map) {			
		for (Iterator<Entry<String, List<String>>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) iterator.next();
			String key = entry.getKey();
			List<String> values = entry.getValue();
			
			System.out.println(key);
			
			for (String string : values) {
				System.out.println("\t" + string);
			}
		}
		
//		System.out.println("Lista: " + vehicles.size());
	}

	/**
	 *  
	 * @param map
	 */
	public static void printStrStr(Map<String, String> map) {			
		for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			
			System.out.println(key + "=" + value);
		}
		
//		System.out.println("Lista: " + vehicles.size());
	}

	public static Map<String, String> toMap(Collection<User> usersOn) {
		Map<String, String> tmp = new HashMap<String, String>();
		
		for (User user : usersOn) {
			tmp.put(user.getName(), user.getColorRGB());
		}
		
		return tmp;
	}
}

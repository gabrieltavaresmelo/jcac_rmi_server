package br.com.gtmf.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Classe que contem metodos utilitarios
 * de acesso a Rede
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public class NetworkUtils {

	public static String getIpHostnameLocal() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			return ip.getHostName();
			
		} catch(Exception e) {
			return "not-found";
		}
	}

	public static String getIpAddressLocal() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			return ip.getHostAddress();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "127.0.0.1";
	}
	
	public static Map<String, String> getInterfaces(){
		Map<String, String> interfaces = new HashMap<String, String>();
		
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();

			while(e.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e.nextElement();
				String nameInterface = ni.getName();

				Enumeration<InetAddress> e2 = ni.getInetAddresses();

				while (e2.hasMoreElements()){
					InetAddress ipAddr = (InetAddress) e2.nextElement();
					if(ipAddr.getHostAddress().contains(".")){
						String ip = ipAddr.getHostAddress();
						interfaces.put(nameInterface, ip);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return interfaces;
	}
	
	public static String getIpAddressExternal() {
		URL myIP;
		try {
			myIP = new URL("http://api.externalip.net/ip/");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					myIP.openStream()));
			return in.readLine();
		} catch (Exception e) {
			try {
				myIP = new URL("http://myip.dnsomatic.com/");

				BufferedReader in = new BufferedReader(new InputStreamReader(
						myIP.openStream()));
				return in.readLine();
			} catch (Exception e1) {
				try {
					myIP = new URL("http://icanhazip.com/");

					BufferedReader in = new BufferedReader(
							new InputStreamReader(myIP.openStream()));
					return in.readLine();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		return null;
	}

}

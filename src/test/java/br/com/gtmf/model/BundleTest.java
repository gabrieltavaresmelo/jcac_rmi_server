package br.com.gtmf.model;

public class BundleTest {
	
	public static void main(String[] args) {
		String jsonUIN = "{\"head\":\""+Bundle.USER_IN+"\",\"body\":\"gabriel\"}";
		String jsonUOU = "{\"head\":\""+Bundle.USER_OUT+"\",\"body\":\"gabriel\"}";
		String jsonLUO = "{\"head\":\""+ Bundle.LIST_USERS_ON
				+ "\",\"body\":{"
				+ "\"lia\":\"00FF00\""
				+ ","
				+ "\"ester\":\"0000FF\""
				+ ","
				+ "\"fran\":\"FF0000\""
				+ "}"
				+ "}";
//		String jsonLIR = "{\"head\":\""+ Bundle.LIST_IMAGES_RECEIVE
//				+ "\",\"body\":{"
//				+ "\"barbara\":[\"IMG_02\",\"IMG_05\",\"IMG_03\",\"IMG_04\"]"
//				+ ","
//				+ "\"dani\":[\"IMG_08\",\"IMG_06\",\"IMG_07\",\"IMG_02\"]"
//				+ "}"
//				+ "}";
//		String jsonLIS = "{\"head\":\""+ Bundle.LIST_IMAGES_SEND
//				+ "\",\"body\":{"
//				+ "\"gabriel\":[\"IMG_01\",\"IMG_02\",\"IMG_03\",\"IMG_04\"]"
//				+ ","
//				+ "\"debora\":[\"IMG_07\",\"IMG_03\",\"IMG_08\",\"IMG_02\"]"
//				+ "}"
//				+ "}";
		String jsonQRC = "{\"head\":\""+Bundle.QUESTION_RECEIVE+"\",\"body\":\"Possui barba?\"}";
		String jsonQSN = "{\"head\":\""+Bundle.QUESTION_SEND+"\",\"body\":\"Possui oculos?\"}";
		String jsonASN = "{\"head\":\""+Bundle.ANSWER_SEND+"\",\"body\":\"Sim\"}";
		String jsonCMR = "{\"head\":\""+Bundle.CHAT_MSG_RECEIVE+"\",\"body\":\"Ola todo mundo.\"}";
		String jsonCMS = "{\"head\":\""+Bundle.CHAT_MSG_SEND+"\",\"body\":\"Oi\"}";
		
//		System.out.println(jsonLIS);
		
		try {
			Bundle bUIN = new Bundle(jsonUIN);
			System.out.println(jsonUIN);
			System.out.println(bUIN.toJson());
			System.out.println();
			
			Bundle bUOU = new Bundle(jsonUOU);
			System.out.println(jsonUOU);
			System.out.println(bUOU.toJson());
			System.out.println();
			
			Bundle bLUO = new Bundle(jsonLUO);
			System.out.println(jsonLUO);
			System.out.println(bLUO.toJson());
			System.out.println();
			
//			Bundle bLIR = new Bundle(jsonLIR);
//			System.out.println(jsonLIR);
//			System.out.println(bLIR.toJson());
//			System.out.println();
//			
//			Bundle bLIS = new Bundle(jsonLIS);
//			System.out.println(jsonLIS);
//			System.out.println(bLIS.toJson());
//			new Bundle(bLIS.toJson());
//			System.out.println();
			
			Bundle bQRC = new Bundle(jsonQRC);
			System.out.println(jsonQRC);
			System.out.println(bQRC.toJson());
			System.out.println();
			
			Bundle bQSN = new Bundle(jsonQSN);
			System.out.println(jsonQSN);
			System.out.println(bQSN.toJson());
			System.out.println();
			
			Bundle bASN = new Bundle(jsonASN);
			System.out.println(jsonASN);
			System.out.println(bASN.toJson());
			System.out.println();
			
			Bundle bCMR = new Bundle(jsonCMR);
			System.out.println(jsonCMR);
			System.out.println(bCMR.toJson());
			System.out.println();
			
			Bundle bCMS = new Bundle(jsonCMS);
			System.out.println(jsonCMS);
			System.out.println(bCMS.toJson());
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

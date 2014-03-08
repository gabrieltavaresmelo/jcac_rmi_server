package br.com.gtmf.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

/**
 * Classe responsavel por encapsular o protocolo da comunicacao.
 * 
 * @author Gabriel Tavares
 * 
 */
public class Bundle {

	/**
	 * Protocolo da mensagem
	 * 
	 */
	public static final String USER_IN = 					"UIN"; // Envia "eu" (recem-login) para o servidor
	public static final String USER_OUT = 					"UOU"; // Envia "eu" (recem-logout) para o servidor
	public static final String SERVER_OUT = 				"SOU"; // Envia para todos os clientes que o servidor esta OFF
	public static final String LIST_USERS_ON = 				"LUO"; // Recebe a lista dos usuarios logados
	public static final String QUESTION_RECEIVE = 			"QRC"; // Recebe a pergunta do adversario (Ex.: Tem barba?)
	public static final String QUESTION_SEND = 				"QSN"; // Envia a pergunta para o adversario (Ex.: Tem barba?)
	public static final String ANSWER_RECEIVE =				"ARC"; // Recebe a resposta para a pergunta do adversario (Ex.: sim/nao)
	public static final String ANSWER_SEND = 				"ASN"; // Envia a resposta para a pergunta do adversario (Ex.: sim/nao)
	public static final String CHAT_MSG_RECEIVE = 			"CMR"; // Recebe a mensagem do chat
	public static final String CHAT_MSG_SEND = 				"CMS"; // Envia a mensagem via chat
	public static final String USER_FIRST = 				"UST"; // Envia para todos o primeiro usuario a se logar
	public static final String NEW_GAME_SEND = 				"NGS"; // Envia para o servidor solicitando o inicio do jogo
	public static final String NEW_GAME_RECEIVE = 			"NGR"; // Recebe do servidor a confirmacao para iniciar o inicio do jogo
	public static final String RIDDLE_PERSONA_SEND = 		"RPS"; // Envia para o servidor o nome do personagem para advinhar
	public static final String RIDDLE_PERSONA_RECEIVE =		"RPR"; // Recebe do servidor se acertou ou nao o personagem adivinhado
	
	
	// Variaveis
	private String head; // cabecalho da mensagem
		
	private String user = null;
	private Map<String, String> usersOn = null; // nome, cor
	private String question = null;
	private String answer = null;
	private String chatMsg = null;
	private String persona = null;
	private String exit = null;
	
	public Bundle(String head, String body) throws Exception{
		this.head = head;
		
		switch (head) {
			case USER_FIRST:							
				user = body;
				break;
				
			case USER_IN:							
				user = body;
				break;
				
			case USER_OUT:							
				user = body;
				break;
				
			case SERVER_OUT:
				exit = body;
				break;
				
			case QUESTION_RECEIVE:						
				question = body;			
				break;
				
			case QUESTION_SEND:						
				question = body;			
				break;
				
			case ANSWER_SEND:							
				answer = body;			
				break;
				
			case CHAT_MSG_RECEIVE:						
				chatMsg = body;			
				break;
				
			case CHAT_MSG_SEND:						
				chatMsg = body;			
				break;
				
			case NEW_GAME_SEND:						
				user = body;			
				break;
				
			case NEW_GAME_RECEIVE:						
				user = body;			
				break;
				
			case RIDDLE_PERSONA_SEND:						
				persona = body;			
				break;
				
			case RIDDLE_PERSONA_RECEIVE:						
				persona = body;			
				break;
	
			default:
				throw new Exception();
		}
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Bundle(String head, Map map) throws Exception{
		this.head = head;
		
		switch (head) {				
			case LIST_USERS_ON:
				usersOn = map;
				break;
	
			default:
				throw new Exception();
		}
	}
	
	public Bundle(String jsonStr) throws IOException, ClassNotFoundException {
		JsonFactory jfactory = new JsonFactory();
		JsonParser jParser = jfactory.createJsonParser(jsonStr);
		
		String body = "";
		
		// Percorre ate o token for igual a "}"
		while (jParser.nextToken() != JsonToken.END_OBJECT) {
			
			String fieldname1 = jParser.getCurrentName();
						
			if ("head".equals(fieldname1)) {
				// token atual e' "head",
				// move para o proximo token, que e' o valor de "head" (ou seja, body)
				jParser.nextToken();
				head = jParser.getText();

				// token do "body"
				jParser.nextToken();
				String fieldname2 = jParser.getText();
				
				if ("body".equals(fieldname2)) {
					
					switch (head) {
						case USER_FIRST:
							jParser.nextToken();
							body = jParser.getText();							
							user = body;
							
							break;
							
						case USER_IN:
							jParser.nextToken();
							body = jParser.getText();							
							user = body;
							
							break;
							
						case USER_OUT:
							break;
							
						case SERVER_OUT:
							jParser.nextToken();
							body = jParser.getText();							
							exit = body;
							break;
							
						case LIST_USERS_ON:
							usersOn = toMap1(jParser, body);
							break;
							
						case QUESTION_RECEIVE:
							jParser.nextToken();
							body = jParser.getText();							
							question = body;
							
							break;
							
						case QUESTION_SEND:
							jParser.nextToken();
							body = jParser.getText();							
							question = body;
							
							break;
							
						case ANSWER_RECEIVE:
							jParser.nextToken();
							body = jParser.getText();							
							answer = body;
							
							break;
							
						case ANSWER_SEND:
							jParser.nextToken();
							body = jParser.getText();							
							answer = body;
							
							break;
							
						case CHAT_MSG_RECEIVE:
							jParser.nextToken();
							body = jParser.getText();							
							chatMsg = body;
							
							break;
							
						case CHAT_MSG_SEND:
							jParser.nextToken();
							body = jParser.getText();							
							chatMsg = body;
							
							break;
							
						case NEW_GAME_SEND:
							jParser.nextToken();
							body = jParser.getText();							
							user = body;
							
							break;
							
						case NEW_GAME_RECEIVE:
							jParser.nextToken();
							body = jParser.getText();							
							user = body;
							
							break;
							
						case RIDDLE_PERSONA_SEND:
							jParser.nextToken();
							body = jParser.getText();							
							persona = body;
							
							break;
							
						case RIDDLE_PERSONA_RECEIVE:
							jParser.nextToken();
							body = jParser.getText();							
							persona = body;
							
							break;
		
						default:
							body = null;
					}					
				}
			}
		}
	}

	private Map<String, String> toMap1(JsonParser jParser, String body) throws IOException,
			JsonParseException {
		
		// Instancia as colecoes
		Map<String, String> images = new HashMap<String, String>();
		
		// token atual e' "{", mova para o proximo
		jParser.nextToken(); 
		
		// Percorre ate o token for igual a "}"
		while (jParser.nextToken() != JsonToken.END_OBJECT) {

			// obtem o nome do usuario
			String username = jParser.getCurrentName();
			
			// token atual e' "cor", mova para o proximo
			jParser.nextToken(); 
			
			// obtem a cor
			body = jParser.getText();
			
			if(username != null && body != null){
//				System.out.println(username + "=" + body);				
				images.put(username, body);
			}
		}
		
		return images;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Map<String, String> getUsersOn() {
		return usersOn;
	}

	public void setUsersOn(Map<String, String> usersOn) {
		this.usersOn = usersOn;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getChatMsg() {
		return chatMsg;
	}

	public void setChatMsg(String chatMsg) {
		this.chatMsg = chatMsg;
	}

	public String getHead() {
		return head;
	}
	
	public void setHead(String head) {
		this.head = head;
	}
	
	public String getPersona() {
		return persona;
	}
	
	public void setPersona(String persona) {
		this.persona = persona;
	}
	
	public String getExit() {
		return exit;
	}
	
	public void setExit(String exit) {
		this.exit = exit;
	}

	public String toJson() throws Exception {

        StringWriter sw = new StringWriter();
//		final ByteArrayOutputStream sw = new ByteArrayOutputStream();
		JsonGenerator jGenerator = new JsonFactory().createJsonGenerator(sw
//		, JsonEncoding.UTF8
		);
		
		jGenerator.writeStartObject(); // {
		
		if(getUser() != null){
			jGenerator.writeStringField("head", head); // "head" : "UIN"
			jGenerator.writeStringField("body", getUser()); // "body" : "fulano"
			
		} if(getExit() != null){
			jGenerator.writeStringField("head", head);
			jGenerator.writeStringField("body", getExit());
			
		} if(getUsersOn() != null){
			jGenerator.writeStringField("head", head); 						 
			jGenerator.writeFieldName("body");
			
			jGenerator.writeStartObject(); // {
		 
			for (Iterator<Entry<String, String>> iterator = getUsersOn().entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
								
				jGenerator.writeStringField(key, value);
			}

			jGenerator.writeEndObject(); // }
			
		} if(getAnswer() != null){
			jGenerator.writeStringField("head", head);
			jGenerator.writeStringField("body", getAnswer()); 
			
		} if(getQuestion() != null){
			jGenerator.writeStringField("head", head); 
			jGenerator.writeStringField("body", getQuestion()); 
			
		} if(getChatMsg() != null){
			jGenerator.writeStringField("head", head); 
			jGenerator.writeStringField("body", getChatMsg()); 
			
		} if(getPersona() != null){
			jGenerator.writeStringField("head", head);
			jGenerator.writeStringField("body", getPersona()); 
			
		} 
		
		jGenerator.writeEndObject(); // }		
		jGenerator.close();

//		final byte[] data = sw.toByteArray();
		String input = sw.toString(); //new String(data);
//		jGenerator.writeString(input);
		
		return input;
	}
	
	@Override
	public String toString() {
		try {
			return toJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

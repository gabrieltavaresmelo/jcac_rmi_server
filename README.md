jcac_server
===========

Jogo Cara-A-Cara de forma distribuída.

Implementação no lado [Servidor] utilizan RMI e o protocolo JSON.


* Autor: Gabriel Tavares
* Disciplina: Programação Paralela e Distribuída
* Curso: Engenharia de Computação - IFCE



![ScreenShot](https://github.com/gabrieltavaresmelo/jcac_server/raw/master/gui.png)



Instalação e Dependências:
----------------------------

1) Adicione o JavaFX no seu classpath:

    sudo mvn com.zenjava:javafx-maven-plugin:2.0:fix-classpath
	

2) Para compilar o projeto:

    mvn clean install
	
	
3) Para executar a aplicação:

    mvn jfx:run
	

4) Distribuir sua aplicação (Modo JAR):

    mvn clean jfx:jar
    
    # Obs.: o arquivo gerado estará em:
    # java -jar ${DIR_APP}/target/jfx/app/jcac_server-jfx.jar
    
	
5) Distribuir sua aplicação (Modo Nativo):

    mvn clean jfx:native
	
6) Distribuir sua aplicação (Modo Web):

    mvn clean jfx:web



Bibliotecas e Tecnologias utilizadas:
----------------------------

* Java 7 (Oracle)
* JavaFX
* Maven 3
* JavaFX MigLayout
* JavaFX Maven Deploy (Zen)
* JavaWebSocket (TooTallNate)
* Logs (Log4j e slf4j)
* JSON (Jackson)
* [ZenJava](http://zenjava.com/javafx/maven/index.html )

 

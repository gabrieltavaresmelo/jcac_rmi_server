package br.com.gtmf;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.tbee.javafx.scene.layout.fxml.MigPane;

import br.com.gtmf.utils.Constants;
import br.com.gtmf.window.MainLayoutWindow;

/**
 * Jogo Cara-A-Cara de forma distribuída.
 * Implementação no lado [Servidor] usando RMI.
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public class MainApp extends Application {

	private MainLayoutWindow mainLayoutWindow;
	

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {    	
    	// Configura o titulo da janela
    	stage.setTitle("[Servidor RMI] Jogo Cara-A-cara");
		
		// Configura o evento ao clicar no botao fechar da janela
    	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
			@Override
			public void handle(WindowEvent event) {
				if(event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST){
					closeApp();
				}	
			}
		});
		
		// Configura o icone da aplicacao
		try {
			stage.getIcons().add(new Image(getClass().getResourceAsStream(Constants.LOGO)));
		} catch (Exception e) {
			System.err.println("LOGO nao encontrada! " + e.getMessage());
		}

//        log.info("Starting Hello JavaFX and Maven demonstration application");
//        log.debug("Carregando o FXML: {}", fxmlRootLayout);        
        
    	// Constroi os layouts
        showMainLayout(stage);

        // Exibe a tela
        stage.show();
    }

    /**
     * Instancia o layout a partir do arquivo FXML
     * 
     * @param stage
     * @throws IOException
     */
	private void showMainLayout(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		MigPane migPane = (MigPane) loader.load(getClass().getResourceAsStream(Constants.fxmlMainLayout));

        Scene scene = new Scene(migPane);
        scene.getStylesheets().add("/styles/styles.css");
        stage.setScene(scene);
        
        // Dar ao Controller acesso a MainApp
        mainLayoutWindow = loader.getController();
        mainLayoutWindow.setParams(stage);
	}
	
	public void closeApp() {
		mainLayoutWindow.finish();
	}
}

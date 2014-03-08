package br.com.gtmf.utils;

import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * Classe que contem metodos de acesso repido
 * a mensagens na tela. Semelhante ao JOptionPane 
 * (Swing).
 * 
 * 
 * @author Gabriel Tavares
 *
 */
public class FXOptionPane {

	public enum Response {
		NO, YES, CANCEL
	};
	
    private static Response buttonSelected = Response.CANCEL;
    private static ImageView icon = new ImageView();

    static class Dialog extends Stage {

        public Dialog(String title, Stage owner, Scene scene, String iconFile) {
            setTitle(title);
            initStyle(StageStyle.UTILITY);
            initModality(Modality.APPLICATION_MODAL);
            initOwner(owner);
            setResizable(false);
            setScene(scene);
            
            try {
				InputStream url = getClass().getResourceAsStream(iconFile);
				if(url != null){
					icon.setImage(new Image(url));
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
        }

        public void showDialog() {
            sizeToScene();
            centerOnScreen();
            showAndWait();
        }
    }

    static class Message extends Text {

        public Message(String msg) {
            super("\n" + msg + "\n");
//            setWrappingWidth(250);
            setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 13));
//            setTextAlignment(TextAlignment.CENTER);
        }
    }
    
    private static String returnText = "";
    
	public static String showInputDialog(Stage owner, String message,
			String title, String titleOkBtn, String titleCancelBtn,
			String defaultValue) {
    	returnText = "";
    	
        VBox vb = new VBox();
        Scene scene = new Scene(vb);
        scene.getStylesheets().add("/styles/styles.css");
        
        final Dialog dial = new Dialog(title, owner, scene, "");
        vb.setPadding( Layout.PADDING );
        vb.setSpacing( Layout.SPACING );

        final TextField textField = new TextField();
        textField.setText(defaultValue);
        
        Button yesButton = new Button(titleOkBtn);
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dial.close();
                returnText = textField.getText();
            }
        });
        
        Button noButton = new Button(titleCancelBtn);
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dial.close();
            }
        });
        
        
        BorderPane bp = new BorderPane();
        
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing( Layout.SPACING );
        buttons.setPrefHeight(50.);
        buttons.getChildren().addAll(yesButton, noButton);
        bp.setCenter(buttons);
        
        HBox msg = new HBox();
        msg.getChildren().addAll(icon, new Message(message));
        
        HBox hboxTf = new HBox();
		hboxTf.getChildren().addAll(textField);
        
        vb.getChildren().addAll(msg, hboxTf, bp);
        
        dial.showDialog();
        
        return returnText;
    }

    public static Response showConfirmDialog(Stage owner, String message, String title, String titleOkBtn, String titleCancelBtn) {
        VBox vb = new VBox();
        Scene scene = new Scene(vb);
        scene.getStylesheets().add("/styles/styles.css");
        
        final Dialog dial = new Dialog(title, owner, scene, "");
        vb.setPadding( Layout.PADDING );
        vb.setSpacing( Layout.SPACING );
        
        Button yesButton = new Button(titleOkBtn);
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dial.close();
                buttonSelected = Response.YES;
            }
        });
        
        Button noButton = new Button(titleCancelBtn);
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dial.close();
                buttonSelected = Response.NO;
            }
        });
        
        BorderPane bp = new BorderPane();
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing( Layout.SPACING );
        buttons.setPrefHeight(50.);
        buttons.getChildren().addAll(yesButton, noButton);
        bp.setCenter(buttons);
        
        HBox msg = new HBox();
        msg.getChildren().addAll(icon, new Message(message));
        
        vb.getChildren().addAll(msg, bp);
        
        dial.showDialog();
        
        return buttonSelected;
    }

    public static void showMessageDialog(Stage owner, String message, String title, String titleOkBtn) {
        showMessageDialog(owner, new Message(message), title, titleOkBtn);
    }

    public static void showMessageDialog(Stage owner, Node message, String title, String titleOkBtn) {
        VBox vb = new VBox();
        Scene scene = new Scene(vb);
        scene.getStylesheets().add("/styles/styles.css");
        final Dialog dial = new Dialog(title, owner, scene, "");
        vb.setPadding( Layout.PADDING );
        vb.setSpacing( Layout.SPACING );
        
        Button okButton = new Button(titleOkBtn);
        vb.setPrefHeight(75.);
        okButton.setAlignment(Pos.CENTER);
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dial.close();
            }
        });
        
        BorderPane bp = new BorderPane();
        bp.setCenter(okButton);
        bp.autosize();
        HBox msg = new HBox();
        msg.getChildren().addAll(icon, message);
        vb.getChildren().addAll(msg, bp);
        dial.showDialog();
    }

	static class Layout {
		public static final Insets PADDING = new Insets(10);
		public static final double SPACING = 10;
		public static final double SPACING_SMALL = 5;
	}
}
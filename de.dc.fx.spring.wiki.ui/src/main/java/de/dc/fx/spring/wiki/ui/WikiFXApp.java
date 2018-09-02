package de.dc.fx.spring.wiki.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SpringBootApplication
public class WikiFXApp extends Application {

    private ConfigurableApplicationContext springContext;
    private BorderPane root;
	private FXMLLoader fxmlLoader;
	private Stage primaryStage;
	
    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(WikiFXApp.class);
        fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, 1400, 800);
        this.primaryStage = primaryStage;
		this.primaryStage.setScene(scene);
        this.primaryStage.initStyle(StageStyle.DECORATED);
        this.primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
        System.exit(0);
    }

    @Bean
    public HostServices getHostService() {
    	return getHostServices();
    }
    
    public static void main(String[] args) {
        launch(WikiFXApp.class);
    }
}

package io.github.scout2012.gui.converter;

import com.yeokhengmeng.docstopdfconverter.Converter;
import com.yeokhengmeng.docstopdfconverter.ConverterRunner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Window extends Application{
	String pathFileToBe = null;
	String fileExtension = null;

	 @Override
	    public void start(Stage primaryStage){    
		 	MenuBar menuBar = new MenuBar();
		 	
		 	Menu fileOption = new Menu("File");
		 	MenuItem openOption = new MenuItem("Open");
		 	
		 	FileChooser chooseFile = new FileChooser();
		 	
		 	Button convertButton = new Button("Covert File");
		 	convertButton.setDisable(true);
		 	
		 	chooseFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Document Files", "*.doc", "*.docx", "*.ppt", "*.pptx", "*.odt"));
		 	
		 	openOption.setOnAction(e ->{
		 		pathFileToBe = chooseFile.showOpenDialog(primaryStage).getAbsolutePath();
		 		fileExtension = pathFileToBe.substring(pathFileToBe.lastIndexOf('.') + 1);
		 		convertButton.setDisable(false);
		 	});
		 	
	        fileOption.getItems().add(openOption);
	        
	        menuBar.getMenus().add(fileOption);
	        
	        convertButton.setOnAction(new EventHandler<ActionEvent>(){
	        	 
	            @Override
	            public void handle(ActionEvent event){
	            	System.out.println(pathFileToBe);
	            	Converter converter;

	        		try{
	        			converter = ConverterRunner.getConvertType(pathFileToBe, fileExtension);
	        		}catch (Exception e){
	        			System.out.println("\n\nInput\\Output file not specified properly.\n" + e.getMessage());
	        			return;
	        		}

	        		if(converter == null){
	        			System.out.println("Unable to determine type of input file.");
	        		}else{
	        			try{
	        				converter.convert();
	        			}catch (Exception e){
	        				System.out.println("Error converting: \n " + e.getMessage());
	        			}
	        		}
	            }
	        });
		 
	        BorderPane root = new BorderPane();
	        GridPane grid = new GridPane();

	        grid.setAlignment(Pos.CENTER);
	        grid.add(convertButton, 0, 0, 1, 1);
	        
	        root.setTop(menuBar);
	        root.setCenter(grid);
	        	        
	        Scene scene = new Scene(root, 300, 250);

	        primaryStage.setTitle("DOC/DOCX/ODT/PPT/PPTX To PDF");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

}

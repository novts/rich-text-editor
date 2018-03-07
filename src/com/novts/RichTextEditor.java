package com.novts;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RichTextEditor extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane group = new Pane();

        VBox vbox=new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(10);
        vbox.setSpacing(20);

        TextArea textArea = new TextArea();
        textArea.setLayoutX(10);
        textArea.setLayoutY(10);
        textArea.setCursor(Cursor.TEXT);
        textArea.setStyle("-fx-background-radius:20;-fx-border-radius:20;-fx-background-color:#ffefd5;-fx-border-width:3pt;-fx-border-color:#cd853f;-fx-font-weight:normal;-fx-font-size:14pt; -fx-font-family:Georgia; -fx-font-style:normal");
        textArea.setPrefSize(600, 200);
        textArea.setEditable(true);
        textArea.setWrapText(true);

        HTMLEditor editor=new HTMLEditor();
        editor.setPrefSize(500, 300);
        editor.setCursor(Cursor.TEXT);
        editor.setHtmlText("<html><body></body></html>");

        HBox hbox=new HBox();
        hbox.setLayoutX(20);
        hbox.setLayoutY(10);
        hbox.setSpacing(20);

        VBox vboxBtn=new VBox();
        vboxBtn.setLayoutX(20);
        vboxBtn.setLayoutY(10);
        vboxBtn.setSpacing(20);

        Button btnShow = new Button();
        btnShow.setText("Show HTML");
        btnShow.setCursor(Cursor.CLOSED_HAND);
        btnShow.setStyle("-fx-font: bold 12pt Georgia;-fx-text-fill: white;-fx-background-color: #a0522d;-fx-border-width: 3px; -fx-border-color:#f4a460 #800000 #800000 #f4a460;" );
        btnShow.setOnAction((ActionEvent e)-> {
                textArea.setText(editor.getHtmlText());
            });

        Button btnClear = new Button();
        btnClear.setText("Clear");
        btnClear.setCursor(Cursor.CLOSED_HAND);
        btnClear.setStyle("-fx-font: bold 12pt Georgia;-fx-text-fill: white;-fx-background-color: #a0522d;-fx-border-width: 3px; -fx-border-color:#f4a460 #800000 #800000 #f4a460;" );
        btnClear.setOnAction((ActionEvent e)-> {
                editor.setHtmlText("<html><body></body></html>");
            });

        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new java.io.File("C:/"));
        fileChooser.setTitle("Select HTML");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html", "*.html"));


        Button btnOpen = new Button();
        btnOpen.setText("Open HTML");
        btnOpen.setCursor(Cursor.CLOSED_HAND);
        btnOpen.setStyle("-fx-font: bold 12pt Georgia;-fx-text-fill: white;-fx-background-color: #a0522d;-fx-border-width: 3px; -fx-border-color:#f4a460 #800000 #800000 #f4a460;" );
        btnOpen.setOnAction((ActionEvent e)-> {
            File file=fileChooser.showOpenDialog(primaryStage);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "UTF8"));
                StringBuffer stringBuffer = new StringBuffer();
                while (bufferedReader.ready()) {
                    stringBuffer.append(bufferedReader.readLine() + "\n");
                }
                editor.setHtmlText(stringBuffer.toString());
                bufferedReader.close();

            } catch (Exception ex) {
            }

        });

        Button btnSave = new Button();
        btnSave.setText("Save HTML");
        btnSave.setCursor(Cursor.CLOSED_HAND);
        btnSave.setStyle("-fx-font: bold 12pt Georgia;-fx-text-fill: white;-fx-background-color: #a0522d;-fx-border-width: 3px; -fx-border-color:#f4a460 #800000 #800000 #f4a460;" );
        btnSave.setOnAction((ActionEvent e)-> {
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                String outputString=editor.getHtmlText();
                try {
                    FileWriter fileWriter = null;
                    fileWriter = new FileWriter(file);
                    fileWriter.write(outputString);
                    fileWriter.close();
                } catch (Exception ex) {
                }
            }
            });

        vboxBtn.getChildren().addAll(btnClear, btnShow, btnOpen, btnSave);
        hbox.getChildren().addAll(textArea, vboxBtn);
        vbox.getChildren().addAll(hbox, editor);
        group.getChildren().addAll(vbox);

        ScrollPane sp = new ScrollPane();
        sp.setLayoutX(10);
        sp.setLayoutY(10);
        sp.setPrefSize(900, 600);
        sp.setContent(group);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setPannable(true);


        Group root = new Group();
        root.getChildren().addAll(sp);
        Scene scene = new Scene(root, 920,620, Color.BEIGE);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

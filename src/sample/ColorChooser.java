package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controller;

public class ColorChooser extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("ColorPicker");
        Scene scene = new Scene(new AnchorPane(), 400, 140);
        AnchorPane box = (AnchorPane) scene.getRoot();
        box.setPadding(new Insets(5, 5, 5, 5));
        stage.initModality(Modality.APPLICATION_MODAL);
        Label Fx=new Label("Color of F(x) function ");
        Fx.setLayoutX(5);
        Fx.setLayoutY(13);
        final ColorPicker colorPickerFx = new ColorPicker();
        colorPickerFx.setLayoutX(160);
        colorPickerFx.setLayoutY(10);
        colorPickerFx.setValue(Color.RED.darker());
        colorPickerFx.setOnAction((event -> Controller.Fx=colorPickerFx.getValue()));

        Label Gx=new Label("Color of G(x) function");
        Gx.setLayoutX(5);
        Gx.setLayoutY(63-25);
        final ColorPicker colorPickerGx = new ColorPicker();
        colorPickerGx.setLayoutX(160);
        colorPickerGx.setLayoutY(60-25);
        colorPickerGx.setValue(Color.BLUE.darker());
        colorPickerGx.setOnAction((event -> Controller.Gx=colorPickerGx.getValue()));

        Label Min=new Label("Minimum's color ");
        Min.setLayoutX(5);
        Min.setLayoutY(87-25);
        final ColorPicker colorPickerMin = new ColorPicker();
        colorPickerMin.setLayoutX(160);
        colorPickerMin.setLayoutY(85-25);
        colorPickerMin.setValue(Color.YELLOW.darker());
        colorPickerMin.setOnAction((event -> Controller.Min=colorPickerMin.getValue()));

        Label lines=new Label("Coordinate axes's color");
        lines.setLayoutX(5);
        lines.setLayoutY(112-25);
        final ColorPicker colorPickerLin = new ColorPicker();
        colorPickerLin.setLayoutX(160);
        colorPickerLin.setLayoutY(110-25);
        colorPickerLin.setValue(Color.BLACK.darker());
        colorPickerLin.setOnAction((event -> Controller.lines=colorPickerLin.getValue()));

      /*colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                text.setFill(colorPicker.getValue());
            }
        });
*/
        box.getChildren().addAll(Fx,colorPickerFx,Gx,colorPickerGx,Min,colorPickerMin,lines,colorPickerLin);
        stage.setScene(scene);
        stage.show();
    }
}


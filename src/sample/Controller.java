package sample;

import calculation.*;
import calculation.Point;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.scene.control.skin.ColorPalette;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private ObservableList<Point> pointsF = FXCollections.observableArrayList();
    private ObservableList<Point> pointsG = FXCollections.observableArrayList();
    File openedFile;
    private Stage stage;
    @FXML
    private Canvas Graph;
    GraphicsContext gc;
    @FXML
    private TableView tablePointsF;
    @FXML
    private TableColumn F_x;
    @FXML
    private TableColumn F_y;
    @FXML
    private TextField From;
    @FXML
    private TextField To;
    @FXML
    private TableView tablePointsG;
    @FXML
    private TableColumn G_x;
    @FXML
    private TableColumn G_y;
    @FXML
    private Label result;
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static Paint lines=Color.BLACK.darker();
    public static Paint Fx=Color.RED.darker();
    public static Paint Gx=Color.BLUE.darker();
    public static Paint Min=Color.YELLOW.darker();
    public void Save_menu(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file(*.xml)", "*.xml"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        try {
            CW c=new CW();
            c.F=toArrayList(pointsF);
            c.G=toArrayList(pointsG);
            c.writeToXML(selectedFile.getAbsolutePath());
        }
        catch (Exception e){
        }
    }
    public void Save_in_last(){
        try {
            CW c=new CW();
            c.F=toArrayList(pointsF);
            c.G=toArrayList(pointsG);
            c.writeToXML(openedFile.getAbsolutePath());
        }
        catch (Exception e){
        }
    }
    public void Open_menu(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file(*.xml)", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        openedFile=selectedFile;
       // if (selectedFile != null) stage.display(selectedFile);
        try {
            pointsF=FXCollections.observableArrayList(new CW().readFromXML(selectedFile.getAbsolutePath()).F);
            pointsG=FXCollections.observableArrayList(new CW().readFromXML(selectedFile.getAbsolutePath()).G);
        }
        catch (Exception e){

        }
        initializeTable();
    }
    static int FONT_SIZE_SMALL = 16;
    static int FONT_SIZE_BIG = 32;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        stage=new Stage();
        F_x.setSortable(false);
        F_y.setSortable(false);
        G_x.setSortable(false);
        G_y.setSortable(false);
        System.out.println("Initialize done");

        tablePointsF.setEditable(true);
        tablePointsG.setEditable(true);

        F_x.setCellValueFactory(new PropertyValueFactory<Point, Double>("x"));
        F_x.setCellFactory((t) -> new EditingCell(tablePointsF));

        F_y.setCellValueFactory(new PropertyValueFactory<Point, Double>("y"));
        F_y.setCellFactory((t) -> new EditingCell(tablePointsF));

        G_x.setCellValueFactory(new PropertyValueFactory<Point, Double>("x"));
        G_x.setCellFactory((t) -> new EditingCell(tablePointsG));

        G_y.setCellValueFactory(new PropertyValueFactory<Point, Double>("y"));
        G_y.setCellFactory((t) -> new EditingCell(tablePointsG));
        // устанавливаем тип и значение которое должно хранится в колонке


        F_x.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Point, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Point, Double> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setX(t.getNewValue());
            }
        });
        F_y.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Point, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Point, Double> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setY(t.getNewValue());
            }
        });

        G_x.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Point, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Point, Double> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setX(t.getNewValue());
            }
        });
        G_y.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Point, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Point, Double> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setY(t.getNewValue());
            }
        });


        ContextMenu tableContextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("Add");
        MenuItem deleteSelectedMenuItem = new MenuItem("Delete");
        addMenuItem.setOnAction((event)->{
            try {
                if(pointsF.size()==0) {
                    pointsF.add( new Point(0, 0));
                }
                else pointsF.add(tablePointsF.getSelectionModel().getFocusedIndex(),new Point(0,0));

                tablePointsF.setItems(pointsF);

            }
            catch (Exception e){
                e.printStackTrace();
                pointsF.add(new Point());
            }
        });
        deleteSelectedMenuItem.setOnAction((event)->{
            try {
                pointsF.remove(tablePointsF.getSelectionModel().getFocusedIndex());
            }
            catch (Exception e){

            }
        });
        tableContextMenu.getItems().addAll(addMenuItem, deleteSelectedMenuItem);
        tablePointsF.setContextMenu(tableContextMenu);
        ContextMenu tableContextMenu1 = new ContextMenu();
        MenuItem addMenuItem1 = new MenuItem("Add");
        MenuItem deleteSelectedMenuItem1 = new MenuItem("Delete");
        addMenuItem1.setOnAction((event)->{
            try {
                if(pointsG.size()==0) {
                    pointsG.add( new Point(0, 0));
                }
                else pointsG.add(tablePointsG.getSelectionModel().getFocusedIndex(),new Point(0,0));
            }
            catch (Exception e){
                pointsG.add(new Point());
            }
        });
        deleteSelectedMenuItem1.setOnAction((event)->{
            try {
                pointsG.remove(tablePointsG.getSelectionModel().getFocusedIndex());
            }
            catch (Exception e){

            }
        });

        tableContextMenu1.getItems().addAll(addMenuItem1, deleteSelectedMenuItem1);
        tablePointsG.setContextMenu(tableContextMenu1);

        System.out.println(pointsF.size());
        // заполняем таблицу данными


    }
    private void initializeTable(){
        tablePointsF.setItems(pointsF);
        tablePointsG.setItems(pointsG);
    }
    public void ButtonDraw(){
        DrawGraph(toArrayList(pointsF),toArrayList(pointsG));
    }
    public void menu_changeColor(){
        ColorChooser chartW = new ColorChooser();
        try {
          chartW.start(new Stage());
          //  while(t.isAlive()){}
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawGraph(toArrayList(pointsF),toArrayList(pointsG));
    }

    public void result_lable()
    {
        if(pointsG.size()!=0 && pointsF.size()!=0) {
            result.setText((new CW().x_minimum_search(Double.parseDouble(From.getText()), Double.parseDouble(To.getText()), 0.1, toArrayList(pointsF), toArrayList(pointsG))) + "");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill data about dots, before drawing", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("Function's data is absent");
            alert.showAndWait();
        }
    }

    private ArrayList<Point> toArrayList(ObservableList<Point> F){
        ArrayList<Point> F_Arr=new ArrayList<Point>();
        for(int i=0;i<F.size();i++)
            F_Arr.add(F.get(i));
        return F_Arr;
    }
    private double commonMin(ArrayList<Point> F, ArrayList<Point> G){
        double minX=F.get(0).getX();
        double maxX=F.get(0).getX();
        for (int i = 0; i < F.size(); i++) {
            if(minX>F.get(i).getX()) minX=F.get(i).getX();
            if(maxX<F.get(i).getX()) maxX=F.get(i).getX();
        }
        CW c=new CW();
        double min=c.Interpoilation(minX,F);
        for(double i=minX;i<maxX;i+=0.1)
            if(min>c.Interpoilation(i,F)) {
                min=c.Interpoilation(i,F);
            }
        double minXG=G.get(0).getX();
        double maxXG=G.get(0).getX();
        for (int i = 0; i < F.size(); i++) {
            if(minXG>G.get(i).getX()) minXG=G.get(i).getX();
            if(maxXG<G.get(i).getX()) maxXG=G.get(i).getX();
        }
        for(double i=minXG;i<maxXG;i+=0.1)
            if(min>c.Interpoilation(i,G)) {
                min=c.Interpoilation(i,G);
            }
        return min;
    }

    private double commonMax(ArrayList<Point> F, ArrayList<Point> G) {


        double minX = F.get(0).getX();
        double maxX = F.get(0).getX();
        for (int i = 0; i < F.size(); i++) {
            if (minX > F.get(i).getX()) minX = F.get(i).getX();
            if (maxX < F.get(i).getX()) maxX = F.get(i).getX();
        }
        CW c = new CW();
        double max = c.Interpoilation(minX, F);
        for (double i = minX; i < maxX; i += 0.1)
            if (max < c.Interpoilation(i, F)) {
                max = c.Interpoilation(i, F);
            }
        double minXG = G.get(0).getX();
        double maxXG = G.get(0).getX();
        for (int i = 0; i < F.size(); i++) {
            if (minXG > G.get(i).getX()) minXG = G.get(i).getX();
            if (maxXG < G.get(i).getX()) maxXG = G.get(i).getX();
        }
        for (double i = minXG; i < maxXG; i += 0.1)
            if (max < c.Interpoilation(i, G)) {
                max=c.Interpoilation(i, G);
            }
        return max;
    }


    private void DrawGraph(ArrayList<Point> F, ArrayList<Point> G){
        if(F.size()!=0 && G.size()!=0) {
            double from = Double.parseDouble(From.getText());
            double to = Double.parseDouble(To.getText());
            double weight = Math.abs(from - to);
            gc = Graph.getGraphicsContext2D();
            gc.setFill(Color.WHITE);
            //gc.clearRect(0, 0, Graph.getWidth(), Graph.getHeight());
            gc.fillRect(0,0,Graph.getWidth(),Graph.getHeight());
            // gc.setStroke(Color.WHITE);
            // gc.rect(0,0,Graph.getWidth(),Graph.getHeight());

            double ScaleX = (Graph.getWidth() - 20) / weight;
            double ScaleY = (Graph.getHeight() - 40) / (Math.abs(commonMax(F, G) - commonMin(F, G)));
            double x0 = 10, y0 = Graph.getHeight() - 20;
            if (from * to < 0) x0 = Math.abs(from) * ScaleX + 10;
            if (commonMax(F, G) * commonMin(F, G) < 0) y0 = y0 - Math.abs(commonMin(F, G)) * ScaleY;
            System.out.println("Max " + commonMax(F, G) + "Min" + commonMin(F, G));

            //long countLinesX=;
            gc.setStroke(lines);
            gc.strokeLine(x0, 0, x0, Graph.getHeight());
            gc.strokeLine(0, y0, Graph.getWidth(), y0);
            double f = (Math.abs(from - to)) / Math.round((Math.abs(from - to)));
            gc.setFill(Color.BLACK);
            for (double i = from; i <= to; i += f) {
                gc.strokeLine(calculateX(x0, i, ScaleX), y0 - 5, calculateX(x0, i, ScaleX), y0 + 5);
                gc.fillText(String.format("%.1f", i), calculateX(x0, i, ScaleX) - 7, y0 + 15);
            }

            for (double i = commonMin(F, G); i <= commonMax(F, G) + 2; i += weight / 7) {
                gc.strokeLine(x0 - 5, calculateY(y0, i, ScaleY), x0 + 5, calculateY(y0, i, ScaleY));
                gc.fillText(String.format("%.1f", i), x0 + 7, calculateY(y0, i, ScaleY));
            }

            gc.setStroke(Fx);
            double minX = F.get(0).getX();
            double maxX = F.get(0).getX();
            for (int i = 0; i < F.size(); i++) {
                if (minX > F.get(i).getX()) minX = F.get(i).getX();
                if (maxX < F.get(i).getX()) maxX = F.get(i).getX();
            }
            CW c = new CW();
            for (double k = minX; k < maxX; k += 0.1) {
                double myx, x2, myy, y2;
                myx = x0 + k * ScaleX;
                x2 = x0 + (k + 0.1) * ScaleX;
                myy = calculateY(y0, c.Interpoilation(k, F), ScaleY);
                y2 = calculateY(y0, c.Interpoilation(k + 0.1, F), ScaleY);
                gc.strokeLine(myx, myy, x2, y2);
            }

            gc.setStroke(Gx);
            minX = G.get(0).getX();
            maxX = G.get(0).getX();
            for (int i = 0; i < G.size(); i++) {
                if (minX > G.get(i).getX()) minX = G.get(i).getX();
                if (maxX < G.get(i).getX()) maxX = G.get(i).getX();
            }
            for (double k = minX; k < maxX; k += 0.1) {
                double myx, x2, myy, y2;
                myx = x0 + k * ScaleX;
                x2 = x0 + (k + 0.1) * ScaleX;
                myy = calculateY(y0, c.Interpoilation(k, G), ScaleY);
                y2 = calculateY(y0, c.Interpoilation(k + 0.1, G), ScaleY);
                gc.strokeLine(myx, myy, x2, y2);
            }

            gc.setStroke(Min);
            gc.strokeLine(calculateX(x0, c.x_minimum_search(from, to, 0.1, F, G), ScaleX),
                    calculateY(y0, c.Interpoilation(c.x_minimum_search(from, to, 0.1, F, G), G), ScaleY),
                    calculateX(x0, c.x_minimum_search(from, to, 0.1, F, G), ScaleX),
                    calculateY(y0, c.Interpoilation(c.x_minimum_search(from, to, 0.1, F, G), F), ScaleY)
            );
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill data about dots, before drawing", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("Function's data is absent");
            alert.showAndWait();
        }
    }
    private double calculateY(double y0,double y,double sc_y){
        return y0-y*sc_y;
    }

    private double calculateX(double x0,double x,double sc_x){
        return x0+x*sc_x;
    }


    public void Save_HTML_report(){
        save_HTML_Report(pointsF);
    }
    public void Save_PDF_report(){save_PDF_Report(pointsF);}

    public void Menu_draw(){
        DrawGraph(toArrayList(pointsF),toArrayList(pointsG));
    }

    public void Menu_minimum(){
        if(pointsG.size()!=0 && pointsF.size()!=0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Minimum was found in dot х=" + new CW().x_minimum_search(Double.parseDouble(From.getText()), Double.parseDouble(To.getText()), 0.1, toArrayList(pointsF), toArrayList(pointsG)), ButtonType.OK);
            alert.setTitle("Minimum");
            alert.setHeaderText("Information");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill data about dots, before drawing", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText("Function's data is absent");
            alert.showAndWait();
        }
    }
    private void save_HTML_Report(ObservableList<Point> F) {

        WritableImage wim = new WritableImage((int) Graph.getWidth(), (int) Graph.getHeight());
        Graph.snapshot(null, wim);
        int countOfReports = new File(PROJECT_PATH + "/src/reports/HTML/countOfHTMLReports.txt").exists() ? Integer.parseInt(new String(new CW().readMainLanguage(PROJECT_PATH + "/src/reports/HTML/countOfHTMLReports.txt"))) : 0;

        File file = new File(PROJECT_PATH + "/src/reports/HTML/screenshots/screenshot_" + (countOfReports + 1) + ".png");
        CW.writeMainLanguage1((countOfReports + 1) + "", PROJECT_PATH + "/src/reports/HTML/countOfHTMLReports.txt");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE HTML PUBLIC \" -//W3C//DTD HTML 4.01 Transitional//EN\"><html> <head><meta http-equiv=\"Content - Type\" content=\"text / html; charset = windows - 1251\" /><style> h1{text-align:center;}table {border: 2px solid #ccc;margin: 0 auto;}th, td {border: 1px solid black;text - align: center;padding: 1em;}# left, #right {position: fixed;top: 5em;}# left {left: 5em;   }# center { margin: 0 auto;}# right {right: 5em;}# green {background: #81FF7A;}# red {background: #FF5252;}</style></head><body>");

        sb.append("\n<h1>Minimum was found in dot х= "+new CW().x_minimum_search(Double.parseDouble(From.getText()),Double.parseDouble(To.getText()),0.1,toArrayList(F),toArrayList(pointsG))+":</h1>");
        sb.append("\n<h1>Screenshot :</h1>");
        sb.append("<center><img src=\"screenshots/screenshot_" + (countOfReports + 1) + ".png\" alt=\"picture\"></center>");

        sb.append("<TABLE>\n");
        sb.append("<caption>Function's F dots</caption>\n");
        sb.append("<TR>\n");
        sb.append("<TD>");
        sb.append("X");
        sb.append("</TD>");

        sb.append("<TD>");
        sb.append("Y");
        sb.append("</TD>");

        sb.append("</TR>\n");



        for (Point arg : F) {
            sb.append("<TR>\n");
            sb.append("<TD>");
            sb.append(arg.getX());
            sb.append("</TD>");

            sb.append("<TD>");
            sb.append(arg.getY());
            sb.append("</TD>");

            sb.append("</TR>");
        }

        sb.append("</TABLE>");


        sb.append("<TABLE>\n");
        sb.append("<caption>Function's G dots</caption>\n");
        sb.append("<TR>\n");
        sb.append("<TD>");
        sb.append("X");
        sb.append("</TD>");

        sb.append("<TD>");
        sb.append("Y");
        sb.append("</TD>");

        sb.append("</TR>\n");

        for (Point arg : pointsG) {
            sb.append("<TR>\n");
            sb.append("<TD>");
            sb.append(arg.getX());
            sb.append("</TD>");

            sb.append("<TD>");
            sb.append(arg.getY());
            sb.append("</TD>");

            sb.append("</TR>");
        }

        sb.append("</TABLE>");
        sb.append("</body></ html > ");
        Date date = new Date();
        sb.append(date);

        CW.writeMainLanguage(new String(sb), PROJECT_PATH + "/src/reports/HTML/report_" + (countOfReports) + ".html");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The report was successfully saved", ButtonType.OK);
        alert.showAndWait();
        try {
            new ProcessBuilder("cmd", "/c", "start", "src\\reports\\HTML\\report_" + (countOfReports) + ".html").start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void save_PDF_Report(ObservableList<Point> F)
    {
        //create counter & screens
        WritableImage wim = new WritableImage((int) Graph.getWidth(), (int) Graph.getHeight());
        Graph.snapshot(null, wim);
        int countOfReports = new File(PROJECT_PATH + "/src/reports/PDF/countOfPDFReports.txt").exists() ? Integer.parseInt(new String(new CW().readMainLanguage(PROJECT_PATH + "/src/reports/PDF/countOfPDFReports.txt"))) : 0;

        File file = new File(PROJECT_PATH + "/src/reports/PDF/screenshots/screenshot_" + (countOfReports) + ".png");
        CW.writeMainLanguage1((countOfReports + 1) + "", PROJECT_PATH + "/src/reports/PDF/countOfPDFReports.txt");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
//create pdf doc
        Document document=new Document(PageSize.A4, 50, 50, 50, 50);
        try{
            PdfWriter.getInstance(document, new FileOutputStream("src\\reports\\PDF\\report_" + countOfReports + ".pdf" ));
            document.open();

            Anchor anchorTarget = new Anchor("Minimum was found in dot x = " + new CW().x_minimum_search(Double.parseDouble(From.getText()),Double.parseDouble(To.getText()),0.1,toArrayList(F),toArrayList(pointsG)));
            Paragraph paragraph1 = new Paragraph();
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setSpacingBefore(50);
            paragraph1.add(anchorTarget);
            document.add(paragraph1);
//screen

            Paragraph someSectionText = new Paragraph("Screenshot:");
            someSectionText.setAlignment(Element.ALIGN_CENTER);
            document.add(someSectionText);
            Image image2 = Image.getInstance("src\\reports\\PDF\\screenshots\\screenshot_" + countOfReports + ".png");
            image2.setAlignment(Element.ALIGN_CENTER);
            image2.scaleAbsolute(240f, 240f);
            document.add(image2);
//table function F dots
            Paragraph fdots = new Paragraph("Function's F dots");
            fdots.setAlignment(Element.ALIGN_CENTER);
            document.add(fdots);
            PdfPTable t1 = new PdfPTable(2);
            t1.setSpacingBefore(25);
            t1.setSpacingAfter(25);
            PdfPCell c1 = new PdfPCell(new Phrase("X"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            t1.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("Y"));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            t1.addCell(c2);
            for (Point arg : pointsF) {
                t1.addCell(arg.getX() + "");
                t1.addCell(arg.getY() + "");
            }
            document.add(t1);
//table function G dots
            Paragraph gdots = new Paragraph("Function's G dots");
            gdots.setAlignment(Element.ALIGN_CENTER);
            document.add(gdots);
            PdfPTable t = new PdfPTable(2);
            t.setSpacingBefore(25);
            t.setSpacingAfter(25);
            PdfPCell c3 = new PdfPCell(new Phrase("X"));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase("Y"));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(c4);
            for (Point arg : pointsG) {
                t.addCell(arg.getX() + "");
                t.addCell(arg.getY() + "");
            }
            document.add(t);
            Date date = new Date();
            Paragraph data = new Paragraph(date + "");
            document.add(data);
            document.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The report was successfully saved", ButtonType.OK);
            alert.showAndWait();
            try {
                new ProcessBuilder("cmd", "/c", "start", "src\\reports\\PDF\\report_" + countOfReports + ".pdf").start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
    public void menu_about(){
        Alert alert = new Alert(Alert.AlertType.NONE, "This program is an Safronov Maksym's course work. Department For Engineering Physics in NTU KPI", ButtonType.OK);
        alert.setTitle("About");
        alert.showAndWait();
    }
    public void menu_help() {
        try {
            new ProcessBuilder("cmd", "/c", "start", "help\\index.htm").start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
    public class EditingCell extends TableCell<Point, Double> {
        private TextField textField;
        private TableView tableDots;

        public EditingCell(TableView t) {
            tableDots=t;
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            Platform.runLater(() -> {
                textField.requestFocus();
                textField.selectAll();
            });
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem()+"");
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(Double.parseDouble(textField.getText()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                } else if (t.getCode() == KeyCode.TAB) {
                    commitEdit(Double.parseDouble(textField.getText()));
                    getNextColumn(t.isShiftDown());
                }
            });
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue && textField != null) {
                    commitEdit(Double.parseDouble(textField.getText()));
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }

        private void getNextColumn(boolean forward) {

            List<TableColumn<Point, ?>> columns = new ArrayList<>();
            for (TableColumn<Point, ?> column : getTableView().getColumns()) {
                columns.addAll(getLeaves(column));
            }

            int currentIndex = columns.indexOf(getTableColumn());
            int nextIndex = currentIndex;

            if (columns.size() < 2) {
                getTableView().edit(getTableRow().getIndex() + 1, columns.get(0));
                return;
            }

            if (!forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    if (getTableRow().getIndex() + 1 <= getTableView().getItems().size() - 1) {
                        getTableView().edit(getTableRow().getIndex() + 1, columns.get(0));
                        scrollDown(getTableRow().getIndex() + 1);
                    } else {
                        getTableView().edit(getTableRow().getIndex(), columns.get(0));
                    }
                    return;
                }
            } else {
                nextIndex--;
                if (nextIndex < 0) {
                    if (getTableRow().getIndex() - 1 >= 0) {
                        getTableView().edit(getTableRow().getIndex() - 1, columns.get(columns.size() - 1));
                        scrollUp(getTableRow().getIndex() - 1);
                    } else {
                        getTableView().edit(getTableRow().getIndex(), columns.get(columns.size() - 1));
                    }
                    return;
                }
            }
            getTableView().edit(getTableRow().getIndex(), columns.get(nextIndex));
        }

        private List<TableColumn<Point, ?>> getLeaves(TableColumn<Point, ?> root) {
            List<TableColumn<Point, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<Point, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }

        private void scrollUp(int index) {
            TableViewSkin<?> ts = (TableViewSkin<?>) tableDots.getSkin();
            VirtualFlow<?> vf = (VirtualFlow<?>) ts.getChildren().get(1);

            int first = vf.getFirstVisibleCellWithinViewPort().getIndex();
            int last = vf.getLastVisibleCellWithinViewPort().getIndex();

            if (index <= (last + first) / 2) {
                vf.scrollTo(index - (last - first) / 2);

                ts = (TableViewSkin<?>) tableDots.getSkin();
                vf = (VirtualFlow<?>) ts.getChildren().get(1);
                first = vf.getFirstVisibleCellWithinViewPort().getIndex();
                last = vf.getLastVisibleCellWithinViewPort().getIndex();
            }
        }

        private void scrollDown(int index) {
            TableViewSkin<?> ts = (TableViewSkin<?>) tableDots.getSkin();
            VirtualFlow<?> vf = (VirtualFlow<?>) ts.getChildren().get(1);

            int first = vf.getFirstVisibleCellWithinViewPort().getIndex();
            int last = vf.getLastVisibleCellWithinViewPort().getIndex();

            if (index >= (last + first) / 2) {
                vf.scrollTo(index - ((last - first) / 2));

                ts = (TableViewSkin<?>) tableDots.getSkin();
                vf = (VirtualFlow<?>) ts.getChildren().get(1);
                first = vf.getFirstVisibleCellWithinViewPort().getIndex();
                last = vf.getLastVisibleCellWithinViewPort().getIndex();
            }
        }
    }
}

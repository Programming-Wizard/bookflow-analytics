package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class bookFlowAnalyticsController implements Initializable {

	@FXML
	private VBox rootVBox;
	@FXML
	private BarChart<String, Number> readsGraph;
	@FXML
	public ImageView backToMainWindow;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			CategoryAxis xAxis = new CategoryAxis();
			NumberAxis yAxis = new NumberAxis(0,5,0.5);
			xAxis.setAnimated(false);
			yAxis.setAnimated(false);	
			readsGraph.setAnimated(true);
			xAxis.setLabel("Books");
			yAxis.setLabel("Rating");
			
			XYChart.Series<String, Number> series = new XYChart.Series<>();
			series.getData().add(new XYChart.Data<>("Book A", 1.5));
			series.getData().add(new XYChart.Data<>("Book B", 5));
			series.getData().add(new XYChart.Data<>("Book C", 4.2));
			series.getData().add(new XYChart.Data<>("Book D", 3.6));
			series.getData().add(new XYChart.Data<>("Book E", 6.6));
			series.getData().add(new XYChart.Data<>("Book F", 7.6));
			series.getData().add(new XYChart.Data<>("Book G", 3.9));
			series.getData().add(new XYChart.Data<>("Book H", 5.4));
			series.getData().add(new XYChart.Data<>("Book I", 7.5));
			series.getData().add(new XYChart.Data<>("Book J", 7.9));
			series.getData().add(new XYChart.Data<>("Book K", 6.4));
			series.getData().add(new XYChart.Data<>("Book L", 4.8));
			series.getData().add(new XYChart.Data<>("Book M", 6.6));
			series.getData().add(new XYChart.Data<>("Book N", 8.6));
			series.getData().add(new XYChart.Data<>("Book O", 7.7));
			
			readsGraph.setPrefHeight(900);


			readsGraph.getData().add(series);
	}
	
	
}

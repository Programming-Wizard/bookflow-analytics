package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class bookFlowAnalyticsController implements Initializable {

	@FXML
	private VBox rootVBox;
	@FXML
	private BarChart<String, Number> readsGraph;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			CategoryAxis xAxis = new CategoryAxis();
			NumberAxis yAxis = new NumberAxis(0,5,0.5);
			xAxis.setAnimated(false);
			yAxis.setAnimated(false);	
			readsGraph.setAnimated(true);
			xAxis.setLabel("Label");
			yAxis.setLabel("Reads");
			
			XYChart.Series<String, Number> series = new XYChart.Series<>();
			series.getData().add(new XYChart.Data<>("Book A", 1.5));
			series.getData().add(new XYChart.Data<>("Book B", 5));
			series.getData().add(new XYChart.Data<>("Book C", 4.2));
			series.getData().add(new XYChart.Data<>("Book D", 3.6));
			
			readsGraph.setPrefHeight(900);
			
			readsGraph.getData().add(series);
			
	}
	
}

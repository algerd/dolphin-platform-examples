package com.guigarage.view;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import com.canoo.dolphin.client.javafx.binding.FXWrapper;
import com.canoo.dolphin.client.javafx.view.AbstractFXMLViewBinder;
import com.guigarage.Constants;
import com.guigarage.model.ChartData;
import com.guigarage.model.ChartModel;
import java.util.function.Function;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartViewBinder extends AbstractFXMLViewBinder<ChartModel> {

    private final Logger LOGGER = LoggerFactory.getLogger(ChartViewBinder.class);
    
    @FXML
    private ChoiceBox<String> countrySelection;

    @FXML
    private BarChart<String, Integer> chart;

    public ChartViewBinder(ClientContext clientContext) {
        super(clientContext, Constants.CONTROLLER_NAME, ChartViewBinder.class.getResource("view.fxml"));
    }

    @Override
    protected void init() {
        try {
            //Configure UI
            XYChart.Series<String, Integer> townCountSeries = new XYChart.Series<>();
            chart.dataProperty().get().add(townCountSeries);
            chart.setLegendVisible(false);
            
            //Bind UI to Dolphin Platform Model
            FXBinder.bind(townCountSeries.getData()).to(getModel().getChartData(), new DataConverter());
            FXBinder.bind(countrySelection.valueProperty()).bidirectionalTo(getModel().selectedCountryProperty());
            countrySelection.setItems(FXWrapper.wrapList(getModel().getCountries()));
        } catch (Exception e) {
            LOGGER.error("Can not initialize ChartViewBinder: {}", e);
        }
    }

    private class DataConverter implements Function<ChartData, XYChart.Data<String, Integer>> {
        
        @Override
        public XYChart.Data<String, Integer> apply(ChartData value) {
            XYChart.Data<String, Integer> data = new XYChart.Data(value.getCategory(), value.getValue());
            value.categoryProperty().onChanged(e -> data.setXValue(value.getCategory()));
            value.valueProperty().onChanged(e -> data.setYValue(value.getValue()));
            return data;
        }
    }

}

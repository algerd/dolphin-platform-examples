package com.guigarage.view;

import com.canoo.dolphin.client.ClientContext;
import com.canoo.dolphin.client.javafx.binding.BidirectionalConverter;
import com.canoo.dolphin.client.javafx.binding.FXBinder;
import com.canoo.dolphin.client.javafx.binding.FXWrapper;
import com.canoo.dolphin.client.javafx.view.AbstractFXMLViewBinder;
import com.guigarage.Constants;
import com.guigarage.util.FXWrapper2;
import com.guigarage.model.ChartData;
import com.guigarage.model.ChartModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;

public class ChartViewBinder extends AbstractFXMLViewBinder<ChartModel> {

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
            XYChart.Series<String, Integer> townCountSeries = new XYChart.Series<>();
            chart.dataProperty().get().add(townCountSeries);
            chart.setLegendVisible(false);
            townCountSeries.setData(FXWrapper2.wrapList(getModel().getChartData(), new DataConverter()));

            FXBinder.bind(countrySelection.valueProperty()).bidirectionalTo(getModel().selectedCountryProperty());
            countrySelection.setItems(FXWrapper.wrapList(getModel().getCountries()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DataConverter implements BidirectionalConverter<ChartData, XYChart.Data<String, Integer>> {

        @Override
        public ChartData convertBack(XYChart.Data<String, Integer> value) {
            throw new RuntimeException("DP API ERROR!");
        }

        @Override
        public XYChart.Data<String, Integer> convert(ChartData value) {
            XYChart.Data<String, Integer> data = new XYChart.Data(value.getCategory(), value.getValue());
            value.categoryProperty().onChanged(e -> data.setXValue(value.getCategory()));
            value.valueProperty().onChanged(e -> data.setYValue(value.getValue()));
            return data;
        }
    }

}

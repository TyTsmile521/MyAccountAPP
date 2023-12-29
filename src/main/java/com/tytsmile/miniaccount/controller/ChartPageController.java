package com.tytsmile.miniaccount.controller;

import com.tytsmile.miniaccount.StartUp;
import com.tytsmile.miniaccount.databases.DAO.RecordDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class ChartPageController implements Initializable{

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private HBox legendBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        xAxis.setLabel("日期");
        yAxis.setLabel("金额（元）");

        RecordDAO recordDAO = new RecordDAO();
        double[] dailyExpenses = recordDAO.getDailyExpenses();
        double[] dailyIncomes = recordDAO.getDailyIncomes();

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        ObservableList<XYChart.Data<String, Number>> incomeData = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<String, Number>> expenseData = FXCollections.observableArrayList();

        // 获取当前年份和月份
        YearMonth currentYearMonth = YearMonth.now();
        // 获取当前月份的天数
        int daysInMonth = currentYearMonth.lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            String day = String.valueOf(i);
            double dailyExpense = dailyExpenses[i - 1];
            double dailyIncome = dailyIncomes[i - 1];
            expenseData.add(new XYChart.Data<>(day, dailyExpense));
            incomeData.add(new XYChart.Data<>(day, dailyIncome));
        }

        incomeSeries.setName("收入");
        expenseSeries.setName("支出");
        incomeSeries.setData(incomeData);
        expenseSeries.setData(expenseData);

        barChart.getData().addAll(incomeSeries, expenseSeries);
        barChart.setCategoryGap(0.5);
        barChart.setBarGap(0);
        barChart.setLegendVisible(false);

        // 设置柱形颜色
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            String seriesName = series.getName();
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node bar = data.getNode();
                if (seriesName.equals("支出")) {
                    bar.setStyle("-fx-bar-fill: #a27e7e;"); // 支出的柱形颜色
                } else if (seriesName.equals("收入")) {
                    bar.setStyle("-fx-bar-fill: #96a48b;"); // 收入的柱形颜色
                }
            }
        }

        // 创建自定义图例
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            Rectangle legendRect = new Rectangle(10, 10); // 自定义图例项，可以根据需要设置大小
            if (series.getName().equals("支出")) {
                legendRect.setFill(Color.web("#a27e7e")); // 支出的颜色
            } else if (series.getName().equals("收入")) {
                legendRect.setFill(Color.web("#96a48b")); // 收入的颜色
            }
            Label legendLabel = new Label(series.getName());
            HBox legendItem = new HBox(legendRect, legendLabel);
            legendItem.setAlignment(Pos.CENTER_LEFT); // 根据需要设置图例项的布局
            legendItem.setSpacing(5); // 根据需要设置图例项的间距
            legendBox.getChildren().add(legendItem); // 将自定义图例项添加到 HBox 中
        }

    }






    @FXML
    public void turnToOverview(){
        StartUp.changeView("view/OverviewPage.fxml");
    }
    @FXML
    public void turnToAccount(){
        AccountPageController.changeId=-1;
        StartUp.changeView("view/AccountPage.fxml");
    }
    @FXML
    public void turnToBill(){
        StartUp.changeView("view/BillPage.fxml");
    }
    @FXML
    public void turnToChart(){
       // StartUp.changeView("view/ChartPage.fxml");
    }
}

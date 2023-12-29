package com.tytsmile.miniaccount.controller;

import com.tytsmile.miniaccount.util.tipsUtil;
import com.tytsmile.miniaccount.StartUp;
import com.tytsmile.miniaccount.databases.DAO.RecordDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OverviewPageController implements Initializable {

    @FXML
    private Label sumOut;
    @FXML
    private Label sumIn;
    @FXML
    private Label sumClearOut;
    @FXML
    private Label everydayOut;
    @FXML
    private Label sumClearOutWord;
    @FXML
    private AnchorPane monthlyExpenseBar;
    @FXML
    private AnchorPane monthlyIncomeBar;
    @FXML
    private AnchorPane monthlyClearExpenseBar;
    @FXML
    private Label tip;
    @FXML
    public void initialize(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RecordDAO recordDAO = new RecordDAO();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // 获取当前月份的总支出
        double totalExpense = recordDAO.getMonthlyExpenses();
        String formattedTotalExpense = String.format("%.2f",totalExpense);
        sumOut.setText(formattedTotalExpense+"元");
        sumOut.setTextFill(Color.web("#965454"));

        // 获取当前月份的总收入
        double totalIncome = recordDAO.getMonthlyIncomes();
        String formattedTotalIncome = String.format("%.2f",totalIncome);
        sumIn.setText(formattedTotalIncome+"元");
        sumIn.setTextFill(Color.web("#7b8b6f"));

        // 获取当前月份的净支出
        double totalClearExpense = totalExpense-totalIncome;
        if(totalClearExpense<0){
            totalClearExpense*=-1;
            sumClearOutWord.setText("净收入");
            sumClearOut.setTextFill(Color.web("#7b8b6f"));

        }
        else{
            sumClearOutWord.setText("净支出");
            sumClearOut.setTextFill(Color.web("#965454"));
        }
        String formattedTotalClearExpense = String.format("%.2f", totalClearExpense);
        sumClearOut.setText(formattedTotalClearExpense+"元");

        //计算每日支出
        LocalDate currentDate = LocalDate.now();
        int dayOfMonth = currentDate.getDayOfMonth();
        String formattedEverydayOut = decimalFormat.format(totalExpense/dayOfMonth);
        everydayOut.setText(formattedEverydayOut+"元");
        everydayOut.setTextFill(Color.web("#965454"));

        //图形相关计算

        sumOut.setMinWidth(sumOut.getText().length() * 20);
        sumOut.setPrefWidth(sumOut.getText().length() * 20);
        sumOut.setMaxWidth(sumOut.getText().length() * 20);

        sumIn.setMinWidth(sumIn.getText().length() * 20);
        sumIn.setPrefWidth(sumIn.getText().length() * 20);
        sumIn.setMaxWidth(sumIn.getText().length() * 20);

        sumClearOut.setMinWidth(sumClearOut.getText().length() * 20);
        sumClearOut.setPrefWidth(sumClearOut.getText().length() * 20);
        sumClearOut.setMaxWidth(sumClearOut.getText().length() * 20);

        everydayOut.setMinWidth(everydayOut.getText().length() * 20);
        everydayOut.setPrefWidth(everydayOut.getText().length() * 20);
        everydayOut.setMaxWidth(everydayOut.getText().length() * 20);

        double maxNumLength=0.0;
        if( sumOut.getMaxWidth() > sumIn.getMaxWidth() ){
            maxNumLength = sumOut.getMaxWidth();
        }
        else{
            maxNumLength = sumIn.getMaxWidth();
        }
        monthlyExpenseBar.setLayoutX(maxNumLength+197);
        monthlyIncomeBar.setLayoutX(maxNumLength+197);
        monthlyClearExpenseBar.setLayoutX(maxNumLength+197);

        if(totalExpense>totalIncome){
            monthlyExpenseBar.setPrefWidth(286);
            monthlyIncomeBar.setPrefWidth(286*totalIncome/totalExpense);
            monthlyClearExpenseBar.setPrefWidth(286-286*totalIncome/totalExpense);
            monthlyClearExpenseBar.setStyle("-fx-background-color: #965454;");
        }
        else{
            monthlyIncomeBar.setPrefWidth(286);
            monthlyExpenseBar.setPrefWidth(286*totalExpense/totalIncome);
            monthlyClearExpenseBar.setPrefWidth(286-286*totalExpense/totalIncome);
            monthlyClearExpenseBar.setStyle("-fx-background-color: #7b8b6f;");
        }

        //tips相关
        String randomTip = tipsUtil.getRandomTip();
        tip.setText("记账小TIP：" + randomTip);
    }
    @FXML
    public void turnToOverview(){
      //  StartUp.changeView("view/OverviewPage.fxml");
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
        StartUp.changeView("view/ChartPage.fxml");
    }

}

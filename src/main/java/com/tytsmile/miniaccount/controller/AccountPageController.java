package com.tytsmile.miniaccount.controller;

import com.tytsmile.miniaccount.databases.DAO.RecordDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.time.LocalDate;

import static com.tytsmile.miniaccount.StartUp.changeView;
import static com.tytsmile.miniaccount.util.openNewWindowUtil.openNewWindow;


public class AccountPageController  {

    public static int changeId=-1;
    public static String changemoney=null;
    public static String changeclassify=null;
    public static String changecomment=null;
    public static LocalDate changedate=null;
    public static boolean changedincomeflag=false;

    //<0为新增，>0为修改
    @FXML
    private TextField money;
    @FXML
    private DatePicker date;
    @FXML
    private TextField classify;
    @FXML
    private TextField comment;
    @FXML
    private CheckBox incomeflag;
    @FXML
    private Button goin;

    @FXML
    public void accountIn() throws Exception {

        String moneyText = money.getText();
        double moneyValue=0.0;
        LocalDate selectedDate = date.getValue();
        String classifyText = classify.getText();
        String commitText=comment.getText();


        //判断金额是否合法
        try {
            moneyValue = Double.parseDouble(moneyText);
            //判断小数位数
            int decimalPlaces = countDecimalPlaces(moneyValue);
            if (decimalPlaces > 2)  {
                // 小数位数超过两位
                openNewWindow("view/WrongMoneyPage.fxml");
                return;
            }
            //判断收入/支出
            if(incomeflag.isSelected()) {
                moneyValue*=-1;
            }
        }catch (NumberFormatException e){
            openNewWindow("view/WrongMoneyPage.fxml");
            return;
        }
        //判断日期是否合法
        if (selectedDate == null ) {
            openNewWindow("view/WrongDatePage.fxml");
            return;
        }

        RecordDAO recordDAO = new RecordDAO();
        //新增
        if(changeId<0) {
            recordDAO.addRecord(moneyValue, classifyText, commitText, Date.valueOf(selectedDate));
            openNewWindow("view/SuccessAccountPage.fxml");
        }
        //修改
        else if(changeId>0) {
            recordDAO.updateRecord(changeId,moneyValue, classifyText, commitText, Date.valueOf(selectedDate));
            openNewWindow("view/SuccessChangePage.fxml");
        }
        changeId=-1;
        changedincomeflag=false;
        // 在新窗口关闭后，返回主界面
        changeView("view/OverviewPage.fxml");

    }
    private int countDecimalPlaces(double value) {
        String valueStr = String.valueOf(value);
        int integerPlaces = valueStr.indexOf('.');
        return integerPlaces < 0 ? 0 : valueStr.length() - integerPlaces - 1;
    }


    @FXML
    public void initialize(){
        if(changeId>0){
            goin.setText("修改");
            money.setText(changemoney);
            date.setValue(changedate);
            classify.setText(changeclassify);
            comment.setText(changecomment);
            if(changedincomeflag==true){
                incomeflag.setSelected(true);
            }
        }
    }
    @FXML
    public void turnToOverview(){
        changeView("view/OverviewPage.fxml");
    }
    @FXML
    public void turnToAccount(){
        //AccountPageController.changeId=-1;
        //changeView("view/AccountPage.fxml");
    }
    @FXML
    public void turnToBill(){
        changeView("view/BillPage.fxml");
    }
    @FXML
    public void turnToChart(){
        changeView("view/ChartPage.fxml");
    }
}

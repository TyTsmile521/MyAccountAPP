package com.tytsmile.miniaccount.controller;

import com.tytsmile.miniaccount.StartUp;
import com.tytsmile.miniaccount.databases.DAO.RecordDAO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.tytsmile.miniaccount.databases.entity.Record;
import javafx.util.Callback;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import static com.tytsmile.miniaccount.util.openNewWindowUtil.openNewWindow;

public class BillPageController {
    @FXML
    private TableView<Record> recordTableView;
    @FXML
    private TableColumn<Record, Integer> idColumn;
    @FXML
    private TableColumn<Record, String> incomeColumn;
    @FXML
    private TableColumn<Record, Double> moneyColumn;
    @FXML
    private TableColumn<Record, String> classifyColumn;
    @FXML
    private TableColumn<Record, String> commentColumn;
    @FXML
    private TableColumn<Record, Date> dateColumn;

    // 实例化 RecordDAO 对象
    private RecordDAO recordDAO = new RecordDAO();

    public void initialize() {
        // 设置每列的单元格值
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        moneyColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMoney()).asObject());
        classifyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassify()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));
        dateColumn.setCellValueFactory(cellData -> {
            Record record = cellData.getValue();
            SimpleObjectProperty<Date> property = new SimpleObjectProperty<>(record.getDate());
            return property;
        });
        // 设置incomeColumn的单元格值
        incomeColumn.setCellValueFactory(cellData -> {
            Record record = cellData.getValue();
            String incomeType = record.getMoney() >= 0 ? "支出" : "收入";
            return new SimpleStringProperty(incomeType);
        });
        // 设置moneyColumn的单元格值
        moneyColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMoney()).asObject());
        moneyColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Record, Double> call(TableColumn<Record, Double> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            // 如果money为负数，将其变为绝对值
                            double moneyValue = item;
                            if (moneyValue < 0) {
                                moneyValue = Math.abs(moneyValue);
                            }
                            // 格式化为两位小数的金额格式
                            String formattedMoney = String.format("%.2f", moneyValue);
                            setText(formattedMoney);
                        }
                    }
                };
            }
        });
        // 加载数据库中的记录到 TableView
        loadRecordsFromDB();
    }

    // 从数据库加载数据到 TableView
    private void loadRecordsFromDB() {
        List<Record> records = recordDAO.getAllRecords();

        // 将获取到的记录集合设置到 TableView 中
        recordTableView.getItems().addAll(records);
    }


    @FXML
    public void add1(){
        AccountPageController.changeId=-1;
        StartUp.changeView("view/AccountPage.fxml");
    }
    @FXML
    public void change1(){
        Record selectedRecord = recordTableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            //获取该行数据
            int id = selectedRecord.getId();
            double money=selectedRecord.getMoney();
            if(money<0){
                money*=-1;
                AccountPageController.changedincomeflag=true;
            }
            String moneystr=String.valueOf(money);
            String classify = selectedRecord.getClassify();
            String comment = selectedRecord.getComment();
            LocalDate date = selectedRecord.getDate().toLocalDate();

            AccountPageController.changeId=id;
            AccountPageController.changemoney=moneystr;
            AccountPageController.changeclassify=classify;
            AccountPageController.changecomment=comment;
            AccountPageController.changedate=date;
            StartUp.changeView("view/AccountPage.fxml");
            System.out.println("执行修改操作");
        }
        else{
            openNewWindow("view/WrongSelectPage.fxml");
            return;
        }
    }

    // 删除按钮点击事件处理方法
    @FXML
    private void delete1(ActionEvent event) {
        Record selectedRecord = recordTableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            int id = selectedRecord.getId();

            // 调用数据库操作方法进行删除
            recordDAO.deleteRecord(id);
            System.out.println("执行删除操作");
            StartUp.changeView("view/BillPage.fxml");
        }
        else{
            openNewWindow("view/WrongSelectPage.fxml");
            return;
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
       // StartUp.changeView("view/BillPage.fxml");
    }
    @FXML
    public void turnToChart(){
        StartUp.changeView("view/ChartPage.fxml");
    }
}

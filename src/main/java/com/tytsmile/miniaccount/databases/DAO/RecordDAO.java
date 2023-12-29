package com.tytsmile.miniaccount.databases.DAO;

import com.tytsmile.miniaccount.databases.entity.Record;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
public class RecordDAO {
    private Connection connect() {
        // SQLite 数据库连接信息
        String url = "jdbc:sqlite:src/main/java/com/tytsmile/miniaccount/db/aaa.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void addRecord(double money, String classify, String comment, Date date) {
        String sql = "INSERT INTO records(money, classify, comment, date) VALUES(?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, money);
            pstmt.setString(2, classify);
            pstmt.setString(3, comment);
            pstmt.setDate(4, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRecord(int id, double money, String classify, String comment, Date date) {
        String sql = "UPDATE records SET money = ?, classify = ?, comment = ?, date = ? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, money);
            pstmt.setString(2, classify);
            pstmt.setString(3, comment);
            pstmt.setDate(4, date);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(int id) {
        String sql = "DELETE FROM records WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Record> getAllRecords() {
        String sql = "SELECT * FROM records";
        List<Record> records = new ArrayList<>();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                String classify = rs.getString("classify");
                String comment = rs.getString("comment");
                Date date = rs.getDate("date");

                Record record = new Record(id, money, classify, comment, date);
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return records;
    }

    // 获取所有账单的金额并计算总额
    public double getTotalAmount() {
        double totalAmount = 0.0;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT money FROM records")) {

            while (rs.next()) {
                totalAmount += rs.getDouble("money");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return totalAmount;
    }


    // 获取总支出
    public double getMonthlyExpenses() {
        double monthlyTotal = 0.0;

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT money, date FROM records")) {

            LocalDate currentDate = LocalDate.now();
            while (rs.next()) {
                Date date = rs.getDate("date");
                LocalDate recordDate = date.toLocalDate();

                if (recordDate.getYear() == currentDate.getYear() &&
                        recordDate.getMonth() == currentDate.getMonth() ) {
                    double money = rs.getDouble("money");
                    if(money>0){
                        monthlyTotal += money;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return monthlyTotal;
    }

    //获取总收入
    public double getMonthlyIncomes() {
        double monthlyTotal = 0.0;

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT money, date FROM records")) {

            LocalDate currentDate = LocalDate.now();
            while (rs.next()) {
                Date date = rs.getDate("date");
                LocalDate recordDate = date.toLocalDate();

                if (recordDate.getYear() == currentDate.getYear() &&
                        recordDate.getMonth() == currentDate.getMonth()) {
                    double money = rs.getDouble("money");
                    if(money<0){
                        monthlyTotal -= money;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return monthlyTotal;
    }

    public double[] getDailyExpenses() {
        // 获取当前年份和月份
        YearMonth currentYearMonth = YearMonth.now();
        // 获取当前月份的天数
        int daysInMonth = currentYearMonth.lengthOfMonth();
        double[] dailyExpenses = new double[daysInMonth];
        LocalDate currentDate = LocalDate.now();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT money, date FROM records")) {

            while (rs.next()) {
                Date date = rs.getDate("date");
                double money = rs.getDouble("money");
                LocalDate recordDate = date.toLocalDate();

                if (recordDate.getYear() == currentDate.getYear() &&
                        recordDate.getMonth() == currentDate.getMonth() &&
                            money > 0 ) {
                    int dayOfMonth = recordDate.getDayOfMonth();
                    dailyExpenses[dayOfMonth - 1] += money; // 累加每天的支出
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dailyExpenses;
    }
    public double[] getDailyIncomes() {
        // 获取当前年份和月份
        YearMonth currentYearMonth = YearMonth.now();
        // 获取当前月份的天数
        int daysInMonth = currentYearMonth.lengthOfMonth();
        double[] dailyExpenses = new double[daysInMonth];
        LocalDate currentDate = LocalDate.now();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT money, date FROM records")) {

            while (rs.next()) {
                Date date = rs.getDate("date");
                double money = rs.getDouble("money");
                LocalDate recordDate = date.toLocalDate();

                if (recordDate.getYear() == currentDate.getYear() &&
                        recordDate.getMonth() == currentDate.getMonth() &&
                            money < 0) {
                    int dayOfMonth = recordDate.getDayOfMonth();
                    dailyExpenses[dayOfMonth - 1] -= money; // 累加每天的收入
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dailyExpenses;
    }
}

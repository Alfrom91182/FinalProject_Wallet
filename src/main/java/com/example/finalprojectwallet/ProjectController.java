package com.example.finalprojectwallet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonArray;
import javafx.scene.control.TextField;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectController {
    @FXML private TextArea textArea1;
    @FXML private TextField Amount;
    @FXML private TextField Notes;
    @FXML private DatePicker DateOp;
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private TextField Available;
    @FXML private TextField Keyword;
    @FXML private PieChart Pie;
    @FXML private BarChart Bars;
    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;

    ProjectModel m = new ProjectModel();
    ProjectModel.tempJson j = m.new tempJson();
    JsonObject doc = m.readJson("./resources/wallet_sample.json");
    String original = null;


    public void incomeSubmitButtonAction(ActionEvent actionEvent){
        JsonArray income = (JsonArray) this.doc.get("income");
        JsonObject temp = j.tempJson();
        jsonIni(temp,income.size()+1);
        income.add(temp);
    }
    public void expenseSubmitButtonAction(ActionEvent actionEvent) {
        JsonArray expense = (JsonArray) this.doc.get("expense");
        JsonObject temp = j.tempJson();
        jsonIni(temp,expense.size()+1);
        expense.add(temp);
    }
    public JsonObject jsonIni (JsonObject jsonUser, int size)
    {
        jsonUser.replace("id",size);
        jsonUser.replace("amount",Amount.getText());
        String date = String.valueOf(DateOp.getValue());
        jsonUser.replace("date",date);
        jsonUser.replace("notes",Notes.getText());
        Amount.clear();
        Notes.clear();
        return jsonUser;
    }
    public void availableSubmitButtonAction(ActionEvent actionEvent) {
        Double incomes=0.0;
        Double expenses=0.0;
        Double available =0.0;

        JsonArray income = (JsonArray) this.doc.get("income");

        for (Object incom : income) {
            JsonObject temp = (JsonObject) incom;
            incomes += Double.parseDouble(String.valueOf(temp.get("amount")));
        }

        JsonArray expense = (JsonArray) this.doc.get("expense");
        for (Object expen : expense) {
            JsonObject temp = (JsonObject) expen;
            expenses += Double.parseDouble(String.valueOf(temp.get("amount")));
        }
        available = incomes-expenses;
        Available.setText("$"+available);
        pieChart(doc);
    }
    public void searchSubmitButtonAction(ActionEvent actionEvent) {
        int counter = 0;
        textArea1.clear();
        textArea1.appendText("\n"+"Income:"+"\n");
        JsonArray income = (JsonArray) this.doc.get("income");
        counter+=search ((JsonArray) this.doc.get("income"));

        textArea1.appendText("\n");
        textArea1.appendText("Expenses:"+"\n");
        JsonArray expense = (JsonArray) this.doc.get("expense");
        counter+=search ((JsonArray) this.doc.get("expense"));

        if (counter == 0)
            textArea1.appendText("No transaction were found" + "\n");

    }

    public int search (JsonArray arrayTemp)
    {   int counter = 0;
        for (Object tempJ : arrayTemp) {
            JsonObject temp = (JsonObject) tempJ;
            LocalDate jsonDate = LocalDate.parse((String) temp.get("date"));
            LocalDate dateFrom = fromDate.getValue() == null ? LocalDate.parse("0000-01-01") : fromDate.getValue();
            LocalDate dateTo = toDate.getValue() == null ? LocalDate.parse("0000-01-01") : toDate.getValue();
            ArrayList<String> arrL = new ArrayList<String>();

            if (jsonDate.isAfter(dateFrom) && jsonDate.isBefore(dateTo)||(temp.get("notes").toString()).equals(Keyword.getText())){
                arrL.add(String.valueOf(temp).substring(1, String.valueOf(temp).length() - 1).toUpperCase().replace(","," ") + "\n");
                counter++;
            }
            arrL.forEach(n -> textArea1.appendText(n));  //Lambda Expression
        }
        return counter;
    }

    public void saveSubmitButtonAction(ActionEvent actionEvent)
    {
        String toWrite = Jsoner.serialize(this.doc);
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("./resources/wallet_sample.json", false));
            out.write(toWrite);
            out.close();
        }

        //  Handle the exceptions
        catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
        this.doc = m.readJson("./resources/wallet_sample.json");
        pieChart(doc);
    }

    public void pieChart (JsonObject Data)
    {
        Pie.setData(m.pieInfo(this.doc));
        Pie.setTitle("Income vs. Expenses");
    }


}
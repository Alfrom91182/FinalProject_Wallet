package com.example.finalprojectwallet;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.io.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class ProjectModel {

    String jsonTemp;
    String original = null;
    public class tempJson {   //Inner Class
        String jsonTemp;

        public JsonObject tempJson() {
            this.jsonTemp = "{"
                    + "\"date\":\"2022-01-01\","
                    + " \"amount\":\"0000.00\","
                    + " \"notes\":\"None\","
                    + " \"id\":\"0\","
                    + " }";
            return Jsoner.deserialize(jsonTemp, new JsonObject());
        }
    }
    public String getDate(){return "0000-01-01";}

    public JsonObject readJson(String filename) {
        return (JsonObject) Jsoner.deserialize(readFile(filename), new JsonObject());
    }

    public String readFile(String filename) {
        String contents = "";
        try (InputStream in = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                contents += new String(buffer, 0, lengthRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        this.original=contents;
        return contents;
    }

    public  ObservableList<PieChart.Data> pieInfo (JsonObject info) {
        JsonArray incomes = (JsonArray) info.get("income");
        JsonArray expenses = (JsonArray) info.get("expense");
        ObservableList<PieChart.Data> pieData=
                FXCollections.observableArrayList(
                        new PieChart.Data("Income",incomes.size()),
                        new PieChart.Data("Expenses",expenses.size())
                );
        return pieData;
    }

}
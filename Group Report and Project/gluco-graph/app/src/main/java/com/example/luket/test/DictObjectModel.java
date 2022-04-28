package com.example.luket.test;

public class DictObjectModel {

    String FoodName, Fat, Energy, TotalSugar, Glucose;

    public DictObjectModel(String FoodName, String Fat, String Energy , String TotalSugar , String Glucose){

        this.FoodName= FoodName;
        this.Fat = Fat;
        this.Energy = Energy;
        this.TotalSugar = TotalSugar;
        this.Glucose = Glucose;


    }
    public  String getFoodName()
    {
        return FoodName;
    }
    public  String getFat()
    {
        return Fat;
    }
    public  String getEnergy()
    {
        return Energy;
    }
    public  String getTotalSugar(){return TotalSugar;}
    public  String getGlucose()
    {
        return Glucose;
    }







}

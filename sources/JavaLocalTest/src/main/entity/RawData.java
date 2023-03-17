package main.entity;

//import javafx.beans.binding.ObjectExpression;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

public class RawData extends Entity {
    private String Item_Name;
    private String Item_Type;
    private Double Item_Price;
    private String Retrieval_City;
    private Date Retrieval_Start_Time;
    private String Retrieval_Courier;
    private String Retrieval_Courier_Gender;
    private String Retrieval_Courier_Phone_Number;
    private Integer Retrieval_Courier_Age;
    private Date Delivery_Finished_Time;
    private String Delivery_City;
    private String Delivery_Courier;
    private String Delivery_Courier_Gender;
    private String Delivery_Courier_Phone_Number;
    private Integer Delivery_Courier_Age;
    private String Item_Export_City;
    private Double Item_Export_Tax;
    private Date Item_Export_Time;
    private String Item_Import_City;
    private Double Item_Import_Tax;
    private Date Item_Import_Time;
    private String Container_Code;
    private String Container_Type;
    private String Ship_Name;
    private String Company_Name;
    private Timestamp Log_Time;

    public RawData() {
    }

    public RawData(String item_Name, String item_Type, Double item_Price, String retrieval_City, Date retrieval_Start_Time, String retrieval_Courier, String retrieval_Courier_Gender, String retrieval_Courier_Phone_Number, Integer retrieval_Courier_Age, Date delivery_Finished_Time, String delivery_City, String delivery_Courier, String delivery_Courier_Gender, String delivery_Courier_Phone_Number, Integer delivery_Courier_Age, String item_Export_City, Double item_Export_Tax, Date item_Export_Time, String item_Import_City, Double item_Import_Tax, Date item_Import_Time, String container_Code, String container_Type, String ship_Name, String company_Name, Timestamp log_Time) {
        Item_Name = item_Name;
        Item_Type = item_Type;
        Item_Price = item_Price;
        Retrieval_City = retrieval_City;
        Retrieval_Start_Time = retrieval_Start_Time;
        Retrieval_Courier = retrieval_Courier;
        Retrieval_Courier_Gender = retrieval_Courier_Gender;
        Retrieval_Courier_Phone_Number = retrieval_Courier_Phone_Number;
        Retrieval_Courier_Age = retrieval_Courier_Age;
        Delivery_Finished_Time = delivery_Finished_Time;
        Delivery_City = delivery_City;
        Delivery_Courier = delivery_Courier;
        Delivery_Courier_Gender = delivery_Courier_Gender;
        Delivery_Courier_Phone_Number = delivery_Courier_Phone_Number;
        Delivery_Courier_Age = delivery_Courier_Age;
        Item_Export_City = item_Export_City;
        Item_Export_Tax = item_Export_Tax;
        Item_Export_Time = item_Export_Time;
        Item_Import_City = item_Import_City;
        Item_Import_Tax = item_Import_Tax;
        Item_Import_Time = item_Import_Time;
        Container_Code = container_Code;
        Container_Type = container_Type;
        Ship_Name = ship_Name;
        Company_Name = company_Name;
        Log_Time = log_Time;
    }

    public RawData(String[] data) {
        try {
            Item_Name = data[0];
            Item_Type = data[1];
            Item_Price = Double.parseDouble(data[2]);
            Retrieval_City = data[3];
            Retrieval_Start_Time = Date.valueOf(data[4]);
            Retrieval_Courier = data[5];
            Retrieval_Courier_Gender = data[6];
            Retrieval_Courier_Phone_Number = data[7];
            Retrieval_Courier_Age = Integer.parseInt(data[8]);
            Delivery_Finished_Time = data[9].equals("") ? null : Date.valueOf(data[9]);
            Delivery_City = data[10];
            Delivery_Courier = data[11].equals("") ? null : data[11];
            Delivery_Courier_Gender = data[12].equals("") ? null : data[12];
            Delivery_Courier_Phone_Number = data[13].equals("") ? null : data[13];
            Delivery_Courier_Age = data[14].equals("") ? null : (int) Double.parseDouble(data[14]);
            Item_Export_City = data[15];
            Item_Export_Tax = data[16].equals("") ? null : Double.parseDouble(data[16]);
            Item_Export_Time = data[17].equals("") ? null : Date.valueOf(data[17]);
            Item_Import_City = data[18];
            Item_Import_Tax = data[19].equals("") ? null : Double.parseDouble(data[19]);
            Item_Import_Time = data[20].equals("") ? null : Date.valueOf(data[20]);
            Container_Code = data[21].equals("") ? null : data[21];
            Container_Type = data[22].equals("") ? null : data[22];
            Ship_Name = data[23].equals("") ? null : data[23];
            Company_Name = data[24];
            Log_Time = Timestamp.valueOf(data[25]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array index out of bounds when load data into table raw_data");
            e.printStackTrace();
            System.exit(2);
        }
    }

    public RawData(String data) {
        new RawData(data.split(","));
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getItem_Type() {
        return Item_Type;
    }

    public void setItem_Type(String item_Type) {
        Item_Type = item_Type;
    }

    public double getItem_Price() {
        return Item_Price;
    }

    public void setItem_Price(double item_Price) {
        Item_Price = item_Price;
    }

    public String getRetrieval_City() {
        return Retrieval_City;
    }

    public void setRetrieval_City(String retrieval_City) {
        Retrieval_City = retrieval_City;
    }

    public Date getRetrieval_Start_Time() {
        return Retrieval_Start_Time;
    }

    public void setRetrieval_Start_Time(Date retrieval_Start_Time) {
        Retrieval_Start_Time = retrieval_Start_Time;
    }

    public String getRetrieval_Courier() {
        return Retrieval_Courier;
    }

    public void setRetrieval_Courier(String retrieval_Courier) {
        Retrieval_Courier = retrieval_Courier;
    }

    public String getRetrieval_Courier_Gender() {
        return Retrieval_Courier_Gender;
    }

    public void setRetrieval_Courier_Gender(String retrieval_Courier_Gender) {
        Retrieval_Courier_Gender = retrieval_Courier_Gender;
    }

    public String getRetrieval_Courier_Phone_Number() {
        return Retrieval_Courier_Phone_Number;
    }

    public void setRetrieval_Courier_Phone_Number(String retrieval_Courier_Phone_Number) {
        Retrieval_Courier_Phone_Number = retrieval_Courier_Phone_Number;
    }

    public int getRetrieval_Courier_Age() {
        return Retrieval_Courier_Age;
    }

    public void setRetrieval_Courier_Age(int retrieval_Courier_Age) {
        Retrieval_Courier_Age = retrieval_Courier_Age;
    }

    public Date getDelivery_Finished_Time() {
        return Delivery_Finished_Time;
    }

    public void setDelivery_Finished_Time(Date delivery_Finished_Time) {
        Delivery_Finished_Time = delivery_Finished_Time;
    }

    public String getDelivery_City() {
        return Delivery_City;
    }

    public void setDelivery_City(String delivery_City) {
        Delivery_City = delivery_City;
    }

    public String getDelivery_Courier() {
        return Delivery_Courier;
    }

    public void setDelivery_Courier(String delivery_Courier) {
        Delivery_Courier = delivery_Courier;
    }

    public String getDelivery_Courier_Gender() {
        return Delivery_Courier_Gender;
    }

    public void setDelivery_Courier_Gender(String delivery_Courier_Gender) {
        Delivery_Courier_Gender = delivery_Courier_Gender;
    }

    public String getDelivery_Courier_Phone_Number() {
        return Delivery_Courier_Phone_Number;
    }

    public void setDelivery_Courier_Phone_Number(String delivery_Courier_Phone_Number) {
        Delivery_Courier_Phone_Number = delivery_Courier_Phone_Number;
    }

    public int getDelivery_Courier_Age() {
        return Delivery_Courier_Age;
    }

    public void setDelivery_Courier_Age(int delivery_Courier_Age) {
        Delivery_Courier_Age = delivery_Courier_Age;
    }

    public String getItem_Export_City() {
        return Item_Export_City;
    }

    public void setItem_Export_City(String item_Export_City) {
        Item_Export_City = item_Export_City;
    }

    public double getItem_Export_Tax() {
        return Item_Export_Tax;
    }

    public void setItem_Export_Tax(double item_Export_Tax) {
        Item_Export_Tax = item_Export_Tax;
    }

    public Date getItem_Export_Time() {
        return Item_Export_Time;
    }

    public void setItem_Export_Time(Date item_Export_Time) {
        Item_Export_Time = item_Export_Time;
    }

    public String getItem_Import_City() {
        return Item_Import_City;
    }

    public void setItem_Import_City(String item_Import_City) {
        Item_Import_City = item_Import_City;
    }

    public double getItem_Import_Tax() {
        return Item_Import_Tax;
    }

    public void setItem_Import_Tax(double item_Import_Tax) {
        Item_Import_Tax = item_Import_Tax;
    }

    public Date getItem_Import_Time() {
        return Item_Import_Time;
    }

    public void setItem_Import_Time(Date item_Import_Time) {
        Item_Import_Time = item_Import_Time;
    }

    public String getContainer_Code() {
        return Container_Code;
    }

    public void setContainer_Code(String container_Code) {
        Container_Code = container_Code;
    }

    public String getContainer_Type() {
        return Container_Type;
    }

    public void setContainer_Type(String container_Type) {
        Container_Type = container_Type;
    }

    public String getShip_Name() {
        return Ship_Name;
    }

    public void setShip_Name(String ship_Name) {
        Ship_Name = ship_Name;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public Timestamp getLog_Time() {
        return Log_Time;
    }

    public void setLog_Time(Timestamp log_Time) {
        Log_Time = log_Time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawData rawData = (RawData) o;
        return Double.compare(rawData.Item_Price, Item_Price) == 0 && Retrieval_Courier_Age == rawData.Retrieval_Courier_Age && Delivery_Courier_Age == rawData.Delivery_Courier_Age && Double.compare(rawData.Item_Export_Tax, Item_Export_Tax) == 0 && Double.compare(rawData.Item_Import_Tax, Item_Import_Tax) == 0 && Item_Name.equals(rawData.Item_Name) && Item_Type.equals(rawData.Item_Type) && Retrieval_City.equals(rawData.Retrieval_City) && Retrieval_Start_Time.equals(rawData.Retrieval_Start_Time) && Retrieval_Courier.equals(rawData.Retrieval_Courier) && Retrieval_Courier_Gender.equals(rawData.Retrieval_Courier_Gender) && Retrieval_Courier_Phone_Number.equals(rawData.Retrieval_Courier_Phone_Number) && Objects.equals(Delivery_Finished_Time, rawData.Delivery_Finished_Time) && Delivery_City.equals(rawData.Delivery_City) && Objects.equals(Delivery_Courier, rawData.Delivery_Courier) && Objects.equals(Delivery_Courier_Gender, rawData.Delivery_Courier_Gender) && Objects.equals(Delivery_Courier_Phone_Number, rawData.Delivery_Courier_Phone_Number) && Item_Export_City.equals(rawData.Item_Export_City) && Objects.equals(Item_Export_Time, rawData.Item_Export_Time) && Item_Import_City.equals(rawData.Item_Import_City) && Objects.equals(Item_Import_Time, rawData.Item_Import_Time) && Objects.equals(Container_Code, rawData.Container_Code) && Objects.equals(Container_Type, rawData.Container_Type) && Objects.equals(Ship_Name, rawData.Ship_Name) && Company_Name.equals(rawData.Company_Name) && Log_Time.equals(rawData.Log_Time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Item_Name, Item_Type, Item_Price, Retrieval_City, Retrieval_Start_Time, Retrieval_Courier, Retrieval_Courier_Gender, Retrieval_Courier_Phone_Number, Retrieval_Courier_Age, Delivery_Finished_Time, Delivery_City, Delivery_Courier, Delivery_Courier_Gender, Delivery_Courier_Phone_Number, Delivery_Courier_Age, Item_Export_City, Item_Export_Tax, Item_Export_Time, Item_Import_City, Item_Import_Tax, Item_Import_Time, Container_Code, Container_Type, Ship_Name, Company_Name, Log_Time);
    }

    @Override
    public String toString() {
        return "RawData{" + "Item_Name='" + Item_Name + '\'' + ", Item_Type='" + Item_Type + '\'' + ", Item_Price=" + Item_Price + ", Retrieval_City='" + Retrieval_City + '\'' + ", Retrieval_Start_Time=" + Retrieval_Start_Time + ", Retrieval_Courier='" + Retrieval_Courier + '\'' + ", Retrieval_Courier_Gender='" + Retrieval_Courier_Gender + '\'' + ", Retrieval_Courier_Phone_Number='" + Retrieval_Courier_Phone_Number + '\'' + ", Retrieval_Courier_Age=" + Retrieval_Courier_Age + ", Delivery_Finished_Time=" + Delivery_Finished_Time + ", Delivery_City='" + Delivery_City + '\'' + ", Delivery_Courier='" + Delivery_Courier + '\'' + ", Delivery_Courier_Gender='" + Delivery_Courier_Gender + '\'' + ", Delivery_Courier_Phone_Number='" + Delivery_Courier_Phone_Number + '\'' + ", Delivery_Courier_Age=" + Delivery_Courier_Age + ", Item_Export_City='" + Item_Export_City + '\'' + ", Item_Export_Tax=" + Item_Export_Tax + ", Item_Export_Time=" + Item_Export_Time + ", Item_Import_City='" + Item_Import_City + '\'' + ", Item_Import_Tax=" + Item_Import_Tax + ", Item_Import_Time=" + Item_Import_Time + ", Container_Code='" + Container_Code + '\'' + ", Container_Type='" + Container_Type + '\'' + ", Ship_Name='" + Ship_Name + '\'' + ", Company_Name='" + Company_Name + '\'' + ", Log_Time=" + Log_Time + '}';
    }

    public Serializable[] getAllFields() {
        ArrayList<Serializable> list = new ArrayList<>();

        list.add(Item_Name);
        list.add(Item_Type);
        list.add(Item_Price);

        list.add(Retrieval_City);
        list.add(Retrieval_Start_Time);
        list.add(Retrieval_Courier);
        list.add(Retrieval_Courier_Gender);
        list.add(Retrieval_Courier_Phone_Number);
        list.add(Retrieval_Courier_Age);

        list.add(Delivery_Finished_Time);
        list.add(Delivery_City);
        list.add(Delivery_Courier);
        list.add(Delivery_Courier_Gender);
        list.add(Delivery_Courier_Phone_Number);
        list.add(Delivery_Courier_Age);

        list.add(Item_Export_City);
        list.add(Item_Export_Tax);
        list.add(Item_Export_Time);
        list.add(Item_Import_City);
        list.add(Item_Import_Tax);
        list.add(Item_Import_Time);

        list.add(Container_Code);
        list.add(Container_Type);
        list.add(Ship_Name);
        list.add(Company_Name);
        list.add(Log_Time);
        return list.toArray(new Serializable[0]);
    }
}

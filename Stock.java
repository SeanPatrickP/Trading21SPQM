class Stock{
    public String shareName;
    public double sharePrice;
    public int amountOfShare;
    private double profit;
    public Stock(String name, double price, int amount){    
        shareName = name;
        sharePrice = price;
        amountOfShare = amount;
    }
    
    public Stock(){    
        shareName = "";
        sharePrice = 0;
        amountOfShare = 0;
    }
    
    public double Profit(double sellPrice, int numberSold){
    return(profit);    
    }
}    
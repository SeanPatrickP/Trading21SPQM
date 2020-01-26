class UnProtectedStock extends Profit{
    private double profit;
    public UnProtectedStock(String name, double price, int amount){
       super(name, price, amount);
    }   
    //this method we override so we calculate the profit based on market value sell price for the stock
    @Override
    public double Profit(double sellPrice, int numberSold){
        profit = sellPrice - sharePrice;
        profit = profit * numberSold;
        return(profit);
    }    
}    
class ProfitProtectedStock extends Profit{
   public double protectionSellPrice;
   private double profit; 
   public ProfitProtectedStock(String name, double price, int amount, double sellFlag){
       super(name, price, amount);
       
       protectionSellPrice = sellFlag;
       shareName = name;
       sharePrice = price;
       amountOfShare = amount;
   }
   public int checker(ProfitProtectedStock toCheck){
       int val = 0; 
       if(toCheck.protectionSellPrice <= toCheck.sharePrice)
        {
            //the stock will only be approved to be purchase if the buyer sets protected sell price above current stock cost, at present you cannot sell an unprotected stock however   
            val = -1;
        }
       return(val);
   }
   //this method we override so we calculate the profit based on what the user sets for the protection sell price for stock as stock will only sell at this point
   @Override
   public double Profit(double sellPrice, int numberSold)
   {
       profit = protectionSellPrice - sharePrice;
       profit = profit * numberSold;
       return(profit);
   }     
}    
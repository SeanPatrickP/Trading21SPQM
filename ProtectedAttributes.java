class ProtectedAttributes extends ProfitProtectedStock{
    public ProtectedAttributes(String name, double price, int amount, double sellFlag){
        super(name, price, amount, sellFlag);    
        shareName = name;
        protectionSellPrice = sellFlag;
    }
    //the above constructor is so we get the two attributes for the profit protection - being the name and price to sell the stock 
}
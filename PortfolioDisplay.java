import java.util.ArrayList; 
   class PortfolioDisplay{
    private Stock [] portfolioDisplay;
    //private ProtectedAttributes [] protectionAttributesDisplay;
    private int counter; 
    private int protectedCounter; 
    private double profit;
    private double sellPrice;
    private ArrayList<ProtectedAttributes> protectionAttributesArrayList;
   //the below constructor sets the initial values for the counter (this is used when adding an index to the array of protfolios) and portfolioDisplay 
   public PortfolioDisplay() {
        counter = 0;
        //protectedCounter = 0;
        portfolioDisplay = new Stock[1];
        protectionAttributesArrayList = new ArrayList<ProtectedAttributes>();
        //protectionAttributesDisplay = new ProtectedAttributes[1];
   }    
   
    public void AddStock(Stock toAdd) {
        counter ++;
        //first we check to see if any stocks already in the session - if not we use the array created in the constructor for the portfolio display - this is available throughout the session
        if(counter == 1)
            portfolioDisplay[0] = toAdd;
        //if the counter isnt 1 we then create a new stock array with indexes 1 greater than current, this array then gets initialised with the current content of the portfolio display plus the new stock
        else
        {
            Stock [] Stocks = new Stock[counter];
            for(int i = 0; i < counter - 1; i++)
                Stocks[i] = portfolioDisplay[i];
            //below we take into account index 0 of the array    
            Stocks[Stocks.length - 1] = toAdd;
            //here we mirror the current array back to portfolio display ready for the next addition of stock
            portfolioDisplay = Stocks;
        }
   }
   //the below method is called in addition to the former if the protection when buying stock is used
   public void ProtectionAttributes(ProtectedAttributes toAdd) {
        //protectedCounter ++;
        protectionAttributesArrayList.add(toAdd);
    
        //first we check to see if any stocks already in the session - if not we use the array created in the constructor for the protectionAttributesDisplay - this is available throughout the session
        //if(protectedCounter == 1)
            //protectionAttributesDisplay[0] = toAdd;
        //if the counter isnt 1 we then create a new stock array with indexes 1 greater than current, this array then gets initialised with the current content of the protectionAttributesDisplay plus the new stock
        //else
        //{
            //ProtectedAttributes [] Stocks = new ProtectedAttributes[counter];
            //for(int i = 0; i < counter - 1; i++)
                //Stocks[i] = protectionAttributesDisplay[i];
            //below we take into account index 0 of the array    
            //Stocks[Stocks.length - 1] = toAdd;
            //here we mirror the current array back to protectionAttributesDisplay ready for the next addition of stock
            //protectionAttributesDisplay = Stocks;
        //}
   }
    //this getter is called when the portfolios for the user is needed to be returned
   public Stock[] getPortfolioNames(){ 
        
       return portfolioDisplay;
   }
   
   //public ProtectedAttributes[] getProtectedStock(){
       
       //return protectionAttributesDisplay;
   //}
   
   public ArrayList<ProtectedAttributes> getProtectedStock(){
       
       return protectionAttributesArrayList;
   }   
   
   public int getStockIndex(String searchName)
   {
       //the below will find the portfolio index within the private array
       for(int i = 0 ; i < portfolioDisplay.length ; i++)
       {
           if((portfolioDisplay[i].shareName).equals(searchName))
                return(i);    
       }   
       //we return -1 here incase the search string isnt found, -1 is our error value
       return(-1);
   }  
   //give an index the below getters will return the information on the stock
   public String getStockName(int index)
   {
       
       return(portfolioDisplay[index].shareName);
   }   
   
   public int getProtectedStockIndex(String searchName)
   {
       try{
       for(int i = 0 ; i < protectionAttributesArrayList.size() ; i++)
       {
           if((protectionAttributesArrayList.get(i).shareName).equals(searchName))
                return(i);    
       }   
      
       return(-1);
       }
       catch(NullPointerException npe){
            return(-1);
        }
        
       //the below will find the portfolio index within the private array
       //we use the try just incase no protected stock is added therfore the array wouldnt exist
       //try{
       //for(int i = 0 ; i < protectionAttributesDisplay.length ; i++)
       //{
           //if((protectionAttributesDisplay[i].shareName).equals(searchName))
                //return(i);    
       //}   
       //we return -1 here incase the search string isnt found, -1 is our no protection on this stock value
       //return(-1);
       //}
       //catch(NullPointerException npe){
            //return(-1);
       //}
   }  
   
   public double getStockPrice(int index)
   {
       
       return(portfolioDisplay[index].sharePrice);
   }   
   
   public int getStockAmount(int index)
   {
       
       return(portfolioDisplay[index].amountOfShare);
   }   
   //the below instance methods Buy / Sell with a given index and the amount will change the share amount in the portfolio
   public void Buy(int index, int moreShare){
       
       portfolioDisplay[index].amountOfShare += moreShare;
   }    
   
   public double Sell(int index, int lessShare, double sellPrice){
       //we get the profit from performing the sell action - please note that at present only the unprotected stock can be sold
       profit = getProfit(portfolioDisplay[index], lessShare, sellPrice);
       portfolioDisplay[index].amountOfShare -= lessShare;     
       return(profit);
   }
   //here we use the superclass for unprotected and protected stock - polymorphism is then used to choose how to implement the calculation for profit method depending on the type of subclass passed through as a variable
   public double getProfit(Stock stockToGetProfitFrom, int lessShare, double sellPrice){
       
       return(stockToGetProfitFrom.Profit(sellPrice, lessShare));
   }
   //method to shuffle the portfolio display if all of stock sold
   public void shufflePortfolioDisplay(int index){
       Stock [] portfolioDisplayShuffled = new Stock[portfolioDisplay.length - 1];
       if(portfolioDisplay.length > 1)
       {
           for(int i = index ; i < portfolioDisplay.length - 1 ; i++)
                portfolioDisplay[index] = portfolioDisplay[index + 1];
           for(int z = 0 ; z < portfolioDisplay.length -1 ; z++)
                portfolioDisplayShuffled[z] = portfolioDisplay[z];
           portfolioDisplay = portfolioDisplayShuffled; 
           //we need to adjust the counter as otherwise the arrayindex when we iterate to add stock will not exist
           counter --;
       }
       //the below is in the case of there only being 1 element in the array at time of sell, meaning a null pointer exception would be thrown
       else
            portfolioDisplay = new Stock[1]; 
   }
   
   public void overRide(Stock [] portfolioDisplay) {
       
       portfolioDisplay = portfolioDisplay;
   }
   
   public int getSize() {
   
       return(portfolioDisplay.length);
   }
}
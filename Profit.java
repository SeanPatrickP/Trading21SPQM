public abstract class Profit extends Stock{
    
    public Profit(String name, double price, int amount){
       super(name, price, amount);
    }   
    //The below method is one that we would have to override in any class that extends this abstract method 
    public abstract double Profit(double sellPrice, int numberSold);
}    
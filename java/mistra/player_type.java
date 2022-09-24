package mistra;

public enum player_type {
    AI(1),
    User(2);
     private final int val;
     player_type(int val){
         this.val=val;
     }
     public int valuen(){return val;}    
}

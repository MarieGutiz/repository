package mistra;


public enum piece_type {
       Flag(0),
	Marshal(1),
	General(2),
	Colonel(3),
	  Major(4),
	Captain(5),
     Lieutenant(6),
       Sergeant(7),
	  Miner(8),
	  Scout(9),
	   Spy(10),
	  Bomb(11),
	Blank(12);

       private int val;
       piece_type(int val){
           this.val=val;
           
      
       }
       public int valuen(){return val;}

}

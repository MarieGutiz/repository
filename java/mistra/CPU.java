package mistra;
import java.util.ArrayList;
public class CPU {
    boolean top;
    public int move_count;//1000 move in total
    private int pieces_left;
    private int[] num_left=new int[12];//la ctdad d pcs d c/tipo
    private gamePiece board[][]=new gamePiece[10][10];
    private  int[][] board_piece_mappings=new int [10][10];
    private  gamePiece[] myPieces=new gamePiece[40];    //AI
    private  boolean[] known=new boolean[40];//conocidas
    private  int[] known_values= new int[40];//valores cnocidos
    private  gamePiece[] opponentPieces= new gamePiece[40];//USER
    private  int[][][] probabilities= new int [40][12][2];//960
    private  int[][][] all_moves= new int [40][10][10];//4000
   
    ArrayList<Integer> gone= new ArrayList<Integer>(); 
    ArrayList<Object> initiatedby1= new ArrayList<Object>();
    ArrayList<Object> initiatedby2= new ArrayList<Object>();
    ArrayList<Integer> move_counter= new ArrayList<Integer>(); //4dnd
    
    ArrayList<Integer> retired= new ArrayList<Integer>(); //cpu
    ArrayList<Integer> actualmc= new ArrayList<Integer>(); 
    
    public clasify cla= new clasify();
	int x_from;
    int y_from;
	int x_to;
	int y_to;
	  /* dos */
   float bombs=0;
   float discovery=0;
   float material=0;
   float attack=0;
   float unknown=0;
   class moves{
         int possible_moves[][]= new int[19][2];
    };
    
   class bomb_move
    {
	int move[]=new int[2];
    };
  
    CPU(boolean top){
	int x;
	move_count=0;
	pieces_left=40;
	num_left[0]=1;
	num_left[1]=1;
	num_left[2]=1;
	num_left[3]=2;
	num_left[4]=3;
	num_left[5]=4;
	num_left[6]=4;
	num_left[7]=4;
	num_left[8]=5;
	num_left[9]=8;
	num_left[10]=1;
	num_left[11]=6;
	this.top = top;
	for(x=0; x<10; x++){
		for(int y=0; y<10; y++){
			board[x][y]=null;
			board_piece_mappings[x][y]=-1;
		}
	}
	myPieces[0]=new gamePiece(piece_type.Marshal, player_type.AI);
	myPieces[1]=new gamePiece(piece_type.General, player_type.AI);
	for(x=2; x<=3; x++){		
		myPieces[x]=new gamePiece(piece_type.Colonel,player_type.AI);
	}
	for(x=4; x<=6; x++){
		myPieces[x]=new gamePiece(piece_type.Major, player_type.AI);
	}
	for(x=7; x<=10; x++){
		myPieces[x]=new gamePiece(piece_type.Captain, player_type.AI);
	}
	for(x=11; x<=14; x++){
		myPieces[x]=new gamePiece(piece_type.Lieutenant, player_type.AI);
	}
	for(x=15; x<=18; x++){
		myPieces[x]=new gamePiece(piece_type.Sergeant, player_type.AI);
	}
	for(x=19; x<=23; x++){
		myPieces[x]=new gamePiece(piece_type.Miner, player_type.AI);
	}
	for(x=24; x<=31; x++){
		myPieces[x]=new gamePiece(piece_type.Scout,player_type.AI);
	}
	myPieces[32]=new gamePiece(piece_type.Spy, player_type.AI);
	for(x=33; x<=38; x++){
		myPieces[x]=new gamePiece(piece_type.Bomb, player_type.AI);
	}
	myPieces[39]=new gamePiece(piece_type.Flag, player_type.AI);
	for(x=0; x<40; x++){
		known[x]=false;
		known_values[x]=999;
		opponentPieces[x]=new gamePiece(piece_type.Blank, player_type.User);
		probabilities[x][0][0]=1;//24
		probabilities[x][0][1]=40;
		probabilities[x][1][0]=1;
		probabilities[x][1][1]=40;
		probabilities[x][2][0]=1;
		probabilities[x][2][1]=40;
		probabilities[x][3][0]=2;
		probabilities[x][3][1]=40;
		probabilities[x][4][0]=3;
		probabilities[x][4][1]=40;
		probabilities[x][5][0]=4;
		probabilities[x][5][1]=40;
		probabilities[x][6][0]=4;
		probabilities[x][6][1]=40;
		probabilities[x][7][0]=4;
		probabilities[x][7][1]=40;
		probabilities[x][8][0]=5;
		probabilities[x][8][1]=40;
		probabilities[x][9][0]=8;
		probabilities[x][9][1]=40;
		probabilities[x][10][0]=1;
		probabilities[x][10][1]=40;
		probabilities[x][11][0]=6;
		probabilities[x][11][1]=40;
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				all_moves[x][i][j]=0;
			}
		}
	}
}//cpu

   public void reset(){
	int x;
	move_count=0;
	pieces_left=40;
	num_left[0]=1;
	num_left[1]=1;
	num_left[2]=1;
	num_left[3]=2;
	num_left[4]=3;
	num_left[5]=4;
	num_left[6]=4;
	num_left[7]=4;
	num_left[8]=5;
	num_left[9]=8;
	num_left[10]=1;
	num_left[11]=6;
	for(x=0; x<10; x++){
		for(int y=0; y<10; y++){
			board[x][y]=null;
			board_piece_mappings[x][y]=-1;
		}
	}
	for(x=0; x<40; x++){
		known[x]=false;
		known_values[x]=999;
		myPieces[x].update_coordinates(-1, -1);
		probabilities[x][0][0]=1;
		probabilities[x][0][1]=40;
		probabilities[x][1][0]=1;
		probabilities[x][1][1]=40;
		probabilities[x][2][0]=1;
		probabilities[x][2][1]=40;
		probabilities[x][3][0]=2;
		probabilities[x][3][1]=40;
		probabilities[x][4][0]=3;
		probabilities[x][4][1]=40;
		probabilities[x][5][0]=4;
		probabilities[x][5][1]=40;
		probabilities[x][6][0]=4;
		probabilities[x][6][1]=40;
		probabilities[x][7][0]=4;
		probabilities[x][7][1]=40;
		probabilities[x][8][0]=5;
		probabilities[x][8][1]=40;
		probabilities[x][9][0]=8;
		probabilities[x][9][1]=40;
		probabilities[x][10][0]=1;
		probabilities[x][10][1]=40;
		probabilities[x][11][0]=6;
		probabilities[x][11][1]=40;
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				all_moves[x][i][j]=0;
			}
		}
	}
	if(top){//if top
		int i=0;
		for(x=0; x<10; x++){
			for(int y=0; y<4; y++){
				opponentPieces[i].update_coordinates(x, y);
				board[x][y]=opponentPieces[i];//aqui
				board_piece_mappings[x][y]=i;
				i++;
			}
		}
	}
/*	else{
		int i=0;
		for(x=0; x<10; x++){
			for(int y=6; y<10; y++){
				opponentPieces[i].update_coordinates(x, y);
				board[x][y]=opponentPieces[i];
				board_piece_mappings[x][y]=i;
				i++;
			}
		}
	}*/
}//reset
CPU(){
	for(int x=0; x<40; x++){
		 myPieces[x]=null;
	}
}//cpu

public void evaluator(gamePiece myPieces[], gamePiece opponentPieces[], boolean known[], boolean top, int probabilities[][][], boolean do_bombs, boolean do_unknown){
	
	for(int x=0; x<40; x++){
		if (known[x])
			discovery+=1;

		if(do_bombs){
			if(opponentPieces[x].getX()!=-1 && probabilities[x][11][0]!=0){
				for(int i=19; i<24; i++){
					if(myPieces[i].getX()!=-1){
						bombs+=Math.abs(myPieces[i].getX()-opponentPieces[x].getX());
						bombs+=Math.abs(myPieces[i].getY()-opponentPieces[x].getY());
					}
				}
			}
		}

		if(top && myPieces[x].getX()!=-1){
			attack+=(9-myPieces[x].getY());
		}
		if(!top && myPieces[x].getX()!=-1){
			attack+=myPieces[x].getY();
		}

		if(do_unknown && !known[x]){
			int x_coord=opponentPieces[x].getX();
			int y_coord=opponentPieces[x].getY();
			for(int y=0; y<40; y++){
				if(myPieces[y].getX()!=-1){
					unknown+=Math.abs(myPieces[y].getX()-x_coord);
					unknown+=Math.abs(myPieces[y].getY()-y_coord);
				}
			}
		}

		if(myPieces[x].getX()>-1){
			if(myPieces[x].getPieceType().valuen()==0)
				material+=100;
			else if(myPieces[x].getPieceType().valuen()==1)
				material+=90;
			else if(myPieces[x].getPieceType().valuen()==2)
				material+=80;
			else if(myPieces[x].getPieceType().valuen()==3)
				material+=70;
			else if(myPieces[x].getPieceType().valuen()==4)
				material+=60;
			else if(myPieces[x].getPieceType().valuen()==5)
				material+=50;
			else if(myPieces[x].getPieceType().valuen()==6)
				material+=40;
			else if(myPieces[x].getPieceType().valuen()==7)
				material+=30;
			else if(myPieces[x].getPieceType().valuen()==8)
				material+=100;
			else if(myPieces[x].getPieceType().valuen()==9)
				material+=10;
			else if(myPieces[x].getPieceType().valuen()==10)
				material+=30;
			else if(myPieces[x].getPieceType().valuen()==11)
				material+=50;
		}
	}
	discovery/=40.0;
	material/=1980.0;
	bombs = (float) ((2000-bombs)/2000.0);
	attack/=300.0;
	unknown=(float) ((15000-unknown)/15000.0);
}//evaluator

private float flag(gamePiece myPieces[], gamePiece board[][]){
	int flag_x = myPieces[39].getX();
	int flag_y = myPieces[39].getY();
	float vulnerabilities=0;
	for(int x=flag_x-2; x<flag_x+2; x++){
		if(x<0 || x>9) continue;
		for(int y=flag_y-2; y<flag_y+2; y++){
			if(y<0 || y>9) continue;
			if((x==2 || x==3 || x==6 || x==7) && (y==4 || y==5)) continue;
			if(x==flag_x && y==flag_y) continue;
			if(board[x][y]==null) vulnerabilities+=0.9;
			else if(board[x][y].getPlayerType()==player_type.User) vulnerabilities+=1.0;
			else if(board[x][y].getPieceType()==piece_type.General) vulnerabilities+=0.1;
			else if(board[x][y].getPieceType()==piece_type.Colonel) vulnerabilities+=0.2;
			else if(board[x][y].getPieceType()==piece_type.Major) vulnerabilities+=0.3;
			else if(board[x][y].getPieceType()==piece_type.Captain) vulnerabilities+=0.4;
			else if(board[x][y].getPieceType()==piece_type.Lieutenant) vulnerabilities+=0.5;
			else if(board[x][y].getPieceType()==piece_type.Sergeant) vulnerabilities+=0.6;
			else if(board[x][y].getPieceType()==piece_type.Miner) vulnerabilities+=0.7;
			else if(board[x][y].getPieceType()==piece_type.Scout) vulnerabilities+=0.9;
			else if(board[x][y].getPieceType()==piece_type.Spy) vulnerabilities+=0.95;
			else if(board[x][y].getPieceType()==piece_type.Bomb) vulnerabilities+=0.25;
		}
	}
	return (float) (1.0 - (vulnerabilities / 22.0));
}//flag
private float heuristic(boolean known[], gamePiece myPieces[], gamePiece board[][], gamePiece opponentPieces[], int probabilities[][][], int move_count, int pieces_left, boolean top){
	float value=0;
	boolean do_bombs=false;
	boolean do_unknown=false;
	//System.out.println("pieces_left "+pieces_left);
	if(move_count>70 && move_count<140 && pieces_left < 25)
		do_bombs=true;
	else if(move_count>=200 && pieces_left < 15)
		do_unknown=true;
	evaluator(myPieces, opponentPieces, known, top, probabilities,do_bombs, do_unknown);
	value += 0.25*discovery;
	value += 0.25*material;
	value += 0.25*flag(myPieces, board);//vulnerabilities
	value += 0.01*attack;
	value += 0.01*unknown;
	if(move_count>70 && move_count<140 && pieces_left < 25)
		value += 0.25*bombs;
	else if(move_count>=140 && move_count<200 && pieces_left < 20)
		value += 0.35*bombs;
	else if(move_count>=200 && pieces_left < 15)
		value += 0.4*bombs;
	return value;
}//heuristic
void boardmine1(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){//oso de arkoudoso
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-2, 7);//1
			board[flipped-2][7]=myPieces[0];
			myPieces[1].update_coordinates(flipped-7, 6);//2
			board[flipped-7][6]=myPieces[1];
			myPieces[2].update_coordinates(flipped-0, 7);//3
			board[flipped-1][7]=myPieces[2];
			myPieces[3].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[3];
			myPieces[4].update_coordinates(flipped-2, 8);//4
			board[flipped-2][8]=myPieces[4];
			myPieces[5].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[5];
			myPieces[6].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[6];
			myPieces[7].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[9];
			myPieces[10].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[10];
			myPieces[11].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[11];
			myPieces[12].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[13];
			
			myPieces[14].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[14];
			myPieces[15].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[15];
			myPieces[16].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[16];
			myPieces[17].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[17];
			myPieces[18].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[19];
			myPieces[20].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[20];
			myPieces[21].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[23];
			myPieces[24].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[24];
			myPieces[25].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[25];
			myPieces[26].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[28];
			myPieces[29].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[29];
			myPieces[30].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[30];
			myPieces[31].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[33];
			myPieces[34].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[34];
			myPieces[35].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[35];
			myPieces[36].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[36];
			myPieces[37].update_coordinates(flipped-8, 9);
			board[flipped-8][5]=myPieces[37];
			myPieces[38].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(2, 7);//1
			board[2][7]=myPieces[0];
			myPieces[1].update_coordinates(7, 6);//2
			board[7][6]=myPieces[1];
			myPieces[2].update_coordinates(0, 7);//3
			board[0][7]=myPieces[2];
			myPieces[3].update_coordinates(3, 6);//3
			board[3][6]=myPieces[3];
			myPieces[4].update_coordinates(2, 8);//4
			board[2][8]=myPieces[4];
			myPieces[5].update_coordinates(5, 7);
			board[5][7]=myPieces[5];
			myPieces[6].update_coordinates(8, 7);
			board[8][7]=myPieces[6];
			myPieces[7].update_coordinates(1, 6);//5
			board[1][6]=myPieces[7];
			myPieces[8].update_coordinates(2, 6);
			board[2][6]=myPieces[8];
			myPieces[9].update_coordinates(4, 6);
			board[4][6]=myPieces[9];
			myPieces[10].update_coordinates(8, 6);
			board[8][6]=myPieces[10];
			myPieces[11].update_coordinates(1, 7);//6
			board[1][7]=myPieces[11];
			myPieces[12].update_coordinates(6, 9);
			board[6][9]=myPieces[12];
			myPieces[13].update_coordinates(8, 8);
			board[8][8]=myPieces[13];
			myPieces[14].update_coordinates(9, 6);//edw flag-liutenent
			board[9][6]=myPieces[14];
			
			myPieces[15].update_coordinates(5, 6);//7
			board[5][6]=myPieces[15];
			myPieces[16].update_coordinates(6, 6);
			board[6][6]=myPieces[16];
			myPieces[17].update_coordinates(7, 7);
			board[7][7]=myPieces[17];
			myPieces[18].update_coordinates(9, 9);
			board[9][9]=myPieces[18];
			myPieces[19].update_coordinates(2, 9);//8
			board[2][9]=myPieces[19];
			myPieces[20].update_coordinates(1, 8);
			board[1][8]=myPieces[20];
			myPieces[21].update_coordinates(7, 9);
			board[7][9]=myPieces[21];
			myPieces[22].update_coordinates(7, 8);
			board[7][8]=myPieces[22];
			myPieces[23].update_coordinates(9, 7);
			board[9][7]=myPieces[23];
			myPieces[24].update_coordinates(3, 8);//9 scout edw
			board[3][8]=myPieces[24];
			myPieces[25].update_coordinates(0, 6);//change with bomb
			board[0][6]=myPieces[25];//
			myPieces[26].update_coordinates(3, 7);
			board[3][7]=myPieces[26];
			myPieces[27].update_coordinates(4, 8);
			board[4][8]=myPieces[27];
			myPieces[28].update_coordinates(4, 9);
			board[4][9]=myPieces[28];
			myPieces[29].update_coordinates(4,7);
			board[4][7]=myPieces[29];
			myPieces[30].update_coordinates(5, 8);
			board[5][8]=myPieces[30];
			myPieces[31].update_coordinates(5, 9);
			board[5][9]=myPieces[31];
			myPieces[32].update_coordinates(6, 7);//10
			board[6][7]=myPieces[32];
			myPieces[33].update_coordinates(0, 8);//11=> bomb
			board[0][8]=myPieces[33];
			myPieces[34].update_coordinates(3, 9);//change with scout
			board[3][9]=myPieces[34];//
			myPieces[35].update_coordinates(1, 9);
			board[1][9]=myPieces[35];
			myPieces[36].update_coordinates(6, 8);
			board[6][8]=myPieces[36];
			myPieces[37].update_coordinates(8, 9);
			board[8][9]=myPieces[37];
			myPieces[38].update_coordinates(9, 8);
			board[9][8]=myPieces[38];
			myPieces[39].update_coordinates(0, 9);
			board[0][9]=myPieces[39];
		}
	}
}
void board1(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){//del
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[0];
			myPieces[1].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[1];
			myPieces[2].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[2];
			myPieces[3].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[3];
			myPieces[4].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[4];
			myPieces[5].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[5];
			myPieces[6].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[6];
			myPieces[7].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[9];
			myPieces[10].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[10];
			myPieces[11].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[13];
			myPieces[14].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[14];
			myPieces[15].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[15];
			myPieces[16].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[16];
			myPieces[17].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[17];
			myPieces[18].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[19];
			myPieces[20].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[20];
			myPieces[21].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[21];
			myPieces[22].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[22];
			myPieces[23].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[24];
			myPieces[25].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[27];
			myPieces[28].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[30];
			myPieces[31].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[33];
			myPieces[34].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[34];
			myPieces[35].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[35];
			myPieces[36].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[36];
			myPieces[37].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[37];
			myPieces[38].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(7, 6);
			board[7][6]=myPieces[0];
			myPieces[1].update_coordinates(2, 6);
			board[2][6]=myPieces[1];
			myPieces[2].update_coordinates(9, 7);
			board[9][7]=myPieces[2];
			myPieces[3].update_coordinates(1, 7);
			board[1][7]=myPieces[3];
			myPieces[4].update_coordinates(3, 6);
			board[3][6]=myPieces[4];
			myPieces[5].update_coordinates(8, 8);
			board[8][8]=myPieces[5];
			myPieces[6].update_coordinates(4, 9);
			board[4][9]=myPieces[6];
			myPieces[7].update_coordinates(9, 6);
			board[9][6]=myPieces[7];
			myPieces[8].update_coordinates(5, 6);
			board[5][6]=myPieces[8];
			myPieces[9].update_coordinates(0, 6);
			board[0][6]=myPieces[9];
			myPieces[10].update_coordinates(8, 7);
			board[8][7]=myPieces[10];
			myPieces[11].update_coordinates(8, 6);
			board[8][6]=myPieces[11];
			myPieces[12].update_coordinates(4, 6);
			board[4][6]=myPieces[12];
			myPieces[13].update_coordinates(4, 8);
			board[4][8]=myPieces[13];
			myPieces[14].update_coordinates(1, 8);
			board[1][8]=myPieces[14];
			myPieces[15].update_coordinates(1, 6);
			board[1][6]=myPieces[15];
			myPieces[16].update_coordinates(6, 6);
			board[6][6]=myPieces[16];
			myPieces[17].update_coordinates(2, 9);
			board[2][9]=myPieces[17];
			myPieces[18].update_coordinates(0, 9);
			board[0][9]=myPieces[18];
			myPieces[19].update_coordinates(0, 7);
			board[0][7]=myPieces[19];
			myPieces[20].update_coordinates(3, 7);
			board[3][7]=myPieces[20];
			myPieces[21].update_coordinates(7, 7);
			board[7][7]=myPieces[21];
			myPieces[22].update_coordinates(7, 8);
			board[7][8]=myPieces[22];
			myPieces[23].update_coordinates(7, 9);
			board[7][9]=myPieces[23];
			myPieces[24].update_coordinates(2, 7);
			board[2][7]=myPieces[24];
			myPieces[25].update_coordinates(4, 7);
			board[4][7]=myPieces[25];
			myPieces[26].update_coordinates(5, 7);
			board[5][7]=myPieces[26];
			myPieces[27].update_coordinates(6, 7);
			board[6][7]=myPieces[27];
			myPieces[28].update_coordinates(5, 8);
			board[5][8]=myPieces[28];
			myPieces[29].update_coordinates(6, 8);
			board[6][8]=myPieces[29];
			myPieces[30].update_coordinates(5, 9);
			board[5][9]=myPieces[30];
			myPieces[31].update_coordinates(6, 9);
			board[6][9]=myPieces[31];
			myPieces[32].update_coordinates(3, 8);
			board[3][8]=myPieces[32];
			myPieces[33].update_coordinates(0, 8);
			board[0][8]=myPieces[33];
			myPieces[34].update_coordinates(2, 8);
			board[2][8]=myPieces[34];
			myPieces[35].update_coordinates(9, 8);
			board[9][8]=myPieces[35];
			myPieces[36].update_coordinates(1, 9);
			board[1][9]=myPieces[36];
			myPieces[37].update_coordinates(3, 9);
			board[3][9]=myPieces[37];
			myPieces[38].update_coordinates(8, 9);
			board[8][9]=myPieces[38];
			myPieces[39].update_coordinates(9, 9);
			board[9][9]=myPieces[39];
		}
	}
	/*else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[0];
			myPieces[1].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[1];
			myPieces[2].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[2];
			myPieces[3].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[3];
			myPieces[4].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[4];
			myPieces[5].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[5];
			myPieces[6].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[6];
			myPieces[7].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[7];
			myPieces[8].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[8];
			myPieces[9].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[9];
			myPieces[10].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[10];
			myPieces[11].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[11];
			myPieces[12].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[13];
			myPieces[14].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[14];
			myPieces[15].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[15];
			myPieces[16].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[16];
			myPieces[17].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[17];
			myPieces[18].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[19];
			myPieces[20].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[20];
			myPieces[21].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[21];
			myPieces[22].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[22];
			myPieces[23].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[23];
			myPieces[24].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[24];
			myPieces[25].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[25];
			myPieces[26].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[26];
			myPieces[27].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[27];
			myPieces[28].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[28];
			myPieces[29].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[29];
			myPieces[30].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[30];
			myPieces[31].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[31];
			myPieces[32].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[33];
			myPieces[34].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[34];
			myPieces[35].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[35];
			myPieces[36].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[36];
			myPieces[37].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[37];
			myPieces[38].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(1, 1);
			board[1][1]=myPieces[0];
			myPieces[1].update_coordinates(0, 2);
			board[0][2]=myPieces[1];
			myPieces[2].update_coordinates(1, 2);
			board[1][2]=myPieces[2];
			myPieces[3].update_coordinates(5, 2);
			board[5][2]=myPieces[3];
			myPieces[4].update_coordinates(3, 3);
			board[3][3]=myPieces[4];
			myPieces[5].update_coordinates(5, 1);
			board[5][1]=myPieces[5];
			myPieces[6].update_coordinates(8, 2);
			board[8][2]=myPieces[6];
			myPieces[7].update_coordinates(4, 2);
			board[4][2]=myPieces[7];
			myPieces[8].update_coordinates(6, 1);
			board[6][1]=myPieces[8];
			myPieces[9].update_coordinates(6, 2);
			board[6][2]=myPieces[9];
			myPieces[10].update_coordinates(7, 2);
			board[7][2]=myPieces[10];
			myPieces[11].update_coordinates(2, 1);
			board[2][1]=myPieces[11];
			myPieces[12].update_coordinates(7, 1);
			board[7][1]=myPieces[12];
			myPieces[13].update_coordinates(8, 1);
			board[8][1]=myPieces[13];
			myPieces[14].update_coordinates(9, 1);
			board[9][1]=myPieces[14];
			myPieces[15].update_coordinates(6, 3);
			board[6][3]=myPieces[15];
			myPieces[16].update_coordinates(7, 3);
			board[7][3]=myPieces[16];
			myPieces[17].update_coordinates(9, 0);
			board[9][0]=myPieces[17];
			myPieces[18].update_coordinates(9, 2);
			board[9][2]=myPieces[18];
			myPieces[19].update_coordinates(2, 3);
			board[2][3]=myPieces[19];
			myPieces[20].update_coordinates(3, 1);
			board[3][1]=myPieces[20];
			myPieces[21].update_coordinates(3, 2);
			board[3][2]=myPieces[21];
			myPieces[22].update_coordinates(4, 1);
			board[4][1]=myPieces[22];
			myPieces[23].update_coordinates(8, 0);
			board[8][0]=myPieces[23];
			myPieces[24].update_coordinates(0, 3);
			board[0][3]=myPieces[24];
			myPieces[25].update_coordinates(1, 3);
			board[1][3]=myPieces[25];
			myPieces[26].update_coordinates(2, 0);
			board[2][0]=myPieces[26];
			myPieces[27].update_coordinates(3, 0);
			board[3][0]=myPieces[27];
			myPieces[28].update_coordinates(4, 0);
			board[4][0]=myPieces[28];
			myPieces[29].update_coordinates(5, 0);
			board[5][0]=myPieces[29];
			myPieces[30].update_coordinates(6, 0);
			board[6][0]=myPieces[30];
			myPieces[31].update_coordinates(7, 0);
			board[7][0]=myPieces[31];
			myPieces[32].update_coordinates(2, 2);
			board[2][2]=myPieces[32];
			myPieces[33].update_coordinates(0, 1);
			board[0][1]=myPieces[33];
			myPieces[34].update_coordinates(1, 0);
			board[1][0]=myPieces[34];
			myPieces[35].update_coordinates(4, 3);
			board[4][3]=myPieces[35];
			myPieces[36].update_coordinates(5, 3);
			board[5][3]=myPieces[36];
			myPieces[37].update_coordinates(8, 3);
			board[8][3]=myPieces[37];
			myPieces[38].update_coordinates(9, 3);
			board[9][3]=myPieces[38];
			myPieces[39].update_coordinates(0, 0);
			board[0][0]=myPieces[39];
		}
	}*/
}//board1 ...more boards 9
void board2(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[0];
			myPieces[1].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[1];
			myPieces[2].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[2];
			myPieces[3].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[3];
			myPieces[4].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[4];
			myPieces[5].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[5];
			myPieces[6].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[6];
			myPieces[7].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[9];
			myPieces[10].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[10];
			myPieces[11].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[14];
			myPieces[15].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[15];
			myPieces[16].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[16];
			myPieces[17].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[17];
			myPieces[18].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[19];
			myPieces[20].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[20];
			myPieces[21].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[24];
			myPieces[25].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[25];
			myPieces[26].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[27];
			myPieces[28].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[30];
			myPieces[31].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[33];
			myPieces[34].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[34];
			myPieces[35].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[35];
			myPieces[36].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[36];
			myPieces[37].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[37];
			myPieces[38].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(2, 7);
			board[2][7]=myPieces[0];
			myPieces[1].update_coordinates(6, 6);
			board[6][6]=myPieces[1];
			myPieces[2].update_coordinates(0, 7);
			board[0][7]=myPieces[2];
			myPieces[3].update_coordinates(9, 7);
			board[9][7]=myPieces[3];
			myPieces[4].update_coordinates(2, 6);
			board[2][6]=myPieces[4];
			myPieces[5].update_coordinates(1, 8);
			board[1][8]=myPieces[5];
			myPieces[6].update_coordinates(8, 9);
			board[8][9]=myPieces[6];
			myPieces[7].update_coordinates(4, 6);
			board[4][6]=myPieces[7];
			myPieces[8].update_coordinates(5, 6);
			board[5][6]=myPieces[8];
			myPieces[9].update_coordinates(9, 6);
			board[9][6]=myPieces[9];
			myPieces[10].update_coordinates(1, 7);
			board[1][7]=myPieces[10];
			myPieces[11].update_coordinates(1, 6);
			board[1][6]=myPieces[11];
			myPieces[12].update_coordinates(8, 6);
			board[8][6]=myPieces[12];
			myPieces[13].update_coordinates(8, 7);
			board[8][7]=myPieces[13];
			myPieces[14].update_coordinates(8, 8);
			board[8][8]=myPieces[14];
			myPieces[15].update_coordinates(4, 8);
			board[4][8]=myPieces[15];
			myPieces[16].update_coordinates(5, 8);
			board[5][8]=myPieces[16];
			myPieces[17].update_coordinates(6, 8);
			board[6][8]=myPieces[17];
			myPieces[18].update_coordinates(2, 9);
			board[2][9]=myPieces[18];
			myPieces[19].update_coordinates(2, 8);
			board[2][8]=myPieces[19];
			myPieces[20].update_coordinates(4, 9);
			board[4][9]=myPieces[20];
			myPieces[21].update_coordinates(5, 9);
			board[5][9]=myPieces[21];
			myPieces[22].update_coordinates(6, 9);
			board[6][9]=myPieces[22];
			myPieces[23].update_coordinates(9, 9);
			board[9][9]=myPieces[23];
			myPieces[24].update_coordinates(3, 6);
			board[3][6]=myPieces[24];
			myPieces[25].update_coordinates(7, 6);
			board[7][6]=myPieces[25];
			myPieces[26].update_coordinates(3, 7);
			board[3][7]=myPieces[26];
			myPieces[27].update_coordinates(7, 7);
			board[7][7]=myPieces[27];
			myPieces[28].update_coordinates(3, 8);
			board[3][8]=myPieces[28];
			myPieces[29].update_coordinates(7, 8);
			board[7][8]=myPieces[29];
			myPieces[30].update_coordinates(3, 9);
			board[3][9]=myPieces[30];
			myPieces[31].update_coordinates(7, 9);
			board[7][9]=myPieces[31];
			myPieces[32].update_coordinates(9, 8);
			board[9][8]=myPieces[32];
			myPieces[33].update_coordinates(0, 6);
			board[0][6]=myPieces[33];
			myPieces[34].update_coordinates(4, 7);
			board[4][7]=myPieces[34];
			myPieces[35].update_coordinates(5, 7);
			board[5][7]=myPieces[35];
			myPieces[36].update_coordinates(6, 7);
			board[6][7]=myPieces[36];
			myPieces[37].update_coordinates(0, 8);
			board[0][8]=myPieces[37];
			myPieces[38].update_coordinates(1, 9);
			board[1][9]=myPieces[38];
			myPieces[39].update_coordinates(0, 9);
			board[0][9]=myPieces[39];
		}
	}
	/*else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[0];
			myPieces[1].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[1];
			myPieces[2].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[2];
			myPieces[3].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[3];
			myPieces[4].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[4];
			myPieces[5].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[5];
			myPieces[6].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[6];
			myPieces[7].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[7];
			myPieces[8].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[8];
			myPieces[9].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[9];
			myPieces[10].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[10];
			myPieces[11].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[11];
			myPieces[12].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[14];
			myPieces[15].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[15];
			myPieces[16].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[16];
			myPieces[17].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[17];
			myPieces[18].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[19];
			myPieces[20].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[20];
			myPieces[21].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[21];
			myPieces[22].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[22];
			myPieces[23].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[23];
			myPieces[24].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[24];
			myPieces[25].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[25];
			myPieces[26].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[26];
			myPieces[27].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[27];
			myPieces[28].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[28];
			myPieces[29].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[29];
			myPieces[30].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[30];
			myPieces[31].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[31];
			myPieces[32].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[33];
			myPieces[34].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[34];
			myPieces[35].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[35];
			myPieces[36].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[36];
			myPieces[37].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[37];
			myPieces[38].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(2, 3);
			board[2][3]=myPieces[0];
			myPieces[1].update_coordinates(1, 3);
			board[1][3]=myPieces[1];
			myPieces[2].update_coordinates(0, 2);
			board[0][2]=myPieces[2];
			myPieces[3].update_coordinates(4, 1);
			board[4][1]=myPieces[3];
			myPieces[4].update_coordinates(1, 1);
			board[1][1]=myPieces[4];
			myPieces[5].update_coordinates(5, 1);
			board[5][1]=myPieces[5];
			myPieces[6].update_coordinates(7, 1);
			board[7][1]=myPieces[6];
			myPieces[7].update_coordinates(3, 2);
			board[3][2]=myPieces[7];
			myPieces[8].update_coordinates(5, 0);
			board[5][0]=myPieces[8];
			myPieces[9].update_coordinates(8, 0);
			board[8][0]=myPieces[9];
			myPieces[10].update_coordinates(9, 2);
			board[9][2]=myPieces[10];
			myPieces[11].update_coordinates(5, 3);
			board[5][3]=myPieces[11];
			myPieces[12].update_coordinates(7, 3);
			board[7][3]=myPieces[12];
			myPieces[13].update_coordinates(8, 2);
			board[8][2]=myPieces[13];
			myPieces[14].update_coordinates(8, 3);
			board[8][3]=myPieces[14];
			myPieces[15].update_coordinates(4, 2);
			board[4][2]=myPieces[15];
			myPieces[16].update_coordinates(4, 3);
			board[4][3]=myPieces[16];
			myPieces[17].update_coordinates(6, 3);
			board[6][3]=myPieces[17];
			myPieces[18].update_coordinates(8, 1);
			board[8][1]=myPieces[18];
			myPieces[19].update_coordinates(2, 0);
			board[2][0]=myPieces[19];
			myPieces[20].update_coordinates(2, 1);
			board[2][1]=myPieces[20];
			myPieces[21].update_coordinates(3, 0);
			board[3][0]=myPieces[21];
			myPieces[22].update_coordinates(3, 1);
			board[3][1]=myPieces[22];
			myPieces[23].update_coordinates(4, 0);
			board[4][0]=myPieces[23];
			myPieces[24].update_coordinates(3, 3);
			board[3][3]=myPieces[24];
			myPieces[25].update_coordinates(5, 2);
			board[5][2]=myPieces[25];
			myPieces[26].update_coordinates(6, 0);
			board[6][0]=myPieces[26];
			myPieces[27].update_coordinates(6, 1);
			board[6][1]=myPieces[27];
			myPieces[28].update_coordinates(6, 2);
			board[6][2]=myPieces[28];
			myPieces[29].update_coordinates(7, 0);
			board[7][0]=myPieces[29];
			myPieces[30].update_coordinates(9, 0);
			board[9][0]=myPieces[30];
			myPieces[31].update_coordinates(9, 1);
			board[9][1]=myPieces[31];
			myPieces[32].update_coordinates(1, 2);
			board[1][2]=myPieces[32];
			myPieces[33].update_coordinates(0, 3);
			board[0][3]=myPieces[33];
			myPieces[34].update_coordinates(1, 0);
			board[1][0]=myPieces[34];
			myPieces[35].update_coordinates(2, 2);
			board[2][2]=myPieces[35];
			myPieces[36].update_coordinates(7, 2);
			board[7][2]=myPieces[36];
			myPieces[37].update_coordinates(9, 3);
			board[9][3]=myPieces[37];
			myPieces[38].update_coordinates(0, 1);
			board[0][1]=myPieces[38];
			myPieces[39].update_coordinates(0, 0);
			board[0][0]=myPieces[39];
		}
	}*/
}

void board3(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[0];
			myPieces[1].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[1];
			myPieces[2].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[2];
			myPieces[3].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[3];
			myPieces[4].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[4];
			myPieces[5].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[5];
			myPieces[6].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[6];
			myPieces[7].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[9];
			myPieces[10].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[10];
			myPieces[11].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[14];
			myPieces[15].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[15];
			myPieces[16].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[16];
			myPieces[17].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[17];
			myPieces[18].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[19];
			myPieces[20].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[20];
			myPieces[21].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[24];
			myPieces[25].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[30];
			myPieces[31].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[32];
			myPieces[33].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[33];
			myPieces[34].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[34];
			myPieces[35].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[35];
			myPieces[36].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[36];
			myPieces[37].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[37];
			myPieces[38].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(2, 7);
			board[2][7]=myPieces[0];
			myPieces[1].update_coordinates(7, 6);
			board[7][6]=myPieces[1];
			myPieces[2].update_coordinates(3, 6);
			board[3][6]=myPieces[2];
			myPieces[3].update_coordinates(0, 7);
			board[0][7]=myPieces[3];
			myPieces[4].update_coordinates(9, 7);
			board[9][7]=myPieces[4];
			myPieces[5].update_coordinates(2, 8);
			board[2][8]=myPieces[5];
			myPieces[6].update_coordinates(4, 9);
			board[4][9]=myPieces[6];
			myPieces[7].update_coordinates(0, 6);
			board[0][6]=myPieces[7];
			myPieces[8].update_coordinates(2, 6);
			board[2][6]=myPieces[8];
			myPieces[9].update_coordinates(4, 6);
			board[4][6]=myPieces[9];
			myPieces[10].update_coordinates(9, 6);
			board[9][6]=myPieces[10];
			myPieces[11].update_coordinates(1, 6);
			board[1][6]=myPieces[11];
			myPieces[12].update_coordinates(5, 6);
			board[5][6]=myPieces[12];
			myPieces[13].update_coordinates(6, 6);
			board[6][6]=myPieces[13];
			myPieces[14].update_coordinates(8, 6);
			board[8][6]=myPieces[14];
			myPieces[15].update_coordinates(8, 7);
			board[8][7]=myPieces[15];
			myPieces[16].update_coordinates(1, 8);
			board[1][8]=myPieces[16];
			myPieces[17].update_coordinates(0, 9);
			board[0][9]=myPieces[17];
			myPieces[18].update_coordinates(7, 9);
			board[7][9]=myPieces[18];
			myPieces[19].update_coordinates(7, 7);
			board[7][7]=myPieces[19];
			myPieces[20].update_coordinates(9, 8);
			board[9][8]=myPieces[20];
			myPieces[21].update_coordinates(1, 9);
			board[1][9]=myPieces[21];
			myPieces[22].update_coordinates(6, 9);
			board[6][9]=myPieces[22];
			myPieces[23].update_coordinates(9, 9);
			board[9][9]=myPieces[23];
			myPieces[24].update_coordinates(3, 7);
			board[3][7]=myPieces[24];
			myPieces[25].update_coordinates(4, 7);
			board[4][7]=myPieces[25];
			myPieces[26].update_coordinates(5, 7);
			board[5][7]=myPieces[26];
			myPieces[27].update_coordinates(4, 8);
			board[4][8]=myPieces[27];
			myPieces[28].update_coordinates(5, 8);
			board[5][8]=myPieces[28];
			myPieces[29].update_coordinates(8, 8);
			board[8][8]=myPieces[29];
			myPieces[30].update_coordinates(5, 9);
			board[5][9]=myPieces[30];
			myPieces[31].update_coordinates(8, 9);
			board[8][9]=myPieces[31];
			myPieces[32].update_coordinates(6, 8);
			board[6][8]=myPieces[32];
			myPieces[33].update_coordinates(1, 7);
			board[1][7]=myPieces[33];
			myPieces[34].update_coordinates(6, 7);
			board[6][7]=myPieces[34];
			myPieces[35].update_coordinates(0, 8);
			board[0][8]=myPieces[35];
			myPieces[36].update_coordinates(3, 8);
			board[3][8]=myPieces[36];
			myPieces[37].update_coordinates(7, 8);
			board[7][8]=myPieces[37];
			myPieces[38].update_coordinates(2, 9);
			board[2][9]=myPieces[38];
			myPieces[39].update_coordinates(3, 9);
			board[3][9]=myPieces[39];
		}
	}
	/*else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[0];
			myPieces[1].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[1];
			myPieces[2].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[2];
			myPieces[3].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[3];
			myPieces[4].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[4];
			myPieces[5].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[5];
			myPieces[6].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[6];
			myPieces[7].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[7];
			myPieces[8].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[8];
			myPieces[9].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[9];
			myPieces[10].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[10];
			myPieces[11].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[11];
			myPieces[12].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[13];
			myPieces[14].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[14];
			myPieces[15].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[15];
			myPieces[16].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[16];
			myPieces[17].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[17];
			myPieces[18].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[18];
			myPieces[19].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[19];
			myPieces[20].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[20];
			myPieces[21].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[21];
			myPieces[22].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[23];
			myPieces[24].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[24];
			myPieces[25].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[25];
			myPieces[26].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[27];
			myPieces[28].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[28];
			myPieces[29].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[29];
			myPieces[30].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[30];
			myPieces[31].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[31];
			myPieces[32].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[32];
			myPieces[33].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[33];
			myPieces[34].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[34];
			myPieces[35].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[35];
			myPieces[36].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[36];
			myPieces[37].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[37];
			myPieces[38].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[38];
			myPieces[39].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(2, 3);
			board[2][3]=myPieces[0];
			myPieces[1].update_coordinates(7, 2);
			board[7][2]=myPieces[1];
			myPieces[2].update_coordinates(5, 2);
			board[5][2]=myPieces[2];
			myPieces[3].update_coordinates(7, 3);
			board[7][3]=myPieces[3];
			myPieces[4].update_coordinates(0, 2);
			board[0][2]=myPieces[4];
			myPieces[5].update_coordinates(3, 2);
			board[3][2]=myPieces[5];
			myPieces[6].update_coordinates(6, 2);
			board[6][2]=myPieces[6];
			myPieces[7].update_coordinates(0, 1);
			board[0][1]=myPieces[7];
			myPieces[8].update_coordinates(7, 0);
			board[7][0]=myPieces[8];
			myPieces[9].update_coordinates(7, 1);
			board[7][1]=myPieces[9];
			myPieces[10].update_coordinates(8, 0);
			board[8][0]=myPieces[10];
			myPieces[11].update_coordinates(2, 1);
			board[2][1]=myPieces[11];
			myPieces[12].update_coordinates(6, 0);
			board[6][0]=myPieces[12];
			myPieces[13].update_coordinates(8, 3);
			board[8][3]=myPieces[13];
			myPieces[14].update_coordinates(9, 1);
			board[9][1]=myPieces[14];
			myPieces[15].update_coordinates(1, 0);
			board[1][0]=myPieces[15];
			myPieces[16].update_coordinates(1, 3);
			board[1][3]=myPieces[16];
			myPieces[17].update_coordinates(4, 2);
			board[4][2]=myPieces[17];
			myPieces[18].update_coordinates(5, 1);
			board[5][1]=myPieces[18];
			myPieces[19].update_coordinates(4, 1);
			board[4][1]=myPieces[19];
			myPieces[20].update_coordinates(5, 0);
			board[5][0]=myPieces[20];
			myPieces[21].update_coordinates(8, 1);
			board[8][1]=myPieces[21];
			myPieces[22].update_coordinates(8, 2);
			board[8][2]=myPieces[22];
			myPieces[23].update_coordinates(9, 0);
			board[9][0]=myPieces[23];
			myPieces[24].update_coordinates(0, 0);
			board[0][0]=myPieces[24];
			myPieces[25].update_coordinates(0, 3);
			board[0][3]=myPieces[25];
			myPieces[26].update_coordinates(3, 3);
			board[3][3]=myPieces[26];
			myPieces[27].update_coordinates(4, 3);
			board[4][3]=myPieces[27];
			myPieces[28].update_coordinates(6, 1);
			board[6][1]=myPieces[28];
			myPieces[29].update_coordinates(6, 3);
			board[6][3]=myPieces[29];
			myPieces[30].update_coordinates(9, 2);
			board[9][2]=myPieces[30];
			myPieces[31].update_coordinates(9, 3);
			board[9][3]=myPieces[31];
			myPieces[32].update_coordinates(2, 2);
			board[2][2]=myPieces[32];
			myPieces[33].update_coordinates(1, 1);
			board[1][1]=myPieces[33];
			myPieces[34].update_coordinates(1, 2);
			board[1][2]=myPieces[34];
			myPieces[35].update_coordinates(2, 0);
			board[2][0]=myPieces[35];
			myPieces[36].update_coordinates(3, 1);
			board[3][1]=myPieces[36];
			myPieces[37].update_coordinates(4, 0);
			board[4][0]=myPieces[37];
			myPieces[38].update_coordinates(5, 3);
			board[5][3]=myPieces[38];
			myPieces[39].update_coordinates(3, 0);
			board[3][0]=myPieces[39];
		}
	}*/
}

void board4(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[0];
			myPieces[1].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[1];
			myPieces[2].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[2];
			myPieces[3].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[3];
			myPieces[4].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[4];
			myPieces[5].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[5];
			myPieces[6].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[6];
			myPieces[7].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[9];
			myPieces[10].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[10];
			myPieces[11].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[13];
			myPieces[14].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[14];
			myPieces[15].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[15];
			myPieces[16].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[16];
			myPieces[17].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[17];
			myPieces[18].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[19];
			myPieces[20].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[20];
			myPieces[21].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[24];
			myPieces[25].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[30];
			myPieces[31].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[32];
			myPieces[33].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[33];
			myPieces[34].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[34];
			myPieces[35].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[35];
			myPieces[36].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[36];
			myPieces[37].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[37];
			myPieces[38].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-9, 9);
			board[9][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(7, 6);
			board[7][6]=myPieces[0];
			myPieces[1].update_coordinates(3, 7);
			board[3][7]=myPieces[1];
			myPieces[2].update_coordinates(0, 6);
			board[0][6]=myPieces[2];
			myPieces[3].update_coordinates(6, 6);
			board[6][6]=myPieces[3];
			myPieces[4].update_coordinates(8, 6);
			board[8][6]=myPieces[4];
			myPieces[5].update_coordinates(6, 8);
			board[6][8]=myPieces[5];
			myPieces[6].update_coordinates(4, 9);
			board[4][9]=myPieces[6];
			myPieces[7].update_coordinates(3, 6);
			board[3][6]=myPieces[7];
			myPieces[8].update_coordinates(5, 6);
			board[5][6]=myPieces[8];
			myPieces[9].update_coordinates(1, 7);
			board[1][7]=myPieces[9];
			myPieces[10].update_coordinates(1, 8);
			board[1][8]=myPieces[10];
			myPieces[11].update_coordinates(1, 6);
			board[1][6]=myPieces[11];
			myPieces[12].update_coordinates(9, 6);
			board[9][6]=myPieces[12];
			myPieces[13].update_coordinates(5, 7);
			board[5][7]=myPieces[13];
			myPieces[14].update_coordinates(9, 7);
			board[9][7]=myPieces[14];
			myPieces[15].update_coordinates(4, 6);
			board[4][6]=myPieces[15];
			myPieces[16].update_coordinates(2, 7);
			board[2][7]=myPieces[16];
			myPieces[17].update_coordinates(7, 7);
			board[7][7]=myPieces[17];
			myPieces[18].update_coordinates(2, 9);
			board[2][9]=myPieces[18];
			myPieces[19].update_coordinates(3, 8);
			board[3][8]=myPieces[19];
			myPieces[20].update_coordinates(7, 8);
			board[7][8]=myPieces[20];
			myPieces[21].update_coordinates(5, 9);
			board[5][9]=myPieces[21];
			myPieces[22].update_coordinates(6, 9);
			board[6][9]=myPieces[22];
			myPieces[23].update_coordinates(7, 9);
			board[7][9]=myPieces[23];
			myPieces[24].update_coordinates(0, 7);
			board[0][7]=myPieces[24];
			myPieces[25].update_coordinates(4, 7);
			board[4][7]=myPieces[25];
			myPieces[26].update_coordinates(8, 7);
			board[8][7]=myPieces[26];
			myPieces[27].update_coordinates(0, 8);
			board[0][8]=myPieces[27];
			myPieces[28].update_coordinates(4, 8);
			board[4][8]=myPieces[28];
			myPieces[29].update_coordinates(5, 8);
			board[5][8]=myPieces[29];
			myPieces[30].update_coordinates(8, 8);
			board[8][8]=myPieces[30];
			myPieces[31].update_coordinates(0, 9);
			board[0][9]=myPieces[31];
			myPieces[32].update_coordinates(2, 6);
			board[2][6]=myPieces[32];
			myPieces[33].update_coordinates(6, 7);
			board[6][7]=myPieces[33];
			myPieces[34].update_coordinates(2, 8);
			board[2][8]=myPieces[34];
			myPieces[35].update_coordinates(9, 8);
			board[9][8]=myPieces[35];
			myPieces[36].update_coordinates(1, 9);
			board[1][9]=myPieces[36];
			myPieces[37].update_coordinates(3, 9);
			board[3][9]=myPieces[37];
			myPieces[38].update_coordinates(8, 9);
			board[8][9]=myPieces[38];
			myPieces[39].update_coordinates(9, 9);
			board[9][9]=myPieces[39];
		}
	}
/*	else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[0];
			myPieces[1].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[1];
			myPieces[2].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[2];
			myPieces[3].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[3];
			myPieces[4].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[4];
			myPieces[5].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[5];
			myPieces[6].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[6];
			myPieces[7].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[7];
			myPieces[8].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[8];
			myPieces[9].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[9];
			myPieces[10].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[10];
			myPieces[11].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[11];
			myPieces[12].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[12];
			myPieces[13].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[14];
			myPieces[15].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[15];
			myPieces[16].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[16];
			myPieces[17].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[17];
			myPieces[18].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[18];
			myPieces[19].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[19];
			myPieces[20].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[20];
			myPieces[21].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[21];
			myPieces[22].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[22];
			myPieces[23].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[23];
			myPieces[24].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[24];
			myPieces[25].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[25];
			myPieces[26].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[27];
			myPieces[28].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[28];
			myPieces[29].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[29];
			myPieces[30].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[30];
			myPieces[31].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[31];
			myPieces[32].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[33];
			myPieces[34].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[34];
			myPieces[35].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[35];
			myPieces[36].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[36];
			myPieces[37].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[37];
			myPieces[38].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[38];
			myPieces[39].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(8, 2);
			board[8][2]=myPieces[0];
			myPieces[1].update_coordinates(2, 3);
			board[2][3]=myPieces[1];
			myPieces[2].update_coordinates(4, 2);
			board[4][2]=myPieces[2];
			myPieces[3].update_coordinates(8, 3);
			board[8][3]=myPieces[3];
			myPieces[4].update_coordinates(0, 1);
			board[0][1]=myPieces[4];
			myPieces[5].update_coordinates(1, 3);
			board[1][3]=myPieces[5];
			myPieces[6].update_coordinates(9, 0);
			board[9][0]=myPieces[6];
			myPieces[7].update_coordinates(3, 3);
			board[3][3]=myPieces[7];
			myPieces[8].update_coordinates(4, 1);
			board[4][1]=myPieces[8];
			myPieces[9].update_coordinates(5, 2);
			board[5][2]=myPieces[9];
			myPieces[10].update_coordinates(6, 3);
			board[6][3]=myPieces[10];
			myPieces[11].update_coordinates(2, 1);
			board[2][1]=myPieces[11];
			myPieces[12].update_coordinates(5, 0);
			board[5][0]=myPieces[12];
			myPieces[13].update_coordinates(6, 2);
			board[6][2]=myPieces[13];
			myPieces[14].update_coordinates(8, 1);
			board[8][1]=myPieces[14];
			myPieces[15].update_coordinates(3, 0);
			board[3][0]=myPieces[15];
			myPieces[16].update_coordinates(5, 3);
			board[5][3]=myPieces[16];
			myPieces[17].update_coordinates(9, 1);
			board[9][1]=myPieces[17];
			myPieces[18].update_coordinates(9, 3);
			board[9][3]=myPieces[18];
			myPieces[19].update_coordinates(3, 2);
			board[3][2]=myPieces[19];
			myPieces[20].update_coordinates(5, 1);
			board[5][1]=myPieces[20];
			myPieces[21].update_coordinates(6, 0);
			board[6][0]=myPieces[21];
			myPieces[22].update_coordinates(7, 2);
			board[7][2]=myPieces[22];
			myPieces[23].update_coordinates(8, 0);
			board[8][0]=myPieces[23];
			myPieces[24].update_coordinates(0, 3);
			board[0][3]=myPieces[24];
			myPieces[25].update_coordinates(1, 2);
			board[1][2]=myPieces[25];
			myPieces[26].update_coordinates(2, 2);
			board[2][2]=myPieces[26];
			myPieces[27].update_coordinates(4, 0);
			board[4][0]=myPieces[27];
			myPieces[28].update_coordinates(4, 3);
			board[4][3]=myPieces[28];
			myPieces[29].update_coordinates(7, 0);
			board[7][0]=myPieces[29];
			myPieces[30].update_coordinates(7, 3);
			board[7][3]=myPieces[30];
			myPieces[31].update_coordinates(9, 2);
			board[9][2]=myPieces[31];
			myPieces[32].update_coordinates(7, 1);
			board[7][1]=myPieces[32];
			myPieces[33].update_coordinates(0, 0);
			board[0][0]=myPieces[33];
			myPieces[34].update_coordinates(0, 2);
			board[0][2]=myPieces[34];
			myPieces[35].update_coordinates(1, 1);
			board[1][1]=myPieces[35];
			myPieces[36].update_coordinates(2, 0);
			board[2][0]=myPieces[36];
			myPieces[37].update_coordinates(3, 1);
			board[3][1]=myPieces[37];
			myPieces[38].update_coordinates(6, 1);
			board[6][1]=myPieces[38];
			myPieces[39].update_coordinates(1, 0);
			board[1][0]=myPieces[39];
		}
	}*/
}

void board5(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[0];
			myPieces[1].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[1];
			myPieces[2].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[2];
			myPieces[3].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[3];
			myPieces[4].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[4];
			myPieces[5].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[5];
			myPieces[6].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[6];
			myPieces[7].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[9];
			myPieces[10].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[10];
			myPieces[11].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[14];
			myPieces[15].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[15];
			myPieces[16].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[16];
			myPieces[17].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[17];
			myPieces[18].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[19];
			myPieces[20].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[20];
			myPieces[21].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[24];
			myPieces[25].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[26];
			myPieces[27].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[30];
			myPieces[31].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[32];
			myPieces[33].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[33];
			myPieces[34].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[34];
			myPieces[35].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[35];
			myPieces[36].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[36];
			myPieces[37].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[37];
			myPieces[38].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(2, 6);
			board[2][6]=myPieces[0];
			myPieces[1].update_coordinates(6, 6);
			board[6][6]=myPieces[1];
			myPieces[2].update_coordinates(0, 8);
			board[0][8]=myPieces[2];
			myPieces[3].update_coordinates(1, 8);
			board[1][8]=myPieces[3];
			myPieces[4].update_coordinates(3, 6);
			board[3][6]=myPieces[4];
			myPieces[5].update_coordinates(5, 7);
			board[5][7]=myPieces[5];
			myPieces[6].update_coordinates(3, 8);
			board[3][8]=myPieces[6];
			myPieces[7].update_coordinates(0, 6);
			board[0][6]=myPieces[7];
			myPieces[8].update_coordinates(4, 6);
			board[4][6]=myPieces[8];
			myPieces[9].update_coordinates(9, 6);
			board[9][6]=myPieces[9];
			myPieces[10].update_coordinates(0, 7);
			board[0][7]=myPieces[10];
			myPieces[11].update_coordinates(5, 6);
			board[5][6]=myPieces[11];
			myPieces[12].update_coordinates(8, 6);
			board[8][6]=myPieces[12];
			myPieces[13].update_coordinates(4, 7);
			board[4][7]=myPieces[13];
			myPieces[14].update_coordinates(8, 7);
			board[8][7]=myPieces[14];
			myPieces[15].update_coordinates(1, 6);
			board[1][6]=myPieces[15];
			myPieces[16].update_coordinates(7, 6);
			board[7][6]=myPieces[16];
			myPieces[17].update_coordinates(1, 7);
			board[1][7]=myPieces[17];
			myPieces[18].update_coordinates(9, 9);
			board[9][9]=myPieces[18];
			myPieces[19].update_coordinates(7, 7);
			board[7][7]=myPieces[19];
			myPieces[20].update_coordinates(0, 9);
			board[0][9]=myPieces[20];
			myPieces[21].update_coordinates(5, 9);
			board[5][9]=myPieces[21];
			myPieces[22].update_coordinates(6, 9);
			board[6][9]=myPieces[22];
			myPieces[23].update_coordinates(7, 9);
			board[7][9]=myPieces[23];
			myPieces[24].update_coordinates(3, 7);
			board[3][7]=myPieces[24];
			myPieces[25].update_coordinates(9, 7);
			board[9][7]=myPieces[25];
			myPieces[26].update_coordinates(4, 8);
			board[4][8]=myPieces[26];
			myPieces[27].update_coordinates(5, 8);
			board[5][8]=myPieces[27];
			myPieces[28].update_coordinates(6, 8);
			board[6][8]=myPieces[28];
			myPieces[29].update_coordinates(7, 8);
			board[7][8]=myPieces[29];
			myPieces[30].update_coordinates(8, 8);
			board[8][8]=myPieces[30];
			myPieces[31].update_coordinates(4, 9);
			board[4][9]=myPieces[31];
			myPieces[32].update_coordinates(6, 7);
			board[6][7]=myPieces[32];
			myPieces[33].update_coordinates(2, 7);
			board[2][7]=myPieces[33];
			myPieces[34].update_coordinates(2, 8);
			board[2][8]=myPieces[34];
			myPieces[35].update_coordinates(9, 8);
			board[9][8]=myPieces[35];
			myPieces[36].update_coordinates(1, 9);
			board[1][9]=myPieces[36];
			myPieces[37].update_coordinates(3, 9);
			board[3][9]=myPieces[37];
			myPieces[38].update_coordinates(8, 9);
			board[8][9]=myPieces[38];
			myPieces[39].update_coordinates(2, 9);
			board[2][9]=myPieces[39];
		}
	}
	/*else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[0];
			myPieces[1].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[1];
			myPieces[2].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[2];
			myPieces[3].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[3];
			myPieces[4].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[4];
			myPieces[5].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[5];
			myPieces[6].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[6];
			myPieces[7].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[7];
			myPieces[8].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[8];
			myPieces[9].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[9];
			myPieces[10].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[10];
			myPieces[11].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[11];
			myPieces[12].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[12];
			myPieces[13].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[13];
			myPieces[14].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[14];
			myPieces[15].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[15];
			myPieces[16].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[16];
			myPieces[17].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[17];
			myPieces[18].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[18];
			myPieces[19].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[19];
			myPieces[20].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[20];
			myPieces[21].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[21];
			myPieces[22].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[22];
			myPieces[23].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[23];
			myPieces[24].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[24];
			myPieces[25].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[25];
			myPieces[26].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[26];
			myPieces[27].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[27];
			myPieces[28].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[28];
			myPieces[29].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[29];
			myPieces[30].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[30];
			myPieces[31].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[31];
			myPieces[32].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[32];
			myPieces[33].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[33];
			myPieces[34].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[34];
			myPieces[35].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[35];
			myPieces[36].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[36];
			myPieces[37].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[37];
			myPieces[38].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[38];
			myPieces[39].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(5, 2);
			board[5][2]=myPieces[0];
			myPieces[1].update_coordinates(6, 0);
			board[6][0]=myPieces[1];
			myPieces[2].update_coordinates(0, 1);
			board[0][1]=myPieces[2];
			myPieces[3].update_coordinates(4, 1);
			board[4][1]=myPieces[3];
			myPieces[4].update_coordinates(1, 2);
			board[1][2]=myPieces[4];
			myPieces[5].update_coordinates(2, 3);
			board[2][3]=myPieces[5];
			myPieces[6].update_coordinates(7, 3);
			board[7][3]=myPieces[6];
			myPieces[7].update_coordinates(2, 1);
			board[2][1]=myPieces[7];
			myPieces[8].update_coordinates(4, 3);
			board[4][3]=myPieces[8];
			myPieces[9].update_coordinates(7, 2);
			board[7][2]=myPieces[9];
			myPieces[10].update_coordinates(9, 0);
			board[9][0]=myPieces[10];
			myPieces[11].update_coordinates(0, 0);
			board[0][0]=myPieces[11];
			myPieces[12].update_coordinates(0, 3);
			board[0][3]=myPieces[12];
			myPieces[13].update_coordinates(1, 1);
			board[1][1]=myPieces[13];
			myPieces[14].update_coordinates(5, 0);
			board[5][0]=myPieces[14];
			myPieces[15].update_coordinates(3, 3);
			board[3][3]=myPieces[15];
			myPieces[16].update_coordinates(5, 3);
			board[5][3]=myPieces[16];
			myPieces[17].update_coordinates(6, 1);
			board[6][1]=myPieces[17];
			myPieces[18].update_coordinates(8, 2);
			board[8][2]=myPieces[18];
			myPieces[19].update_coordinates(0, 2);
			board[0][2]=myPieces[19];
			myPieces[20].update_coordinates(1, 0);
			board[1][0]=myPieces[20];
			myPieces[21].update_coordinates(3, 2);
			board[3][2]=myPieces[21];
			myPieces[22].update_coordinates(4, 2);
			board[4][2]=myPieces[22];
			myPieces[23].update_coordinates(7, 1);
			board[7][1]=myPieces[23];
			myPieces[24].update_coordinates(1, 3);
			board[1][3]=myPieces[24];
			myPieces[25].update_coordinates(6, 2);
			board[6][2]=myPieces[25];
			myPieces[26].update_coordinates(6, 3);
			board[6][3]=myPieces[26];
			myPieces[27].update_coordinates(7, 0);
			board[7][0]=myPieces[27];
			myPieces[28].update_coordinates(8, 1);
			board[8][1]=myPieces[28];
			myPieces[29].update_coordinates(8, 3);
			board[8][3]=myPieces[29];
			myPieces[30].update_coordinates(9, 2);
			board[9][2]=myPieces[30];
			myPieces[31].update_coordinates(9, 3);
			board[9][3]=myPieces[31];
			myPieces[32].update_coordinates(5, 1);
			board[5][1]=myPieces[32];
			myPieces[33].update_coordinates(2, 0);
			board[2][0]=myPieces[33];
			myPieces[34].update_coordinates(2, 2);
			board[2][2]=myPieces[34];
			myPieces[35].update_coordinates(3, 1);
			board[3][1]=myPieces[35];
			myPieces[36].update_coordinates(4, 0);
			board[4][0]=myPieces[36];
			myPieces[37].update_coordinates(8, 0);
			board[8][0]=myPieces[37];
			myPieces[38].update_coordinates(9, 1);
			board[9][1]=myPieces[38];
			myPieces[39].update_coordinates(3, 0);
			board[3][0]=myPieces[39];
		}
	}*/
}

void board6(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[0];
			myPieces[1].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[1];
			myPieces[2].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[2];
			myPieces[3].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[3];
			myPieces[4].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[4];
			myPieces[5].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[5];
			myPieces[6].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[6];
			myPieces[7].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[9];
			myPieces[10].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[10];
			myPieces[11].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[14];
			myPieces[15].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[15];
			myPieces[16].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[16];
			myPieces[17].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[17];
			myPieces[18].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[19];
			myPieces[20].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[20];
			myPieces[21].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[24];
			myPieces[25].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[30];
			myPieces[31].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[32];
			myPieces[33].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[33];
			myPieces[34].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[34];
			myPieces[35].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[35];
			myPieces[36].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[36];
			myPieces[37].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[37];
			myPieces[38].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(3, 7);
			board[3][7]=myPieces[0];
			myPieces[1].update_coordinates(9, 7);
			board[9][7]=myPieces[1];
			myPieces[2].update_coordinates(6, 6);
			board[6][6]=myPieces[2];
			myPieces[3].update_coordinates(1, 8);
			board[1][8]=myPieces[3];
			myPieces[4].update_coordinates(9, 6);
			board[9][6]=myPieces[4];
			myPieces[5].update_coordinates(2, 8);
			board[2][8]=myPieces[5];
			myPieces[6].update_coordinates(5, 8);
			board[5][8]=myPieces[6];
			myPieces[7].update_coordinates(0, 6);
			board[0][6]=myPieces[7];
			myPieces[8].update_coordinates(4, 6);
			board[4][6]=myPieces[8];
			myPieces[9].update_coordinates(7, 6);
			board[7][6]=myPieces[9];
			myPieces[10].update_coordinates(1, 7);
			board[1][7]=myPieces[10];
			myPieces[11].update_coordinates(1, 6);
			board[1][6]=myPieces[11];
			myPieces[12].update_coordinates(3, 6);
			board[3][6]=myPieces[12];
			myPieces[13].update_coordinates(5, 6);
			board[5][6]=myPieces[13];
			myPieces[14].update_coordinates(8, 6);
			board[8][6]=myPieces[14];
			myPieces[15].update_coordinates(8, 7);
			board[8][7]=myPieces[15];
			myPieces[16].update_coordinates(7, 8);
			board[7][8]=myPieces[16];
			myPieces[17].update_coordinates(0, 9);
			board[0][9]=myPieces[17];
			myPieces[18].update_coordinates(6, 9);
			board[6][9]=myPieces[18];
			myPieces[19].update_coordinates(2, 6);
			board[2][6]=myPieces[19];
			myPieces[20].update_coordinates(7, 7);
			board[7][7]=myPieces[20];
			myPieces[21].update_coordinates(3, 9);
			board[3][9]=myPieces[21];
			myPieces[22].update_coordinates(5, 9);
			board[5][9]=myPieces[22];
			myPieces[23].update_coordinates(9, 9);
			board[9][9]=myPieces[23];
			myPieces[24].update_coordinates(0, 7);
			board[0][7]=myPieces[24];
			myPieces[25].update_coordinates(4, 7);
			board[4][7]=myPieces[25];
			myPieces[26].update_coordinates(0, 8);
			board[0][8]=myPieces[26];
			myPieces[27].update_coordinates(4, 8);
			board[4][8]=myPieces[27];
			myPieces[28].update_coordinates(8, 8);
			board[8][8]=myPieces[28];
			myPieces[29].update_coordinates(9, 8);
			board[9][8]=myPieces[29];
			myPieces[30].update_coordinates(4, 9);
			board[4][9]=myPieces[30];
			myPieces[31].update_coordinates(8, 9);
			board[8][9]=myPieces[31];
			myPieces[32].update_coordinates(6, 7);
			board[6][7]=myPieces[32];
			myPieces[33].update_coordinates(2, 7);
			board[2][7]=myPieces[33];
			myPieces[34].update_coordinates(5, 7);
			board[5][7]=myPieces[34];
			myPieces[35].update_coordinates(3, 8);
			board[3][8]=myPieces[35];
			myPieces[36].update_coordinates(6, 8);
			board[6][8]=myPieces[36];
			myPieces[37].update_coordinates(1, 9);
			board[1][9]=myPieces[37];
			myPieces[38].update_coordinates(7, 9);
			board[7][9]=myPieces[38];
			myPieces[39].update_coordinates(2, 9);
			board[2][9]=myPieces[39];
		}
	}
/*	else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[0];
			myPieces[1].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[1];
			myPieces[2].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[2];
			myPieces[3].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[3];
			myPieces[4].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[4];
			myPieces[5].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[5];
			myPieces[6].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[6];
			myPieces[7].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[7];
			myPieces[8].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[8];
			myPieces[9].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[9];
			myPieces[10].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[10];
			myPieces[11].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[11];
			myPieces[12].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[13];
			myPieces[14].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[14];
			myPieces[15].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[15];
			myPieces[16].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[16];
			myPieces[17].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[17];
			myPieces[18].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[19];
			myPieces[20].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[20];
			myPieces[21].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[21];
			myPieces[22].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[22];
			myPieces[23].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[23];
			myPieces[24].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[24];
			myPieces[25].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[25];
			myPieces[26].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[26];
			myPieces[27].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[27];
			myPieces[28].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[28];
			myPieces[29].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[29];
			myPieces[30].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[30];
			myPieces[31].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[31];
			myPieces[32].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[33];
			myPieces[34].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[34];
			myPieces[35].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[35];
			myPieces[36].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[36];
			myPieces[37].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[37];
			myPieces[38].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(1, 1);
			board[1][1]=myPieces[0];
			myPieces[1].update_coordinates(0, 2);
			board[0][2]=myPieces[1];
			myPieces[2].update_coordinates(1, 2);
			board[1][2]=myPieces[2];
			myPieces[3].update_coordinates(5, 2);
			board[5][2]=myPieces[3];
			myPieces[4].update_coordinates(3, 3);
			board[3][3]=myPieces[4];
			myPieces[5].update_coordinates(5, 1);
			board[5][1]=myPieces[5];
			myPieces[6].update_coordinates(8, 2);
			board[8][2]=myPieces[6];
			myPieces[7].update_coordinates(4, 2);
			board[4][2]=myPieces[7];
			myPieces[8].update_coordinates(6, 1);
			board[6][1]=myPieces[8];
			myPieces[9].update_coordinates(6, 2);
			board[6][2]=myPieces[9];
			myPieces[10].update_coordinates(7, 2);
			board[7][2]=myPieces[10];
			myPieces[11].update_coordinates(2, 1);
			board[2][1]=myPieces[11];
			myPieces[12].update_coordinates(7, 1);
			board[7][1]=myPieces[12];
			myPieces[13].update_coordinates(8, 1);
			board[8][1]=myPieces[13];
			myPieces[14].update_coordinates(9, 1);
			board[9][1]=myPieces[14];
			myPieces[15].update_coordinates(6, 3);
			board[6][3]=myPieces[15];
			myPieces[16].update_coordinates(7, 3);
			board[7][3]=myPieces[16];
			myPieces[17].update_coordinates(9, 0);
			board[9][0]=myPieces[17];
			myPieces[18].update_coordinates(9, 2);
			board[9][2]=myPieces[18];
			myPieces[19].update_coordinates(2, 3);
			board[2][3]=myPieces[19];
			myPieces[20].update_coordinates(3, 1);
			board[3][1]=myPieces[20];
			myPieces[21].update_coordinates(3, 2);
			board[3][2]=myPieces[21];
			myPieces[22].update_coordinates(4, 1);
			board[4][1]=myPieces[22];
			myPieces[23].update_coordinates(8, 0);
			board[8][0]=myPieces[23];
			myPieces[24].update_coordinates(8, 3);
			board[8][3]=myPieces[24];
			myPieces[25].update_coordinates(9, 3);
			board[9][3]=myPieces[25];
			myPieces[26].update_coordinates(2, 0);
			board[2][0]=myPieces[26];
			myPieces[27].update_coordinates(3, 0);
			board[3][0]=myPieces[27];
			myPieces[28].update_coordinates(4, 0);
			board[4][0]=myPieces[28];
			myPieces[29].update_coordinates(5, 0);
			board[5][0]=myPieces[29];
			myPieces[30].update_coordinates(6, 0);
			board[6][0]=myPieces[30];
			myPieces[31].update_coordinates(7, 0);
			board[7][0]=myPieces[31];
			myPieces[32].update_coordinates(2, 2);
			board[2][2]=myPieces[32];
			myPieces[33].update_coordinates(0, 1);
			board[0][1]=myPieces[33];
			myPieces[34].update_coordinates(1, 0);
			board[1][0]=myPieces[34];
			myPieces[35].update_coordinates(4, 3);
			board[4][3]=myPieces[35];
			myPieces[36].update_coordinates(5, 3);
			board[5][3]=myPieces[36];
			myPieces[37].update_coordinates(0, 3);
			board[0][3]=myPieces[37];
			myPieces[38].update_coordinates(1, 3);
			board[1][3]=myPieces[38];
			myPieces[39].update_coordinates(0, 0);
			board[0][0]=myPieces[39];
		}
	}*/
}

void board7(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[0];
			myPieces[1].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[1];
			myPieces[2].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[2];
			myPieces[3].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[3];
			myPieces[4].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[4];
			myPieces[5].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[5];
			myPieces[6].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[6];
			myPieces[7].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[9];
			myPieces[10].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[10];
			myPieces[11].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[13];
			myPieces[14].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[14];
			myPieces[15].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[15];
			myPieces[16].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[16];
			myPieces[17].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[17];
			myPieces[18].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[19];
			myPieces[20].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[20];
			myPieces[21].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[21];
			myPieces[22].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[24];
			myPieces[25].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[30];
			myPieces[31].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[33];
			myPieces[34].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[34];
			myPieces[35].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[35];
			myPieces[36].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[36];
			myPieces[37].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[37];
			myPieces[38].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(3, 6);
			board[3][6]=myPieces[0];
			myPieces[1].update_coordinates(7, 7);
			board[7][7]=myPieces[1];
			myPieces[2].update_coordinates(0, 6);
			board[0][6]=myPieces[2];
			myPieces[3].update_coordinates(3, 9);
			board[3][9]=myPieces[3];
			myPieces[4].update_coordinates(7, 6);
			board[7][6]=myPieces[4];
			myPieces[5].update_coordinates(2, 7);
			board[2][7]=myPieces[5];
			myPieces[6].update_coordinates(9, 7);
			board[9][7]=myPieces[6];
			myPieces[7].update_coordinates(4, 6);
			board[4][6]=myPieces[7];
			myPieces[8].update_coordinates(8, 6);
			board[8][6]=myPieces[8];
			myPieces[9].update_coordinates(0, 7);
			board[0][7]=myPieces[9];
			myPieces[10].update_coordinates(2, 8);
			board[2][8]=myPieces[10];
			myPieces[11].update_coordinates(2, 6);
			board[2][6]=myPieces[11];
			myPieces[12].update_coordinates(6, 6);
			board[6][6]=myPieces[12];
			myPieces[13].update_coordinates(8, 7);
			board[8][7]=myPieces[13];
			myPieces[14].update_coordinates(6, 9);
			board[6][9]=myPieces[14];
			myPieces[15].update_coordinates(5, 6);
			board[5][6]=myPieces[15];
			myPieces[16].update_coordinates(6, 7);
			board[6][7]=myPieces[16];
			myPieces[17].update_coordinates(5, 8);
			board[5][8]=myPieces[17];
			myPieces[18].update_coordinates(7, 9);
			board[7][9]=myPieces[18];
			myPieces[19].update_coordinates(0, 8);
			board[0][8]=myPieces[19];
			myPieces[20].update_coordinates(6, 8);
			board[6][8]=myPieces[20];
			myPieces[21].update_coordinates(9, 8);
			board[9][8]=myPieces[21];
			myPieces[22].update_coordinates(8, 9);
			board[8][9]=myPieces[22];
			myPieces[23].update_coordinates(9, 9);
			board[9][9]=myPieces[23];
			myPieces[24].update_coordinates(1, 6);
			board[1][6]=myPieces[24];
			myPieces[25].update_coordinates(1, 7);
			board[1][7]=myPieces[25];
			myPieces[26].update_coordinates(4, 7);
			board[4][7]=myPieces[26];
			myPieces[27].update_coordinates(4, 8);
			board[4][8]=myPieces[27];
			myPieces[28].update_coordinates(7, 8);
			board[7][8]=myPieces[28];
			myPieces[29].update_coordinates(8, 8);
			board[8][8]=myPieces[29];
			myPieces[30].update_coordinates(4, 9);
			board[4][9]=myPieces[30];
			myPieces[31].update_coordinates(5, 9);
			board[5][9]=myPieces[31];
			myPieces[32].update_coordinates(3, 8);
			board[3][8]=myPieces[32];
			myPieces[33].update_coordinates(0, 9);
			board[0][9]=myPieces[33];
			myPieces[34].update_coordinates(3, 7);
			board[3][7]=myPieces[34];
			myPieces[35].update_coordinates(5, 7);
			board[5][7]=myPieces[35];
			myPieces[36].update_coordinates(1, 8);
			board[1][8]=myPieces[36];
			myPieces[37].update_coordinates(0, 9);
			board[0][9]=myPieces[37];
			myPieces[38].update_coordinates(2, 9);
			board[2][9]=myPieces[38];
			myPieces[39].update_coordinates(1, 9);
			board[1][9]=myPieces[39];
		}
	}
/*	else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[0];
			myPieces[1].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[1];
			myPieces[2].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[2];
			myPieces[3].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[3];
			myPieces[4].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[4];
			myPieces[5].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[5];
			myPieces[6].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[6];
			myPieces[7].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[7];
			myPieces[8].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[8];
			myPieces[9].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[9];
			myPieces[10].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[10];
			myPieces[11].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[11];
			myPieces[12].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[14];
			myPieces[15].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[15];
			myPieces[16].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[16];
			myPieces[17].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[17];
			myPieces[18].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[18];
			myPieces[19].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[19];
			myPieces[20].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[20];
			myPieces[21].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[21];
			myPieces[22].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[23];
			myPieces[24].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[24];
			myPieces[25].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[25];
			myPieces[26].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[27];
			myPieces[28].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[28];
			myPieces[29].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[29];
			myPieces[30].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[30];
			myPieces[31].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[31];
			myPieces[32].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[33];
			myPieces[34].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[34];
			myPieces[35].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[35];
			myPieces[36].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[36];
			myPieces[37].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[37];
			myPieces[38].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(7, 2);
			board[7][2]=myPieces[0];
			myPieces[1].update_coordinates(2, 3);
			board[2][3]=myPieces[1];
			myPieces[2].update_coordinates(2, 1);
			board[2][1]=myPieces[2];
			myPieces[3].update_coordinates(5, 3);
			board[5][3]=myPieces[3];
			myPieces[4].update_coordinates(2, 2);
			board[2][2]=myPieces[4];
			myPieces[5].update_coordinates(3, 0);
			board[3][0]=myPieces[5];
			myPieces[6].update_coordinates(9, 2);
			board[9][2]=myPieces[6];
			myPieces[7].update_coordinates(0, 3);
			board[0][3]=myPieces[7];
			myPieces[8].update_coordinates(3, 2);
			board[3][2]=myPieces[8];
			myPieces[9].update_coordinates(5, 1);
			board[5][1]=myPieces[9];
			myPieces[10].update_coordinates(6, 0);
			board[6][0]=myPieces[10];
			myPieces[11].update_coordinates(3, 1);
			board[3][1]=myPieces[11];
			myPieces[12].update_coordinates(3, 3);
			board[3][3]=myPieces[12];
			myPieces[13].update_coordinates(8, 0);
			board[8][0]=myPieces[13];
			myPieces[14].update_coordinates(8, 3);
			board[8][3]=myPieces[14];
			myPieces[15].update_coordinates(0, 1);
			board[0][1]=myPieces[15];
			myPieces[16].update_coordinates(1, 0);
			board[1][0]=myPieces[16];
			myPieces[17].update_coordinates(5, 0);
			board[5][0]=myPieces[17];
			myPieces[18].update_coordinates(7, 3);
			board[7][3]=myPieces[18];
			myPieces[19].update_coordinates(4, 0);
			board[4][0]=myPieces[19];
			myPieces[20].update_coordinates(6, 2);
			board[6][2]=myPieces[20];
			myPieces[21].update_coordinates(7, 0);
			board[7][0]=myPieces[21];
			myPieces[22].update_coordinates(8, 2);
			board[8][2]=myPieces[22];
			myPieces[23].update_coordinates(9, 1);
			board[9][1]=myPieces[23];
			myPieces[24].update_coordinates(1, 2);
			board[1][2]=myPieces[24];
			myPieces[25].update_coordinates(1, 3);
			board[1][3]=myPieces[25];
			myPieces[26].update_coordinates(4, 2);
			board[4][2]=myPieces[26];
			myPieces[27].update_coordinates(4, 3);
			board[4][3]=myPieces[27];
			myPieces[28].update_coordinates(5, 2);
			board[5][2]=myPieces[28];
			myPieces[29].update_coordinates(6, 3);
			board[6][3]=myPieces[29];
			myPieces[30].update_coordinates(8, 1);
			board[8][1]=myPieces[30];
			myPieces[31].update_coordinates(9, 3);
			board[9][3]=myPieces[31];
			myPieces[32].update_coordinates(6, 1);
			board[6][1]=myPieces[32];
			myPieces[33].update_coordinates(0, 2);
			board[0][2]=myPieces[33];
			myPieces[34].update_coordinates(1, 1);
			board[1][1]=myPieces[34];
			myPieces[35].update_coordinates(2, 0);
			board[2][0]=myPieces[35];
			myPieces[36].update_coordinates(4, 1);
			board[4][1]=myPieces[36];
			myPieces[37].update_coordinates(7, 1);
			board[7][1]=myPieces[37];
			myPieces[38].update_coordinates(9, 0);
			board[9][0]=myPieces[38];
			myPieces[39].update_coordinates(0, 0);
			board[0][0]=myPieces[39];
		}
	}*/
}

void board8(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[0];
			myPieces[1].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[1];
			myPieces[2].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[2];
			myPieces[3].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[3];
			myPieces[4].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[4];
			myPieces[5].update_coordinates(flipped-1, 8);
			board[flipped-1][8]=myPieces[5];
			myPieces[6].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[6];
			myPieces[7].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[9];
			myPieces[10].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[10];
			myPieces[11].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[12];
			myPieces[13].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[14];
			myPieces[15].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[15];
			myPieces[16].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[16];
			myPieces[17].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[17];
			myPieces[18].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[19];
			myPieces[20].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[20];
			myPieces[21].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[24];
			myPieces[25].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[30];
			myPieces[31].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[31];
			myPieces[32].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[33];
			myPieces[34].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[34];
			myPieces[35].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[35];
			myPieces[36].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[36];
			myPieces[37].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[37];
			myPieces[38].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(3, 7);
			board[3][7]=myPieces[0];
			myPieces[1].update_coordinates(2, 6);
			board[2][6]=myPieces[1];
			myPieces[2].update_coordinates(6, 6);
			board[6][6]=myPieces[2];
			myPieces[3].update_coordinates(9, 6);
			board[9][6]=myPieces[3];
			myPieces[4].update_coordinates(5, 6);
			board[5][6]=myPieces[4];
			myPieces[5].update_coordinates(1, 8);
			board[1][8]=myPieces[5];
			myPieces[6].update_coordinates(4, 8);
			board[4][8]=myPieces[6];
			myPieces[7].update_coordinates(1, 6);
			board[1][6]=myPieces[7];
			myPieces[8].update_coordinates(8, 6);
			board[8][6]=myPieces[8];
			myPieces[9].update_coordinates(0, 7);
			board[0][7]=myPieces[9];
			myPieces[10].update_coordinates(1, 7);
			board[1][7]=myPieces[10];
			myPieces[11].update_coordinates(7, 6);
			board[7][6]=myPieces[11];
			myPieces[12].update_coordinates(2, 7);
			board[2][7]=myPieces[12];
			myPieces[13].update_coordinates(5, 7);
			board[5][7]=myPieces[13];
			myPieces[14].update_coordinates(8, 7);
			board[8][7]=myPieces[14];
			myPieces[15].update_coordinates(3, 6);
			board[3][6]=myPieces[15];
			myPieces[16].update_coordinates(9, 7);
			board[9][7]=myPieces[16];
			myPieces[17].update_coordinates(1, 9);
			board[1][9]=myPieces[17];
			myPieces[18].update_coordinates(5, 9);
			board[5][9]=myPieces[18];
			myPieces[19].update_coordinates(0, 8);
			board[0][8]=myPieces[19];
			myPieces[20].update_coordinates(0, 9);
			board[0][9]=myPieces[20];
			myPieces[21].update_coordinates(6, 9);
			board[6][9]=myPieces[21];
			myPieces[22].update_coordinates(7, 9);
			board[7][9]=myPieces[22];
			myPieces[23].update_coordinates(9, 9);
			board[9][9]=myPieces[23];
			myPieces[24].update_coordinates(4, 7);
			board[4][7]=myPieces[24];
			myPieces[25].update_coordinates(6, 7);
			board[6][7]=myPieces[25];
			myPieces[26].update_coordinates(7, 7);
			board[7][7]=myPieces[26];
			myPieces[27].update_coordinates(6, 8);
			board[6][8]=myPieces[27];
			myPieces[28].update_coordinates(7, 8);
			board[7][8]=myPieces[28];
			myPieces[29].update_coordinates(8, 8);
			board[8][8]=myPieces[29];
			myPieces[30].update_coordinates(9, 8);
			board[9][8]=myPieces[30];
			myPieces[31].update_coordinates(8, 9);
			board[8][9]=myPieces[31];
			myPieces[32].update_coordinates(2, 8);
			board[2][8]=myPieces[32];
			myPieces[33].update_coordinates(0, 6);
			board[0][6]=myPieces[33];
			myPieces[34].update_coordinates(4, 6);
			board[4][6]=myPieces[34];
			myPieces[35].update_coordinates(3, 8);
			board[3][8]=myPieces[35];
			myPieces[36].update_coordinates(5, 8);
			board[5][8]=myPieces[36];
			myPieces[37].update_coordinates(2, 9);
			board[2][9]=myPieces[37];
			myPieces[38].update_coordinates(4, 9);
			board[4][9]=myPieces[38];
			myPieces[39].update_coordinates(3, 9);
			board[3][9]=myPieces[39];
		}
	}
	/*else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[0];
			myPieces[1].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[1];
			myPieces[2].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[2];
			myPieces[3].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[3];
			myPieces[4].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[4];
			myPieces[5].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[5];
			myPieces[6].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[6];
			myPieces[7].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[7];
			myPieces[8].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[8];
			myPieces[9].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[9];
			myPieces[10].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[10];
			myPieces[11].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[11];
			myPieces[12].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[13];
			myPieces[14].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[14];
			myPieces[15].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[15];
			myPieces[16].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[16];
			myPieces[17].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[17];
			myPieces[18].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[19];
			myPieces[20].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[20];
			myPieces[21].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[21];
			myPieces[22].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[22];
			myPieces[23].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[23];
			myPieces[24].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[24];
			myPieces[25].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[25];
			myPieces[26].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[26];
			myPieces[27].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[27];
			myPieces[28].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[28];
			myPieces[29].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[29];
			myPieces[30].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[30];
			myPieces[31].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[31];
			myPieces[32].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[33];
			myPieces[34].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[34];
			myPieces[35].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[35];
			myPieces[36].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[36];
			myPieces[37].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[37];
			myPieces[38].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(5, 2);
			board[5][2]=myPieces[0];
			myPieces[1].update_coordinates(1, 3);
			board[1][3]=myPieces[1];
			myPieces[2].update_coordinates(3, 3);
			board[3][3]=myPieces[2];
			myPieces[3].update_coordinates(7, 3);
			board[7][3]=myPieces[3];
			myPieces[4].update_coordinates(4, 2);
			board[4][2]=myPieces[4];
			myPieces[5].update_coordinates(8, 2);
			board[8][2]=myPieces[5];
			myPieces[6].update_coordinates(9, 0);
			board[9][0]=myPieces[6];
			myPieces[7].update_coordinates(3, 2);
			board[3][2]=myPieces[7];
			myPieces[8].update_coordinates(4, 0);
			board[4][0]=myPieces[8];
			myPieces[9].update_coordinates(7, 1);
			board[7][1]=myPieces[9];
			myPieces[10].update_coordinates(9, 1);
			board[9][1]=myPieces[10];
			myPieces[11].update_coordinates(5, 1);
			board[5][1]=myPieces[11];
			myPieces[12].update_coordinates(5, 3);
			board[5][3]=myPieces[12];
			myPieces[13].update_coordinates(8, 0);
			board[8][0]=myPieces[13];
			myPieces[14].update_coordinates(9, 3);
			board[9][3]=myPieces[14];
			myPieces[15].update_coordinates(0, 2);
			board[0][2]=myPieces[15];
			myPieces[16].update_coordinates(1, 1);
			board[1][1]=myPieces[16];
			myPieces[17].update_coordinates(2, 0);
			board[2][0]=myPieces[17];
			myPieces[18].update_coordinates(6, 1);
			board[6][1]=myPieces[18];
			myPieces[19].update_coordinates(2, 2);
			board[2][2]=myPieces[19];
			myPieces[20].update_coordinates(5, 0);
			board[5][0]=myPieces[20];
			myPieces[21].update_coordinates(6, 0);
			board[6][0]=myPieces[21];
			myPieces[22].update_coordinates(6, 3);
			board[6][3]=myPieces[22];
			myPieces[23].update_coordinates(7, 0);
			board[7][0]=myPieces[23];
			myPieces[24].update_coordinates(3, 1);
			board[3][1]=myPieces[24];
			myPieces[25].update_coordinates(4, 1);
			board[4][1]=myPieces[25];
			myPieces[26].update_coordinates(4, 3);
			board[4][3]=myPieces[26];
			myPieces[27].update_coordinates(6, 2);
			board[6][2]=myPieces[27];
			myPieces[28].update_coordinates(7, 2);
			board[7][2]=myPieces[28];
			myPieces[29].update_coordinates(8, 1);
			board[8][1]=myPieces[29];
			myPieces[30].update_coordinates(8, 3);
			board[8][3]=myPieces[30];
			myPieces[31].update_coordinates(9, 2);
			board[9][2]=myPieces[31];
			myPieces[32].update_coordinates(2, 3);
			board[2][3]=myPieces[32];
			myPieces[33].update_coordinates(0, 1);
			board[0][1]=myPieces[33];
			myPieces[34].update_coordinates(1, 0);
			board[1][0]=myPieces[34];
			myPieces[35].update_coordinates(0, 3);
			board[0][3]=myPieces[35];
			myPieces[36].update_coordinates(1, 2);
			board[1][2]=myPieces[36];
			myPieces[37].update_coordinates(2, 1);
			board[2][1]=myPieces[37];
			myPieces[38].update_coordinates(3, 0);
			board[3][0]=myPieces[38];
			myPieces[39].update_coordinates(0, 0);
			board[0][0]=myPieces[39];
		}
	}*/
}
void board9(gamePiece myPieces[], gamePiece board[][], boolean top, int flipped){
	if(top){
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-6, 7);
			board[flipped-6][7]=myPieces[0];
			myPieces[1].update_coordinates(flipped-3, 6);
			board[flipped-3][6]=myPieces[1];
			myPieces[2].update_coordinates(flipped-6, 6);
			board[flipped-6][6]=myPieces[2];
			myPieces[3].update_coordinates(flipped-7, 7);
			board[flipped-7][7]=myPieces[3];
			myPieces[4].update_coordinates(flipped-1, 7);
			board[flipped-1][7]=myPieces[4];
			myPieces[5].update_coordinates(flipped-0, 8);
			board[flipped-0][8]=myPieces[5];
			myPieces[6].update_coordinates(flipped-3, 8);
			board[flipped-3][8]=myPieces[6];
			myPieces[7].update_coordinates(flipped-1, 6);
			board[flipped-1][6]=myPieces[7];
			myPieces[8].update_coordinates(flipped-5, 6);
			board[flipped-5][6]=myPieces[8];
			myPieces[9].update_coordinates(flipped-9, 6);
			board[flipped-9][6]=myPieces[9];
			myPieces[10].update_coordinates(flipped-4, 7);
			board[flipped-4][7]=myPieces[10];
			myPieces[11].update_coordinates(flipped-0, 6);
			board[flipped-0][6]=myPieces[11];
			myPieces[12].update_coordinates(flipped-2, 6);
			board[flipped-2][6]=myPieces[12];
			myPieces[13].update_coordinates(flipped-5, 7);
			board[flipped-5][7]=myPieces[13];
			myPieces[14].update_coordinates(flipped-9, 7);
			board[flipped-9][7]=myPieces[14];
			myPieces[15].update_coordinates(flipped-4, 6);
			board[flipped-4][6]=myPieces[15];
			myPieces[16].update_coordinates(flipped-7, 6);
			board[flipped-7][6]=myPieces[16];
			myPieces[17].update_coordinates(flipped-8, 6);
			board[flipped-8][6]=myPieces[17];
			myPieces[18].update_coordinates(flipped-9, 9);
			board[flipped-9][9]=myPieces[18];
			myPieces[19].update_coordinates(flipped-2, 7);
			board[flipped-2][7]=myPieces[19];
			myPieces[20].update_coordinates(flipped-2, 9);
			board[flipped-2][9]=myPieces[20];
			myPieces[21].update_coordinates(flipped-4, 9);
			board[flipped-4][9]=myPieces[21];
			myPieces[22].update_coordinates(flipped-5, 9);
			board[flipped-5][9]=myPieces[22];
			myPieces[23].update_coordinates(flipped-6, 9);
			board[flipped-6][9]=myPieces[23];
			myPieces[24].update_coordinates(flipped-0, 7);
			board[flipped-0][7]=myPieces[24];
			myPieces[25].update_coordinates(flipped-3, 7);
			board[flipped-3][7]=myPieces[25];
			myPieces[26].update_coordinates(flipped-8, 7);
			board[flipped-8][7]=myPieces[26];
			myPieces[27].update_coordinates(flipped-4, 8);
			board[flipped-4][8]=myPieces[27];
			myPieces[28].update_coordinates(flipped-5, 8);
			board[flipped-5][8]=myPieces[28];
			myPieces[29].update_coordinates(flipped-6, 8);
			board[flipped-6][8]=myPieces[29];
			myPieces[30].update_coordinates(flipped-7, 8);
			board[flipped-7][8]=myPieces[30];
			myPieces[31].update_coordinates(flipped-8, 8);
			board[flipped-8][8]=myPieces[31];
			myPieces[32].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[32];
			myPieces[33].update_coordinates(flipped-2, 8);
			board[flipped-2][8]=myPieces[33];
			myPieces[34].update_coordinates(flipped-9, 8);
			board[flipped-9][8]=myPieces[34];
			myPieces[35].update_coordinates(flipped-0, 9);
			board[flipped-0][9]=myPieces[35];
			myPieces[36].update_coordinates(flipped-3, 9);
			board[flipped-3][9]=myPieces[36];
			myPieces[37].update_coordinates(flipped-7, 9);
			board[flipped-7][9]=myPieces[37];
			myPieces[38].update_coordinates(flipped-8, 9);
			board[flipped-8][9]=myPieces[38];
			myPieces[39].update_coordinates(flipped-1, 9);
			board[flipped-1][9]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(6, 7);
			board[6][7]=myPieces[0];
			myPieces[1].update_coordinates(3, 6);
			board[3][6]=myPieces[1];
			myPieces[2].update_coordinates(6, 6);
			board[6][6]=myPieces[2];
			myPieces[3].update_coordinates(7, 7);
			board[7][7]=myPieces[3];
			myPieces[4].update_coordinates(1, 7);
			board[1][7]=myPieces[4];
			myPieces[5].update_coordinates(0, 8);
			board[0][8]=myPieces[5];
			myPieces[6].update_coordinates(3, 8);
			board[3][8]=myPieces[6];
			myPieces[7].update_coordinates(1, 6);
			board[1][6]=myPieces[7];
			myPieces[8].update_coordinates(5, 6);
			board[5][6]=myPieces[8];
			myPieces[9].update_coordinates(9, 6);
			board[9][6]=myPieces[9];
			myPieces[10].update_coordinates(4, 7);
			board[4][7]=myPieces[10];
			myPieces[11].update_coordinates(0, 6);
			board[0][6]=myPieces[11];
			myPieces[12].update_coordinates(2, 6);
			board[2][6]=myPieces[12];
			myPieces[13].update_coordinates(5, 7);
			board[5][7]=myPieces[13];
			myPieces[14].update_coordinates(9, 7);
			board[9][7]=myPieces[14];
			myPieces[15].update_coordinates(4, 6);
			board[4][6]=myPieces[15];
			myPieces[16].update_coordinates(7, 6);
			board[7][6]=myPieces[16];
			myPieces[17].update_coordinates(8, 6);
			board[8][6]=myPieces[17];
			myPieces[18].update_coordinates(9, 9);
			board[9][9]=myPieces[18];
			myPieces[19].update_coordinates(2, 7);
			board[2][7]=myPieces[19];
			myPieces[20].update_coordinates(2, 9);
			board[2][9]=myPieces[20];
			myPieces[21].update_coordinates(4, 9);
			board[4][9]=myPieces[21];
			myPieces[22].update_coordinates(5, 9);
			board[5][9]=myPieces[22];
			myPieces[23].update_coordinates(6, 9);
			board[6][9]=myPieces[23];
			myPieces[24].update_coordinates(0, 7);
			board[0][7]=myPieces[24];
			myPieces[25].update_coordinates(3, 7);
			board[3][7]=myPieces[25];
			myPieces[26].update_coordinates(8, 7);
			board[8][7]=myPieces[26];
			myPieces[27].update_coordinates(4, 8);
			board[4][8]=myPieces[27];
			myPieces[28].update_coordinates(5, 8);
			board[5][8]=myPieces[28];
			myPieces[29].update_coordinates(6, 8);
			board[6][8]=myPieces[29];
			myPieces[30].update_coordinates(7, 8);
			board[7][8]=myPieces[30];
			myPieces[31].update_coordinates(8, 8);
			board[8][8]=myPieces[31];
			myPieces[32].update_coordinates(2, 8);
			board[2][8]=myPieces[32];
			myPieces[33].update_coordinates(2, 8);
			board[2][8]=myPieces[33];
			myPieces[34].update_coordinates(9, 8);
			board[9][8]=myPieces[34];
			myPieces[35].update_coordinates(0, 9);
			board[0][9]=myPieces[35];
			myPieces[36].update_coordinates(3, 9);
			board[3][9]=myPieces[36];
			myPieces[37].update_coordinates(7, 9);
			board[7][9]=myPieces[37];
			myPieces[38].update_coordinates(8, 9);
			board[8][9]=myPieces[38];
			myPieces[39].update_coordinates(1, 9);
			board[1][9]=myPieces[39];
		}
	}
	/*else{
		if(flipped==9){
			myPieces[0].update_coordinates(flipped-7, 2);
			board[flipped-7][2]=myPieces[0];
			myPieces[1].update_coordinates(flipped-2, 3);
			board[flipped-2][3]=myPieces[1];
			myPieces[2].update_coordinates(flipped-2, 1);
			board[flipped-2][1]=myPieces[2];
			myPieces[3].update_coordinates(flipped-4, 1);
			board[flipped-4][1]=myPieces[3];
			myPieces[4].update_coordinates(flipped-2, 2);
			board[flipped-2][2]=myPieces[4];
			myPieces[5].update_coordinates(flipped-3, 0);
			board[flipped-3][0]=myPieces[5];
			myPieces[6].update_coordinates(flipped-9, 2);
			board[flipped-9][2]=myPieces[6];
			myPieces[7].update_coordinates(flipped-0, 3);
			board[flipped-0][3]=myPieces[7];
			myPieces[8].update_coordinates(flipped-3, 2);
			board[flipped-3][2]=myPieces[8];
			myPieces[9].update_coordinates(flipped-5, 1);
			board[flipped-5][1]=myPieces[9];
			myPieces[10].update_coordinates(flipped-6, 0);
			board[flipped-6][0]=myPieces[10];
			myPieces[11].update_coordinates(flipped-3, 1);
			board[flipped-3][1]=myPieces[11];
			myPieces[12].update_coordinates(flipped-3, 3);
			board[flipped-3][3]=myPieces[12];
			myPieces[13].update_coordinates(flipped-8, 0);
			board[flipped-8][0]=myPieces[13];
			myPieces[14].update_coordinates(flipped-8, 3);
			board[flipped-8][3]=myPieces[14];
			myPieces[15].update_coordinates(flipped-0, 1);
			board[flipped-0][1]=myPieces[15];
			myPieces[16].update_coordinates(flipped-1, 0);
			board[flipped-1][0]=myPieces[16];
			myPieces[17].update_coordinates(flipped-5, 0);
			board[flipped-5][0]=myPieces[17];
			myPieces[18].update_coordinates(flipped-7, 3);
			board[flipped-7][3]=myPieces[18];
			myPieces[19].update_coordinates(flipped-4, 0);
			board[flipped-4][0]=myPieces[19];
			myPieces[20].update_coordinates(flipped-6, 2);
			board[flipped-6][2]=myPieces[20];
			myPieces[21].update_coordinates(flipped-7, 0);
			board[flipped-7][0]=myPieces[21];
			myPieces[22].update_coordinates(flipped-8, 2);
			board[flipped-8][2]=myPieces[22];
			myPieces[23].update_coordinates(flipped-9, 1);
			board[flipped-9][1]=myPieces[23];
			myPieces[24].update_coordinates(flipped-1, 2);
			board[flipped-1][2]=myPieces[24];
			myPieces[25].update_coordinates(flipped-1, 3);
			board[flipped-1][3]=myPieces[25];
			myPieces[26].update_coordinates(flipped-4, 2);
			board[flipped-4][2]=myPieces[26];
			myPieces[27].update_coordinates(flipped-7, 1);
			board[flipped-7][1]=myPieces[27];
			myPieces[28].update_coordinates(flipped-5, 2);
			board[flipped-5][2]=myPieces[28];
			myPieces[29].update_coordinates(flipped-6, 3);
			board[flipped-6][3]=myPieces[29];
			myPieces[30].update_coordinates(flipped-8, 1);
			board[flipped-8][1]=myPieces[30];
			myPieces[31].update_coordinates(flipped-9, 3);
			board[flipped-9][3]=myPieces[31];
			myPieces[32].update_coordinates(flipped-6, 1);
			board[flipped-6][1]=myPieces[32];
			myPieces[33].update_coordinates(flipped-0, 2);
			board[flipped-0][2]=myPieces[33];
			myPieces[34].update_coordinates(flipped-1, 1);
			board[flipped-1][1]=myPieces[34];
			myPieces[35].update_coordinates(flipped-2, 0);
			board[flipped-2][0]=myPieces[35];
			myPieces[36].update_coordinates(flipped-4, 3);
			board[flipped-4][3]=myPieces[36];
			myPieces[37].update_coordinates(flipped-5, 3);
			board[flipped-5][3]=myPieces[37];
			myPieces[38].update_coordinates(flipped-9, 0);
			board[flipped-9][0]=myPieces[38];
			myPieces[39].update_coordinates(flipped-0, 0);
			board[flipped-0][0]=myPieces[39];
		}
		else{
			myPieces[0].update_coordinates(7, 2);
			board[7][2]=myPieces[0];
			myPieces[1].update_coordinates(2, 3);
			board[2][3]=myPieces[1];
			myPieces[2].update_coordinates(2, 1);
			board[2][1]=myPieces[2];
			myPieces[3].update_coordinates(4, 1);
			board[4][1]=myPieces[3];
			myPieces[4].update_coordinates(2, 2);
			board[2][2]=myPieces[4];
			myPieces[5].update_coordinates(3, 0);
			board[3][0]=myPieces[5];
			myPieces[6].update_coordinates(9, 2);
			board[9][2]=myPieces[6];
			myPieces[7].update_coordinates(0, 3);
			board[0][3]=myPieces[7];
			myPieces[8].update_coordinates(3, 2);
			board[3][2]=myPieces[8];
			myPieces[9].update_coordinates(5, 1);
			board[5][1]=myPieces[9];
			myPieces[10].update_coordinates(6, 0);
			board[6][0]=myPieces[10];
			myPieces[11].update_coordinates(3, 1);
			board[3][1]=myPieces[11];
			myPieces[12].update_coordinates(3, 3);
			board[3][3]=myPieces[12];
			myPieces[13].update_coordinates(8, 0);
			board[8][0]=myPieces[13];
			myPieces[14].update_coordinates(8, 3);
			board[8][3]=myPieces[14];
			myPieces[15].update_coordinates(0, 1);
			board[0][1]=myPieces[15];
			myPieces[16].update_coordinates(1, 0);
			board[1][0]=myPieces[16];
			myPieces[17].update_coordinates(5, 0);
			board[5][0]=myPieces[17];
			myPieces[18].update_coordinates(7, 3);
			board[7][3]=myPieces[18];
			myPieces[19].update_coordinates(4, 0);
			board[4][0]=myPieces[19];
			myPieces[20].update_coordinates(6, 2);
			board[6][2]=myPieces[20];
			myPieces[21].update_coordinates(7, 0);
			board[7][0]=myPieces[21];
			myPieces[22].update_coordinates(8, 2);
			board[8][2]=myPieces[22];
			myPieces[23].update_coordinates(9, 1);
			board[9][1]=myPieces[23];
			myPieces[24].update_coordinates(1, 2);
			board[1][2]=myPieces[24];
			myPieces[25].update_coordinates(1, 3);
			board[1][3]=myPieces[25];
			myPieces[26].update_coordinates(4, 2);
			board[4][2]=myPieces[26];
			myPieces[27].update_coordinates(7, 1);
			board[7][1]=myPieces[27];
			myPieces[28].update_coordinates(5, 2);
			board[5][2]=myPieces[28];
			myPieces[29].update_coordinates(6, 3);
			board[6][3]=myPieces[29];
			myPieces[30].update_coordinates(8, 1);
			board[8][1]=myPieces[30];
			myPieces[31].update_coordinates(9, 3);
			board[9][3]=myPieces[31];
			myPieces[32].update_coordinates(6, 1);
			board[6][1]=myPieces[32];
			myPieces[33].update_coordinates(0, 2);
			board[0][2]=myPieces[33];
			myPieces[34].update_coordinates(1, 1);
			board[1][1]=myPieces[34];
			myPieces[35].update_coordinates(2, 0);
			board[2][0]=myPieces[35];
			myPieces[36].update_coordinates(4, 3);
			board[4][3]=myPieces[36];
			myPieces[37].update_coordinates(5, 3);
			board[5][3]=myPieces[37];
			myPieces[38].update_coordinates(9, 0);
			board[9][0]=myPieces[38];
			myPieces[39].update_coordinates(0, 0);
			board[0][0]=myPieces[39];
		}
	}*/
}
public gamePiece[] place_pieces(){
	int flipped = (int) (9 * (Math.random() * 2));
	if(top)
		flipped = (int) (9 * (Math.random() * 2));
	//System.out.println("flipped "+flipped);
	int board_num = (int) (Math.random() * 9 + 1);
	if(top)
		board_num = (int) (Math.random() * 9 + 1);
         board_num =1;
         flipped =1;
	if(board_num==1){
		boardmine1(myPieces, board, top, flipped);
		//board1(myPieces, board, top, flipped);
	}		
	else if(board_num==2)
		board2(myPieces, board, top, flipped);
	else if(board_num==3)
		board3(myPieces, board, top, flipped);
	else if(board_num==4)
		board4(myPieces, board, top, flipped);
	else if(board_num==5)
		board5(myPieces, board, top, flipped);
	else if(board_num==6)
		board6(myPieces, board, top, flipped);
	else if(board_num==7)
		board7(myPieces, board, top, flipped);
	else if(board_num==8)
		board8(myPieces, board, top, flipped);
	else if(board_num==9)
		board9(myPieces, board, top, flipped);
	return myPieces;
}//place_pieces

moves possible_moves(gamePiece piece, gamePiece board[][], int known_values[], int board_piece_mappings[][]){//3 square rules
	moves poss = new moves();
	poss.possible_moves[0][0]=0;
//	int num_moves;
	//System.out.println("piece.getPieceType() "+piece.getPlayerType());
	if(piece.getPieceType()!=piece_type.Bomb && piece.getPieceType()!=piece_type.Flag){//pcs of mine
		if(piece.getPieceType()!=piece_type.Scout){
			int x = piece.getX();
			int y = piece.getY();
			if(x!=0 && (board[x-1][y]==null || (board[x-1][y].getPlayerType()==player_type.User && ((known_values[board_piece_mappings[x-1][y]] > piece.getPieceType().valuen() && known_values[board_piece_mappings[x-1][y]]!=11) || (known_values[board_piece_mappings[x-1][y]]==11 && piece.getPieceType().valuen()==8)))) && !((y==4 || y==5) && (x-1 == 2 || x-1 == 3 || x-1 == 6 || x-1 == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x-1;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y;
			}
			if(x!=9 && (board[x+1][y]==null|| (board[x+1][y].getPlayerType()==player_type.User && ((known_values[board_piece_mappings[x+1][y]] > piece.getPieceType().valuen() && known_values[board_piece_mappings[x+1][y]]!=11) || (known_values[board_piece_mappings[x+1][y]]==11 && piece.getPieceType().valuen()==8)))) && !((y==4 || y==5) && (x+1 == 2 || x+1 == 3 || x+1 == 6 || x+1 == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x+1;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y;
			}
			if(y!=0 && (board[x][y-1]==null || (board[x][y-1].getPlayerType()==player_type.User && ((known_values[board_piece_mappings[x][y-1]] > piece.getPieceType().valuen() && known_values[board_piece_mappings[x][y-1]]!=11) || (known_values[board_piece_mappings[x][y-1]]==11 && piece.getPieceType().valuen()==8)))) && !((y-1==4 || y-1==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y-1;
			}
			if(y!=9 && (board[x][y+1]==null || (board[x][y+1].getPlayerType()==player_type.User && ((known_values[board_piece_mappings[x][y+1]] > piece.getPieceType().valuen() && known_values[board_piece_mappings[x][y+1]]!=11) || (known_values[board_piece_mappings[x][y+1]]==11 && piece.getPieceType().valuen()==8)))) && !((y+1==4 || y+1==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y+1;
			}
		}
		else{//scouts
			int x = piece.getX();
			int y = piece.getY();
			int x_to;
			int y_to;
			for(x_to=x-1; x_to >= 0; x_to--){//leftward
				if((board[x_to][y]==null || (board[x_to][y].getPlayerType()==player_type.User && known_values[board_piece_mappings[x_to][y]] > piece.getPieceType().valuen())) && !((y==4 || y==5) && (x_to == 2 || x_to == 3 || x_to == 6 || x_to == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x_to;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y;
					if(board[x_to][y]!=null)
						break;
				}
				else
					break;
			}
			for(x_to=x+1; x_to <= 9; x_to++){//Rightward
				if((board[x_to][y]==null || (board[x_to][y].getPlayerType()==player_type.User && known_values[board_piece_mappings[x_to][y]] > piece.getPieceType().valuen())) && !((y==4 || y==5) && (x_to == 2 || x_to == 3 || x_to == 6 || x_to == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x_to;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y;
					if(board[x_to][y]!=null)
						break;
				}
				else
					break;
			}
			for(y_to=y-1; y_to >= 0; y_to--){//backward
				if((board[x][y_to]==null || (board[x][y_to].getPlayerType()==player_type.User && known_values[board_piece_mappings[x][y_to]] > piece.getPieceType().valuen())) && !((y_to==4 || y_to==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y_to;
					if(board[x][y_to]!=null)
						break;
				}
				else
					break;
			}
			for(y_to=y+1; y_to <= 9; y_to++){//frontward
				if((board[x][y_to]==null || (board[x][y_to].getPlayerType()==player_type.User && known_values[board_piece_mappings[x][y_to]] > piece.getPieceType().valuen())) && !((y_to==4 || y_to==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y_to;
					/*System.out.println("x "+x);
					System.out.println("y_to "+y_to);*/
					if(board[x][y_to]!=null)
						break;
				}
				else
					break;
			}
		}
	}
	//System.out.println("poss "+ poss.possible_moves[0][0]);
	return poss;
}//Moves_possible_piece

private moves possible_opponent_moves(gamePiece piece, gamePiece board[][], int known_values[], int board_piece_mappings[][]){
	moves poss;
        poss=new moves();
	poss.possible_moves[0][0]=0;
	//int num_moves;
	////std::cout<<piece.getX()<<" "<<piece.getY()<<std::endl;
	if(piece.getPieceType()!=piece_type.Bomb && piece.getPieceType()!=piece_type.Flag){
		if(piece.getPieceType()!=piece_type.Scout){
			int x = piece.getX();
			int y = piece.getY();
			if(x!=0 && (board[x-1][y]==null || (board[x-1][y].getPlayerType()==player_type.AI && (((board[x-1][y].getPieceType().valuen()> known_values[board_piece_mappings[x][y]] || known_values[board_piece_mappings[x][y]]==999) && board[x-1][y].getPieceType().valuen()!=11) || (board[x-1][y].getPieceType().valuen()==11 && known_values[board_piece_mappings[x][y]]==8)))) && !((y==4 || y==5) && (x-1 == 2 || x-1 == 3 || x-1 == 6 || x-1 == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x-1;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y;
			}
			if(x!=9 && (board[x+1][y]==null || (board[x+1][y].getPlayerType()==player_type.AI && (((board[x+1][y].getPieceType().valuen()> known_values[board_piece_mappings[x][y]] || known_values[board_piece_mappings[x][y]]==999) && board[x+1][y].getPieceType().valuen()!=11) || (board[x+1][y].getPieceType().valuen()==11 && known_values[board_piece_mappings[x][y]]==8)))) && !((y==4 || y==5) && (x+1 == 2 || x+1 == 3 || x+1 == 6 || x+1 == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x+1;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y;
			}
			if(y!=0 && (board[x][y-1]==null || (board[x][y-1].getPlayerType()==player_type.AI && (((board[x][y-1].getPieceType().valuen() > known_values[board_piece_mappings[x][y]] || known_values[board_piece_mappings[x][y]]==999) && board[x][y-1].getPieceType().valuen()!=11) || (board[x][y-1].getPieceType().valuen()==11 && known_values[board_piece_mappings[x][y]]==8)))) && !((y-1==4 || y-1==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y-1;
			}
			if(y!=9 && (board[x][y+1]==null || (board[x][y+1].getPlayerType()==player_type.AI && (((board[x][y+1].getPieceType().valuen() > known_values[board_piece_mappings[x][y]] || known_values[board_piece_mappings[x][y]]==999) && board[x][y+1].getPieceType().valuen()!=11) || (board[x][y+1].getPieceType().valuen()==11 && known_values[board_piece_mappings[x][y]]==8)))) && !((y+1==4 || y+1==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
				poss.possible_moves[0][0]++;
				poss.possible_moves[poss.possible_moves[0][0]][0]=x;
				poss.possible_moves[poss.possible_moves[0][0]][1]=y+1;
			}
		}
		else{
			int x = piece.getX();
			int y = piece.getY();
			int x_to;
			int y_to;
			for(x_to=x-1; x_to >= 0; x_to--){
				if((board[x_to][y]==null || (board[x_to][y].getPlayerType()==player_type.AI && board[x_to][y].getPieceType().valuen() > 9)) && !((y==4 || y==5) && (x_to == 2 || x_to == 3 || x_to == 6 || x_to == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x_to;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y;
					if(board[x_to][y]!=null)
						break;
				}
				else
					break;
			}
			for(x_to=x+1; x_to <= 9; x_to++){
				if((board[x_to][y]==null || (board[x_to][y].getPlayerType()==player_type.AI && board[x_to][y].getPieceType().valuen() > 9)) && !((y==4 || y==5) && (x_to == 2 || x_to == 3 || x_to == 6 || x_to == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x_to;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y;
					if(board[x_to][y]!=null)
						break;
				}
				else
					break;
			}
			for(y_to=y-1; y_to >= 0; y_to--){
				if((board[x][y_to]==null || (board[x][y_to].getPlayerType()==player_type.AI && board[x][y_to].getPieceType().valuen() > 9)) && !((y_to==4 || y_to==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y_to;
					if(board[x][y_to]!=null)
						break;
				}
				else
					break;
			}
			for(y_to=y+1; y_to <= 9; y_to++){
				if((board[x][y_to]==null || (board[x][y_to].getPlayerType()==player_type.AI && board[x][y_to].getPieceType().valuen() > 9)) && !((y_to==4 || y_to==5) && (x == 2 || x == 3 || x == 6 || x == 7))){
					poss.possible_moves[0][0]++;
					poss.possible_moves[poss.possible_moves[0][0]][0]=x;
					poss.possible_moves[poss.possible_moves[0][0]][1]=y_to;
					if(board[x][y_to]!=null)
						break;
				}
				else
					break;
			}
		}
	}
	return poss;
}//possible opponents moves

public float sim_opponent_move(int x_from, int y_from, int x_to, int y_to, boolean known_copy[], gamePiece myPieces_copy[], gamePiece opponentPieces_copy[], int probabilities[][][], gamePiece board_copy[][], int board_piece_mappings_copy[][], int known_values[], int num_deep, float alpha, float beta, int move_count, int pieces_left, boolean top){
	int piece_num=-1;
	boolean was_known = false;
	boolean collision=false;
	gamePiece collision_piece = null;
	piece_num=board_piece_mappings_copy[x_from][y_from];
	//int board_test[][][];
	if(board_copy[x_to][y_to]==null){
		// board_copy[x_from][y_from]==null!!!!!!!!!!!!!!
		board_copy[x_from][y_from].update_coordinates(x_to, y_to);
		board_copy[x_to][y_to]=board_copy[x_from][y_from];
		board_copy[x_from][y_from]=null;
		board_piece_mappings_copy[x_from][y_from]=-1;
		board_piece_mappings_copy[x_to][y_to]=piece_num;
	}
	else{
		collision=true;
		collision_piece=board_copy[x_to][y_to];
		if(known_copy[piece_num]){
			was_known=true;
			board_copy[x_to][y_to].update_coordinates(-1, -1);
			board_copy[x_from][y_from].update_coordinates(x_to, y_to);
			board_copy[x_to][y_to]=board_copy[x_from][y_from];
			board_copy[x_from][y_from]=null;
			board_piece_mappings_copy[x_from][y_from]=-1;
			board_piece_mappings_copy[x_to][y_to]=piece_num;
		}
		else{
			was_known=false;
			float prob=0;
			for(int p=1; p<board_copy[x_to][y_to].getPieceType().valuen(); p++){
				if(probabilities[piece_num][p][1]!=0)
					prob+=(probabilities[piece_num][p][0]/probabilities[piece_num][p][1]);
			}
			if(prob<=0.5){
				board_copy[x_from][y_from].update_coordinates(-1, -1);
				board_copy[x_from][y_from]=null;
				board_piece_mappings_copy[x_from][y_from]=-1;
			}
			else{
				board_copy[x_to][y_to].update_coordinates(-1, -1);
				board_copy[x_from][y_from].update_coordinates(x_to, y_to);
				board_copy[x_to][y_to]=board_copy[x_from][y_from];
				board_copy[x_from][y_from]=null;
				board_piece_mappings_copy[x_from][y_from]=-1;
				board_piece_mappings_copy[x_to][y_to]=piece_num;
			}
			known_copy[piece_num]=true;
		}
	}
	num_deep++;
	float score=-1;
	boolean pruned=false;
	if(num_deep<2){
		for(int i=0; i<40; i++){
			if (myPieces_copy[i].getX()!=-1){
				moves poss=possible_moves(myPieces_copy[i], board_copy, known_values, board_piece_mappings_copy);
				for(int j=1; j<=poss.possible_moves[0][0]; j++){
					int temp_x_from=myPieces_copy[i].getX();
					int temp_y_from=myPieces_copy[i].getY();
					int temp_x_to=poss.possible_moves[j][0];
					int temp_y_to=poss.possible_moves[j][1];
					float temp_score = sim_move(temp_x_from, temp_y_from, temp_x_to, temp_y_to, known_copy, myPieces_copy, opponentPieces_copy, probabilities, board_copy, board_piece_mappings_copy, known_values, num_deep, alpha, beta, move_count, pieces_left, top);
					//System.out.println("temp_score " +temp_score);
					if(temp_score > score)
						score=temp_score;
					if(alpha < temp_score){
						alpha=temp_score;
						if(alpha>beta){
							pruned=true;
							break;
						}
					}
				}
			}
			if(pruned)
				break;
		}
	}
	else{
		score=heuristic(known_copy, myPieces_copy, board_copy, opponentPieces_copy, probabilities, move_count, pieces_left, top);
	}
	opponentPieces_copy[piece_num].update_coordinates(x_from, y_from);
	board_copy[x_from][y_from]=opponentPieces_copy[piece_num];
	board_piece_mappings_copy[x_from][y_from]=piece_num;
	board_piece_mappings_copy[x_to][y_to]=-1;
	if(collision){
		known_copy[piece_num]=was_known;
		board_copy[x_to][y_to]=collision_piece;
		collision_piece.update_coordinates(x_to, y_to);
	}
	else
		board_copy[x_to][y_to]=null;
	return score;
}//sim opponent move

public float sim_move(int x_from, int y_from, int x_to, int y_to, boolean known_copy[], gamePiece myPieces_copy[], gamePiece opponentPieces_copy[], int probabilities[][][], gamePiece board_copy[][], int board_piece_mappings_copy[][], int known_values[], int num_deep, float alpha, float beta, int move_count, int pieces_left, boolean top){
	int piece_num=-1;
	boolean was_known = false;
	gamePiece move_piece;
	/*moving*/
	move_piece=board_copy[x_from][y_from];
	if(board_copy[x_to][y_to]==null){
		board_copy[x_from][y_from].update_coordinates(x_to, y_to);
		board_copy[x_to][y_to]=board_copy[x_from][y_from];
		board_copy[x_from][y_from]=null;
	}
	else{
		/*System.out.println("x_to  "+ x_to);
		System.out.println("y_to  "+ y_to);*/
		piece_num=board_piece_mappings_copy[x_to][y_to];//int
		//System.out.println("piece_num   "+ piece_num);
		if(known_copy[piece_num]){//my enemy
			was_known=true;
			board_copy[x_from][y_from].update_coordinates(x_to, y_to);
			board_copy[x_to][y_to]=board_copy[x_from][y_from];
			board_copy[x_from][y_from]=null;
			opponentPieces_copy[piece_num].update_coordinates(-1, -1);
			board_piece_mappings_copy[x_to][y_to]=-1;
		}
		else{
			//System.out.println("piece_num 2  "+ piece_num);
			was_known=false;
			float prob=0;//my piece probability
			//System.out.println("board_copy[x_from][y_from].getPieceType().valuen() "+ board_copy[x_from][y_from].getPieceType().valuen());
			for(int p=1; p<board_copy[x_from][y_from].getPieceType().valuen(); p++){
				/*System.out.println("probabilities[piece_num][p][1] "+ probabilities[piece_num][p][1]);
				System.out.println("probabilities[piece_num][p][0] "+ probabilities[piece_num][p][0]);*/
				if(probabilities[piece_num][p][1]!=0)
					prob+=(probabilities[piece_num][p][0]/probabilities[piece_num][p][1]);
			}
			//System.out.println("prob "+ prob);
			if(prob<=0.5){
				board_copy[x_from][y_from].update_coordinates(x_to, y_to);
				board_copy[x_to][y_to]=board_copy[x_from][y_from];
				board_copy[x_from][y_from]=null;
				opponentPieces_copy[piece_num].update_coordinates(-1, -1);
				board_piece_mappings_copy[x_to][y_to]=-1;
			}
			else{
				board_copy[x_from][y_from].update_coordinates(-1, -1);
				board_copy[x_from][y_from]=null;
			}
			known_copy[piece_num]=true;
			//System.out.println("known_copy[piece_num] " + known_copy[piece_num]+  "kai num " +piece_num);
		}
	}

	boolean pruned=false;
	float score=(float) 1.1;
	for(int i=0; i<40; i++){
		if (opponentPieces_copy[i].getX()!=-1){
			moves poss=possible_opponent_moves(opponentPieces_copy[i], board_copy, known_values, board_piece_mappings_copy);
			for(int j=1; j<=poss.possible_moves[0][0]; j++){
				int temp_x_from=opponentPieces_copy[i].getX();
				int temp_y_from=opponentPieces_copy[i].getY();
				int temp_x_to=poss.possible_moves[j][0];
				int temp_y_to=poss.possible_moves[j][1];
				float temp_score = sim_opponent_move(temp_x_from, temp_y_from, temp_x_to, temp_y_to, known_copy, myPieces_copy, opponentPieces_copy, probabilities, board_copy, board_piece_mappings_copy, known_values, num_deep, alpha, beta, move_count, pieces_left, top);
				if(temp_score < score)
					score=temp_score;
				if(beta > temp_score){
					beta=temp_score;
					if(alpha>beta){
						pruned=true;
						break;
					}
				}
			}
		}
		if(pruned)
			break;
	}

	move_piece.update_coordinates(x_from, y_from);
	board_copy[x_from][y_from]=move_piece;
	if(piece_num!=-1){
		known_copy[piece_num]=was_known;
		opponentPieces_copy[piece_num].update_coordinates(x_to, y_to);
		board_copy[x_to][y_to]=opponentPieces_copy[piece_num];
		board_piece_mappings_copy[x_to][y_to]=piece_num;
	}
	else
		board_copy[x_to][y_to]=null;
	return score;

}//sim move

public void make_move(dragdrop dnd){//make
	int x_from, y_from, x_to,  y_to;
	x_to = -1;
	y_to = -1;
	x_from = -1;
	y_from = -1;
	float score = -1;
	boolean[] known_copy= new boolean[40];
	gamePiece[] myPieces_copy= new gamePiece[40];
	gamePiece[][] board_copy= new gamePiece[10][10];
	gamePiece[] opponentPieces_copy= new gamePiece[40];
	int[][] board_piece_mappings_copy= new int[10][10];
	for(int x=0; x<10; x++){
		for(int y=0; y<10; y++){
			board_copy[x][y]=null;
			//System.out.println("board_piece_mappings[x][y]" +board_piece_mappings[x][y]);
			board_piece_mappings_copy[x][y]=board_piece_mappings[x][y];
		 }
	}
	//llenado
	for(int i=0; i<40; i++){
		known_copy[i]=known[i];//known pcs
		myPieces_copy[i]=new gamePiece(myPieces[i].getPieceType(), myPieces[i].getPlayerType());
		opponentPieces_copy[i]=new gamePiece(opponentPieces[i].getPieceType(), opponentPieces[i].getPlayerType());
		myPieces_copy[i].update_coordinates(myPieces[i].getX(), myPieces[i].getY());
		opponentPieces_copy[i].update_coordinates(opponentPieces[i].getX(), opponentPieces[i].getY());
		if(myPieces_copy[i].getX()>-1)
			board_copy[myPieces_copy[i].getX()][myPieces_copy[i].getY()]=myPieces_copy[i];
		if(opponentPieces_copy[i].getX()>-1)
			board_copy[opponentPieces_copy[i].getX()][opponentPieces_copy[i].getY()]=opponentPieces_copy[i];
	}
	float alpha=-999;
	float beta=999;
	int curr_piece=-1;
	for(int i=0; i<40; i++){
		if (myPieces[i].getX()!=-1){//X  
			moves poss=possible_moves(myPieces[i], board, known_values, board_piece_mappings);//cvez*c pza
			//System.out.println("i "+i+"poss "+poss.possible_moves[0][0]);
			for(int j=1; j<=poss.possible_moves[0][0]; j++){
				int temp_x_from=myPieces[i].getX();
				int temp_y_from=myPieces[i].getY();
				int temp_x_to=poss.possible_moves[j][0];
				int temp_y_to=poss.possible_moves[j][1];/*Evalua el mov d las piezas a mover*/
				//Search another temp if its the same and breaks the rules
		//	System.out.println("poss.possible_moves[0][0] " +poss.possible_moves[0][0]);
				/*System.out.println("temp_x_from "+temp_x_from);
				System.out.println("temp_y_from "+temp_y_from);
				System.out.println("temp_x_to "+temp_x_to);
				System.out.println("temp_y_to "+temp_y_to);*/
				//System.out.println("all_moves[i][temp_x_to][temp_y_to] "+all_moves[i][temp_x_to][temp_y_to]);
				if(all_moves[i][temp_x_to][temp_y_to]<10 || poss.possible_moves[0][0]==1){//filter && the 3 movements
				//System.out.println("all_moves[i][temp_x_to][temp_y_to] "+ all_moves[i][temp_x_to][temp_y_to]);
				//if(move_count<25 || ((float)all_moves[i][temp_x_to][temp_y_to])/((float)move_count)<0.05){edw
					float temp_score = sim_move(temp_x_from, temp_y_from, temp_x_to, temp_y_to, known_copy, myPieces_copy, opponentPieces_copy, probabilities, board_copy, board_piece_mappings_copy, known_values, 0, alpha, beta, move_count, pieces_left, top);
					//temp_Score evaluator					
					if(temp_score > score){
					//System.out.println("tempscore>score");
						score=temp_score;
						x_to=temp_x_to;
						x_from=temp_x_from;
						y_to=temp_y_to;
						y_from=temp_y_from;
						curr_piece=i;
						/*System.out.println("tempyto<tempyfrom");
						System.out.println("x_to "+x_to);
						System.out.println("x_from "+x_from);
						System.out.println("y_to "+y_to);
						System.out.println("y_from "+y_from);*/
					}
					if(temp_score==score){
						if(!top){
							if (temp_y_to > temp_y_from){
								x_to=temp_x_to;
								x_from=temp_x_from;
								y_to=temp_y_to;
								y_from=temp_y_from;
								curr_piece=i;
							}
						}
						else{
							if (temp_y_to < temp_y_from){								
								x_to=temp_x_to;
								x_from=temp_x_from;
								y_to=temp_y_to;
								y_from=temp_y_from;
								curr_piece=i;
								/*System.out.println("tempyto<tempyfrom");
								System.out.println("x_to "+x_to);
								System.out.println("x_from "+x_from);
								System.out.println("y_to "+y_to);
								System.out.println("y_from "+y_from);*/
							}
						}//else
					}//tempscore
				}//allmoves
			}//poss
		}
	}//loop
	/*deleting*/
	for(int i=0; i<40; i++){
		myPieces_copy[i]=null;
		opponentPieces_copy[i]=null;
	}
	move_count++;
	//System.out.println("curr_piece "+curr_piece);
	all_moves[curr_piece][x_to][y_to]++;
	if(y_to==-1 ||((move_count>3)&&(this.breaksTwoSquareRules(x_from, y_from, x_to, y_to, move_count,dnd))) ){//Y or the 3 square rule ||((move_count>3)&&())
	//	System.out.println("AI pasa");
		int piece;
		moves poss=new moves();
		int move;
		do{
			piece = (int) (Math.random() % 40);
			if(myPieces[piece].getX()!=-1)
			poss = possible_moves(myPieces[piece], board, known_values, board_piece_mappings);
		} while (myPieces[piece].getX()==-1 || poss.possible_moves[0][0]==0);
		move = (int) ((Math.random() % poss.possible_moves[0][0]) + 1);
		x_to=poss.possible_moves[move][0];
		y_to=poss.possible_moves[move][1];
		x_from=myPieces[piece].getX();
		y_from=myPieces[piece].getY();
		
	}
	this.x_from=x_from;
	this.y_from=y_from;
	this.x_to=x_to;
	this.y_to=y_to;
/*	System.out.println(x_from);
	System.out.println(y_from);
	System.out.println(x_to);
	System.out.println(y_to);*/
}//make move

public void update_board(int x_from, int y_from, int x_to, int y_to, int collision_state, piece_type move_piece, piece_type collision_piece, dragdrop dnd){
 // System.out.println("collision_state "+collision_state);
 	if(collision_state==0){//no piece 
		gamePiece piece = board[x_from][y_from];
		if(piece.getPlayerType()==player_type.User){
			board_piece_mappings[x_to][y_to]=board_piece_mappings[x_from][y_from];//the movemts
			board_piece_mappings[x_from][y_from]=-1;
			if(!known[board_piece_mappings[x_to][y_to]]){
				if(x_to-x_from>1 || x_to-x_from<-1 || y_to-y_from>1 || y_to-y_from<-1){//is scout
					for(int x=0; x<9; x++){
						probabilities[board_piece_mappings[x_to][y_to]][x][0]=0;
					}
					probabilities[board_piece_mappings[x_to][y_to]][9][0]=1;
					probabilities[board_piece_mappings[x_to][y_to]][9][1]=1;
					known[board_piece_mappings[x_to][y_to]]=true;
					known_values[board_piece_mappings[x_to][y_to]]=9;
					probabilities[board_piece_mappings[x_to][y_to]][10][0]=0;
					probabilities[board_piece_mappings[x_to][y_to]][11][0]=0;
					for(int x=0; x<40; x++){
						if(!known[x] && probabilities[x][9][0]!=0){
							for(int y=0; y<12; y++){
								if(probabilities[x][y][1]!=probabilities[x][y][0] && probabilities[x][y][0]!=0){
									probabilities[x][y][1]--;
									if(y==9)
										probabilities[x][y][0]--;
								}
							}
						}
					}
				}
				else{//!=scout
					//System.out.println("probabilities[board_piece_mappings[x_to][y_to]][0][0] "+probabilities[board_piece_mappings[x_to][y_to]][11][0]);
					if(probabilities[board_piece_mappings[x_to][y_to]][0][0]!=0 || probabilities[board_piece_mappings[x_to][y_to]][11][0]!=0){
						probabilities[board_piece_mappings[x_to][y_to]][0][0]=0;
						probabilities[board_piece_mappings[x_to][y_to]][11][0]=0;
						for(int y=1; y<11; y++){
							if(probabilities[board_piece_mappings[x_to][y_to]][y][1]!=probabilities[board_piece_mappings[x_to][y_to]][y][0] && probabilities[board_piece_mappings[x_to][y_to]][y][0]!=0){
								// System.out.println(" probabilities[board_piece_mappings[x_to][y_to]][y][1] " + probabilities[board_piece_mappings[x_to][y_to]][y][1]);
							 probabilities[board_piece_mappings[x_to][y_to]][y][1]= probabilities[board_piece_mappings[x_to][y_to]][y][1]-num_left[0]-num_left[11]; //33 attack with scout
							}	
						}
					}
				}//!=scout
			}//if known
		}
		piece.update_coordinates(x_to, y_to);
		board[x_to][y_to]=piece;
		set(x_from,y_from,board[x_from][y_from],x_to,y_to,collision_state,dnd);    
		board[x_from][y_from]=null;
	}
	if(collision_state>0)
	{
		gamePiece piece1 = board[x_from][y_from];
		gamePiece piece2 = board[x_to][y_to];
		
		if(piece1.getPlayerType()==player_type.User){//or wins or loses check
			for(int x=0; x<12; x++){
				if(x==move_piece.valuen()){//the pieces value
					probabilities[board_piece_mappings[x_from][y_from]][x][0]=1;
					probabilities[board_piece_mappings[x_from][y_from]][x][1]=1;
					known[board_piece_mappings[x_from][y_from]]=true;
					known_values[board_piece_mappings[x_from][y_from]]=x;
				}
				else
					probabilities[board_piece_mappings[x_from][y_from]][x][0]=0;
			}
			for(int x=0; x<40; x++){//the pieces
				if(!known[x] && probabilities[x][move_piece.valuen()][0]!=0){
					for(int y=0; y<12; y++){
						if(probabilities[x][y][1]!=probabilities[x][y][0] && probabilities[x][y][0]!=0){
							probabilities[x][y][1]--;
							if(y == move_piece.valuen())
								probabilities[x][y][0]--;
						}
					}
				}
			}
		}
		else{//me bot
			int num=board_piece_mappings[x_to][y_to];
			for(int x=0; x<12; x++){
				if(x==collision_piece.valuen()){
					probabilities[num][x][0]=1;
					probabilities[num][x][1]=1;
					known[num]=true;
					known_values[num]=x;
				}
				else{
					try {
						probabilities[num][x][0]=0;//problem -java.lang.ArrayIndexOutOfBoundsException: -1
						//throw new Exception("Invalid index. " + num + " x "+x+ "x_to "+ x_to +" y_to "+ y_to);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
			}
			for(int x=0; x<40; x++){
				if(!known[x] && probabilities[x][move_piece.valuen()][0]!=0){
					for(int y=0; y<12; y++){
						if(probabilities[x][y][1]!=probabilities[x][y][0] && probabilities[x][y][0]!=0){
							probabilities[x][y][1]--;
							if(y==collision_piece.valuen())
								probabilities[x][y][0]--;
						}
					}
				}
			}
		}
	}
	if(collision_state==1){//pc < other pc=>this win
		gamePiece piece1 = board[x_from][y_from];
		gamePiece piece2 = board[x_to][y_to];
		if(piece1.getPlayerType()==player_type.User){
			dnd.check(retired, board_piece_mappings[x_from][y_from], actualmc , move_count,initiatedby2,player_type.User,"cpu 1 user");
			board_piece_mappings[x_to][y_to]=board_piece_mappings[x_from][y_from];
			board_piece_mappings[x_from][y_from]=-1;
		}
		else{
			dnd.check(retired, board_piece_mappings[x_to][y_to], actualmc , move_count,initiatedby2,player_type.AI,"cpu 1 ai");
			board_piece_mappings[x_to][y_to]=-1;
			pieces_left--;
			num_left[collision_piece.valuen()]--;
		}
		set(x_from,y_from,board[x_from][y_from],x_to,y_to,collision_state,dnd);
		piece2.update_coordinates(-1, -1);
		piece1.update_coordinates(x_to, y_to);
		board[x_to][y_to]=piece1;
		board[x_from][y_from]=null;
	}
	else if(collision_state==2){//pc > other pcs=>this lose, this leaves
		gamePiece piece1 = board[x_from][y_from];
		if(piece1.getPlayerType()==player_type.User){
			pieces_left--;
			num_left[move_piece.valuen()]--;
			dnd.check(retired, board_piece_mappings[x_from][y_from], actualmc , move_count,initiatedby2,player_type.User,"cpu 2 user");
			board_piece_mappings[x_from][y_from]=-1;
		}
		set(x_from,y_from,board[x_from][y_from],x_to,y_to,collision_state,dnd);
		piece1.update_coordinates(-1, -1);
		board[x_from][y_from]=null;
	}
	else if(collision_state==3){//Same value
		gamePiece piece1 = board[x_from][y_from];
		gamePiece piece2 = board[x_to][y_to];
		if(piece1.getPlayerType()==player_type.User){
			dnd.check(retired, board_piece_mappings[x_from][y_from], actualmc , move_count,initiatedby2,player_type.User,"cpu 3 user");
			board_piece_mappings[x_from][y_from]=-1;
			num_left[move_piece.valuen()]--;
		}
		else{
			dnd.check(retired, board_piece_mappings[x_to][y_to], actualmc , move_count,initiatedby2,player_type.AI,"cpu 3 ais ");
			board_piece_mappings[x_to][y_to]=-1;
			num_left[collision_piece.valuen()]--;
		}
		pieces_left--;
		set(x_from,y_from,board[x_from][y_from],x_to,y_to,collision_state,dnd);
		piece1.update_coordinates(-1, -1);
		piece2.update_coordinates(-1, -1);
		board[x_from][y_from]=null;
		board[x_to][y_to]=null;
	}
}//update board
private void set(int x, int y, gamePiece p,  int x_to, int y_to,int collision_state, dragdrop dnd) {
	// TODO Auto-generated method stub
	if(collision_state==0){
		if(p.getPlayerType()==player_type.User){
			 cla.clasifying(x,y,board[x][y],3,dnd,collision_state);
			 cla.clasifying(x_to,y_to,dnd.board[x_to][y_to],4,dnd,collision_state); //the affected //I will use the values later
		}			
		else{
			cla.clasifying(x,y,board[x][y],1,dnd,collision_state);
			cla.clasifying(x_to,y_to,board[x_to][y_to],2,dnd,collision_state);
		}	 
	 }
	if(collision_state>0){
		if(p.getPlayerType()==player_type.User){
			 cla.clasifying(x,y,board[x][y],3,dnd,collision_state);
			 cla.clasifying(x_to,y_to,board[x_to][y_to],4,dnd,collision_state);//the same value problem get updated before
		}			
		else{
			cla.clasifying(x,y,board[x][y],1,dnd,collision_state);
			cla.clasifying(x_to,y_to,board[x_to][y_to],2,dnd,collision_state);
		}
	}
	
}
public void undoing(int x_from, int y_from, int x_to, int y_to, int collision_state, player_type type, dragdrop dnd, boolean oldvisible, boolean affectedpc){
	
	if(collision_state==0){
		System.out.println("cs0 cpu");
		gamePiece piece = board[x_to][y_to];
		if(piece.getPlayerType()==player_type.User){ 
			board_piece_mappings[x_from][y_from]=board_piece_mappings[x_to][y_to];//the movemts
			board_piece_mappings[x_to][y_to]=-1;			
		}
		piece.update_coordinates(x_from, y_from);
		board[x_from][y_from]=piece;  
		board[x_to][y_to]=null;
		dnd.undo(x_from,y_from,  x_to, y_to,dnd.board[x_to][y_to],board[x_from][y_from],collision_state,oldvisible,affectedpc);
	}
	if(collision_state==1){//this wins
		//a function to change values alternative
		System.out.println("cs1 cpu");
		gamePiece piece1 = board[x_to][y_to];//this attak and wins
		gamePiece piece2 = null;//the retired
		int x;ArrayList<Integer> arra;
		if(piece1.getPlayerType()==player_type.User){//attacks
			board_piece_mappings[x_from][y_from]=board_piece_mappings[x_to][y_to];//the movemts
			board_piece_mappings[x_to][y_to]=-1;//change pos
			arra=check(move_counter,dnd.move_count);
			x=arra.get(0);
			if(x!=-1){//seach opponent
				piece2=dnd.opponent_pieces[gone.get(x)];
			}
		}
		else{
			int k;ArrayList<Integer> arra2;
			arra2=check(actualmc,move_count);
			//System.out.println("arra2 "+arra2.size() + " mc " +move_count);
			k=arra2.get(0);
			board_piece_mappings[x_to][y_to]=k;//?search
			pieces_left++;
			arra=check(move_counter,dnd.move_count);
			x=arra.get(0);
			if(x!=-1){
				piece2=dnd.player_pieces[gone.get(x)];//for labl
			}
			num_left[piece2.getPieceType().valuen()]++;
		}
		if(piece2!=null) 
		piece2.update_coordinates(x_to, y_to);//the p wich left
		piece1.update_coordinates(x_from, y_from);
		board[x_to][y_to]=piece2;
		board[x_from][y_from]=piece1;  		
		dnd.undo(x_from,y_from,  x_to, y_to,piece1,piece2,collision_state,oldvisible,affectedpc);
	}
	if(collision_state==2){
		System.out.println("cs2 cpu");
		gamePiece piece2 = board[x_to][y_to];//was attacked and remain
		gamePiece piece1 = null;//the piece wich attaks and get retired
		int x;ArrayList<Integer> arra;
		if(piece2.getPlayerType()==player_type.AI){//user attacks and lost
			int k;ArrayList<Integer> arra2;
			arra=check(move_counter,dnd.move_count);
			x=arra.get(0);
			if(x!=-1){
				piece1=dnd.player_pieces[gone.get(x)];
			}
			pieces_left++;
			num_left[piece1.getPieceType().valuen()]++;
			arra2=check(actualmc,move_count);
			k=arra2.get(0);
			board_piece_mappings[x_from][y_from]=k;
		}
		else{
			//System.out.println("dnd.move_count 2" +dnd.move_count);
			arra=check(move_counter,dnd.move_count);
			System.out.println("arra2 "+ arra.size());
			x=arra.get(0);
			if(x!=-1){
				piece1=dnd.opponent_pieces[gone.get(x)];
			}
		}
		piece1.update_coordinates(x_from, y_from);
		board[x_from][y_from]=piece1;
		dnd.undo(x_from,y_from,  x_to, y_to,piece1,board[x_to][y_to],collision_state,oldvisible,affectedpc);//?
	}
	if(collision_state==3){
		System.out.println("cs3 cpu");
		gamePiece piece1 = null;//attaks
		gamePiece piece2 = null;
		int k;ArrayList<Integer> arra; ArrayList<Integer> arra2;
	//	System.out.println("dnd.move_count 3 " +dnd.move_count);
		arra=check(move_counter,dnd.move_count);//check
		System.out.println("arra3 "+ arra.size());
		if(arra.size()>1){
			int m;
		   for(int l=0;l<arra.size();l++){
			m=arra.get(l);
			if(initiatedby1.get(m)==player_type.User)
				piece2= dnd.player_pieces[gone.get(m)];			
			else
			    piece1= dnd.opponent_pieces[gone.get(m)];
		    }
		}
		else{
			int x;
			x=arra.get(0);//the same label	
			//System.out.println("the same " + x);
			piece1=dnd.opponent_pieces[gone.get(x)];
			piece2=dnd.player_pieces[gone.get(x)];
		}
		arra2=check(actualmc,move_count);
		k=arra2.get(0);		
		pieces_left++;
		if(type==player_type.User){//ai attacks
		//	System.out.println("Ai attacks");
			num_left[piece2.getPieceType().valuen()]++;
			board_piece_mappings[x_to][y_to]=k;
			piece1.update_coordinates(x_from, y_from);//ai attaks
			piece2.update_coordinates(x_to, y_to);//user
			board[x_from][y_from]=piece1;
			board[x_to][y_to]=piece2;	
			dnd.undo(x_from,y_from,x_to,y_to,piece1,piece2,collision_state,oldvisible,affectedpc);
		}
		else{//user attaks
			//System.out.println("user attacks");
			board_piece_mappings[x_from][y_from]=k;
			num_left[piece1.getPieceType().valuen()]++;
			piece2.update_coordinates(x_from, y_from);//user attaks
			piece1.update_coordinates(x_to, y_to);//ai
			board[x_from][y_from]=piece2;
			board[x_to][y_to]=piece1;
			dnd.undo(x_from,y_from,x_to,y_to,piece2,piece1,collision_state,oldvisible,affectedpc);
		}
	}
}
private ArrayList<Integer> check(ArrayList<Integer> list, int mc) {
	// TODO Auto-generated method stub
	ArrayList<Integer> listreturn = new ArrayList<Integer>();
	for(int k=0; k<list.size();k++){
		if(list.get(k)==mc){
			System.out.println("find me "+ k);
			listreturn.add(k);
		}
	}
	return listreturn;
}
//2 square rules
public  boolean breaksTwoSquareRules(int FROMX,int FROMY,int toX,int toY, int current,dragdrop dnd){
	int limit=3;
	 Memento m;
	int prevX;
	int prevY;
	int TOX;
	int TOY;
	boolean myarray[]= new boolean[3];
	boolean val=false;
	//System.out.println("FROMX "+ FROMX +"FROMY "+ FROMY+" toX "+toX +"toY "+toY);//chequear repeticiones
	//System.out.println("board[FROMX][FROMY] "+board[FROMX][FROMY].getPieceType());
	int k=0;
	for(int count=1; count<=limit;count++){
		if(board[FROMX][FROMY].getPlayerType()==player_type.AI){
			 System.out.println("mc 3 square" + (move_count-(count++)) );//get out
			//if(cla.AI.size()>2)
			m=cla.AI.get(move_count-(count++));
			prevX=m.getX();
			prevY= m.getY();
			m=cla.AI_FX.get(move_count-(count++));   
			TOX=m.getX();
			TOY=m.getY();
		 }
		else{
			//use cla to get each value Memento USER & USER_FX
			m=cla.USER.get(move_count-count);
			prevX=m.getX();
			prevY= m.getY();
			m=cla.USER_FX.get(move_count-count);   
			TOX=m.getX();
			TOY=m.getY();
			//System.out.println("prevX "+prevX +"prevY "+prevY +"TOX "+TOX +"TOY "+TOY);
	      }
			if(!equal(FROMX, FROMY, toX,toY, prevX, prevY,TOX,TOY)&&!this.reverse(FROMX, FROMY, toX,toY, prevX, prevY,TOX,TOY)){
			   myarray[k++]=false;
				}
			else{
			   myarray[k++]=true;
			}
	}    
	   if(myarray[0]&&myarray[1]&&myarray[2]){//probar otras situaciones
		  // System.out.println("los 3 son iguales ");
		   val=true;
	   }
	   else return false;
     return val;
	
}
private boolean equal(int FROMX,int FROMY,int toX,int toY,int prevX, int prevY, int tOX, int tOY){//work
	if((toX==tOX &&toY==tOY)&&(FROMX==prevX && FROMY==prevY))return true;
	else return false;
}
private boolean reverse(int FROMX,int FROMY,int toX,int toY,int prevX, int prevY, int tOX, int tOY){
	if((toX==prevX && toY==prevY)&&(FROMX==tOX && FROMY==tOY))return true;//ver
	else return false;
}

}//End classss
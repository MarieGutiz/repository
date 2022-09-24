package mistra;
public interface Memento {
 public int getX();
 public int getY();
 public player_type getPlayerType();
 public piece_type getPieceType();
 public boolean isVisible();
 public int collisionstate();
 public String del();/*prove*/
}
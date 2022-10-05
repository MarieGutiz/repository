package javapckage;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author MarieGutiz
 * @create date 2022-07-27 01:40:06
 * @modify date 2022-07-27 01:40:06
 * @desc Create a double linked list from array. e.g. {1, {2, 7, {8, 10, 11}, 9}, 3}
 *      
 */
// 1---2---3
//     |
//     7---8---9
//         |
//         10---11
public class DoublyLinkedList {

    static final int[][] NODE_WITH_CHILD = new int[][] { { 2 }, { 7 }, { 8, 10, 11 }, { 9 } };
    static int[][][] nodes = new int[][][] { { { 1 } }, NODE_WITH_CHILD, { { 3 } } };   



    public static void main(String... args) {
         Node doublyList = buildMultiLevel(nodes);
         System.out.println("printing the List ");
         printDoublyList(doublyList);
         System.out.println("Visualize ");
         visualize(doublyList);
    }

    private static void printDoublyList(Node head) {
     if(head!=null){

        System.out.println(head.val);

        if(head.child != null){
            printDoublyList(head.child);
        }

        printDoublyList(head.next);
     }

    }

    private static Node buildMultiLevel(Object nodes) {
        Object actualNode = null;

        if(nodes.getClass().isArray()){
            actualNode = Array.get(nodes, 0);

            if(actualNode.getClass().isArray() && Array.getLength(actualNode) >1){
                actualNode = Array.get(actualNode, 0);
            }
        }
        Node head = new Node(mergeLevel(actualNode));
        Node currentNode = head;
       

        for (int index = 1; index < Array.getLength(nodes); index++) {
            Node nextNode;
            Object val = Array.get(nodes, index);

            if (val.getClass().isArray() && Array.getLength(val) >1) {
               nextNode = buildMultiLevel(val);
               currentNode.child = nextNode;
               continue;
            }

            nextNode = new Node(mergeLevel(val));
            currentNode.next = nextNode;
            nextNode.prev = currentNode;
            currentNode = nextNode;
        }

        return head;
    }

    private static Integer mergeLevel(Object object){
      Integer integer= null;

      if(object.getClass().isArray()){
         for (int i = 0; i < Array.getLength(object); i++) {
            integer = mergeLevel(Array.get(object, i));
         }
      }
      else{
          integer = (Integer)object;
      }

        return integer;
    }

    private static void visualize(Node head){
       if(head == null) return;
       List<List<String>> list = new ArrayList<>();
       getStr(head, list);
       System.out.println("list "+list);
       Collections.reverse(list);
       
       String indentation="";
       int k=0;
       for(List<String> l:list){
        int indent=0;
        int count=-1;
        StringBuilder strb= new StringBuilder();

          for (int i = 0; i < l.size(); i++) {
            if(l.get(i) != "|"){
               strb.append(l.get(i)+" ");
               count+=1;
            }
            else{
               indent = count * 4; 
            }
          }
          String strfy = String.join("---", strb.toString().split(" "));
          System.out.println(indentation+strfy);

          if(list.size()>1 && k< list.size()-1){
             char[] spaces = new char[indent];
             Arrays.fill(spaces, ' ');
             indentation+= new String(spaces);

             System.out.println(indentation+"|");
          }

          k++;
       }
    }

    private static void getStr(Node head, List<List<String>> list){
        if(head == null) return;
        List<String> nodes = new ArrayList<>();

        while(head!=null){
            nodes.add(String.valueOf(head.val));
            if(head.child != null){
                nodes.add(String.valueOf(head.child.val));
                nodes.add("|");
                getStr(head.child.next, list);
            }
           head = head.next; 
        }
        list.add(nodes);
    }
}

class Node {
    Integer val;
    Node next;
    Node prev;
    Node child;

    public Node(Integer val) {
        this.val = val;
    }

}
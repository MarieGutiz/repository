package javapckage;


import java.lang.reflect.Array;

/**
 * @author MarieGutiz
 * @create date 2022-07-27 01:40:06
 * @modify date 2022-07-27 01:40:06
 * @desc Create a double linked list from array. e.g. {1, {2, 7, {8, 10, 11},
 *       9}, 3}
 */

public class DoublyLinkedList {

    static final int[][] NODE_WITH_CHILD = new int[][] { { 2 }, { 7 }, { 8, 10, 11 }, { 9 } };
    static int[][][] nodes = new int[][][] { { { 1 } }, NODE_WITH_CHILD, { { 3 } } };   



    public static void main(String... args) {
         Node doublyList = buildMultiLevel(nodes);
         System.out.println("printing the List ");
         printDoublyList(doublyList);
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
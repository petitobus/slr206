package linkedlists.lockbased;

import linkedlists.lockbased.Node;
import contention.abstractions.AbstractCompositionalIntSet;

public class OptimisticListBasedSet extends AbstractCompositionalIntSet {


    // sentinel nodes
    private Node head;
    private Node tail;

    public OptimisticListBasedSet(){     
	  head = new Node(Integer.MIN_VALUE);
	  tail = new Node(Integer.MAX_VALUE);
      head.next = tail;
    }
	
	@Override
	public boolean addInt(int x) {
		while(true){
			Node pred=head;
			Node curr=pred.next;
			while(curr.value<x){
				pred=curr;
				curr=curr.next;
			}
			pred.lock();
			curr.lock();
			try{
				if(validate(pred,curr)){
					if(curr.value==x){
						return false;
					}
					Node node = new Node(x);
					node.next=curr;
					pred.next=node;
					return true;
				}
			} finally{
				pred.unlock();
				curr.unlock();
			}
			
		}
	}

	@Override
	public boolean removeInt(int x) {
		while(true){
			Node pred=head;
			Node curr=pred.next;
			while (curr.value<x){
				pred=curr;
				curr=curr.next;
			}
			pred.lock();
			curr.lock();
			try{
				if(validate(pred,curr)){
					if(curr.value == x){
						pred.next=curr.next;
						return true;
					}
					return false;
				}
			}finally{
				pred.unlock();
				curr.unlock();
			}
		}
	}

	@Override
	public boolean containsInt(int x) {
		while(true){
			Node pred=head;
			Node curr=pred.next;
			while (curr.value<x){
				pred=curr;
				curr=curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred,curr)){
					return (curr.value==x);
				}
			}finally{
				pred.unlock();
				curr.unlock();
			}
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}
	
	private boolean validate(Node pred, Node curr){
		Node node=head;
		while (node.value <= pred.value){
			if(node == pred){
				return pred.next==curr;
			}
			node=node.next;
		}
		return false;
	}
}


package CHuffmanCompressor;

class CPriorityQueue{
	final int DEFAULTMAX = 256;
	
	HuffmanNode [] nodes;
	int capacity,total;
	
	public CPriorityQueue(){
		capacity = DEFAULTMAX;
		total = 0;
		nodes = new HuffmanNode[capacity];
	}
	public CPriorityQueue(int max){
		capacity = max;
		total = 0;
		nodes = new HuffmanNode[capacity];
		
	}
	
	public boolean Enqueue(HuffmanNode np){
		
		if(isFull()) return false;
		
		if(isEmpty()){ //first element?
			nodes[total++] = np;
			return true;
		}
		int i = total-1,pos;
		while(i >= 0){
			if(nodes[i].freq < np.freq){
				break;
				}
			i--;
			}
		pos = total-1;
		while(pos >= i+1){
			nodes[pos+1] = nodes[pos];
			pos--;
			}
		nodes[i+1] = np;
		total++;
		return true;
	}
	
	public HuffmanNode Dequeue(){
		
		if(isEmpty()) return null;
		HuffmanNode ret = nodes[0];
		total--;
		for(int i = 0;i<total;i++)
		nodes[i] = nodes[i+1];
		return ret;
		}
	
	public boolean isEmpty(){
		return (total == 0);
		}
	
	public boolean isFull(){
		return (total == capacity);
		}
		
	public int totalNodes(){
		return total;
		}
		
	//debug
	public void displayQ(){
	for(int i=0;i<total;i++){
		System.out.println("Q" + i + ") " + nodes[i].ch + " : " + nodes[i].freq);
		}	
		
		}
	
}


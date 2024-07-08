package wtng;
public class Dispatcher {
	public static void main(String[] args) {
		Product p= new Product();
		Producer pr = new Producer(p, 5);
		Consumer cons = new Consumer(p,5);
		pr.start();
		cons.start();
	}
}
class Product{
	int id;
	boolean isProduced;
	synchronized void getIdProduct() {
		if(!isProduced) {
			try {
				wait();
			}catch(InterruptedException e) {}
		}
		System.out.println("Get " + this.id);
		isProduced = false;
		notify();
	}
	synchronized void setIdProduct(int id) {
			if(isProduced) {
			try {
				wait();
			}catch(InterruptedException e) {}
		}
		this.id = id;
		System.out.println("Set " + this.id);
		isProduced = true;
		notify();
	}
		
}
class Producer extends Thread{
	Product p;
	int count;
	Producer(Product p, int count){
		this.p = p;
		this.count = count;
	}
	public void run() {
		for (int i = 1; i < count + 1; i++) {
			p.setIdProduct(i);
		}
	}
}
class Consumer extends Thread{
	Product p;
	int count;
	Consumer(Product p, int count){
		this.p = p;
		this.count = count;
	}
	public void run() {
		for (int i = 1; i < count + 1; i++) {
			p.getIdProduct();
		}
	}
}
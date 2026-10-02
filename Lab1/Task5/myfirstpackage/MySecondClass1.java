package myfirstpackage;

public class MySecondClass1{
	private int a, b;
	
	public int getA(){
		return a;
	}
	public int getB(){
		return b;
	}
	public void setA(int a){
		this.a = a;
	}
	public void setB(int b){
		this.b = b;
	}


	public MySecondClass1(){}
	
	public MySecondClass1(int a, int b){
		this.a = a;
		this.b = b;
	}
 
	public int Sum(){
		return a + b;
	}

}
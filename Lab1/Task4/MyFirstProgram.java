class MyFirstClass{
	static void main(String[] args){
		
		MySecondClass c = new MySecondClass();
		int i, j;
		for (i = 1; i <= 8; i++){
			for (j = 0; j <= 8; j++){
				c.setA(i);
				c.setB(j);
				System.out.print(c.Sum());
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}


class MySecondClass{
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


	public MySecondClass(){}
	
	public MySecondClass(int a, int b){
		this.a = a;
		this.b = b;
	}
 
	public int Sum(){
		return a + b;
	}

}

 

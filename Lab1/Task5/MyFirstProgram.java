import myfirstpackage.*;

class MyFirstClass{
	static void main(String[] args){
		
		MySecondClass1 c = new MySecondClass1();
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
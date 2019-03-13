public class newtriangle{
	public static void drawTriangle(int number){
		int i = 0;
		int j = 0;
		for(i = 1; i < number + 1; i++)
			{for(j = 0; j < i;j++)
				System.out.print("*");
			 System.out.println();
			 }
	}
	public static void main(String[] args){
		drawTriangle(10);
	}
}
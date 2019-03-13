public class windowpossum{
	public static void windowPosSum(int[] a, int n){
		for(int i = 0; i < a.length;i++)
		{	int sum = 0;
			if(a[i] > 0)
			{	for(int j = 0; j < n + 1; j++)
				{	sum += a[i + j];
					if((i + j + 1) >= a.length)
						break;
				}
				a[i] = sum;
			}
		}
	}
	public static void main(String[] args) {
		int[] a = {1, 2, -3, 4, 5, 4};
		int n = 3;
		windowPosSum(a, n);
		System.out.println(java.util.Arrays.toString(a));
	}
}
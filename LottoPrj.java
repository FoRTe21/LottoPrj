import java.io.*;
import java.util.Random;

class Numbers {
	public int number;
	public int freq;
};
public class LottoPrj {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final int BUFSIZE = 46;
		BufferedReader reader = null;
		FileReader in = null;
		
		int[] seven = new int[6]; // 최종 뽑히는 숫자 6개 
		Numbers[] numbers = new Numbers[BUFSIZE]; // 1~45까지 로또 숫자
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = new Numbers();
		}
		for(int i = 0; i < BUFSIZE; i++)
		{
			numbers[i].number = i;
			numbers[i].freq = 0;
		}
		String oneLine;
		
		
		try {
			in = new FileReader("LottoData.csv");
			reader = new BufferedReader(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while((oneLine = reader.readLine()) != null)				// 한줄씩 읽기
		{
			String[] parsing = oneLine.split(",");
			for(int i = 1; i < parsing.length; i++)					// 최근의 회차에 가중치를 더 주기 위해서 +1의 누적이 아닌 +회차를 함.
			{
				numbers[Integer.parseInt(parsing[i])].freq += (Integer.parseInt(parsing[0]) / 10);
			}
		}
		
		Numbers tmpNumber;											// 오름차순으로 정렬
		for(int i = BUFSIZE - 1; i > 0; i--)
		{
			for(int j = 1; j < i; j++)
			{
				if(numbers[j + 1].freq > numbers[j].freq)
				{
					tmpNumber = numbers[j];
					numbers[j] = numbers[j + 1];
					numbers[j + 1] = tmpNumber;
				}
			}
		}
		
		int ran;
		boolean flag = true;
		//상위 10개에서 4개를 랜덤으로 뽑고,
																// 하위 10개에서 3개를 뽑아서 7개의 숫자를 구성.
			for(int j = 0; j < 6; j++)
			{
				seven[j] = 0;
			}
			for(int j = 0; j < 3; j++)
			{
				flag = true;
				ran = (int)(Math.random()* 10 % 10 + 1);
				for(int k = 0; k < j; k++)
				{
					if(numbers[ran].number == seven[k])
					{
						flag = false;
						break;
					}
				}
				if(flag == false)
				{
					j--;
				}
				else
				{
					seven[j] = numbers[ran].number;
				}
			}
			for(int j = 3; j < 6; j++)
			{
				flag = true;
				ran = (int)(Math.random()* 10 % 10 + 1);
				for(int k = 0; k < j; k++)
				{
					if(numbers[BUFSIZE - ran - 1].number == seven[k])
					{
						flag = false;
						break;
					}
				}
				if(flag == false)
				{
					j--;
				}
				else
				{
					seven[j] = numbers[BUFSIZE - ran - 1].number;
				}
			}
			
			
			for(int l = 0; l < 6; l++)									// 마지막으로 뽑은 7개의 숫자를 인덱스로 해서 다시 7개의 숫자를 출력
			{
				System.out.print(numbers[seven[l]].number + " ");
			}
			System.out.println();
		in.close();
	}
	
}

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#define BUFSIZE 30
#define LOTTONUMBERS 46
#define TRUE 1
#define FALSE 0
#define SIX 6 
int main()
{
	int i, j, k, l, turn;
	int count = 1; 
	int cnt = 0;
	char oneLine[BUFSIZE] = {0,};
	char twoLine[BUFSIZE] = {0,};
	int seven[SIX] = {0,};
	int temp_one[SIX] = {0,};
	int temp_two[SIX] = {0,};
	char *digit = NULL;
	FILE *fp = NULL;
	fp = fopen("LottoData.csv", "r");
	if(fp == NULL)
	{
		printf("fopenError\n");
		exit(1);
	}


	//////////////////////////////////////////////////////////////
	// 앞 뒤로 차이의 평균을 구해서 마지막 숫자에 가감함.
	// //////////////////////////////////////////////////////////
	
	fgets(oneLine, BUFSIZE - 1, fp);		// 최초의 문자열 읽음.
	digit = strtok(oneLine, ",");
	while(digit = strtok(NULL, ","))		// 각 데이터를 1~7번으로 나누어서 저장.
	{										// 후에 다음 데이터와 뺄셈으로 차이값을 누적함.
		temp_one[cnt++] = atoi(digit);	
	}
	while (!feof(fp)) {
		cnt = 0;
		memset(twoLine, 0, sizeof(char) * BUFSIZE);		// 다음 데이터를 읽어들임
		if(fgets(twoLine, BUFSIZE - 1, fp) <= 0)
		{
			break;
		}
		count++;
		digit = strtok(twoLine, ",");
		while (digit = strtok(NULL, ",")) {				// 두번째 데이터를 뽑아냄
			temp_two[cnt++] = atoi(digit);
		}
		
		for(i = 0; i < SIX; i++)						// 앞과 뒤의 데이터 차이값을 누적
		{
			seven[i] += temp_two[i] - temp_one[i];
		}
		memcpy(temp_one, temp_two, sizeof(int) * SIX);
	}
	
	

	printf("average : ");
	for (i = 0; i < SIX; i++) {
		printf("%d ", seven[i]);	
	}
	printf("\n");
	
		for(j = 0; j < SIX; j++)
		{
			printf("%d ", temp_two[j] + seven[j]);
		}
		printf("\n");	

	fclose(fp);
	return 0;
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 
 * 대각선은 인접했다고 할 수 없음 -> 상하좌우
 * 
 * 필요한 변수
 * - 현재 자리에 배치된 좌석을 저장한 배열 seat[N][N]
 * - 각 학생이 좋아하는 학생 정보 담은 배열 info[N*N][4]
 * - 좋아하는 학생 정보를 담은 favorite[4] 배열
 * - 학생 N인 경우 가장 큰 인접한 좋아하는 학생의 수 maxLike
 * - 학생 N인 경우 가장 큰 주변 빈 자리의 수 maxEmpty
 * - 각 i, j 마다 인접한 좋아하는 학생의 수 like_num
 * - 각 i, j 마다 인접한 빈 자리의 수 empty_num
 * - 위치할 좌표 res_x, res_y
 * 
 * 좌석 정보 배열 seat[N][N] 선언
 * 좋아하는 학생 정보 배열 info[N^2][4]
 * N^2만큼 돌아다님
 * - int studentNum
 * - 좋아하는 학생의 수를 담은 배열 favorite[4] 초기화
 * - int maxCount = 0;
 * - int maxEmpty = 0;
 * 
 * - (0,0) ~ (N-1, N-1)
 * 	- if(seat[i][j] == 0)
 * 	 - 상하좌우 탐색 // 1. 좋아하는 학생이 있는지 탐색 2. 빈칸이 있는지 탐색
 * 	 - 범위에 벗어나는 곳이면 continue;
 * 	 - 비어있으면 empty_num++
 * 	  - favorite[0]부터 돌면서
 * 		- 각 seat[nx][ny] == favorite[k] like_num++
 *   - if(like_num > maxLike || (like_num == maxLike && empty_num > maxEmpty))
 *   	1. 좋아하는 학생 수가 가장 많을 때 
 *   	2. 좋아하는 학생 수가 동일하면서 빈 공간이 많을 때
 *   	3. 좋아하는 학생 수도 같고 빈 공간도 같을 때 고려해야 하는 3번째 조건은 어차피 처음 초기화된 경우기 때문에 따로 조건문을 생각할 필요가 없다
 *   	res_x = i;
 *   	res_y = j;
 *   	maxLike = like_num
 *   	maxEmpty = empty_num
 * - seat[res_x][res_y] = studentNum; // 자리 앉힘
 * 
 * 점수 계산
 * 
 * 
 */

public class _21608상어초등학교{
	
	public static void main(String[] args) throws IOException{
		
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        int N = Integer.parseInt(br.readLine());
        int N_sq = N*N;
        int[][] seat = new int[N][N];
        int[][] info = new int[N_sq+1][4];
        int[] dx = {-1, 0, 0, 1};
        int[] dy = {0, 1, -1, 0};
        
        while(N_sq-- > 0) {
        	int[] favorite = new int[4];
        	int maxLike = -1;
        	int maxEmpty = -1;
        	int res_x = 0;
        	int res_y = 0;
        	
        	st = new StringTokenizer(br.readLine(), " ");
        	int student = Integer.parseInt(st.nextToken());
        	
        	// 좋아하는 학생 저장
        	for(int i=0; i<4; i++) {
        		favorite[i] = Integer.parseInt(st.nextToken());
        		info[student][i] = favorite[i];
        	}
        	
        	for(int i=0; i<N; i++) {
        		for(int j=0; j<N; j++) {
        			
        			if(seat[i][j] == 0) {
        				int like_num = 0;
        				int empty_num = 0;
        				
        				// 상하좌우 탐색
        				for(int k=0; k<4; k++) {
        					int nx = i+dx[k];
        					int ny = j+dy[k];
        					
        					if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
        					else {
        						
        						if(seat[nx][ny] == 0) empty_num++;
        						else {
        							for(int l = 0; l < 4; l++) {
        								if(seat[nx][ny] == favorite[l]) like_num++;
        							}
        						}
        					}
        				}
        				// 조건에 따라 좌석 위치 결정
        				if(like_num > maxLike || (like_num == maxLike && empty_num > maxEmpty)) {
        					res_x = i;
        					res_y = j;
        					
        					maxLike = like_num;
        					maxEmpty = empty_num;
        				}
        			}
        		}
        	}
        	
        	seat[res_x][res_y] = student; // 좌석 착석
        }
        
        // 점수 계산
        int happy = 0;
        for(int i=0; i<N; i++) {
        	for(int j=0; j<N; j++) {
        		int student = seat[i][j]; // 조사할 학생
        		int cnt = 0; // 인접한 학생의 수
        		
        		for(int k=0; k<4; k++) {
        			int nx = i+dx[k];
        			int ny = j+dy[k];
        			
        			if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
        			int near = seat[nx][ny]; // 인접한 학생
        			
        			for(int l = 0; l < 4; l++) {
        				// 인접한 학생이 좋아하는 학생이라면 cnt++
        				if(near == info[student][l]) cnt++;
        			}
        		}
        		
        		if(cnt == 1) happy += 1;
        		else if(cnt == 2) happy += 10;
        		else if(cnt == 3) happy += 100;
        		else if(cnt == 4) happy += 1000;
        	}
        }
        
        System.out.println(happy);
	}
}
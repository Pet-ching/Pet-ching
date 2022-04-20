package com.mandarin.petching;

import com.mandarin.petching.domain.AnswerType;
import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.BoardType;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetchingApplication.class, args);
	}


	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) {
		return (args) -> {
			Member member = (Member)userRepository.save(Member.builder().userId("김귀영 추가합니다 ㅋ").userPwd("1234").build());
			IntStream.rangeClosed(1, 10).forEach((index) -> {
				boardRepository.save(Board.builder().title("게시글" + index).content("컨텐츠").boardType(BoardType.QnA).regDate(LocalDateTime.now()).member(member).answerType(AnswerType.대기).build());
			});
		};
	}
}

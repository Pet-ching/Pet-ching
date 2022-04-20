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

}

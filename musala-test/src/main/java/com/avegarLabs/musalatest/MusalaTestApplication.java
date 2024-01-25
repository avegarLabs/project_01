package com.avegarLabs.musalatest;

import com.avegarLabs.musalatest.models.Posts;
import com.avegarLabs.musalatest.models.Users;
import com.avegarLabs.musalatest.repositories.PostsRepository;
import com.avegarLabs.musalatest.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@Slf4j
public class MusalaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusalaTestApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(UsersRepository usersRepository, PostsRepository postsRepository) {
        return args -> {
            List<Users> list = usersRepository.findAll();
            if (list.size() == 0) {
                log.info("Init Data for Test");
                Users users1 = new Users();
                users1.setFullName("Alfredo Vega");
                usersRepository.save(users1);

                Users users2 = new Users();
                users2.setFullName("Odet Lopez");
                usersRepository.save(users2);

                Users users3 = new Users();
                users3.setFullName("Luna M. Vega");
                usersRepository.save(users3);

                Users users4 = new Users();
                users4.setFullName("Luna M. Vega");
                usersRepository.save(users4);

                Posts p1 = new Posts();
                p1.setText("Simple things must be simple, complex things must be possible.");
                p1.setUser(users1);
                p1.setVisibility("public");
                p1.setPostedOn(LocalDateTime.now());
                postsRepository.save(p1);

                Posts p2 = new Posts();
                p2.setText("Simplicity is a prerequisite for reliability");
                p2.setUser(users2);
                p2.setVisibility("public");
                p2.setPostedOn(LocalDateTime.now());
                postsRepository.save(p2);


                Posts p3 = new Posts();
                p3.setText("Before software can be reusable, it must first be usable");
                p3.setUser(users2);
                p3.setVisibility("public");
                p3.setPostedOn(LocalDateTime.now());
                postsRepository.save(p3);

                Posts p4 = new Posts();
                p4.setText("Premature optimization is the root of all evil.");
                p4.setUser(users3);
                p4.setVisibility("public");
                p4.setPostedOn(LocalDateTime.now());
                postsRepository.save(p4);
            }
        };
    }

}
package com.group.artifact.domain;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.fixture.Fixture;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

//@Transactional
public class UserRepositoryTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    private UserRepository userRepository;

    @Test
//    @Transactional
    public void deleteTEst(){
        //@Transacation이 있으면 테스트 통과
        //없으면
        //org.springframework.dao.InvalidDataAccessApiUsageException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call; nested exception is javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
        userRepository.save(Fixture.nnn);
        userRepository.deleteBySlackId(Fixture.nnn.getSlackId());

    }

    @Test
    public void deleteTransactionTest(){
        userRepository.deleteBySlackId(Fixture.nnn.getSlackId());
    }

    @Test
    public void create(){
        userRepository.save(new User(Fixture.mmm.getSlackId(), new ArrayList<>()));
        Optional<User> maybeUser = userRepository.findBySlackId(Fixture.mmm.getSlackId());
        softly.assertThat(maybeUser.isPresent()).isTrue();
    }
}

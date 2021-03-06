package com.group.artifact.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySlackId(String slackId);

    @Transactional
    void deleteBySlackId(String slackId);
}

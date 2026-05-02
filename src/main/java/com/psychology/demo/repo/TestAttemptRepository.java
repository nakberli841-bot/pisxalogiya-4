//package com.psychology.demo.repository;
//
//import com.psychology.demo.entity.TestAttempt;
//import com.psychology.demo.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {
//    List<TestAttempt> findAllByUserOrderByCompletedAtDesc(User user);
//}
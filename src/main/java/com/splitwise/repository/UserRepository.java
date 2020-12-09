package com.splitwise.repository;

import com.splitwise.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    List<UserData> findByUserFromOrUserTo(Integer userId, Integer userId1);

    @Query("select u from UserData u where (u.userFrom = ?1 AND u.userTo = ?2) OR (u.userFrom = ?2 AND u.userTo= ?1)")
    UserData findByTwoDifferentUsers(Integer userId1, Integer userId2);

    @Modifying
    @Query("update UserData set transactionTotalAmount=?3 where userFrom=?1 and userTo=?2")
    void updateData(Integer userId1, Integer userId2, double updatedAmount);
}

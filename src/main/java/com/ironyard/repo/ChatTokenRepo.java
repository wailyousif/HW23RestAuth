package com.ironyard.repo;

import com.ironyard.data.ChatToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by wailm.yousif on 2/21/17.
 */
public interface ChatTokenRepo extends PagingAndSortingRepository<ChatToken, Long> {
    public ChatToken findByTokenString(String tokenString);

    @Query("SELECT t from ChatToken t where tokenString=:tokenString")
    public ChatToken findByTokenStringQ(@Param("tokenString") String tokenString);
}

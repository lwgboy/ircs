package com.github.hasoo.ircs.core.repository;

import org.springframework.data.repository.CrudRepository;
import com.github.hasoo.ircs.core.entity.User;

public interface UserRedisRepository extends CrudRepository<User, String> {

}

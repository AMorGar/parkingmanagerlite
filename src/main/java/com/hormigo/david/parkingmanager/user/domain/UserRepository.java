package com.hormigo.david.parkingmanager.user.domain;

import org.springframework.data.repository.CrudRepository;

import com.hormigo.david.parkingmanager.draw.domain.Draw;

public interface UserRepository extends CrudRepository<User,Long> {

    public User findByEmail(String email);

    public void save(Draw any);

}
    

package com.bhawna.SpringBoot.Security_app.repositories;

import com.bhawna.SpringBoot.Security_app.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}


package ru.javafx.dolphin.springbootfx.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.javafx.dolphin.springbootfx.core.entity.Authority;

@Transactional
@RepositoryRestResource(collectionResourceRel = "authorities", path = "authorities")
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    
    Authority findByAuthority(String authority);
    
}

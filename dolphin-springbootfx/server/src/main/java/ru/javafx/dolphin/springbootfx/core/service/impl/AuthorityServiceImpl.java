
package ru.javafx.dolphin.springbootfx.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javafx.dolphin.springbootfx.core.entity.Authority;
import ru.javafx.dolphin.springbootfx.core.repository.AuthorityRepository;
import ru.javafx.dolphin.springbootfx.core.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
       
    @Autowired
    private AuthorityRepository authorityRepository;
    
    @Override
    public Authority findAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }

}

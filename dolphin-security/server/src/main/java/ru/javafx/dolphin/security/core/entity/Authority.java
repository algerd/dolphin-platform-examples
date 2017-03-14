
package ru.javafx.dolphin.security.core.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(
    name="authorities",    
    uniqueConstraints = {@UniqueConstraint(name = "uk_authority", columnNames = "authority")
})
public class Authority implements GrantedAuthority {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    
    @NotNull(message = "error.authority.authority.empty")
    @Size(max = 32, message = "error.authority.authority.size")
    @Column(name="authority")
    private String authority;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    private Set<User> users;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }
       
    public long getId() {
        return this.id;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Authority && ((Authority) other).getAuthority().equals(authority);
    }

}

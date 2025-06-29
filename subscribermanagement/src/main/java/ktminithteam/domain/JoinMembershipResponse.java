package ktminithteam.domain;

import java.time.LocalDateTime;
import java.util.Set;

public class JoinMembershipResponse {
    private Long id;
    private String username;
    private String email;
    private String name;
    private Set<Role> roles;
    private LocalDateTime createdAt;


    public JoinMembershipResponse() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

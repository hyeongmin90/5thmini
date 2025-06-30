package ktminithteam.domain;

import java.time.LocalDateTime;
import java.util.Set;

public class JoinMembershipResponse {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;


    public JoinMembershipResponse() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

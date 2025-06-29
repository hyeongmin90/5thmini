package ktminithteam.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Table(name = "members")
public class JoinMembership {  // JoinMembershipCommand -> JoinMembership으로 변경 - 회원정보

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;                    // 고유 식별 로그인 아이디(협의필요) & 회원식별 방법 어떻게? 모름

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;                        // 실명 : 중복 허용

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles")
    private Set<Role> roles = new HashSet<>();

    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean enabled = true;

    // 기본 생성자
    public JoinMembership() {}

    // 생성자
    public JoinMembership(String username, String email, String password, String name) {
        this.username = username; //
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles.add(Role.USER); // 기본 역할
    }

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public enum Role {

        USER("일반 사용자"),
        AUTHOR("작가"),
        ADMIN("관리자"),
        SUBSCRIBER("구독자");

        private final String description;

        Role(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
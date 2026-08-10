package org.SprintForge.modules.workspace.workspace.directory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryMemberResponse {
    private Long userId;
    private String userName;
    private String email;
    private String avatarUrl;
    private String jobTitle;
    private String department;
    private List<Long> teamIds;
    private List<String> teamNames;
    private Long roleId;
    private String roleName;
    private String status;
    private LocalDateTime joinedAt;
    private LocalDateTime lastSeenAt;
}

package org.SprintForge.modules.workspace.workspace.directory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.directory.dto.response.DirectoryMemberResponse;
import org.SprintForge.modules.workspace.workspace.directory.dto.response.DirectoryResponse;
import org.SprintForge.modules.workspace.workspace.directory.service.DirectoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Organization Directory", description = "Endpoints for viewing workspace members")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class DirectoryController {

    private final DirectoryService directoryService;

    @GetMapping("/workspaces/{workspaceId}/directory")
    @Operation(summary = "Get workspace directory")
    public DirectoryResponse getDirectory(@PathVariable Long workspaceId, 
                                          @RequestParam(defaultValue = "0") int page, 
                                          @RequestParam(defaultValue = "20") int size) {
        return directoryService.getDirectory(workspaceId, page, size);
    }

    @GetMapping("/workspaces/{workspaceId}/directory/search")
    @Operation(summary = "Search workspace directory")
    public DirectoryResponse searchDirectory(@PathVariable Long workspaceId, 
                                             @RequestParam("q") String query, 
                                             @RequestParam(defaultValue = "0") int page, 
                                             @RequestParam(defaultValue = "20") int size) {
        return directoryService.searchDirectory(workspaceId, query, page, size);
    }

    @GetMapping("/workspaces/{workspaceId}/directory/teams/{teamId}")
    @Operation(summary = "Get team directory")
    public List<DirectoryMemberResponse> getTeamDirectory(@PathVariable Long workspaceId, 
                                                          @PathVariable Long teamId) {
        return directoryService.getTeamDirectory(workspaceId, teamId);
    }
}

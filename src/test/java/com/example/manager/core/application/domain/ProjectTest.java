package com.example.manager.core.application.domain;

import com.example.manager.core.domain.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ProjectTest {
    @Test
    public void test() {
        Project project = new Project(1L,"team","status","title","description", false);
        assertThat(project.getUid()).isEqualTo(1L);
        assertThat(project.getTeam()).isEqualTo("team");
        assertThat(project.getStatus()).isEqualTo("status");
        assertThat(project.getTitle()).isEqualTo("title");
        assertThat(project.getDescription()).isEqualTo("description");
        assertThat(project.isHidden()).isFalse();
        assertThat(project.toString()).isEqualTo("Project(uid=1, team=team, status=status, title=title, description=description, hidden=false)");
        assertThat(project.hashCode()).isNotEqualTo(new Project(1L,"teamw","status","title","description", false).hashCode());
        assertDoesNotThrow(() -> project.setDescription("description"));
        assertDoesNotThrow(() -> project.setTeam("team"));
        assertDoesNotThrow(() -> project.setStatus("status"));
        assertDoesNotThrow(() -> project.setTitle("title"));
        assertDoesNotThrow(() -> project.setUid(1L));
        assertDoesNotThrow(() -> project.setHidden(false));
        assertThat(project.equals(new Project(1L,"team","status","title","description", false))).isTrue();
        assertThat(new Project(1L,"team","status","title","description", false)).isEqualTo(project);
    }
}

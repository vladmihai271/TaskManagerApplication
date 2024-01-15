package com.example.manager.core.application.domain;

import com.example.manager.core.domain.Sprint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class SprintTest {
    @Test
    public void test() {
        Sprint sprint = new Sprint(1L,"team","task1,",
                "status", "project","title");
        assertThat(sprint.getUid()).isEqualTo(1L);
        assertThat(sprint.getTeam()).isEqualTo("team");
        assertThat(sprint.getTasks()).isEqualTo("task1,");
        assertThat(sprint.getStatus()).isEqualTo("status");
        assertThat(sprint.getProject()).isEqualTo("project");
        assertThat(sprint.getTitle()).isEqualTo("title");
        assertThat(sprint.toString()).isEqualTo("Sprint(uid=1, team=team, tasks=task1,, status=status, project=project, title=title)");
        assertThat(sprint.hashCode()).isEqualTo(-1454088066);
        assertThat(sprint.equals(new Sprint(1L,"team","task1,",
                "status", "project","title"))).isTrue();
        assertThat(sprint.equals(new Sprint(1L,"team1","task1,",
                "status", "project","title"))).isFalse();
        assertThat(sprint.equals(new Sprint(12L,"team","task1,",
                "status", "project","title"))).isFalse();
        assertThat(sprint.equals(new Sprint(1L,"team","task12,",
                "status", "project","title"))).isFalse();
        assertThat(sprint.equals(new Sprint(1L,"team","task1,",
                "sta2tus", "project","title"))).isFalse();
        assertThat(sprint.equals(new Sprint(1L,"team","task1,",
                "status", "projec2t","title"))).isFalse();
        assertThat(sprint.equals(new Sprint(1L,"team","task1,",
                "status", "project","t2itle"))).isFalse();
        assertDoesNotThrow(() -> sprint.setTeam("team"));
        assertDoesNotThrow(() -> sprint.setTasks("task1,"));
        assertDoesNotThrow(() -> sprint.setStatus("status"));
        assertDoesNotThrow(() -> sprint.setProject("project"));
        assertDoesNotThrow(() -> sprint.setTitle("title"));
        assertDoesNotThrow(() -> sprint.setUid(1L));
        assertThat(new Sprint(1L,"team","task1,",
                "status", "project","title")).isEqualTo(sprint);
    }
}

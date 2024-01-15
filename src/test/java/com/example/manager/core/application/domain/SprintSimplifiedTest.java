package com.example.manager.core.application.domain;
import com.example.manager.core.domain.SprintSimplified;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
public class SprintSimplifiedTest {
    @Test
    public void test() {
        SprintSimplified sprintSimplified = new SprintSimplified(1L,
                "team", "status", "project", "title");
        assertThat(sprintSimplified.getUid()).isEqualTo(1L);
        assertThat(sprintSimplified.getTeam()).isEqualTo("team");
        assertThat(sprintSimplified.getStatus()).isEqualTo("status");
        assertThat(sprintSimplified.getProject()).isEqualTo("project");
        assertThat(sprintSimplified.getTitle()).isEqualTo("title");
        assertThat(sprintSimplified.toString()).isEqualTo("SprintSimplified(uid=1, team=team, status=status, project=project, title=title)");
        assertThat(sprintSimplified.equals(new SprintSimplified(1L,
                "team", "status", "project", "title"))).isTrue();
        assertThat(sprintSimplified.equals(new SprintSimplified(1L,
                "team", "status", "pr2oject", "title"))).isFalse();
        assertThat(sprintSimplified.equals(new SprintSimplified(2L,
                "team", "status", "project", "title"))).isFalse();
        assertThat(sprintSimplified.equals(new SprintSimplified(1L,
                "team", "status", "project", "tit2le"))).isFalse();
        assertThat(sprintSimplified.equals(new SprintSimplified(1L,
                "team", "sta2tus", "project", "title"))).isFalse();
        assertThat(sprintSimplified.equals(new SprintSimplified(1L,
                "tea2m", "status", "project", "title"))).isFalse();
        assertDoesNotThrow(() -> sprintSimplified.setTeam("team"));
        assertDoesNotThrow(() -> sprintSimplified.setStatus("status"));
        assertDoesNotThrow(() -> sprintSimplified.setProject("project"));

        assertDoesNotThrow(() -> sprintSimplified.setTitle("title"));
        assertDoesNotThrow(() -> sprintSimplified.setUid(1L));
        assertThat(new SprintSimplified(1L,
                "team", "status", "project", "title")).isEqualTo(sprintSimplified);

    }
}

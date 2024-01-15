package com.example.manager.core.application.domain;

import com.example.manager.core.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class TaskTest {
    @Test
    public void test() {
        Task task = new Task(1L,"title","description","sprint",
                "status","project","asignee",3);
        assertThat(task.getUid()).isEqualTo(1L);
        assertThat(task.getTitle()).isEqualTo("title");
        assertThat(task.getDescription()).isEqualTo("description");
        assertThat(task.getSprint()).isEqualTo("sprint");
        assertThat(task.getStatus()).isEqualTo("status");
        assertThat(task.getProject()).isEqualTo("project");
        assertThat(task.getAssignee()).isEqualTo("asignee");
        assertThat(task.getStoryPoints()).isEqualTo(3);
        assertThat(task.toString()).isEqualTo("Task(uid=1, title=title, description=description, sprint=sprint, status=status, project=project, assignee=asignee, storyPoints=3)");
        assertThat(task.hashCode()).isEqualTo(-525842684);
        assertDoesNotThrow(() -> task.setTitle("title"));
        assertDoesNotThrow(() -> task.setDescription("description"));
        assertDoesNotThrow(() -> task.setSprint("sprint"));
        assertDoesNotThrow(() -> task.setStatus("status"));
        assertDoesNotThrow(() -> task.setProject("project"));
        assertThat(task.equals(new Task(1L,"title","description","sprint",
                "status","project","asignee",3))).isTrue();
        assertThat(task.equals(new Task(12L,"title","description","sprint",
                "status","project","asignee",3))).isFalse();
        assertThat(task.equals(new Task(1L,"titl2e","description","sprint",
                "status","project","asignee",3))).isFalse();
        assertThat(task.equals(new Task(1L,"title","de2scription","sprint",
                "status","project","asignee",3))).isFalse();
        assertThat(task.equals(new Task(1L,"title","description","sp2rint",
                "status","project","asignee",3))).isFalse();
        assertThat(task.equals(new Task(1L,"title","description","sprint",
                "status","project","asignee",2))).isFalse();
        assertThat(task.equals(new Task(1L,"title","description","sprint",
                "stat2us","project","asignee",3))).isFalse();
        assertThat(task.equals(new Task(1L,"title","description","sprint",
                "status","pr2oject","asignee",3))).isFalse();
        assertThat(task.equals(new Task(1L,"title","description","sprint",
                "status","project","asig2nee",3))).isFalse();

        assertThat(new Task(1L,"title","description","sprint",
                "status","project","asignee",3)).isEqualTo(task);
    }
}

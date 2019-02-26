package com.hwang.kanban.services;

import com.hwang.kanban.domain.Backlog;
import com.hwang.kanban.domain.ProjectTask;
import com.hwang.kanban.repositories.BacklogRepository;
import com.hwang.kanban.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);

        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;
        
        backlog.setPTSequence(BacklogSequence);

        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);


        if(projectTask.getStatus() == "" || projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        if(projectTask.getPriority() == null){
            projectTask.setPriority(3);
        }


        return projectTaskRepository.save(projectTask);

    }
}

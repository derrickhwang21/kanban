package com.hwang.kanban.services;

import com.hwang.kanban.domain.Backlog;
import com.hwang.kanban.domain.Project;
import com.hwang.kanban.domain.ProjectTask;
import com.hwang.kanban.exceptions.ProjectNotFoundException;
import com.hwang.kanban.repositories.BacklogRepository;
import com.hwang.kanban.repositories.ProjectRepository;
import com.hwang.kanban.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try{
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

        }catch(Exception e){
            throw new ProjectNotFoundException("Project not found");
        }


    }

    public Iterable<ProjectTask> findBacklogById(String id){
        Project project = projectRepository.findByProjectIdentifier(id);
        if(project == null){
            throw new ProjectNotFoundException("Project with ID: '" +id+ "' does not exist");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){


        return projectTaskRepository.findByProjectSequence(pt_id);
    }
}

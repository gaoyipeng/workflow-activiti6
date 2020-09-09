package com.sxdx.workflow.activiti.rest.service.impl;

import com.sxdx.workflow.activiti.rest.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FormService formService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;


    /**
     * Removes an individual comment with the given id.
     * @param commentId
     */
    @Override
    public void deleteComment(String commentId) {
        taskService.deleteComment(commentId);
    }

    /**
     * Removes all comments from the provided task and/or process instance
     * @param taskId
     * @param processInstanceId
     */
    @Override
    public void deleteComments(String taskId, String processInstanceId) {
        taskService.deleteComments(taskId,processInstanceId);
    }

    /**
     * 获取任务的所有审批
     * @param taskId
     * @param type
     * @return
     */
    @Override
    public List<Comment> getTaskComments(String taskId, String type) {
        List<Comment> taskComments = new ArrayList<>();
        if (type == null){
            taskComments = taskService.getTaskComments(taskId);
        }else{
            taskComments = taskService.getTaskComments(taskId,type);
        }
        return taskComments;
    }

    /**
     * 通过commentId获取审批意见
     * @param commentId
     * @return
     */
    @Override
    public Comment 	getComment(String commentId) {
        Comment comment = taskService.getComment(commentId);
        return comment;
    }

    /**
     * 通过type获取审批意见
     * @param type
     * @return
     */
    @Override
    public List<Comment> getCommentsByType(String type) {
        List<Comment> taskComments = taskService.getCommentsByType(type);
        return taskComments;
    }

    /**
     * 通过某个流程实例的全部审批意见
     * @param processInstanceId
     * @param type
     * @return
     */
    @Override
    public List<Comment> getProcessInstanceComments(String processInstanceId, String type) {
        List<Comment> taskComments = new ArrayList<>();
        if (type == null){
            taskComments = taskService.getProcessInstanceComments(processInstanceId);
        }else{
            taskComments = taskService.getProcessInstanceComments(processInstanceId,type);
        }
        return taskComments;
    }
}

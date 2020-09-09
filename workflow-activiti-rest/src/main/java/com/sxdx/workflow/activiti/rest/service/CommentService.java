package com.sxdx.workflow.activiti.rest.service;

import org.activiti.engine.task.Comment;

import java.util.List;

public interface CommentService {
    void deleteComment(String commentId);
    void deleteComments(String taskId, String processInstanceId);
    List<Comment> getTaskComments(String taskId, String type);
    Comment getComment(String commentId);
    List<Comment> getCommentsByType(String type);
    List<Comment> getProcessInstanceComments(String processInstanceId,String type);

}

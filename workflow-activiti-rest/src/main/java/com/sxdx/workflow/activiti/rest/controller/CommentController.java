package com.sxdx.workflow.activiti.rest.controller;

import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.workflow.activiti.rest.service.CommentService;
import com.sxdx.workflow.activiti.rest.service.ProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value="审批意见模块", description="审批意见模块")
@RestController
@RequestMapping("/common")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 通过commentId删除审批意见
     */
    @DeleteMapping(value = "/deleteComment/{commentId}")
    @ApiOperation(value = "通过commentId删除审批意见",notes = "通过commentId删除审批意见")
    public CommonResponse deleteComment(@PathVariable(value = "commentId",required = true) @ApiParam(value = "审批意见ID",required = true)String commentId) {
        commentService.deleteComment(commentId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("删除成功");
    }

    /**
     * 通过commentId、processInstanceId删除审批意见
     */
    @DeleteMapping(value = "/deleteComments")
    @ApiOperation(value = "通过commentId、processInstanceId删除审批意见",notes = "通过commentId、processInstanceId删除审批意见")
    public CommonResponse deleteComments(@RequestParam(value = "taskId",required = false) @ApiParam(value = "任务ID",required = false)String taskId,
                                         @RequestParam(value = "processInstanceId",required = false) @ApiParam(value = "流程实例ID",required = false)String processInstanceId) {
        commentService.deleteComments(taskId,processInstanceId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("删除成功");
    }

    /**
     * 通过commentId获取审批意见
     */
    @GetMapping(value = "/getComment/{commentId}")
    @ApiOperation(value = "通过commentId获取审批意见",notes = "通过commentId获取审批意见")
    public CommonResponse getComment(@PathVariable(value = "commentId",required = true) @ApiParam(value = "审批意见ID",required = true)String commentId) {
        Comment comment = commentService.getComment(commentId);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(comment);
    }

    /**
     * 通过type获取审批意见
     */
    @GetMapping(value = "/getCommentsByType/{type}")
    @ApiOperation(value = "通过type获取审批意见",notes = "通过type获取审批意见")
    public CommonResponse getCommentsByType(@PathVariable(value = "type",required = true) @ApiParam(value = "审批意见type",required = true)String type) {
        List<Comment> taskComments = commentService.getCommentsByType(type);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(taskComments);
    }

    /**
     * 获取流程实例的全部审批意见
     */
    @PostMapping(value = "/getProcessInstanceComments")
    @ApiOperation(value = "获取流程实例的全部审批意见",notes = "获取流程实例的全部审批意见")
    public CommonResponse getProcessInstanceComments(@RequestParam(value = "processInstanceId",required = true) @ApiParam(value = "流程实例ID",required = true)String processInstanceId,
                                                     @RequestParam(value = "type",required = false) @ApiParam(value = "类型",required = false)String type) {
        List<Comment> taskComments = commentService.getProcessInstanceComments(processInstanceId,type);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(taskComments);
    }

    /**
     * 获取任务的审批意见
     */
    @PostMapping(value = "/getTaskComments")
    @ApiOperation(value = "获取任务的审批意见",notes = "获取任务的审批意见")
    public CommonResponse getTaskComments(@RequestParam(value = "taskId",required = true) @ApiParam(value = "任务ID",required = true)String taskId,
                                          @RequestParam(value = "type",required = false) @ApiParam(value = "类型",required = false)String type,
                                          HttpServletRequest request) {
        List<Comment> taskComments = commentService.getTaskComments(taskId, type);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(taskComments);
    }




}

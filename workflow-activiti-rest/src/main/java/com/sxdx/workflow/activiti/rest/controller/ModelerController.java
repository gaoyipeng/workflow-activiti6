package com.sxdx.workflow.activiti.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.util.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

@Api(value="流程模型模块", description="流程模型模块")
@RestController
@Slf4j
public class ModelerController {

    @Autowired
    private RepositoryService repositoryService;

    @ApiOperation(value = "创建模型",notes = "创建模型")
    @PostMapping(value = "/modeler/create")
    public CommonResponse create(@RequestParam(value = "name",required=true) @ApiParam("模型名") String name,
                       @RequestParam(value = "key",required=true) @ApiParam("模型key")String key,
                       @RequestParam(value = "description",required=true) @ApiParam("模型描述")String description) {
       /* String name = "请假流程";
        String key = "qingjia";
        String description = "这是一个简单的请假流程";*/

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");

            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");

            editorNode.put("stencilset", stencilSetNode);

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(MODEL_DESCRIPTION, description);

            Model newModel = repositoryService.newModel();
            newModel.setMetaInfo(modelObjectNode.toString());
            newModel.setName(name);
            newModel.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(newModel);
            repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes("utf-8"));

            System.out.println("生成的moduleId:"+newModel.getId());
            return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("部署成功").data(newModel);
        } catch (Exception e) {
            log.error("创建模型失败");
        }
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("部署成功");
    }


    @GetMapping(value = "/modeler/deploy/{modelId}")
    @ApiOperation(value = "根据 Model部署流程",notes = "流程模型（act_re_model）转流程定义（act_re_procdef）")
    public CommonResponse deploy(@PathVariable("modelId") @ApiParam("流程模型Id") String modelId) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
            log.info("部署成功，部署ID=" + deployment.getId());
            return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("部署成功");
        } catch (Exception e) {
            log.error("根据模型部署流程失败：modelId={}", modelId, e);
        }
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("部署失败");
    }


    @GetMapping(value = "/modeler/export/{modelId}")
    @ApiOperation(value = "将流程模型导出为 bpmn文件",notes = "将流程模型导出为 bpmn文件")
    public void export(@PathVariable("modelId") @ApiParam("流程模型Id") String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 流程非空判断
            if (!CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

                ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
                String filename = bpmnModel.getMainProcess().getId() + ".bpmn";
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                IOUtils.copy(in, response.getOutputStream());
                response.flushBuffer();
            } else {
                log.error("导出model的bpmn文件失败：modelId={}", modelId);
            }
        } catch (Exception e) {
            log.error("导出model的bpmn文件失败：modelId={}", modelId, e);
        }
    }

    @GetMapping(value = "/modeler/delete/{modelId}")
    @ApiOperation(value = "删除流程模型",notes = "删除流程模型")
    public CommonResponse remove(@PathVariable("modelId") @ApiParam("流程模型Id") String modelId) {
       repositoryService.deleteModel(modelId);
       return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("删除流程模型成功");
    }

}

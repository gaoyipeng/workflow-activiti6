package com.sxdx.workflow.activiti.rest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.sxdx.common.config.GlobalConfig;
import com.sxdx.common.constant.CodeEnum;
import com.sxdx.common.constant.Constants;
import com.sxdx.common.exception.base.CommonException;
import com.sxdx.common.util.CommonResponse;
import com.sxdx.common.util.StringUtils;
import com.sxdx.common.util.file.FileUploadUtils;
import com.sxdx.workflow.activiti.rest.service.ProcessDefinitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Api(value="流程定义模块", description="流程定义模块")
@RestController
@RequestMapping("/definition")
@Slf4j
public class ProcessDefinitionController {

    private String prefix = "definition";

    @Autowired
    private ProcessDefinitionService processDefinitionService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;


    /**
     * 部署流程定义
     */
    @PostMapping("/upload")
    @ApiOperation(value = "部署流程定义",notes = "部署流程定义（支持文件格式：zip、bar、bpmn）")
    public CommonResponse upload(@RequestParam("processDefinition") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String extensionName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
                if (!"bpmn".equalsIgnoreCase(extensionName)
                        && !"zip".equalsIgnoreCase(extensionName)
                        && !"bar".equalsIgnoreCase(extensionName)) {
                    return new CommonResponse().code(CodeEnum.FAILURE.getCode()).message("流程定义文件仅支持 bpmn, zip 和 bar 格式！");
                }
                // p.s. 此时 FileUploadUtils.upload() 返回字符串 fileName 前缀为 Constants.RESOURCE_PREFIX，需剔除
                // 详见: FileUploadUtils.getPathFileName(...)
                String fileName = FileUploadUtils.upload(GlobalConfig.getProfile() + "/processDefiniton", file);
                if (StringUtils.isNotBlank(fileName)) {
                    String realFilePath = GlobalConfig.getProfile() + fileName.substring(Constants.RESOURCE_PREFIX.length());
                    processDefinitionService.deployProcessDefinition(realFilePath);
                    return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("部署成功");
                }
            }
            return new CommonResponse().code(CodeEnum.FAILURE.getCode()).message("不允许上传空文件！");
        }
        catch (Exception e) {
            log.error("上传流程定义文件失败！", e);
            return new CommonResponse().code(CodeEnum.FAILURE.getCode()).message(e.getMessage());
        }
    }

    @PostMapping("/findProcessDefinition")
    @ApiOperation(value = "查询流程定义列表",notes = "查询流程定义列表")
    public CommonResponse findProcessDefinition(@RequestParam(value = "processDefinitionKey",required = false) @ApiParam("流程定义Key")String processDefinitionKey,
                                                @RequestParam(value = "processDefinitionName",required = false) @ApiParam("流程定义Name")String processDefinitionName,
                                                @RequestParam(value = "pageNum", required = false,defaultValue = "1")@ApiParam(value = "页码" ,required = false)int pageNum,
                                                @RequestParam(value = "pageSize", required = false,defaultValue = "10")@ApiParam(value = "条数" ,required = false)int pageSize)  {
        List<Map<String ,Object>> list= processDefinitionService.findProcessDefinition(pageNum,pageSize,processDefinitionKey,processDefinitionName);
        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).data(list);
    }

    /**
     * 转换流程定义为模型
     * @param processDefinitionId 流程定义id
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @PostMapping(value = "/convertToModel")
    @ApiOperation(value = "转换流程定义为模型",notes = "转换流程定义为模型")
    public CommonResponse convertToModel(@RequestParam("processDefinitionId") @ApiParam("流程实例ID,act_re_procdef表ID")String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        org.activiti.engine.repository.ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("转换成功");
    }

    /**
     * 删除已部署的流程定义
     * @param deploymentId 流程部署ID
     */
    @GetMapping(value = "/process/delete/{deploymentId}")
    @ApiOperation(value = "删除已部署的流程定义",notes = "删除已部署的流程定义")
    public CommonResponse delete(@PathVariable("deploymentId") @ApiParam("流程定义ID (act_re_deployment表id)")  String deploymentId) {

        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery()
                .deploymentId(deploymentId)
                .list();
        if (!CollectionUtils.isEmpty(instanceList)) {
            // 存在流程实例的流程定义
            throw new CommonException("删除失败，存在运行中的流程实例");
        }
        repositoryService.deleteDeployment(deploymentId, true); // true 表示级联删除引用，比如 act_ru_execution 数据

        return new CommonResponse().code(CodeEnum.SUCCESS.getCode()).message("删除已部署的流程定义成功");
    }
}

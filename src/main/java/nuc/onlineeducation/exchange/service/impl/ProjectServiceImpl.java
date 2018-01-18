package nuc.onlineeducation.exchange.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.dao.ProjectMapper;
import nuc.onlineeducation.exchange.dao.UserMapper;
import nuc.onlineeducation.exchange.model.Project;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.IProjectService;
import nuc.onlineeducation.exchange.service.ISensitiveService;
import nuc.onlineeducation.exchange.util.DateTimeUtil;
import nuc.onlineeducation.exchange.vo.ProjectVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 23:52 2018/1/13.
 */
@Service(value = "iProjectService")
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ISensitiveService iSensitiveService;

    @Override
    public ServerResponse saveProject(Project project) {
        if (project == null) {
            return ServerResponse.createByErrorMessage("发布课题失败，课题实体不能为空");
        }
        // TML标签过滤H -> 转义
        project.setTitle(HtmlUtils.htmlEscape(project.getTitle()));
        // 敏感词过滤
        project.setTitle(iSensitiveService.filter(project.getTitle()));
        int result = projectMapper.insert(project);
        if (result > 0) {
            return ServerResponse.createBySuccess("发布课题成功", project.getId());
        }
        return ServerResponse.createByErrorMessage("发布课题失败");
    }

    @Override
    public ServerResponse<PageInfo> getProjects(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Project> projectList = projectMapper.selectProjects();

        List<ProjectVO> projectVOList = Lists.newArrayList();
        for (Project projectItem : projectList) {
            ProjectVO projectVO = assembleProjectVO(projectItem);
            projectVOList.add(projectVO);
        }
        PageInfo pageInfo = new PageInfo(projectList);
        pageInfo.setList(projectVOList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<ProjectVO> getProjectDetail(Integer projectId) {
        if (StringUtils.isBlank(projectId.toString())) {
            return ServerResponse.createByErrorMessage("课题id不能为空");
        }
        Project project = projectMapper.selectByPrimaryKey(projectId);
        ProjectVO projectVO = assembleProjectVO(project);
        return ServerResponse.createBySuccess("查询课题详情成功", projectVO);
    }

   /* @Override
    public ServerResponse<Project> getProjectById(Integer projectId) {
        if (StringUtils.isBlank(projectId.toString())) {
            return ServerResponse.createByErrorMessage("课题id不能为空");
        }
        Project project = projectMapper.selectByPrimaryKey(projectId);
        return ServerResponse.createBySuccess("查询课题详情成功", project);
    }*/

    @Override
    public ServerResponse removeProjectById(Integer projectId) {
        if (StringUtils.isBlank(projectId.toString())) {
            return ServerResponse.createByErrorMessage("课题的id不能为空");
        }
        // 尽量别删
        int result = projectMapper.deleteByPrimaryKey(projectId);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("课题删除失败");
    }

    @Override
    public ServerResponse updateProject(Project project) {
        // TML标签过滤H -> 转义
        project.setTitle(HtmlUtils.htmlEscape(project.getTitle()));
        // 敏感词过滤
        project.setTitle(iSensitiveService.filter(project.getTitle()));
        int result = projectMapper.updateByPrimaryKeySelective(project);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("课题详情更改成功");
        }
        return ServerResponse.createByErrorMessage("课题详情更改失败");
    }

    private ProjectVO assembleProjectVO(Project project) {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(project.getId());
        projectVO.setTitle(project.getTitle());
        projectVO.setDetail(project.getDetail());
        projectVO.setOrigin(project.getOrigin());

        Const.OriginTypeEnum[] originTypeEnums = Const.OriginTypeEnum.values();
        for (Const.OriginTypeEnum originTypeEnumItem :
                originTypeEnums) {
            if (originTypeEnumItem.getCode() == project.getOrigin()) {
                projectVO.setOriginName(originTypeEnumItem.getValue());
            }
            continue;
        }
        /*switch (originTypeEnum) {
            case SOFTWARE_COLLEGE:
                projectVO.setOriginName(Const.OriginTypeEnum.SOFTWARE_COLLEGE.getValue());
                break;
            case BIGDATA_COLLEGE:
                projectVO.setOriginName(Const.OriginTypeEnum.BIGDATA_COLLEGE.getValue());
                break;
            case CHEMICALINDUSTRY_COLLEGE:
                projectVO.setOriginName(Const.OriginTypeEnum.CHEMICALINDUSTRY_COLLEGE.getValue());
                break;
            default:
                projectVO.setOriginName(Const.OriginTypeEnum.UNKNOWN.getValue());// 未知来源
                break;
        }*/
        projectVO.setCreateTime(DateTimeUtil.dateToStr(project.getCreateTime()));
        projectVO.setUpdateTime(DateTimeUtil.dateToStr(project.getUpdateTime()));

        User user = userMapper.selectByPrimaryKey(project.getUserId());
        projectVO.setHeadUrl(user.getHeadUrl());
        projectVO.setUserId(user.getId());
        projectVO.setUsername(user.getUsername());
        return projectVO;
    }
}

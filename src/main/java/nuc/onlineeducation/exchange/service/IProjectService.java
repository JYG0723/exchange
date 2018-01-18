package nuc.onlineeducation.exchange.service;

import com.github.pagehelper.PageInfo;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.Project;
import nuc.onlineeducation.exchange.vo.ProjectVO;

/**
 * @author Ji YongGuang.
 * @date 23:52 2018/1/13.
 */
public interface IProjectService {

    ServerResponse saveProject(Project project);

    ServerResponse<PageInfo> getProjects(Integer pageNum, Integer pageSize);

    ServerResponse<ProjectVO> getProjectDetail(Integer projectId);

    ServerResponse removeProjectById(Integer projectId);

    ServerResponse updateProject(Project project);

//    ServerResponse<Project> getProjectById(Integer projectId);
}

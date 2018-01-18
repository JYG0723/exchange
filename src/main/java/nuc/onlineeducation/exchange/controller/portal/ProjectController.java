package nuc.onlineeducation.exchange.controller.portal;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ResponseCodeEnum;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.Project;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.IFileService;
import nuc.onlineeducation.exchange.service.IProjectService;
import nuc.onlineeducation.exchange.service.IUserService;
import nuc.onlineeducation.exchange.util.PropertiesUtil;
import nuc.onlineeducation.exchange.vo.ProjectVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Ji YongGuang.
 * @date 23:50 2018/1/13.
 */
@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    private IProjectService iProjectService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IFileService iFileService;


    /**
     * 新增课题
     *
     * @param project 课题实体
     * @return
     */
    @PostMapping("/")
    public ServerResponse projectSave(Project project) {
        return iProjectService.saveProject(project);
    }

    /**
     * 查询课题列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/")
    public ServerResponse<PageInfo> getProjects(@RequestParam(value = "pageNum", defaultValue = "0") Integer
                                                        pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer
                                                        pageSize) {
        return iProjectService.getProjects(pageNum, pageSize);
    }

    /**
     * 查询课题详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ServerResponse<ProjectVO> getProjectDetail(@PathVariable("id") Integer id) {
        return iProjectService.getProjectDetail(id);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping(value = "/file/upload")
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false)
                                         MultipartFile file, HttpServletRequest request) {
        User user = hostHolder.getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(), "用户未登录,请教师账号");
        }
//        User user = iUserService.getUserById(65).getData();
        if (iUserService.checkTeacherRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            // 文件上传
            String targetFileName = iFileService.upload(file, path, Const.UploadFileType.UPLOAD_PATH_FILE.getValue());
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMessage("非教师账号，无权限操作");
        }
    }


    @PostMapping(value = "/richtext/img/upload")
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false)
            MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
//        User user = hostHolder.getUser();
        User user = iUserService.getUserById(hostHolder.getUser().getId()).getData();
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录教师账号");
            return resultMap;
        }
        // simditor
        /*{
            "success": true/false,
                "msg": "error message", # optional
            "file_path": "[real file path]"
        }*/
        if (iUserService.checkTeacherRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            // 文件上传
            String targetFileName = iFileService.upload(file, path, Const.UploadFileType.UPLOAD_PATH_IMAGE.getValue());
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "非教师账号，无权限操作");
            return resultMap;
        }
    }
}

package nuc.onlineeducation.exchange.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import nuc.onlineeducation.exchange.service.IFileService;
import nuc.onlineeducation.exchange.util.FTPUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Ji YongGuang.
 * @date 9:26 2018/1/14.
 */
@Log4j2
@Service(value = "iFileService")
public class FileServiceImpl implements IFileService {

    /**
     * 文件上传功能
     *
     * @param file 上传的文件
     * @param path 上传的路径
     * @return 文件服务器上生成的目标文件名
     */
    @Override
    public String upload(MultipartFile file, String path, String remotePath) {
        String fileName = file.getOriginalFilename();
        // 扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 新文件名，防止重名覆盖 A:abc.txt B:abc.txt
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始文件上传，上传的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

        // 声明 目录
        File fileDir = new File(path);
        if (!fileDir.exists()) {// 第一次进来，不存在创建该目录
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        // webapp下的upload目录下新建一个name = uploadFileName的空文件，用来装载上传上来的文件
        File targetFile = new File(path, uploadFileName);
        try {
            // 将接收到的文件内容[传输]到给定的目标文件
            file.transferTo(targetFile);
            // 将文件上传到FTP服务器，此时targetFile的内容已被填充
            FTPUtil.uploadFile(Lists.newArrayList(targetFile), remotePath);
            // upload文件夹下的文件删除掉，upload空文件夹可以保留
            targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常!", e);
            return null;
        }
        return targetFile.getName();
    }
}

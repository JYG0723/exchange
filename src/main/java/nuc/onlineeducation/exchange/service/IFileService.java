package nuc.onlineeducation.exchange.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ji YongGuang.
 * @date 9:26 2018/1/14.
 */
public interface IFileService {

    String upload(MultipartFile file, String path, String remotePath) ;
}

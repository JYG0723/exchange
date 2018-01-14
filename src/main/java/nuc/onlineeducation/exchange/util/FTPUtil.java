package nuc.onlineeducation.exchange.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 9:28 2018/1/14.
 */
@Log4j2
@Getter
@Setter
public class FTPUtil {

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * 文件上传
     * <p/>
     * FTPUtil只对外开放这一个接口
     *
     * @param fileList 上传的文件集合,批量上传
     * @return 本次操作是否执行成功，true OR false
     * @throws IOException 交给业务层处理的IOException
     */
    public static boolean uploadFile(List<File> fileList, String remotePath) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        log.info("开始连接FTP服务器");
        // 将文件上传到ftp服务器文件夹下的img文件夹下。因为我们要上传的是图片，所以这里写死了。
        // 不同的文件可以将该remotePath参数设置成方法的参数，动态修改
        boolean result = ftpUtil.uploadFile(remotePath, fileList);
        log.info("开始连接FTP服务器，结束上传，上传结果:{}", result);
        return result;
    }

    /**
     * 文件上传的具体方法
     * <p/>
     * 拆出来一个封装，上传的具体逻辑在这个方法中
     *
     * @param remotePath 远程路径,文件夹
     * @param fileList   上传文件的集合
     * @return 文件上传操作的执行结果 true/false
     * @throws IOException IOException
     */
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        // 是否上传成功,默认true
        boolean uploaded = true;
        FileInputStream fis = null;
        // 链接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                // 首先修改工作目录,需不需要切换当前工作文件夹位置，如果remotePath = null，就切换不了了。默认上传到FTP服务器文件夹下
                // 这里传过来的是img，那么就是上传到了FTP服务器文件夹下的img文件夹下。
                ftpClient.changeWorkingDirectory(remotePath);
                // 设置缓冲区 1024(Byte) = 1KB
                ftpClient.setBufferSize(1024);
                // 控制连接的字符编码
                ftpClient.setControlEncoding("UTF-8");
                // 设置上传到FTP服务器的文件类型,二进制文件类型。这样会防止一些乱码的问题
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 开启本地被动模式，FTP服务器上配置的是被动模式，并对外提供了服务的被动端口范围
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList) {
                    fis = new FileInputStream(fileItem);
                    // 往FTP服务器上存储文件,{文件名},{File流}
                    ftpClient.storeFile(fileItem.getName(), fis);
                }
            } catch (IOException e) {
                log.error("上传文件异常", e);
                uploaded = false;
            } finally {
                // 上传成功之后需要释放掉链接，不然时间长了容易出问题
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    /**
     * 链接FTP服务器
     * <p/>
     * 将连接FTP服务器的功能拆分出来
     *
     * @param ip       ftp_ip
     * @param port     ftp_port
     * @param username ftp_username
     * @param pwd      ftp_password
     * @return 连接操作的执行结果
     */
    private boolean connectServer(String ip, int port, String username, String pwd) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            // 登录,返回值是boolean，因为是拉出来的服务功能，所以需要将链接结果返回给上一层。
            isSuccess = ftpClient.login(username, pwd);
        } catch (IOException e) {
            log.error("链接FTP服务器异常", e);
        }
        return isSuccess;
    }

}

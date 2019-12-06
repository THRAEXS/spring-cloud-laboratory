package org.thraex.fs.base.service;

import org.springframework.web.multipart.MultipartFile;
import org.thraex.fs.base.entity.FileInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

/**
 * @author 鬼王
 * @date 2019/11/25 21:21
 */
public interface FileService {

    /**
     * Download the archive
     *
     * @param request
     * @param response
     * @param archiveName
     * @param conditions
     */
    void downloadArchive(HttpServletRequest request, HttpServletResponse response,
                         String archiveName, Stream<FileInfo> conditions);

    /**
     * Save files
     *
     * @param file
     * @param app default: others
     * @return {@link FileInfo}
     */
    FileInfo save(MultipartFile file, String app);

    /**
     * Delete files
     */
    void delete();

    /**
     * File archiving
     */
    void archive();

    /**
     * File to base64
     */
    void base64();

}

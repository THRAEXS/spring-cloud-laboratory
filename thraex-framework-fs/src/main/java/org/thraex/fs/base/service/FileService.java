package org.thraex.fs.base.service;

import org.springframework.web.multipart.MultipartFile;
import org.thraex.fs.base.entity.FileInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author 鬼王
 * @date 2019/11/25 21:21
 */
public interface FileService {

    /**
     * Get file list
     *
     * @return {@link FileInfo} list
     */
    List<FileInfo> list();

    /**
     * Get file list
     *
     * @param ids {@link FileInfo} id list
     * @return
     */
    List<FileInfo> list(List<String> ids);

    /**
     * Get file
     *
     * @param id {@link FileInfo} id
     * @return {@link FileInfo}
     */
    FileInfo find(String id);

    /**
     * Save files
     *
     * @param file {@link MultipartFile}
     * @param app default: others
     * @return {@link FileInfo}
     */
    FileInfo save(MultipartFile file, String app);

    /**
     * Batch save files
     *
     * @param files {@link MultipartFile} list
     * @param app default: others
     * @return {@link FileInfo} list
     */
    List<FileInfo> save(List<MultipartFile> files, String app);

    /**
     * Delete files

     * @param id {@link FileInfo} id
     */
    void delete(String id);

    /**
     * Clear all files
     */
    void clear();

    /**
     * File archiving
     */
    void archive();

    /**
     * File to base64
     */
    void base64();

    /**
     * Download the archive
     *
     * @param request
     * @param response
     * @param archiveName
     * @param conditions
     */
    @Deprecated
    void downloadArchive(HttpServletRequest request, HttpServletResponse response,
                         String archiveName, Stream<FileInfo> conditions);

}

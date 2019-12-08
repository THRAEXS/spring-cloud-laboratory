package org.thraex.fs.base.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thraex.fs.base.entity.FileInfo;
import org.thraex.fs.base.mapper.FileInfoMapper;
import org.thraex.fs.base.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 鬼王
 * @date 2019/11/25 21:31
 */
@Service
@Log4j2
public class FileServiceImpl implements FileService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Value("${thraex.fs.rootDir:./fs}")
    private String rootDir;

    private List<FileInfo> infoList;

    public FileServiceImpl() {
        log.info("Initializing data.");

        infoList = Stream.of(
                "CB190说明书.pdf", "baby-name.md",
                "example-gui-config.json", "index.vue",
                "sublime.jpg", "yanxin_label.xlsx")
                .map(it -> new FileInfo(String.valueOf(it.hashCode()), it,
                        "/fs/thraex-fs/" + it, it.substring(it.lastIndexOf("."))))
                .peek(it -> log.info("{}[{}]", it.getName(), it.getId()))
                .collect(Collectors.toList());
    }

    private List<FileInfo> getInfoList(List<String> ids) {
        return infoList.stream().filter(it -> ids.contains(it.getId())).collect(Collectors.toList());
    }

    @Override
    public void downloadArchive(HttpServletRequest request, HttpServletResponse response,
                                String archiveName, Stream<FileInfo> conditions) {
        try (OutputStream os = response.getOutputStream()) {
            List<String> ids = Optional.of(
                    conditions.parallel()
                            .filter(it -> !(it.getId() == null || it.getId().trim().length() == 0))
                            .map(it -> it.getId().trim())
                            .collect(Collectors.toList()))
                    .map(it -> it.isEmpty() ? null : it)
                    .orElseThrow(() -> new IllegalArgumentException("参数有误"));

            List<FileInfo> infos = Optional.of(
                    getInfoList(ids))
                    .map(it -> it.isEmpty() ? null : it)
                    .orElseThrow(() -> new FileNotFoundException("无匹配文件信息"));

            List<Path> paths = Optional.of(
                    infos.parallelStream()
                            .map(it -> Paths.get(it.getPath()))
                            .filter(it -> Files.exists(it))
                            .collect(Collectors.toList()))
                    .map(it -> it.isEmpty() ? null : it)
                    .orElseThrow(() -> new FileNotFoundException("文件未找到"));

            String tmpName = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            Path zip = Files.createTempFile(tmpName, ".zip");

            FileOutputStream fos = new FileOutputStream(zip.toFile());
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            paths.stream().forEach(it -> {
                try {
                    File fileToZip = it.toFile();
                    FileInputStream fis = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(it.getFileName().toString());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            zipOut.close();
            fos.close();

            String filename = Optional.ofNullable(archiveName)
                    .map(it -> it.trim()).orElse(tmpName) + ".zip";
            response.reset();
            response.setContentType("application/x-zip-compressed;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    filename.getBytes("utf-8"));
            FileInputStream fis = new FileInputStream(zip.toFile());
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                os.write(bytes, 0, length);
            }
            fis.close();
            os.flush();
        } catch (Exception e) {
            try (OutputStream os = response.getOutputStream()) {
                response.setContentType("application/json;charset=utf-8");
                os.write(e.getMessage().getBytes("utf-8"));
                os.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public FileInfo save(MultipartFile file, String app) {
        FileInfo info = new FileInfo();
        String originName = file.getOriginalFilename();
        info.setName(originName);
        info.setContentType(file.getContentType());
        info.setSuffix(originName.substring(originName.lastIndexOf(".")));
        info.setSize(file.getSize());
        try {
            long sn = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            String others = "./fs/others/";
            Files.createDirectories(Paths.get(others));
            Path path = Paths.get(others + sn + info.getSuffix());
            Path file1 = Files.createFile(path);
            file.transferTo(file1);
            info.setPath(path.toString());
            info.setDirectory(path.getParent().toString());
            fileInfoMapper.insert(info);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }

    public static void main(String[] args) {
        System.out.println(File.separator);
        System.out.println(File.separatorChar);
        System.out.println(File.pathSeparator);
        System.out.println(File.pathSeparatorChar);
    }

    @Override
    public void delete() {

    }

    @Override
    public void archive() {

    }

    @Override
    public void base64() {

    }
}

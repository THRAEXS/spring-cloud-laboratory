package org.thraex.fs.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thraex.fs.base.entity.FileInfo;
import org.thraex.fs.base.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author 鬼王
 * @date 2019/11/25 21:14
 */
@RestController
@RequestMapping("download")
public class DownloadController {

    @Autowired
    private FileService fileService;

    @RequestMapping(
            value = { "/archive", "/archive/{archiveName}" },
            method = { RequestMethod.GET, RequestMethod.POST }
    )
    public void archive(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable(required = false) String archiveName,
            @RequestBody(required = false) Map<String, List<FileInfo>> postQuery,
            @RequestParam(required = false) String getQuery) {
        Supplier<Optional<Stream<FileInfo>>> stream = HttpMethod.GET.matches(request.getMethod()) ?
                () -> Optional.ofNullable(getQuery)
                        .map(it -> Stream.of(it.split(",")))
                        .map(it -> it.map(i -> i.split("::"))
                                .map(i -> new FileInfo(i[0], i.length > 1 ? i[1] : null))) :
                () -> Optional.ofNullable(postQuery).map(it -> it.get("files")).map(it -> it.stream());

        fileService.downloadArchive(request, response, archiveName, stream.get().orElse(Stream.of()));
    }

}

package dev.zendal.hdfs.controller.rest;

import dev.zendal.hdfs.dto.response.FileItem;
import dev.zendal.hdfs.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(WebConstants.FULL_WEB + "/browse")
@RequiredArgsConstructor
public class HDFSRestController {

    private final FileSystem fileSystem;

    @Operation(
            description = "Просмотр файлов"
    )
    @GetMapping
    public List<FileItem> browse(@RequestParam(defaultValue = "/") String path) throws IOException {
        Path hdfsPath = new Path(path);
        FileStatus[] fileStatuses = this.fileSystem.listStatus(hdfsPath);

        return Arrays.stream(fileStatuses)
                .map(val -> FileItem.builder()
                        .name(val.getPath().getName())
                        .size(val.getLen())
                        .isDirectory(val.isDirectory())
                        .lastModified(val.getModificationTime())
                        .build()
                ).toList();
    }

    @Operation(
            description = "Создать директорию"
    )
    @PostMapping("/directories")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createDirectory(@RequestParam(defaultValue = "/") String path) throws IOException {
        if (!this.fileSystem.mkdirs(new Path(path))) {
            throw new EntityNotFoundException("Can't create directory: " + path);
        }
    }


}

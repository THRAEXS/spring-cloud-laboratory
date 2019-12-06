package org.thraex.fs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thraex.fs.base.entity.FileInfo;
import org.thraex.fs.base.mapper.FileInfoMapper;

/**
 * @Author 鬼王
 * @Date 2019/08/07 16:55
 */
@SpringBootApplication
public class FsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FsApplication.class, args);
    }

    private final FileInfoMapper fileInfoMapper;

    public FsApplication(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        FileInfo fi = new FileInfo();
        fi.setName("yy.bat");
        fi.setSuffix(".bat");
        fi.setSize(200);
        fi.setPath("/fs/yy.bat");
        fi.setDirectory("/fs/");
        int insert = this.fileInfoMapper.insert(fi);
        System.out.println(insert);

        System.out.println(this.fileInfoMapper.findById("THRAEX"));
        System.out.println(this.fileInfoMapper.getList());

        System.out.println(this.fileInfoMapper.delete("THRAEX"));
        System.out.println(this.fileInfoMapper.clear());
    }
}

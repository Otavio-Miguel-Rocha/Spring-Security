package net.weg.otavio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@NoArgsConstructor
@Entity(name = "tb_file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

    public File(MultipartFile file) throws IOException {
        this.name = file.getOriginalFilename();
        this.type = file.getContentType();
        this.content = file.getBytes();
    }
}

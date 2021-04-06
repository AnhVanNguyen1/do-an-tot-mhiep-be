package response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileDTO {
    private String fileName;
    private String fileUrl;
    private String message;
}


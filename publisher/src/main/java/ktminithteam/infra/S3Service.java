package ktminithteam.infra;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {
    private final S3Client s3Client;
    @Value("${aws.bucket}")
    private String bucket;
    private Region region;

    public S3Service(
            @Value("${aws.access-key}") String accessKey,
            @Value("${aws.secret-key}") String secretKey,
            @Value("${aws.region}") String region
    ) {
        this.region = Region.of(region);
        this.s3Client = S3Client.builder()
                .region(Region.of(region)) // Change to your region
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }
    // S3 서비스 관련 메서드 구현

    public String uploadCoverImage(MultipartFile file, String key) throws IOException {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
        );
        // 버킷이 public이면 아래 URL로 접근 가능
        return "https://" + bucket + ".s3." + region.id() + ".amazonaws.com/" + key;
    }

    public String uploadSummaryAsPdf(String summary, String key) {
        try {
            // 1. Create PDF in memory
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            InputStream fontStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("fonts/NanumGothic.ttf");
            if (fontStream == null) {
                throw new RuntimeException("폰트 파일을 classpath에서 찾을 수 없습니다: fonts/NanumGothic.ttf");
            }
            BaseFont bf = BaseFont.createFont(
                    "fonts/NanumGothic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED,
                    false, fontStream.readAllBytes(), null
            );
            Font font = new Font(bf, 12);

            document.add(new Paragraph(summary, font));
            document.close();

            // 2. Upload PDF to S3
            String pdfKey = key.endsWith(".pdf") ? key : key + ".pdf";
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(pdfKey)
                            .contentType("application/pdf")
                            .build(),
                    RequestBody.fromBytes(baos.toByteArray())
            );

            // 3. Return S3 URL
            return "https://" + bucket + ".s3." + region.id() + ".amazonaws.com/" + pdfKey;
        } catch (Exception e) {
            System.out.println("⚠\uFE0F pdf 생성 실패:" + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}

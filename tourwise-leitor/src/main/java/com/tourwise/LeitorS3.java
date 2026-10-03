package com.tourwise;

import java.io.InputStream;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class LeitorS3 {

    public InputStream lerS3 (String bucket, String chave) {
        try (S3Client s3 = S3Client.builder().region(Region.of(System.getenv()
                        .getOrDefault("AWS_REGION", "us-east-1")))
                .build()) {

            GetObjectRequest req = GetObjectRequest.builder()
                    .bucket(System.getenv("S3_BUCKET"))
                    .key(System.getenv("S3_KEY"))
                    .build();

            try (ResponseInputStream<GetObjectResponse> in = s3.getObject(req);

            )
        }




    }
}

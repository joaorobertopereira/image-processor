package bet.logame.app.writer;

import bet.logame.app.model.SisCassinoJogo;
import bet.logame.app.repository.SisCassinoJogoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Component
@Slf4j
public class ImageFileWriter implements ItemWriter<SisCassinoJogo> {

    @Autowired
    private SisCassinoJogoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${upload.base-url}")
    private String baseUrl;

    @Value("${upload.default-folder}")
    private String defaultFolder;

    @Override
    public void write(Chunk<? extends SisCassinoJogo> chunk) throws Exception {
        for (SisCassinoJogo jogo : chunk) {
            if (jogo != null) {
                // Atualize o banco de dados
                log.info("Salvando jogo: {}", jogo.getGameid());
                repository.save(jogo);

                // Faça o upload da imagem
                log.info("Fazendo upload da imagem: {}", jogo.getGameid());
                String fileName = jogo.getImagemHorizontal() != null ? jogo.getImagemHorizontal() : jogo.getImagemQuadrada();
                if (fileName != null) {
                    File file = new File("src/main/resources/image/" + fileName);
                    if (file.exists()) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_PNG);

                        FileSystemResource fileResource = new FileSystemResource(file);
                        HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(fileResource, headers);

                        String url = String.format("%s/%s/%s", baseUrl, defaultFolder, fileName);
                        restTemplate.put(url, requestEntity, String.class);
                    }
                }
            }
        }
    }
}
package bet.logame.app.processor;

import bet.logame.app.model.SisCassinoJogo;
import bet.logame.app.repository.SisCassinoJogoRepository;
import bet.logame.app.utils.FileNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
@Slf4j
public class ImageFileProcessor implements ItemProcessor<File, SisCassinoJogo> {
    private final SisCassinoJogoRepository repository;

    @Value("${upload.download-url-base}")
    private String urlBase;

    @Value("${upload.default-folder}")
    private String defaultFolder;

    public ImageFileProcessor(SisCassinoJogoRepository repository) {
        this.repository = repository;
    }

    @Override
    public SisCassinoJogo process(File file) throws Exception {
        log.info("Processando arquivo: {}", file.getName());
        String fileName = file.getName();
        String[] parts = fileName.split("-");
        String gameId = parts[0];
        String provedor = parts[1];
        String formato = parts[2];
        String color = "#" + parts[3].substring(0, 6);

        Optional<SisCassinoJogo> optionalJogo = repository.findByGameidAndFornecedor(gameId, provedor);
        if (optionalJogo.isPresent()) {
            SisCassinoJogo jogo = optionalJogo.get();
            String newFileName = FileNameGenerator.gerarNomeArquivo();
            String downloadLink = String.format("%s/%s/%s", urlBase, defaultFolder, newFileName);

            if (formato.equalsIgnoreCase("vertical")) {
                jogo.setImagemHorizontal(downloadLink);
            } else if (formato.equalsIgnoreCase("square")) {
                jogo.setImagemQuadrada(downloadLink);
            }

            jogo.setColor(color);
            return jogo;
        }
        return null;
    }
}
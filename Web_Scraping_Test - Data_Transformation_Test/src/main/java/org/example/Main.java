package org.example;

import org.example.data.TransformationDataService;
import org.example.webscraping.WebScrapingService;
import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        WebScrapingService webScrapingService = new WebScrapingService();
        TransformationDataService transformationDataService = new TransformationDataService();

        try {
            webScrapingService.doWebScraping();
            System.out.println("Processo de Web Scraping concluído!");


            Path pdfPath = Files.list(Paths.get("pdfs_baixados"))
                    .filter(path -> path.toString().contains("Anexo_I"))
                    .findFirst()
                    .orElseThrow(() -> new IOException("Anexo I não encontrado!"));

            System.out.println("Iniciando transformação de dados...");
            transformationDataService.processData();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro:");
            e.printStackTrace();
        }
    }
}

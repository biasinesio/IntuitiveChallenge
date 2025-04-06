package org.example.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class WebScrapingService {
    public void doWebScraping() throws IOException {

        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos"; // Altere para o site desejado
        Document doc = Jsoup.connect(url).get();

        Elements attachmentsLinks = doc.select("a[href$=.pdf]");

        if(attachmentsLinks.isEmpty()){
            System.out.println("Nenhum PDF encontrado!");
            return;
        }

        String downloadDir = "pdfs_baixados";
        Files.createDirectories(Paths.get(downloadDir));

        for (Element link : attachmentsLinks){
            String pdfUrl = link.absUrl("href");
            String fileName =  pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);

            if (fileName.contains("Anexo_I") || fileName.contains("Anexo_II")){
                Path filePath = Paths.get(downloadDir, fileName);
                System.out.println("Baixando: " + pdfUrl);
                downloadFile(pdfUrl, filePath.toString());
            }
        }

        //TODO: Zipar o diretório
        String zipFileName = "pdfs_baixados.zip";
        zipDirectory(downloadDir, zipFileName);
        System.out.println("PDFs compactados em: " + zipFileName);

    }

    private void downloadFile(String fileURL, String savePath) {
        try (InputStream in = new URL(fileURL).openStream();
             FileOutputStream out = new FileOutputStream(savePath)){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1){
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.out.println("Erro ao baixar: " + fileURL);
            e.printStackTrace();
        }
    }

    private void zipDirectory(String downloadDir, String zipFileName) {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)){
            File dir = new File(downloadDir);
            for (File file: dir.listFiles()){
                try(FileInputStream fis = new FileInputStream(file)){
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao criar ZIP");
            e.printStackTrace();
        }
    }


}
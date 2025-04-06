package org.example.data;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.ObjectExtractor;
import technology.tabula.PageIterator;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import org.apache.commons.csv.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.zip.*;

public class TransformationDataService {
    public void processData() {
        String pdfDirectory = "pdfs_baixados";
        String anexoIFileName = "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
        String pdfPath = pdfDirectory + "/" + anexoIFileName;

        String csvPath = "Tabela_Extraida.csv";
        String zipPath = "Teste_Ana_Beatriz.zip";

        try {
            extractTableFromPDF(pdfPath, csvPath);
            replaceAbbreviations(csvPath);
            zipFile(csvPath, zipPath);

            System.out.println("Processo concluído! Arquivo ZIP gerado: " + zipPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void extractTableFromPDF(String pdfPath, String csvPath) throws IOException {
        File pdfFile = new File(pdfPath);
        PDDocument document = PDDocument.load(pdfFile);
        ObjectExtractor extractor = new ObjectExtractor(document);
        PageIterator pages = extractor.extract();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            while (pages.hasNext()) {
                Page page = pages.next();
                SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
                List<Table> tables = sea.extract(page);

                for (Table table : tables) {
                    for (List<RectangularTextContainer> row : table.getRows()) {
                        for (RectangularTextContainer cell : row) {
                            csvPrinter.print(cell.getText());
                        }
                        csvPrinter.println();
                    }
                }
            }
        }

        document.close();
        System.out.println("Extração concluída! Dados salvos em " + csvPath);
    }

    private static void replaceAbbreviations(String csvPath) throws IOException {
        Path path = Paths.get(csvPath);
        List<String> lines = Files.readAllLines(path);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            for (String line : lines) {
                line = line.replaceAll("\\bOD\\b", "Odontológica")
                        .replaceAll("\\bAMB\\b", "Ambulatorial");
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("Substituições concluídas!");
    }

    private static void zipFile(String filePath, String zipPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zipOut = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(filePath)) {

            ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
            zipOut.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zipOut.write(buffer, 0, len);
            }
        }
    }
}

package com.progressoft.tools;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class NormalizerImp implements Normalizer {
    private final ArrayList<String[]> data = new ArrayList<>();
    private int colToWorkWithIndex;

    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) throws IOException {
        return dataNormalize(csvPath, destPath, colToStandardize, "_z", this::standardizeData);
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) throws IOException {
        return dataNormalize(csvPath, destPath, colToNormalize, "_mm", this::normalize);
    }

    private ScoringSummary dataNormalize(Path csvPath, Path destPath, String colToCalculate, String postfix, NormalizeMethod normalizeMethod) throws IOException {
        readCSV(csvPath);
        findColumnToWorkWith(colToCalculate);

        ArrayList<BigDecimal> dataToNormalize = extractData();

        ScoringSummaryImp scoringSummary = new ScoringSummaryImp();
        scoringSummary.setData(dataToNormalize);

        String newNormalizedCol = colToCalculate + postfix;
        ArrayList<BigDecimal> calculatedData = normalizeMethod.invoke(dataToNormalize);

        addCalculatedCol(destPath, newNormalizedCol, calculatedData);
        return scoringSummary;

    }

    private void readCSV(Path csvPath) throws IOException {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(csvPath)))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] rowData = row.split(",");
                this.data.add(rowData);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("source file not found");
        }
    }

    public static int findIndex(String[] arrayToSearch, String elementToFind) {
        if (arrayToSearch == null) {
            return -1;
        }
        for (int indexToFind = 0; indexToFind < arrayToSearch.length; indexToFind++) {
            if (arrayToSearch[indexToFind].equals(elementToFind)) {
                return indexToFind;
            }
        }
        return -1;
    }

    private void findColumnToWorkWith(String colToWorkWith) {
        this.colToWorkWithIndex = findIndex(data.get(0), colToWorkWith);
        if (this.colToWorkWithIndex == -1) {
            throw new IllegalArgumentException("column " + colToWorkWith + " not found");
        }
    }

    private ArrayList<BigDecimal> extractData() {
        ArrayList<BigDecimal> dataToWorkWith = new ArrayList<>();
        for (int rowIndex = 1; rowIndex < data.size(); rowIndex++)
            dataToWorkWith.add(BigDecimal.valueOf(Long.parseLong(data.get(rowIndex)[this.colToWorkWithIndex])));
        return dataToWorkWith;
    }

    private void addCalculatedCol(Path destPath, String newCol, ArrayList<BigDecimal> calculatedData) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(String.valueOf(destPath))) {
            StringBuilder csvHeader = new StringBuilder();
            for (int headerColIndex = 0; headerColIndex <= this.colToWorkWithIndex; headerColIndex++) {
                csvHeader.append(data.get(0)[headerColIndex]);
                csvHeader.append(",");
            }
            csvHeader.append(newCol);
            if (data.get(0).length - 1 != this.colToWorkWithIndex) {
                csvHeader.append(",");
                for (int headerColIndex = this.colToWorkWithIndex + 1; headerColIndex < data.get(0).length; headerColIndex++) {
                    csvHeader.append(data.get(0)[headerColIndex]);
                    if (headerColIndex != data.get(0).length - 1)
                        csvHeader.append(",");
                }
            }
            csvHeader.append('\n');

            writer.write(csvHeader.toString());
            StringBuilder csvData = new StringBuilder();
            for (int dataRowIndex = 1; dataRowIndex < data.size(); dataRowIndex++) {
                for (int dataColIndex = 0; dataColIndex <= this.colToWorkWithIndex; dataColIndex++) {
                    csvData.append(data.get(dataRowIndex)[dataColIndex]);
                    csvData.append(",");
                }
                csvData.append(calculatedData.get(dataRowIndex - 1));
                if (data.get(0).length - 1 != this.colToWorkWithIndex) {
                    csvData.append(",");
                    for (int dataColIndex = this.colToWorkWithIndex + 1; dataColIndex < data.get(dataRowIndex).length; dataColIndex++) {
                        csvData.append(data.get(dataRowIndex)[dataColIndex]);
                        if (dataColIndex != data.get(dataRowIndex).length - 1)
                            csvData.append(",");
                    }
                }
                csvData.append('\n');
            }

            writer.write(csvData.toString());

        }
    }

    private ArrayList<BigDecimal> standardizeData(ArrayList<BigDecimal> dataToStandardize) {
        ScoringSummaryImp scoringSummary = new ScoringSummaryImp();
        scoringSummary.setData(dataToStandardize);
        ArrayList<BigDecimal> standardizedData = new ArrayList<>();
        BigDecimal mean = scoringSummary.mean();
        BigDecimal standardDeviation = scoringSummary.standardDeviation();

        for (BigDecimal elementToStandardize : dataToStandardize) {
            standardizedData.add(elementToStandardize.subtract(mean).divide(standardDeviation, RoundingMode.HALF_EVEN));
        }
        return standardizedData;
    }

    private ArrayList<BigDecimal> normalize(ArrayList<BigDecimal> dataToNormalize) {
        ScoringSummaryImp scoringSummary = new ScoringSummaryImp();
        scoringSummary.setData(dataToNormalize);
        ArrayList<BigDecimal> normalizedData = new ArrayList<>();
        BigDecimal max = scoringSummary.max();
        BigDecimal min = scoringSummary.min();

        for (BigDecimal elementToNormalize : dataToNormalize) {
            normalizedData.add((elementToNormalize.subtract(min)).divide(max.subtract(min), RoundingMode.HALF_EVEN));
        }
        return normalizedData;
    }

    @FunctionalInterface
    public interface NormalizeMethod {
        ArrayList<BigDecimal> invoke(ArrayList<BigDecimal> input);
    }
    public static void main(String[] arg) throws IOException {
        NormalizerImp normalizerImp=new NormalizerImp();
        if(arg.length!=4)
            throw new IllegalArgumentException("must be four argument");

        if(arg[3].equals("min-max")){
    normalizerImp.minMaxScaling(Paths.get(arg[0]),Paths.get(arg[1]),arg[2]);
}
else if(arg[3].equals("z-score"))
{
    normalizerImp.zscore(Paths.get(arg[0]),Paths.get(arg[1]),arg[2]);

}
else
            throw new IllegalArgumentException("Invalid NORMALIZATION_METHOD");

    }
}



package com.progressoft.tools;

import java.io.IOException;
import java.nio.file.Path;

public interface Normalizer {

    /**
     * Accepts a <code>csvPath</code> for a CSV file, perform a Z-Score normalization against
     * <code>colToStandardize</code>, then generate the result file with additional scored column to
     * <code>destPath</code>.
     *
     * @param csvPath          path of CSV file to read
     * @param destPath         path to which the scaled CSV file should be written
     * @param colToStandardize the name of the column to normalize
     * @return
     */
    ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) throws IOException;

    /**
     * Accepts a <code>csvPath</code> for a CSV file, perform a Min-Max normalization against
     * <code>colToNormalize</code>, then generate the result file with additional scored column to
     * <code>destPath</code>.
     *
     * @param csvPath          path of CSV file to read
     * @param destPath         path to which the scaled CSV file should be written
     * @param colToNormalize the name of the column to normalize
     * @return
     */
    ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) throws IOException;
}

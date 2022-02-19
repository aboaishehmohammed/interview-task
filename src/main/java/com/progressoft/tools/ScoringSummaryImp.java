package com.progressoft.tools;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class ScoringSummaryImp implements ScoringSummary {
    private ArrayList<BigDecimal> dataForCalculate = new ArrayList<>();
    private BigDecimal sum = new BigDecimal(0);
    private BigDecimal count = new BigDecimal(0);

    private BigDecimal getSum() {
        return sum;
    }

    private void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    private BigDecimal getCount() {
        return count;
    }

    private void setCount(BigDecimal count) {
        this.count = count;
    }

    public void setData(ArrayList<BigDecimal> data) {
        this.dataForCalculate = data;

        for (BigDecimal i : dataForCalculate) {
            sum = sum.add(i);
        }

        this.setSum(sum);
        this.setCount(BigDecimal.valueOf(dataForCalculate.size()));
    }

    @Override
    public BigDecimal mean() {

        return this.sum.divide(this.count, RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal standardDeviation() {
        BigDecimal standardDeviation = new BigDecimal(0);
        BigDecimal mean = mean();
        for (BigDecimal number : dataForCalculate) {
            standardDeviation = standardDeviation.add(number.subtract(mean).pow(2));
        }
        MathContext mathContext = new MathContext(10, RoundingMode.UP);

        return standardDeviation.divide(count, RoundingMode.UP).sqrt(mathContext).setScale(2, RoundingMode.UP);
    }

    @Override
    public BigDecimal variance() {
        BigDecimal variance = standardDeviation().pow(2).setScale(0, RoundingMode.HALF_EVEN);
        return variance.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal median() {
        Collections.sort(dataForCalculate);
        BigDecimal median;

        if (dataForCalculate.size() % 2 == 0) {
            int mid1 = dataForCalculate.size() / 2;
            int mid2 = (dataForCalculate.size() / 2) + 1;
            median = (dataForCalculate.get(mid1).add(dataForCalculate.get(mid2)).divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN));
            return median.setScale(2, RoundingMode.HALF_EVEN);
        }

        int mid = dataForCalculate.size() / 2;
        median = (dataForCalculate.get(mid));
        return median.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal min() {
        return Collections.min(dataForCalculate).setScale(2,RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal max() {
        return Collections.max(dataForCalculate).setScale(2,RoundingMode.HALF_EVEN);
    }


}

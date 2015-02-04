package com.powwau.setilite;

import android.content.Context;
import java.util.Random;

/**
 * 20150201. Initial version created by jorge.
 */
public class SignalData {

    final static Integer MAX_RANDOM = 100;

    public enum LifeProbability {
        NONE(0),
        MINIMAL(50),
        LOW(80),
        SOME(95),
        GOT_IT(99);

        private Integer threshold;
        LifeProbability(Integer threshold) {
            this.threshold = threshold;
        }

        public Integer getThreshold() {
            return threshold;
        }
    }

    String mStarName;
    LifeProbability mProbability;
    static Context mContext;

    SignalData(Context context, String starName) {
        mContext = context;
        mStarName = starName;
        Random random = new Random();
        Integer probability = random.nextInt(MAX_RANDOM);
        if (probability < LifeProbability.MINIMAL.getThreshold()) {
            mProbability = LifeProbability.NONE;
        } else if (probability < LifeProbability.LOW.getThreshold()) {
            mProbability = LifeProbability.MINIMAL;
        } else if (probability < LifeProbability.SOME.getThreshold()) {
            mProbability = LifeProbability.LOW;
        } else if (probability < LifeProbability.GOT_IT.getThreshold()) {
            mProbability = LifeProbability.SOME;
        } else {
            mProbability = LifeProbability.GOT_IT;
        }
    }

    public String getStarName() {
        return mStarName;
    }

    public void setStarName(String starName) {
        mStarName = starName;
    }

    public LifeProbability getProbability() {
        return mProbability;
    }

    public void setProbability(LifeProbability probability) {
        mProbability = probability;
    }

    public String getProbabilityString() {
        String probabilityString = "";
        switch (mProbability) {
            case NONE:
                probabilityString = mContext.getString(R.string.probability_none);
                break;
            case MINIMAL:
                probabilityString = mContext.getString(R.string.probability_minimal);
                break;
            case LOW:
                probabilityString = mContext.getString(R.string.probability_low);
                break;
            case SOME:
                probabilityString = mContext.getString(R.string.probability_some);
                break;
            case GOT_IT:
                probabilityString = mContext.getString(R.string.probability_got_it);
                break;
        }
        return probabilityString;
    }

    @Override
    public String toString() {
        return String.format(mContext.getString(R.string.signal_data_string), mStarName, getProbabilityString());
    }
}

package com.example.ruzun.ejarahtest;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ModelAdapter {

    final String cat[] = {"advice/question", "animals", "anime/manga", "appearance", "arts", "autos", "board_game",
            "books", "card_game", "company/website", "crypto", "drugs", "education", "electronics", "finance/money", "food/drink",
            "geo", "hardware/tools", "health", "hobby", "meta", "movies", "music", "other", "parenting", "politics/viewpoint", "profession",
            "programming", "religion/supernatural", "rpg", "sex/relationships", "social_group", "software", "sports", "stem",
            "travel", "tv_show", "video_game", "writing/stories"};
    JSONObject obj = null;
    Context context;

    ModelAdapter(Context context){
        this.context = context;

        String str = loadJSONFromAsset(context,"word_dict2.json");
        try {
            obj = new JSONObject(str);
            Log.i("RESULT OF JSON", obj.get("book") + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String Tokenizing(String input_string) {
        float[][] in = new float[1][30000];

        String[] tokens = input_string.split(" ");
        for (String i : tokens) {
            try {
                int index = (int) obj.get(i);
                if (index < 30000)
                    in[0][index] = 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        int maxIndex = 23;
        float maxValue = Float.MIN_VALUE;
        try {
            float output[][] = predeict(in);
            for (int i = 0; i < output[0].length; i++) {
                if (output[0][i] > maxValue) {
                    maxValue = output[0][i];
                    maxIndex = i;
                }
            }

            return cat[maxIndex];
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return cat[maxIndex];
    }


    public String preprocessing(String str) throws JSONException {
        String preprocessedString = "";
        preprocessedString = str.trim();
        preprocessedString = preprocessedString.toLowerCase();
        preprocessedString = preprocessedString.replaceAll("[0-9]", "");
        preprocessedString = preprocessedString.replaceAll("[^A-za-z ]", "");
        preprocessedString = lemmatize(preprocessedString);


        return preprocessedString;
    }

    public String lemmatize(String inputString) throws JSONException {


        String lemmatizedString = "";
        String [] tokens = inputString.split(" ");
        for (int j=0; j<tokens.length; j++)
        {
            for (int i=tokens[j].length(); i>=1; i--)
            {
                String subToken = tokens[j].substring(0,i);

                if (!obj.isNull(subToken))
                {
                    tokens[j]=subToken;
                    break;
                }
            }
            lemmatizedString = lemmatizedString +" "+tokens[j];
        }

        Log.i("LEMMM", lemmatizedString);
        return lemmatizedString;
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("converted_model1.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    protected Interpreter tflite;

    public float[][] predeict(float [][]seq) throws IOException {
        tflite = new Interpreter(loadModelFile());
        float input [][]= seq;
        float output [][] = new float [1] [39];
        tflite.run(input, output);
        return output;
    }

    public String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}

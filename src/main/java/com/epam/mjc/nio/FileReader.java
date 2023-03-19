package com.epam.mjc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        StringBuilder stringBuilder = new StringBuilder();

        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                stringBuilder.append((char) byteBuffer.get());
            }

            String[][] array = makeStringToMap(stringBuilder.toString().split("\n"));
            for (int i = 0; i < array.length; i++) {
                if (array[i][0].equals("Name")) {
                    profile.setName(array[i][1]);
                }
                if (array[i][0].equals("Age")) {
                    profile.setAge(Integer.parseInt(array[i][1]));
                }
                if (array[i][0].equals("Email")) {
                    profile.setEmail(array[i][1]);
                }
                if (array[i][0].equals("Phone")) {
                    profile.setPhone(Long.parseLong(array[i][1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public String[][] makeStringToMap(String[] arr) {
        String[][] array = new String[arr.length][2];
        int counter = 0;
        String str;
        for (int i = 0; i < array.length; i++) {
            str = arr[i];
            for (String s : str.split(": ")) {
                array[i][counter++] = s;
            }
            counter = 0;
        }
        return array;
    }
}
